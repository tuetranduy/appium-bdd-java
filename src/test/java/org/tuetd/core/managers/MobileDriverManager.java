package org.tuetd.core.managers;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.remote.IOSMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.tuetd.utils.Constants;
import org.tuetd.utils.PropertyUtils;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

public class MobileDriverManager {

    private static final String PLATFORM_VERSION = PropertyUtils.getProperty(Constants.PLATFORM_VERSION_KEY);
    private static final String PLATFORM_NAME = PropertyUtils.getProperty(Constants.PLATFORM_NAME_KEY);
    private static final String DEVICE_NAME = PropertyUtils.getProperty(Constants.DEVICE_NAME_KEY);
    private static final String IOS_APP_NAME = "APP_NAME";
    private static final String ANDROID_APP_NAME = PropertyUtils.getProperty(Constants.ANDROID_APP_NAME_KEY);
    private static final String AUTOMATION_NAME = PropertyUtils.getProperty(Constants.ANDROID_AUTOMATION_NAME_KEY);

    private static final ThreadLocal<AppiumDriver<MobileElement>> mobileDrivers = new ThreadLocal<>();

    private static synchronized AppiumDriver<MobileElement> getMobileDriver() {
        return mobileDrivers.get();
    }

    private static synchronized void setMobileDrivers(AppiumDriver driver) {
        mobileDrivers.set(driver);
    }

    public static IOSDriver<IOSElement> createIOSDriver() {
        LoggingManager.logInfo(MobileDriverManager.class, "=== Creating new iOS driver ===");

        IOSDriver<IOSElement> driver = null;
        DesiredCapabilities capabilities = new DesiredCapabilities();

        capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, PLATFORM_VERSION);
        capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, PLATFORM_NAME);
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, DEVICE_NAME);
        capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, AUTOMATION_NAME);
        capabilities.setCapability(MobileCapabilityType.APP, getAppAbsolutePath(IOS_APP_NAME));

        capabilities.setCapability(IOSMobileCapabilityType.LAUNCH_TIMEOUT, 500000);

        try {
            driver = new IOSDriver<>(new URL("http://0.0.0.0:4723/wd/hub"), capabilities);
            setMobileDrivers(driver);
        } catch (MalformedURLException exception) {
            LoggingManager.logError(MobileDriverManager.class, "Error when creating iOS driver", exception);
        }

        return driver;
    }

    public static AndroidDriver<AndroidElement> createAndroidDriver() {
        LoggingManager.logInfo(MobileDriverManager.class, "=== Creating new Android driver ===");

        AndroidDriver<AndroidElement> driver = null;
        DesiredCapabilities capabilities = new DesiredCapabilities();

        capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, PLATFORM_NAME);
        capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, PLATFORM_VERSION);
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, DEVICE_NAME);
        capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, AUTOMATION_NAME);
        capabilities.setCapability(MobileCapabilityType.APP, getAppAbsolutePath(ANDROID_APP_NAME));

        try {
            driver = new AndroidDriver<>(new URL(getAppiumUrl()), capabilities);
            setMobileDrivers(driver);
        } catch (MalformedURLException exception) {
            LoggingManager.logError(MobileDriverManager.class, "Error when creating Android driver", exception);
        }

        return driver;
    }

    private static String getAppAbsolutePath(String appName) {
        return PropertyUtils.getProperty("user.dir") + File.separator + "resources" + File.separator + "apps" + File.separator + appName;
    }

    private static String getAppiumUrl() {
        String url = PropertyUtils.getProperty(Constants.ANDROID_APPIUM_URL_KEY);
        String port = PropertyUtils.getProperty(Constants.ANDROID_APPIUM_PORT_KEY);

        return url + ":" + port + "/wd/hub";
    }
}
