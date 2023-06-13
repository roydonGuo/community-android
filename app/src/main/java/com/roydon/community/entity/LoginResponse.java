package com.roydon.community.entity;

public class LoginResponse {

    /**
     * "msg": "操作成功",
     * "code": 200,
     * "token": "eyJhbGciOi..."
     */
    private String msg;
    private int code;
    private String token;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
