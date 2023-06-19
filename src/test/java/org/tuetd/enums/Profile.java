package org.tuetd.enums;

import java.util.Arrays;

public enum Profile {

    LOCAL_IOS("local.ios"),
    BROWSERSTACK_IOS("bs.ios"),
    LOCAL_ANDROID("local.android"),
    BROWSERSTACK_ANDROID("bs.android");

    public final String name;

    Profile(String name) {
        this.name = name;
    }

    public static Profile getProfile(String value) {
        return Arrays.stream(Profile.values())
                .filter(profile -> profile.name.equalsIgnoreCase(value))
                .findFirst()
                .orElse(Profile.LOCAL_ANDROID);
    }
}
