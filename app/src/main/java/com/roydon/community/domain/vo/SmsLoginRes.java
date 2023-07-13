package com.roydon.community.domain.vo;

public class SmsLoginRes {
    private int code;
    private String msg;
    private String token;

    public SmsLoginRes() {
    }

    public SmsLoginRes(int code, String msg,String token) {
        this.code = code;
        this.msg = msg;
        this.token = token;
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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
