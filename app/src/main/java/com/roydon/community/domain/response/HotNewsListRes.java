package com.roydon.community.domain.response;

import com.roydon.community.domain.vo.HotNews;

import java.util.List;

public class HotNewsListRes extends BaseResponse {

    private int total;
    private List<HotNews> rows;

    public HotNewsListRes() {
    }

    public HotNewsListRes(int total, List<HotNews> rows) {
        this.total = total;
        this.rows = rows;
    }

    public HotNewsListRes(String msg, int code, int total, List<HotNews> rows) {
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

    public List<HotNews> getRows() {
        return rows;
    }

    public void setRows(List<HotNews> rows) {
        this.rows = rows;
    }
}
