# Selenium Java – Automatización web

Framework de automatización UI con **Selenium 4 + Java 17 + TestNG + ExtentReports**, que prueba dos sitios públicos, cada uno en su propio flujo:

| Sitio | Qué cubre | Paquete |
|---|---|---|
| [SauceDemo](https://www.saucedemo.com/) | Flujos E2E de negocio: login, catálogo, carrito, checkout | `com.automation.saucedemo` |
| [The Internet](https://the-internet.herokuapp.com/) | Técnicas de Selenium aisladas: alerts, ventanas, frames, uploads, hovers, tablas, esperas | `com.automation.theinternet` |

## Requisitos

- JDK 17+ con `JAVA_HOME` apuntando a él
- Maven 3.9+
- Chrome, Firefox y/o Edge instalados (los drivers los descarga **Selenium Manager** automáticamente)

## Ejecución

```bash
mvn test                                                   # regresión completa (testng.xml), Chrome visible
mvn test -Dheadless=true                                   # sin ventana
mvn test -Dbrowser=firefox                                 # chrome | firefox | edge
mvn test -Dsuite=src/test/resources/suites/smoke.xml       # solo grupo smoke
mvn test -Dsuite=src/test/resources/suites/saucedemo.xml   # solo SauceDemo
mvn test -Dsuite=src/test/resources/suites/theinternet.xml # solo The Internet
mvn test -Dtest=CheckoutTests                              # una clase (ignora el XML de suite)
mvn checkstyle:check                                       # lint (debe quedar en 0 violaciones)
```

Reporte: `target/extent-report/index.html` (HTML autocontenido; las capturas de fallos van incrustadas en Base64).

## Estructura

```
src/main/java/com/automation/framework   ← núcleo reutilizable, sin nada de un sitio concreto
  base/BaseTest          navegador nuevo por test (@BeforeMethod / @AfterMethod)
  config/Config          propiedades: -D > variable de entorno > config.properties
  driver/                DriverFactory (Chrome/Firefox/Edge) + DriverManager (ThreadLocal)
  listeners/TestListener eventos TestNG → Extent, captura en fallo, flush al final
  pages/BasePage         esperas explícitas y helpers comunes
  reporting/             ExtentManager (instancia única) + ExtentTestManager (nodo por test)
src/test/java/com/automation/<sitio>
  pages/                 Page Objects (y componentes, p. ej. HeaderComponent)
  tests/                 clases de test
  data/                  datos de prueba
src/test/resources
  config.properties, logback-test.xml, suites/*.xml, files/
```

## Buenas prácticas aplicadas

- **Page Object Model** con métodos fluidos que devuelven la siguiente página; los tests no contienen locators.
- **Solo esperas explícitas** (`WebDriverWait`); nunca `implicitlyWait` ni `Thread.sleep`.
- **Un navegador por test** y **driver por hilo** (`ThreadLocal`) → tests independientes y paralelizables. El driver nunca se guarda en un campo de instancia: con `parallel="methods"` los métodos de una misma instancia corren a la vez.
- **Locators estables**: ids y clases semánticas; productos y filas de tabla se buscan por texto visible, no por posición ni ids generados.
- **Data-driven** con `@DataProvider` para los casos negativos.
- **Grupos** `smoke` / `regression` para elegir qué ejecutar.
- **Configuración externa**: nada fijo en el código; todo se puede sobrescribir con `-D` o variables de entorno (p. ej. `SAUCEDEMO_PASSWORD`). Las credenciales del repo son las demo públicas que cada sitio muestra; en una aplicación real van en secretos de CI.
- **Los logs no muestran contraseñas** (solo el usuario de prueba).
- **Reporte con pasos de negocio**: cada acción de página se registra como paso en Extent y en el log (con nombre de hilo).
- **Lint con Checkstyle** a 0 violaciones (`config/checkstyle/checkstyle.xml`: 4 espacios, 110 columnas, imports, nombres, llaves y errores habituales) como paso previo del CI.

## Notas técnicas

- **Checkstyle fijado en 12.x.** Desde la 13.0.0 requiere Java 21; con Java 17 el plugin falla con `UnsupportedClassVersionError`.
- **Codificación UTF-8 en los tests.** Surefire arranca la JVM con `-Dfile.encoding=UTF-8` porque Extent escribe el HTML con el charset por defecto (Cp1252 en Windows con JDK 17) y las tildes quedarían corruptas. Si ejecutas desde el IDE y ves `�` en el reporte, agrega esa misma opción a la run configuration.
- **Surefire fijado en 3.5.x.** La versión 3.6.0 eliminó el proveedor nativo de TestNG (ahora lo ejecuta vía JUnit Platform) e ignora `suiteXmlFiles`, con lo que el paralelismo definido en los XML deja de aplicarse sin dar error.
- La advertencia `se-metadata.json cannot be parsed` al arrancar en paralelo es inocua: varios hilos escriben a la vez la caché de Selenium Manager y este vuelve a resolver el driver.

## CI

`.github/workflows/ui-tests.yml`: primero un job de lint (Checkstyle); luego ejecuta la suite en headless con una matriz Chrome / Firefox / Edge en `ubuntu-latest` y publica el reporte de Extent de cada navegador como artefacto, incluso si hay fallos. Se puede lanzar manualmente eligiendo el XML de suite.
