package org.tuetd.pages;

import io.appium.java_client.pagefactory.AndroidBy;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.tuetd.utils.Element;

public class ExamplePage extends BasePage {

    @AndroidFindBy(id = "com.applitools.helloworld.android:id/random_number_check_box")
    WebElement firstCheckbox;

    @AndroidFindBy(id = "com.applitools.helloworld.android:id/simulate_diffs_check_box")
    WebElement secondCheckbox;

    @AndroidFindBy(id = "com.applitools.helloworld.android:id/click_me_btn")
    WebElement clickMeBtn;

    @AndroidFindBy(id = "com.applitools.helloworld.android:id/image")
    WebElement image;

    Element firstCkbElement = new Element(firstCheckbox);
    Element secondCkbElement = new Element(secondCheckbox);
    Element clickMeBtnElement = new Element(clickMeBtn);

    public void clickFirstCheckbox() {
        firstCkbElement.click(getClass(), "Unable to click to First check box");
    }

    public void clickSecondCheckbox() {
        secondCkbElement.click(getClass(), "Unable to click to 2nd check box");
    }

    public void clickClickMeButton() {
        clickMeBtnElement.click(getClass(), "Unable to click to Click Me button");
    }

    public boolean isImageDisplayed() {
        return image.isDisplayed();
    }
}
