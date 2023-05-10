package org.tuetd.runner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.tuetd.managers.ReportManager;

@CucumberOptions(
        features = "src/test/resources/features",
        glue = "org/tuetd/steps",
        monochrome = true)
public class CucumberTest extends AbstractTestNGCucumberTests {

    @Override
    @DataProvider()
    public Object[][] scenarios() {
        return super.scenarios();
    }

    @BeforeClass(alwaysRun = true)
    protected void setUpClass() {
        ReportManager.createHtmlReporter();
        ReportManager.createBddReport();
    }

    @AfterClass(alwaysRun = true)
    public void tearDownClass() {
        ReportManager.generateReport();
    }
}