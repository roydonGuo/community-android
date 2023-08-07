package com.roydon.community.domain.response;

import com.roydon.community.domain.entity.MallUserAddress;

public class AddressAddRes extends BaseResponse {

    private MallUserAddress data;

    public AddressAddRes() {
    }

    public AddressAddRes(MallUserAddress data) {
        this.data = data;
    }

    public AddressAddRes(String msg, int code, MallUserAddress data) {
        super(msg, code);
        this.data = data;
    }

    public MallUserAddress getData() {
        return data;
    }

    public void setData(MallUserAddress data) {
        this.data = data;
    }
}
