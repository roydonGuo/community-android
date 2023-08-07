package com.roydon.community.enums;

public enum NormalDisableEnum {

    OK("0", "正常"), NO("1", "停用");

    private final String code;
    private final String info;

    NormalDisableEnum(String code, String info) {
        this.code = code;
        this.info = info;
    }

    public String getCode() {
        return code;
    }

    public String getInfo() {
        return info;
    }
}
