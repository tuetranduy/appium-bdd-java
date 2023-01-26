package org.tuetd.utils;


import org.openqa.selenium.support.ui.WebDriverWait;
import org.tuetd.managers.LoggingManager;
import org.tuetd.managers.MobileDriverManager;

import java.time.Duration;

public class WaitUtils {

    private static final double WAIT_INTERVAL = 1;
    private static final long WAIT_INTERVAL_MS = (long) (WAIT_INTERVAL * 1000);
    private static final long WAIT_TIME = 20;

    public static WebDriverWait waitDefault() {
        return new WebDriverWait(MobileDriverManager.getMobileDriver(), Duration.ofSeconds(WAIT_TIME), Duration.ofMillis(WAIT_INTERVAL_MS));
    }

    public static void sleep(Class<?> className, int seconds) {
        try {
            seconds = seconds * 1000;
            Thread.sleep(seconds);
        } catch (InterruptedException exception) {
            LoggingManager.logError(className, "Error during perform request", exception);
        }
    }

}