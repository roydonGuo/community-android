package com.roydon.community.domain.response;

import com.roydon.community.domain.entity.EpidemicHealthCode;

public class EpidemicHealthCodeRes extends BaseResponse{

    private EpidemicHealthCode data;

    public EpidemicHealthCodeRes() {
    }

    public EpidemicHealthCodeRes(EpidemicHealthCode data) {
        this.data = data;
    }

    public EpidemicHealthCodeRes(String msg, int code, EpidemicHealthCode data) {
        super(msg, code);
        this.data = data;
    }

    public EpidemicHealthCode getData() {
        return data;
    }

    public void setData(EpidemicHealthCode data) {
        this.data = data;
    }
}
