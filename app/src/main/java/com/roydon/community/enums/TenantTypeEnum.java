package com.roydon.community.enums;

public enum TenantTypeEnum {
    NO("0", "房东"),
    YES("1", "租户");

    private final String code;
    private final String info;

    TenantTypeEnum(String code, String info) {
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
