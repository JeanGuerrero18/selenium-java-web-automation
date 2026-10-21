package com.automation.framework.listeners;

import com.automation.framework.driver.DriverManager;
import com.automation.framework.reporting.ExtentManager;
import com.automation.framework.reporting.ExtentTestManager;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriverException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.ITestListener;
import org.testng.ITestResult;

/**
 * Alimenta el reporte de Extent a partir de los eventos de TestNG.
 *
 * <p>La captura del fallo se toma en {@link #afterInvocation}, que corre justo después del
 * método de test y antes de cualquier {@code @AfterMethod}; así se garantiza que el navegador
 * sigue abierto.
 */
public class TestListener implements ITestListener, ISuiteListener, IInvokedMethodListener {

    private static final Logger LOG = LoggerFactory.getLogger(TestListener.class);

    @Override
    public void onTestStart(ITestResult result) {
        LOG.info("START  {}", qualifiedName(result));
        ExtentTestManager.start(result);
    }

    @Override
    public void afterInvocation(IInvokedMethod method, ITestResult result) {
        if (method.isTestMethod() && result.getStatus() == ITestResult.FAILURE) {
            attachScreenshot(ExtentTestManager.forResult(result));
        }
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        LOG.info("PASS   {}", qualifiedName(result));
        ExtentTestManager.forResult(result).pass("Test passed");
        ExtentTestManager.clear();
    }

    @Override
    public void onTestFailure(ITestResult result) {
        String message = String.valueOf(result.getThrowable().getMessage()).strip();
        LOG.error("FAIL   {}: {}", qualifiedName(result), message);
        ExtentTestManager.forResult(result).fail(result.getThrowable());
        ExtentTestManager.clear();
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        LOG.warn("SKIP   {}", qualifiedName(result));
        ExtentTest test = ExtentTestManager.forResult(result);
        if (result.getThrowable() != null) {
            test.skip(result.getThrowable());
        } else {
            test.skip("Test skipped");
        }
        ExtentTestManager.clear();
    }

    @Override
    public void onFinish(ISuite suite) {
        ExtentManager.flush();
        LOG.info("Extent report written to {}", ExtentManager.REPORT_FILE.toAbsolutePath());
    }

    private static void attachScreenshot(ExtentTest test) {
        if (!DriverManager.hasDriver()) {
            return;
        }
        try {
            String base64 = ((TakesScreenshot) DriverManager.getDriver()).getScreenshotAs(OutputType.BASE64);
            test.fail("Screenshot at failure",
                    MediaEntityBuilder.createScreenCaptureFromBase64String(base64).build());
        } catch (WebDriverException e) {
            // Un navegador caído no debe ocultar el fallo original.
            LOG.warn("Could not capture screenshot: {}", e.getMessage());
        }
    }

    private static String qualifiedName(ITestResult result) {
        String className = result.getTestClass().getRealClass().getSimpleName();
        return className + "." + result.getMethod().getMethodName();
    }
}
