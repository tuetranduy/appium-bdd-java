package org.tuetd.utils;

import org.tuetd.managers.LoggingManager;

public class AssertionUtils {

    public static void assertTrue(Class<?> className, Boolean condition, String message) {
        if (condition) {
            LoggingManager.logPass(className, message);
        } else {
            LoggingManager.logFail(className, message);
        }
    }
}