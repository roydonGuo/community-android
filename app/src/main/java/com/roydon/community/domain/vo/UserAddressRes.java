package com.roydon.community.domain.vo;

import com.roydon.community.domain.entity.MallUserAddress;
import com.roydon.community.domain.response.BaseResponse;

/**
 * @author roydon
 * @date 2023/6/25 13:58
 * @description community-android
 */
public class UserAddressRes extends BaseResponse {
    private MallUserAddress data;

    public UserAddressRes() {
    }

    public UserAddressRes(String msg, int code) {
        super(msg, code);
    }

    public UserAddressRes(MallUserAddress data) {
        this.data = data;
    }

    public UserAddressRes(String msg, int code, MallUserAddress data) {
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
