package com.roydon.community.enums;

public enum AccessTypeEnum {
    OUT("0", "出"),
    IN("1", "进");

    private final String code;
    private final String info;

    AccessTypeEnum(String code, String info) {
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
