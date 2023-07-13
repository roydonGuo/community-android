package com.roydon.community.domain.vo;

/**
 * 获取手机验证码接口响应体
 * {
 *     "msg": "225456",
 *     "code": 200
 * }
 */
public class PhoneCodeRes {
    private int code;
    private String msg;

    public PhoneCodeRes() {
    }

    public PhoneCodeRes(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
