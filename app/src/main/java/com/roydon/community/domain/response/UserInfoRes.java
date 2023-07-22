package com.roydon.community.domain.response;

import com.roydon.community.domain.vo.AppUser;
import com.roydon.community.domain.vo.BaseResponse;

public class UserInfoRes extends BaseResponse {

    private AppUser data;

    public UserInfoRes() {
    }

    public UserInfoRes(AppUser data) {
        this.data = data;
    }

    public UserInfoRes(String msg, int code, AppUser data) {
        super(msg, code);
        this.data = data;
    }

    public AppUser getData() {
        return data;
    }

    public void setData(AppUser data) {
        this.data = data;
    }
}
