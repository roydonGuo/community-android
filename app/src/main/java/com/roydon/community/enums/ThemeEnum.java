package com.roydon.community.enums;

public enum ThemeEnum {

    SUN(1), MOON(2);

    private final int code;

    ThemeEnum(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
