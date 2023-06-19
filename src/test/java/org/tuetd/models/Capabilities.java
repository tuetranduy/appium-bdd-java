package org.tuetd.models;

import org.tuetd.utils.PropertyUtils;

public class Capabilities {
    private String platformVersion;
    private String platformName;
    private String deviceName;
    private String automationName;
    private String appName;

    public Capabilities(String profile) {
        setPlatformVersion(PropertyUtils.getIniProperty(profile, "platform.version"));
        setPlatformName(PropertyUtils.getIniProperty(profile, "platform.name"));
        setAppName(PropertyUtils.getIniProperty(profile, "app.name"));
        setAutomationName(PropertyUtils.getIniProperty(profile, "automation.name"));
        setDeviceName(PropertyUtils.getIniProperty(profile, "device.name"));
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
