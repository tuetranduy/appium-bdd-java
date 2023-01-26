package org.tuetd.steps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.tuetd.pages.ExamplePage;

public class ExampleSteps {

    @Given("Click Press to make the number below random")
    public void clickFirstCheckbox() {
        ExamplePage examplePage = new ExamplePage();

        examplePage.clickFirstCheckbox();
    }

    @When("Click Press to simulate additional differences")
    public void clickSecondCheckbox() {
        ExamplePage examplePage = new ExamplePage();

        examplePage.clickSecondCheckbox();
    }

    @And("Click ClickMe button")
    public void clickClickMeButton() {
        ExamplePage examplePage = new ExamplePage();

        examplePage.clickClickMeButton();
    }

    @Then("Image should show up")
    public void verifyImageShowedUp() {
        ExamplePage examplePage = new ExamplePage();

        Assert.assertTrue(examplePage.isImageDisplayed());
    }

}
