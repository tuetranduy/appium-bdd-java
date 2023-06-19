package org.tuetd.utils;

import org.apache.commons.configuration2.INIConfiguration;
import org.apache.commons.configuration2.SubnodeConfiguration;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.tuetd.enums.Profile;
import org.tuetd.managers.LoggingManager;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

public class PropertyUtils {

    public static INIConfiguration loadIniFile() {
        Configurations configs = new Configurations();
        try {
            String userDir = PropertyUtils.getProperty("user.dir");
            return configs.ini(userDir + "/src/test/resources/configurations/config.ini");
        } catch (Exception exception) {
            LoggingManager.logError(PropertyUtils.class, "Unable to load Ini File", exception);
        }

        return null;
    }

    public static Properties loadPropertiesFiles(String propertiesFiles) {
        Properties properties = new Properties();
        String userDir = PropertyUtils.getProperty("user.dir");
        InputStream inputStream = null;
        String[] allPropertiesFiles = propertiesFiles.split(",");

        for (String propertiesFileName : allPropertiesFiles) {
            propertiesFileName = propertiesFileName.trim();

            try {
                inputStream = Files.newInputStream(Paths.get(userDir + "/src/test/resources/configuration-profiles/" + propertiesFileName));
                properties.load(inputStream);

            } catch (Exception exception) {
                LoggingManager.logError(PropertyUtils.class, "Unable to load Property Files", exception);

            } finally {
                if (inputStream != null) {
                    try {
                        inputStream.close();

                    } catch (IOException exception) {
                        LoggingManager.logError(PropertyUtils.class, "Unable to close file stream", exception);
                    }
                }
            }
        }

        return properties;
    }

    public static String getIniProperty(String sectionName, String propertyKey) {
        INIConfiguration ini = loadIniFile();

        if (ini != null) {
            SubnodeConfiguration section = ini.getSection(sectionName);
            return section.getString(propertyKey);
        }

        return "";
    }

    public static String getProperty(String propertyKey) {

        loadIniFile();

        String propertyValueFromFile = null;

        try {
            propertyValueFromFile = PROPERTIES_FILES.getProperty(propertyKey);

        } catch (Exception exception) {
            LoggingManager.logDebug(PropertyUtils.class, "Property '" + propertyKey + "'" + " does not exist in any of the properties files: '" + getPropertiesFiles() + "'");
        }

        String propertyValueFromSystem = System.getProperty(propertyKey, propertyValueFromFile);
        LoggingManager.logDebug(PropertyUtils.class, propertyKey + " = " + propertyValueFromSystem);
        return propertyValueFromSystem;
    }

    public static String getProperty(String propertyKey, String defaultValue) {
        String propertyValueFromFile = defaultValue;

        try {
            propertyValueFromFile = PROPERTIES_FILES.getProperty(propertyKey, defaultValue);

        } catch (Exception exception) {
            LoggingManager.logDebug(PropertyUtils.class, "Property '" + propertyKey + "'" + " does not exist in any of the properties files: '" + getPropertiesFiles() + "'");
        }

        String propertyValueFromSystem = System.getProperty(propertyKey, propertyValueFromFile);
        LoggingManager.logDebug(PropertyUtils.class, propertyKey + " = " + propertyValueFromSystem);
        return propertyValueFromSystem;
    }

    public static String getPropertiesFiles() {
        return System.getProperty(Constants.PROPERTY_PROPERTIES_FILES, Constants.DEFAULT_PROPERTIES_FILES);
    }

    public static Profile getProfile() {
        return Profile.getProfile(getIniProperty("common", "profile"));
    }

    public static Boolean isProfile(Profile profileExpected) {
        return getProfile().equals(profileExpected);
    }

    private static final Properties PROPERTIES_FILES = PropertyUtils.loadPropertiesFiles(getPropertiesFiles());

}
