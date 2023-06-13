package com.roydon.community.entity;

import java.util.List;

public class NewsListRes {
    /**
     * code:200
     * msg:"查询成功"
     * rows:[{},{}]
     * total:3704
     */
    private String msg;
    private int code;
    private int total;
    private List<AppNews> rows;

    public NewsListRes() {
    }

    public NewsListRes(String msg, int code, int total, List<AppNews> rows) {
        this.msg = msg;
        this.code = code;
        this.total = total;
        this.rows = rows;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
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

    public List<AppNews> getRows() {
        return rows;
    }

    public void setRows(List<AppNews> rows) {
        this.rows = rows;
    }
}
