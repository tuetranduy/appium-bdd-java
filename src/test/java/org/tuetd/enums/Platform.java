package org.tuetd.enums;

import java.util.Arrays;

public enum Platform {

    IOS("ios"),
    IOS_BS("ios_bs"),
    ANDROID("android"),
    ANDROID_BS("android_bs");

    public final String name;

    Platform(String name) {
        this.name = name;
    }

    public static Platform getPlatform(String value) {
        return Arrays.stream(Platform.values())
                .filter(platform -> platform.name.equalsIgnoreCase(value))
                .findFirst()
                .orElse(Platform.ANDROID);
    }
}
