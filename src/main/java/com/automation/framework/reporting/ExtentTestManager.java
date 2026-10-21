package com.automation.framework.reporting;

import com.automation.framework.config.Config;
import com.aventstack.extentreports.ExtentTest;
import org.testng.ITestResult;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Asocia cada resultado de TestNG con su nodo del reporte. El nodo se guarda en el propio
 * resultado (seguro entre callbacks del listener) y en un ThreadLocal (para que los page objects
 * puedan registrar pasos).
 */
public final class ExtentTestManager {

    private static final String RESULT_ATTRIBUTE = "extentTest";
    private static final ThreadLocal<ExtentTest> CURRENT = new ThreadLocal<>();

    private ExtentTestManager() {
    }

    public static ExtentTest start(ITestResult result) {
        ExtentTest test = ExtentManager.getInstance()
                .createTest(displayName(result), result.getMethod().getDescription());
        test.assignCategory(result.getTestClass().getRealClass().getSimpleName());
        test.assignCategory(result.getMethod().getGroups());
        test.assignDevice(Config.browser().name());
        result.setAttribute(RESULT_ATTRIBUTE, test);
        CURRENT.set(test);
        return test;
    }

    /** Devuelve el nodo de este resultado y lo crea si TestNG saltó el test antes de que empezara. */
    public static ExtentTest forResult(ITestResult result) {
        Object test = result.getAttribute(RESULT_ATTRIBUTE);
        return test instanceof ExtentTest extentTest ? extentTest : start(result);
    }

    /** Registra un paso info en el test del hilo actual; fuera de un test (p. ej. setup) no hace nada. */
    public static void info(String message) {
        ExtentTest test = CURRENT.get();
        if (test != null) {
            test.info(message);
        }
    }

    public static void clear() {
        CURRENT.remove();
    }

    private static String displayName(ITestResult result) {
        String name = result.getMethod().getMethodName();
        Object[] parameters = result.getParameters();
        if (parameters.length == 0) {
            return name;
        }
        return name + Arrays.stream(parameters)
                .map(String::valueOf)
                .collect(Collectors.joining(", ", " [", "]"));
    }
}
