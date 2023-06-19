package org.tuetd.models;

import org.tuetd.utils.PropertyUtils;

public class Capabilities {
    private String platformVersion;
    private String platformName;
    private String deviceName;
    private String automationName;
    private String appName;
    private String browserStackUsername;
    private String browserStackAccessKey;
    private String appiumServerUrl;
    private String appiumPort;

    public Capabilities(String profile) {
        setPlatformVersion(PropertyUtils.getIniProperty(profile, "platformVersion"));
        setPlatformName(PropertyUtils.getIniProperty(profile, "platformName"));
        setAppName(PropertyUtils.getIniProperty(profile, "appName"));
        setAutomationName(PropertyUtils.getIniProperty(profile, "automationName"));
        setDeviceName(PropertyUtils.getIniProperty(profile, "deviceName"));
        setBrowserStackUsername(PropertyUtils.getIniProperty(profile, "bsUsername"));
        setBrowserStackAccessKey(PropertyUtils.getIniProperty(profile, "bsAccessKey"));
        setAppiumServerUrl(PropertyUtils.getIniProperty(profile, "appiumUrl"));
        setAppiumPort(PropertyUtils.getIniProperty(profile, "appiumPort"));
    }

    public String getAppiumPort() {
        return appiumPort;
    }

    public void setAppiumPort(String appiumPort) {
        this.appiumPort = appiumPort;
    }

    public String getAppiumServerUrl() {
        return appiumServerUrl;
    }

    public void setAppiumServerUrl(String appiumServerUrl) {
        this.appiumServerUrl = appiumServerUrl;
    }

    public String getBrowserStackUsername() {
        return browserStackUsername;
    }

    public void setBrowserStackUsername(String browserStackUsername) {
        this.browserStackUsername = browserStackUsername;
    }

    public String getBrowserStackAccessKey() {
        return browserStackAccessKey;
    }

    public void setBrowserStackAccessKey(String browserStackAccessKey) {
        this.browserStackAccessKey = browserStackAccessKey;
    }

    public String getPlatformVersion() {
        return platformVersion;
    }

    public void setPlatformVersion(String platformVersion) {
        this.platformVersion = platformVersion;
    }

    public String getPlatformName() {
        return platformName;
    }

    public void setPlatformName(String platformName) {
        this.platformName = platformName;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getAutomationName() {
        return automationName;
    }

    public void setAutomationName(String automationName) {
        this.automationName = automationName;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }
}
