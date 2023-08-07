package com.roydon.community.domain.vo;

import com.roydon.community.domain.entity.AppNews;
import com.roydon.community.domain.response.BaseResponse;

import java.io.Serializable;

/**
 * @author roydon
 * @date 2023/6/13 19:37
 */
public class AppNewsRes extends BaseResponse implements Serializable {

    private AppNews data;

    public AppNewsRes() {
    }

    public AppNewsRes(String msg, int code, AppNews data) {
        super(msg, code);
        this.data = data;
    }

    public AppNews getData() {
        return data;
    }

    public void setData(AppNews data) {
        this.data = data;
    }
}
