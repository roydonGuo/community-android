package com.roydon.community.domain.vo;

import java.util.List;

public class OrderListRes {

    /**
     * code:200
     * msg:"查询成功"
     * data:[{},{}]
     * total:3
     */
    private String msg;
    private int code;
    private int total;
    private List<MallOrderVO> data;

    public OrderListRes() {
    }

    public OrderListRes(String msg, int code, int total, List<MallOrderVO> data) {
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

    public List<MallOrderVO> getData() {
        return data;
    }

    public void setData(List<MallOrderVO> data) {
        this.data = data;
    }
}
