package com.roydon.community.domain.response;

import com.roydon.community.domain.entity.EpidemicInoculationHistory;

public class EpidemicInoculationHistoryRes extends BaseResponse{

    private EpidemicInoculationHistory data;

    public EpidemicInoculationHistoryRes() {
    }

    public EpidemicInoculationHistoryRes(EpidemicInoculationHistory data) {
        this.data = data;
    }

    public EpidemicInoculationHistoryRes(String msg, int code, EpidemicInoculationHistory data) {
        super(msg, code);
        this.data = data;
    }

    public EpidemicInoculationHistory getData() {
        return data;
    }

    public void setData(EpidemicInoculationHistory data) {
        this.data = data;
    }
}
