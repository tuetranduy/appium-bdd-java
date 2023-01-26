package org.tuetd.steps;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import org.tuetd.managers.LoggingManager;
import org.tuetd.managers.MobileDriverManager;

public class CommonSteps {

    @Before
    public static void setUp() {
        setupMobileDriver();
    }

    @After
    public static void tearDown() {
        tearDownMobileDriver();
    }

    public static void setupMobileDriver() {
        if (!MobileDriverManager.isDriverSessionActive()) {
            MobileDriverManager.createMobileDriver();
            LoggingManager.logInfo(CommonSteps.class, "Opened an application");
        }
    }

    public static void tearDownMobileDriver() {
        if (MobileDriverManager.isDriverSessionActive()) {
            MobileDriverManager.tearDownDriver();
        }
    }
}

