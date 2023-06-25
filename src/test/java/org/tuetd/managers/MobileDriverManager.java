package org.tuetd.managers;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.IOSMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.SessionId;
import org.tuetd.enums.Profile;
import org.tuetd.models.Capabilities;
import org.tuetd.utils.PropertyUtils;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class MobileDriverManager {
    private static final ThreadLocal<AppiumDriver> mobileDrivers = new ThreadLocal<>();

    public static synchronized AppiumDriver getMobileDriver() {
        return mobileDrivers.get();
    }

    public static synchronized void setMobileDrivers(AppiumDriver driver) {
        mobileDrivers.set(driver);
    }

    public static synchronized Boolean doesDriverExist() {
        return getMobileDriver() != null;
    }

    public static synchronized Boolean isDriverSessionActive() {
        return getDriverSessionId() != null;
    }

    private static synchronized SessionId getDriverSessionId() {
        SessionId sessionId = null;

        if (doesDriverExist()) {
            sessionId = MobileDriverManager.getMobileDriver().getSessionId();
        }

        return sessionId;
    }

    public static IOSDriver createIOSDriver(Capabilities capabilities) {
        LoggingManager.logInfo(MobileDriverManager.class, "=== Creating new iOS driver ===");

        IOSDriver driver = null;
        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();

        desiredCapabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, capabilities.getPlatformVersion());
        desiredCapabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, capabilities.getPlatformName());
        desiredCapabilities.setCapability(MobileCapabilityType.DEVICE_NAME, capabilities.getDeviceName());
        desiredCapabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, capabilities.getAutomationName());
        desiredCapabilities.setCapability(MobileCapabilityType.APP, getAppAbsolutePath("ios" + File.separator + capabilities.getAppName()));

        desiredCapabilities.setCapability(IOSMobileCapabilityType.WDA_LAUNCH_TIMEOUT, 500000);

        try {
            driver = new IOSDriver(new URL(capabilities.getAppiumServerUrl() + ":" + capabilities.getAppiumPort() + "/wd/hub"), desiredCapabilities);
        } catch (MalformedURLException exception) {
            LoggingManager.logError(MobileDriverManager.class, "Error when creating iOS driver", exception);
        }

        return driver;
    }

    public static AndroidDriver createAndroidDriver(Capabilities capabilities) {
        LoggingManager.logInfo(MobileDriverManager.class, "=== Creating new Android driver ===");

        AndroidDriver driver = null;
        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();

        desiredCapabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, capabilities.getPlatformName());
        desiredCapabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, capabilities.getPlatformVersion());
        desiredCapabilities.setCapability(MobileCapabilityType.DEVICE_NAME, capabilities.getDeviceName());
        desiredCapabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, capabilities.getAutomationName());
        desiredCapabilities.setCapability(MobileCapabilityType.APP, getAppAbsolutePath("android" + File.separator + capabilities.getAppName()));

        try {
            driver = new AndroidDriver(new URL(capabilities.getAppiumServerUrl() + ":" + capabilities.getAppiumPort() + "/wd/hub"), desiredCapabilities);
        } catch (MalformedURLException exception) {
            LoggingManager.logError(MobileDriverManager.class, "Error when creating Android driver", exception);
        }

        return driver;
    }

    public static IOSDriver createBrowserStackIOSDriver(Capabilities capabilities) {
        LoggingManager.logInfo(MobileDriverManager.class, "=====> Creating new BrowserStack iOS driver ===");

        IOSDriver driver = null;
        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();

        desiredCapabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, capabilities.getPlatformVersion());
        desiredCapabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, capabilities.getPlatformName());
        desiredCapabilities.setCapability(MobileCapabilityType.DEVICE_NAME, capabilities.getDeviceName());
        desiredCapabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, capabilities.getAutomationName());
        desiredCapabilities.setCapability(MobileCapabilityType.APP, capabilities.getAppName());

        desiredCapabilities.setCapability(IOSMobileCapabilityType.WDA_LAUNCH_TIMEOUT, 500000);

        try {
            driver = new IOSDriver(new URL("http://" + capabilities.getBrowserStackUsername() + ":" + capabilities.getBrowserStackAccessKey() + "@" + capabilities.getAppiumServerUrl() + "/wd/hub"), desiredCapabilities);
        } catch (MalformedURLException exception) {
            LoggingManager.logError(MobileDriverManager.class, "Error when creating BrowserStack iOS driver", exception);
        }

        return driver;
    }

    public static void createMobileDriver() {
        if (PropertyUtils.isProfile(Profile.LOCAL_IOS)) {
            Capabilities capabilities = new Capabilities(Profile.LOCAL_IOS.name);
            IOSDriver driver = createIOSDriver(capabilities);
            setMobileDrivers(driver);
        } else if (PropertyUtils.isProfile(Profile.LOCAL_ANDROID)) {
            Capabilities capabilities = new Capabilities(Profile.LOCAL_ANDROID.name);
            AndroidDriver driver = createAndroidDriver(capabilities);
            setMobileDrivers(driver);
        } else if (PropertyUtils.isProfile(Profile.BROWSERSTACK_IOS)) {
            Capabilities capabilities = new Capabilities(Profile.BROWSERSTACK_IOS.name);
            IOSDriver driver = createBrowserStackIOSDriver(capabilities);
            setMobileDrivers(driver);
        }
    }

    private static String getAppAbsolutePath(String appName) {
        return System.getProperty("user.dir") +
                File.separator + "src" +
                File.separator + "test" +
                File.separator + "resources" +
                File.separator + "apps" +
                File.separator + appName;
    }

    public static void tearDownDriver() {
        AppiumDriver driver = getMobileDriver();

        if (driver != null) {
            driver.quit();
        }
    }

    public static File getScreenshot() {
        File screenshotFile;

        try {
            AppiumDriver appiumDriver = getMobileDriver();
            File screenshotSource = ((TakesScreenshot) appiumDriver).getScreenshotAs(OutputType.FILE);

            String screenshotFilePath = System.getProperty("user.dir") + File.separator;
            screenshotFilePath += "test-output" + File.separator;
            screenshotFilePath += "extent-reports" + File.separator;
            screenshotFilePath += "screenshots" + File.separator;

            String reportName = "Appium Test Report";
            screenshotFilePath += reportName + "_";
            screenshotFilePath += System.currentTimeMillis() + ".png";

            screenshotFile = new File(screenshotFilePath);
            org.apache.commons.io.FileUtils.copyFile(screenshotSource, screenshotFile);

        } catch (IOException exception) {
            throw new RuntimeException("Unable to take screenshot", exception);
        }

        return screenshotFile;
    }
}
