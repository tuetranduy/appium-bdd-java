package org.tuetd.steps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.tuetd.managers.LoggingManager;
import org.tuetd.pages.ExamplePage;
import org.tuetd.utils.AssertionUtils;

public class ExampleSteps {

    @Given("Click Press to make the number below random")
    public void clickFirstCheckbox() {

        LoggingManager.logGiven(getClass(), "Click Press to make the number below random");

        ExamplePage examplePage = new ExamplePage();

        examplePage.clickFirstCheckbox();
    }

    @When("Click Press to simulate additional differences")
    public void clickSecondCheckbox() {

        LoggingManager.logWhen(getClass(), "Click Press to simulate additional differences");

        ExamplePage examplePage = new ExamplePage();

        examplePage.clickSecondCheckbox();
    }

    @And("Click ClickMe button")
    public void clickClickMeButton() {

        LoggingManager.logAnd(getClass(), "Click ClickMe button");

        ExamplePage examplePage = new ExamplePage();

        examplePage.clickClickMeButton();
    }

    @Then("Image should show up")
    public void verifyImageShowedUp() {

        LoggingManager.logThen(getClass(), "Image should show up");

        ExamplePage examplePage = new ExamplePage();

        AssertionUtils.assertTrue(getClass(), examplePage.isImageDisplayed(), "Image should be displayed");
    }

}
