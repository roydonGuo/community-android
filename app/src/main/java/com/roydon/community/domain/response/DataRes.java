package com.roydon.community.domain.response;

public class DataRes extends BaseResponse{

    private String data;

    public DataRes() {
    }

    public DataRes(String data) {
        this.data = data;
    }

    public DataRes(String msg, int code, String data) {
        super(msg, code);
        this.data = data;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
