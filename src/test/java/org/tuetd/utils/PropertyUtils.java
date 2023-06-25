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
        String userDir = System.getProperty("user.dir");
        try {

            return configs.ini(Paths.get(userDir + "/src/test/resources/configurations/config.ini").toFile());
        } catch (Exception exception) {
            LoggingManager.logError(PropertyUtils.class, "Unable to load Ini File", exception);
        }

        return null;
    }

    public static String getIniProperty(String sectionName, String propertyKey) {
        INIConfiguration ini = loadIniFile();

        if (ini != null) {
            SubnodeConfiguration section = ini.getSection(sectionName);
            return section.getString(propertyKey);
        }

        return "";
    }

    public static Profile getProfile() {
        return Profile.getProfile(getIniProperty("common", "profile"));
    }

    public static Boolean isProfile(Profile profileExpected) {
        return getProfile().equals(profileExpected);
    }
}
