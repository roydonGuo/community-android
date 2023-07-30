package com.roydon.community.domain.vo;

import com.roydon.community.domain.entity.MallUserAddress;
import com.roydon.community.domain.response.BaseResponse;

import java.util.List;

/**
 * @author roydon
 * @date 2023/6/25 12:51
 * @description community-android
 */
public class UserAddressListRes extends BaseResponse {
    private int total;
    private List<MallUserAddress> data;

    public UserAddressListRes() {
    }

    public UserAddressListRes(String msg, int code) {
        super(msg, code);
    }

    public UserAddressListRes(int total, List<MallUserAddress> data) {
        this.total = total;
        this.data = data;
    }

    public UserAddressListRes(String msg, int code, int total, List<MallUserAddress> data) {
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

    public List<MallUserAddress> getData() {
        return data;
    }

    public void setData(List<MallUserAddress> data) {
        this.data = data;
    }
}
