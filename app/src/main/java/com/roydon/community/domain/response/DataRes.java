package com.roydon.community.domain.response;

public class DataRes<T> extends BaseResponse {

    private T data;

    public DataRes() {
    }

    public DataRes(T data) {
        this.data = data;
    }

    public DataRes(String msg, int code, T data) {
        super(msg, code);
        this.data = data;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
