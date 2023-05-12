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
import org.tuetd.enums.Platform;
import org.tuetd.utils.Constants;
import org.tuetd.utils.PropertyUtils;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class MobileDriverManager {

    private static final String PLATFORM_VERSION = PropertyUtils.getProperty(Constants.PLATFORM_VERSION_KEY);
    private static final String PLATFORM_NAME = PropertyUtils.getProperty(Constants.PLATFORM_NAME_KEY);
    private static final String DEVICE_NAME = PropertyUtils.getProperty(Constants.DEVICE_NAME_KEY);
    private static final String APP_NAME = PropertyUtils.getProperty(Constants.APP_NAME);
    private static final String AUTOMATION_NAME = PropertyUtils.getProperty(Constants.AUTOMATION_NAME);
    private static final String BS_ACCESS_KEY = PropertyUtils.getProperty(Constants.BS_ACCESS_KEY);
    private static final String BS_USERNAME = PropertyUtils.getProperty(Constants.BS_USERNAME);
    private static final String BS_SERVER = PropertyUtils.getProperty(Constants.BS_SERVER);
    private static final String BS_APP_URL = PropertyUtils.getProperty(Constants.BS_APP_URL);

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

    public static IOSDriver createIOSDriver() {
        LoggingManager.logInfo(MobileDriverManager.class, "=== Creating new iOS driver ===");

        IOSDriver driver = null;
        DesiredCapabilities capabilities = new DesiredCapabilities();

        capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, PLATFORM_VERSION);
        capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, PLATFORM_NAME);
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, DEVICE_NAME);
        capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, AUTOMATION_NAME);
        capabilities.setCapability(MobileCapabilityType.APP, getAppAbsolutePath(APP_NAME));

        capabilities.setCapability(IOSMobileCapabilityType.WDA_LAUNCH_TIMEOUT, 500000);

        try {
            driver = new IOSDriver(new URL(getAppiumUrl()), capabilities);
        } catch (MalformedURLException exception) {
            LoggingManager.logError(MobileDriverManager.class, "Error when creating iOS driver", exception);
        }

        return driver;
    }

    public static AndroidDriver createAndroidDriver() {
        LoggingManager.logInfo(MobileDriverManager.class, "=== Creating new Android driver ===");

        AndroidDriver driver = null;
        DesiredCapabilities capabilities = new DesiredCapabilities();

        capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, PLATFORM_NAME);
        capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, PLATFORM_VERSION);
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, DEVICE_NAME);
        capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, AUTOMATION_NAME);
        capabilities.setCapability(MobileCapabilityType.APP, getAppAbsolutePath("android" + File.separator + APP_NAME));

        try {
            driver = new AndroidDriver(new URL(getAppiumUrl()), capabilities);
        } catch (MalformedURLException exception) {
            LoggingManager.logError(MobileDriverManager.class, "Error when creating Android driver", exception);
        }

        return driver;
    }

    public static IOSDriver createBrowserStackIOSDriver() {
        LoggingManager.logInfo(MobileDriverManager.class, "=====> Creating new BrowserStack iOS driver ===");

        IOSDriver driver = null;
        DesiredCapabilities capabilities = new DesiredCapabilities();

        capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, PLATFORM_VERSION);
        capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, PLATFORM_NAME);
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, DEVICE_NAME);
        capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, AUTOMATION_NAME);
        capabilities.setCapability(MobileCapabilityType.APP, BS_APP_URL);

        capabilities.setCapability(IOSMobileCapabilityType.WDA_LAUNCH_TIMEOUT, 500000);

        try {
            driver = new IOSDriver(new URL("http://" + BS_USERNAME + ":" + BS_ACCESS_KEY + "@" + BS_SERVER + "/wd/hub"), capabilities);
        } catch (MalformedURLException exception) {
            LoggingManager.logError(MobileDriverManager.class, "Error when creating BrowserStack iOS driver", exception);
        }

        return driver;
    }

    public static void createMobileDriver() {
        if (PropertyUtils.isPlatform(Platform.IOS)) {
            IOSDriver driver = createIOSDriver();
            setMobileDrivers(driver);
        } else if (PropertyUtils.isPlatform(Platform.ANDROID)) {
            AndroidDriver driver = createAndroidDriver();
            setMobileDrivers(driver);
        }
    }

    private static String getAppAbsolutePath(String appName) {
        return PropertyUtils.getProperty("user.dir") +
                File.separator + "src" +
                File.separator + "test" +
                File.separator + "resources" +
                File.separator + "apps" +
                File.separator + appName;
    }

    private static String getAppiumUrl() {
        String url = PropertyUtils.getProperty(Constants.APPIUM_URL);
        String port = PropertyUtils.getProperty(Constants.APPIUM_PORT);

        return url + ":" + port + "/wd/hub";
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

            String screenshotFilePath = PropertyUtils.getProperty("user.dir") + File.separator;
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
