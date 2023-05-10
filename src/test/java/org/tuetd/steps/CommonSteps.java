package org.tuetd.steps;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.Status;
import org.testng.Reporter;
import org.tuetd.managers.LoggingManager;
import org.tuetd.managers.MobileDriverManager;
import org.tuetd.managers.ReportManager;

import java.util.Collection;

public class CommonSteps {

    @Before
    public static void setUp(Scenario scenario) {
        setUpScenario(scenario);
        setupMobileDriver();
    }

    @After
    public static void tearDown(Scenario scenario) {
        tearDownMobileDriver();
        tearDownScenario(scenario);
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

    protected static void setUpScenario(Scenario scenario) {
        logFeatureAndScenario(scenario);
        setScenarioTags(scenario);
    }

    protected static void tearDownScenario(Scenario scenario) {
        logScenarioStatus(scenario);
    }

    private static Collection<String> getScenarioTags(Scenario scenario) {
        return scenario.getSourceTagNames();
    }

    private static void setScenarioTags(Scenario scenario) {
        ReportManager.setScenarioTags(getScenarioTags(scenario));
    }

    private static String getCurrentFeatureName() {
        String featureName = null;

        try {
            Object[] currentTestParameters = Reporter.getCurrentTestResult().getParameters();
            featureName = currentTestParameters[1].toString().replaceAll("(^\"Optional\\[|\\]\"$)", "");

        } catch (Exception exception) {
            LoggingManager.logError(CommonSteps.class, "Unable to parse feature (check for syntax errors)", exception);
        }

        return featureName;
    }

    private static void logFeatureAndScenario(Scenario scenario) {
        String featureName = getCurrentFeatureName();
        String scenarioName = scenario.getName();
        LoggingManager.logDebug(CommonSteps.class, featureName + " > " + scenarioName);

        ReportManager.createFeature(featureName);
        ReportManager.createScenario(scenarioName);
    }

    private static void logScenarioStatus(Scenario scenario) {
        String featureName = ReportManager.getCurrentFeatureName();
        String scenarioName = scenario.getName();
        String scenarioStatus = scenario.getStatus().toString();

        if (scenarioPassed(scenario)) {
            LoggingManager.logDebug(CommonSteps.class, featureName + " > " + scenarioName + " > " + scenarioStatus);

        } else if (scenarioFailed(scenario)) {
            LoggingManager.logFail(CommonSteps.class, featureName + " > " + scenarioName + " > " + scenarioStatus);

        }
    }

    protected static Boolean scenarioPassed(Scenario scenario) {
        return scenario.getStatus() == Status.PASSED;
    }

    protected static Boolean scenarioFailed(Scenario scenario) {
        return scenario.getStatus() == Status.FAILED;
    }
}

