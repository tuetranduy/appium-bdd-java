package org.tuetd.utils;

import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.tuetd.managers.LoggingManager;

public class Element {

    private WebElement webElement;

    public Element(WebElement webElement) {
        this.webElement = webElement;
    }

    public void click(Class<?> className, String errorMessage) {
        try {
            WaitUtils.waitDefault().ignoring(StaleElementReferenceException.class).until(ExpectedConditions.visibilityOf(webElement));
            WaitUtils.waitDefault().ignoring(StaleElementReferenceException.class).until(ExpectedConditions.elementToBeClickable(webElement));
            webElement.click();
        } catch (Exception exception) {
            LoggingManager.logError(className, errorMessage, exception);
        }
    }

    public void waitUntilClickable(Class<?> className, String errorMessage) {
        try {
            WaitUtils.waitDefault().ignoring(StaleElementReferenceException.class).until(ExpectedConditions.elementToBeClickable(webElement));

        } catch (Exception exception) {
            LoggingManager.logError(className, errorMessage, exception);
        }
    }

    public void waitUntilVisible(Class<?> className, String errorMessage) {
        try {
            WaitUtils.waitDefault().ignoring(StaleElementReferenceException.class).until(ExpectedConditions.visibilityOf(webElement));

        } catch (Exception exception) {
            LoggingManager.logError(className, errorMessage, exception);
        }
    }
}
