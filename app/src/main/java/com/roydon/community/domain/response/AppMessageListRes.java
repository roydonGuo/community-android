package com.roydon.community.domain.response;

import com.roydon.community.domain.entity.AppMessage;

import java.util.List;

public class AppMessageListRes extends BaseResponse {

    private int total;
    private List<AppMessage> data;

    public AppMessageListRes() {
    }

    public AppMessageListRes(int total, List<AppMessage> data) {
        this.total = total;
        this.data = data;
    }

    public AppMessageListRes(String msg, int code, int total, List<AppMessage> data) {
        super(msg, code);
        this.total = total;
        this.data = data;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<AppMessage> getData() {
        return data;
    }

    public void setData(List<AppMessage> data) {
        this.data = data;
    }
}
