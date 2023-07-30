package com.roydon.community.domain.vo;

import com.roydon.community.domain.entity.AppBannerNotice;
import com.roydon.community.domain.response.BaseResponse;

import java.io.Serializable;
import java.util.List;

/**
 * @author roydon
 * @date 2023/6/19 0:43
 */
public class BannerNoticeListRes extends BaseResponse implements Serializable {

    private int total;
    private List<AppBannerNotice> rows;

    public BannerNoticeListRes() {
    }

    public BannerNoticeListRes(int total, List<AppBannerNotice> rows) {
        this.total = total;
        this.rows = rows;
    }

    public BannerNoticeListRes(String msg, int code, int total, List<AppBannerNotice> rows) {
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

    public List<AppBannerNotice> getRows() {
        return rows;
    }

    public void setRows(List<AppBannerNotice> rows) {
        this.rows = rows;
    }
}
