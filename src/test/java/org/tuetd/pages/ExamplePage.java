package org.tuetd.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ExamplePage extends BasePage {

    @FindBy(id = "com.applitools.helloworld.android:id/random_number_check_box")
    WebElement firstCheckbox;

    @FindBy(id = "com.applitools.helloworld.android:id/simulate_diffs_check_box")
    WebElement secondCheckbox;

    @FindBy(id = "com.applitools.helloworld.android:id/click_me_btn")
    WebElement clickMeBtn;

    @FindBy(id = "com.applitools.helloworld.android:id/image")
    WebElement image;

    public void clickFirstCheckbox() {
        click(firstCheckbox, "Unable to click to First check box");
    }

    public void clickSecondCheckbox() {
        click(secondCheckbox, "Unable to click to 2nd check box");
    }

    public void clickClickMeButton() {
        click(clickMeBtn, "Unable to click to Click Me button");
    }

    public boolean isImageDisplayed() {
        return image.isDisplayed();
    }
}
