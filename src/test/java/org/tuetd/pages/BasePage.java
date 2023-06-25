package org.tuetd.pages;

import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.support.PageFactory;
import org.tuetd.managers.MobileDriverManager;

public class BasePage {

    public BasePage() {
        initializePageElements();
    }

    private void initializePageElements() {
        PageFactory.initElements(new AppiumFieldDecorator(MobileDriverManager.getMobileDriver()), this);
    }
}
