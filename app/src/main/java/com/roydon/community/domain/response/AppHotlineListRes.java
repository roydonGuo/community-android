package com.roydon.community.domain.response;

import com.roydon.community.domain.entity.AppHotline;

import java.util.List;

public class AppHotlineListRes extends BaseResponse {

    private int total;
    private List<AppHotline> rows;

    public AppHotlineListRes() {
    }

    public AppHotlineListRes(int total, List<AppHotline> rows) {
        this.total = total;
        this.rows = rows;
    }

    public AppHotlineListRes(String msg, int code, int total, List<AppHotline> rows) {
        super(msg, code);
        this.total = total;
        this.rows = rows;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<AppHotline> getRows() {
        return rows;
    }

    public void setRows(List<AppHotline> rows) {
        this.rows = rows;
    }
}
