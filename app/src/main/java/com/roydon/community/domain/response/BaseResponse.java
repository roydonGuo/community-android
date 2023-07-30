package com.roydon.community.domain.response;

public class BaseResponse {

    /**
     * msg : success
     * code : 200
     */
    private String msg;
    private int code;

    public BaseResponse() {
    }

    public BaseResponse(String msg, int code) {
        this.msg = msg;
        this.code = code;
    }

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
}