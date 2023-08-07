package com.roydon.community.domain.vo;

import com.roydon.community.domain.entity.MallGoodsVO;
import com.roydon.community.domain.response.BaseResponse;

import java.io.Serializable;

/**
 * @author roydon
 * @date 2023/6/24 22:00
 * @description 商品详情响应体
 */
public class GoodsDetailRes extends BaseResponse implements Serializable {
    private MallGoodsVO data;

    public GoodsDetailRes() {
    }

    public GoodsDetailRes(String msg, int code, MallGoodsVO data) {
        super(msg, code);
        this.data = data;
    }

    public MallGoodsVO getData() {
        return data;
    }

    public void setData(MallGoodsVO data) {
        this.data = data;
    }
}
