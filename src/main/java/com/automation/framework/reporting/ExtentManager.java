package com.automation.framework.reporting;

import com.automation.framework.config.Config;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import java.nio.file.Path;

/**
 * Una única instancia de ExtentReports para toda la ejecución. Las capturas se incrustan en
 * Base64, así que el HTML generado es autocontenido y se puede compartir o subir como artefacto
 * de CI.
 */
public final class ExtentManager {

    public static final Path REPORT_FILE = Path.of("target", "extent-report", "index.html");

    private static ExtentReports extent;

    private ExtentManager() {
    }

    public static synchronized ExtentReports getInstance() {
        if (extent == null) {
            ExtentSparkReporter spark = new ExtentSparkReporter(REPORT_FILE.toString());
            spark.config().setDocumentTitle("Web Automation Report");
            spark.config().setReportName("Selenium Java - SauceDemo & The Internet");
            spark.config().setTheme(Theme.STANDARD);
            spark.config().setTimeStampFormat("yyyy-MM-dd HH:mm:ss");

            extent = new ExtentReports();
            extent.attachReporter(spark);
            extent.setSystemInfo("Browser", Config.browser().name());
            extent.setSystemInfo("Headless", String.valueOf(Config.headless()));
            extent.setSystemInfo("OS", System.getProperty("os.name"));
            extent.setSystemInfo("Java", System.getProperty("java.version"));
        }
        return extent;
    }

    public static synchronized void flush() {
        if (extent != null) {
            extent.flush();
        }
    }
}
