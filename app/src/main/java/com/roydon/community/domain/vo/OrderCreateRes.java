package com.roydon.community.domain.vo;

import com.roydon.community.domain.entity.MallOrder;
import com.roydon.community.domain.response.BaseResponse;

/**
 * @author roydon
 * @date 2023/6/25 16:35
 * @description community-android
 */
public class OrderCreateRes extends BaseResponse {
    private MallOrder data;

    public OrderCreateRes() {
    }

    public OrderCreateRes(MallOrder data) {
        this.data = data;
    }

    public OrderCreateRes(String msg, int code, MallOrder data) {
        super(msg, code);
        this.data = data;
    }

    public MallOrder getData() {
        return data;
    }

    public void setData(MallOrder data) {
        this.data = data;
    }
}
