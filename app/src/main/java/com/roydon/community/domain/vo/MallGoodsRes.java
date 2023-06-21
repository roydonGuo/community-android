package com.roydon.community.domain.vo;

import com.roydon.community.domain.entity.MallGoodsVO;

import java.util.List;

/**
 * @author roydon
 * @date 2023/6/21 23:10
 * @description community-android
 */
public class MallGoodsRes {
    /**
     * code:200
     * msg:"查询成功"
     * data:[{},{}]
     * total:3704
     */
    private String msg;
    private int code;
    private int total;
    private List<MallGoodsVO> data;

    public MallGoodsRes() {
    }

    public MallGoodsRes(String msg, int code, int total, List<MallGoodsVO> data) {
        this.msg = msg;
        this.code = code;
        this.total = total;
        this.data = data;
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

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<MallGoodsVO> getData() {
        return data;
    }

    public void setData(List<MallGoodsVO> data) {
        this.data = data;
    }
}
