package com.roydon.community.domain.entity;

import java.io.Serializable;

public class AppBannerNotice implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 公告ID
     */
    private Integer noticeId;
    /**
     * 标题
     */
    private String noticeTitle;
    /**
     * 图片地址
     */
    private String noticeImgUrl;
    /**
     * 状态（0关闭 1展示）
     */
    private String showInApp;

    public AppBannerNotice() {
    }

    public AppBannerNotice(Integer noticeId, String noticeTitle, String noticeImgUrl, String showInApp) {
        this.noticeId = noticeId;
        this.noticeTitle = noticeTitle;
        this.noticeImgUrl = noticeImgUrl;
        this.showInApp = showInApp;
    }

    public Integer getNoticeId() {
        return noticeId;
    }

    public void setNoticeId(Integer noticeId) {
        this.noticeId = noticeId;
    }

    public String getNoticeTitle() {
        return noticeTitle;
    }

    public void setNoticeTitle(String noticeTitle) {
        this.noticeTitle = noticeTitle;
    }

    public String getNoticeImgUrl() {
        return noticeImgUrl;
    }

    public void setNoticeImgUrl(String noticeImgUrl) {
        this.noticeImgUrl = noticeImgUrl;
    }

    public String getShowInApp() {
        return showInApp;
    }

    public void setShowInApp(String showInApp) {
        this.showInApp = showInApp;
    }
}
