package com.roydon.community.enums;

/**
 * 出入社区报备类型
 */
public enum ReportTypeEnum {
    ONESELF("0", "手动报备"),
    ADMIN("1", "管理录入");

    private final String code;
    private final String info;

    ReportTypeEnum(String code, String info) {
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
