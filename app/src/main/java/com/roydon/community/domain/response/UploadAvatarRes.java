package com.roydon.community.domain.response;

public class UploadAvatarRes extends BaseResponse{
    private String imgUrl;

    public UploadAvatarRes() {
    }

    public UploadAvatarRes(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public UploadAvatarRes(String msg, int code, String imgUrl) {
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
