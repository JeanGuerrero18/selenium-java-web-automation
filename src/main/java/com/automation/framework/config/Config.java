package com.automation.framework.config;

import com.automation.framework.driver.BrowserType;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.time.Duration;
import java.util.Locale;
import java.util.Properties;

/**
 * Acceso centralizado a la configuración de ejecución.
 *
 * <p>Orden de resolución de cada clave (gana el primer valor no vacío):
 * <ol>
 *   <li>Propiedad de sistema de la JVM, p. ej. {@code -Dbrowser=firefox}</li>
 *   <li>Variable de entorno en mayúsculas, con guiones bajos en vez de puntos, p. ej.
 *       {@code SAUCEDEMO_PASSWORD}</li>
 *   <li>{@code config.properties} en el classpath de test</li>
 * </ol>
 * Así CI puede inyectar secretos y sobrescribir valores sin tocar archivos versionados.
 */
public final class Config {

    private static final String CONFIG_FILE = "config.properties";
    private static final Properties PROPERTIES = load();

    private Config() {
    }

    public static String get(String key) {
        String systemValue = System.getProperty(key);
        if (isPresent(systemValue)) {
            return systemValue.trim();
        }
        String envValue = System.getenv(key.toUpperCase(Locale.ROOT).replace('.', '_'));
        if (isPresent(envValue)) {
            return envValue.trim();
        }
        String fileValue = PROPERTIES.getProperty(key);
        if (!isPresent(fileValue)) {
            throw new IllegalStateException("Missing configuration value for key '" + key + "'");
        }
        return fileValue.trim();
    }

    public static int getInt(String key) {
        return Integer.parseInt(get(key));
    }

    public static boolean getBoolean(String key) {
        return Boolean.parseBoolean(get(key));
    }

    public static BrowserType browser() {
        return BrowserType.from(get("browser"));
    }

    public static boolean headless() {
        return getBoolean("headless");
    }

    public static Duration timeout() {
        return Duration.ofSeconds(getInt("timeout.seconds"));
    }

    private static boolean isPresent(String value) {
        return value != null && !value.isBlank();
    }

    private static Properties load() {
        Properties properties = new Properties();
        try (InputStream in = Config.class.getClassLoader().getResourceAsStream(CONFIG_FILE)) {
            if (in == null) {
                throw new IllegalStateException(CONFIG_FILE + " not found on the classpath");
            }
            properties.load(in);
        } catch (IOException e) {
            throw new UncheckedIOException("Could not read " + CONFIG_FILE, e);
        }
        return properties;
    }
}
