package com.roydon.community.domain.response;

public class UploadImageRes extends BaseResponse{
    private String imgUrl;

    public UploadImageRes() {
    }

    public UploadImageRes(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public UploadImageRes(String msg, int code, String imgUrl) {
        super(msg, code);
        this.imgUrl = imgUrl;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
