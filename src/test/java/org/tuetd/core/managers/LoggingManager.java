package org.tuetd.core.managers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggingManager {
    private static Logger getLogger(Class<?> className) {
        return LoggerFactory.getLogger(className);
    }

    public static void logDebug(Class<?> className, String message) {
        getLogger(className).debug(message);
    }

    public static void logInfo(Class<?> className, String message) {
        getLogger(className).info(message);
    }

    public static void logError(Class<?> className, String message, Exception exception) {
        getLogger(className).error(message, exception);
    }

}
