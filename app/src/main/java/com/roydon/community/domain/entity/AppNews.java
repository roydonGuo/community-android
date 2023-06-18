package com.roydon.community.domain.entity;

import java.io.Serializable;
import java.util.Date;

public class AppNews implements Serializable {
    private static final long serialVersionUID = 1L;

    private String newsId;

    private String newsTitle;

    private String coverImg;

    private String source;

    private String newsType;

    private String thereNewsId;

    private String digest;

    private Date postTime;

    private String newsContent;

    private String contentImages;

    private String showInApp;

    private String delFlag;

    private Integer viewNum;

    public AppNews() {
    }

    public AppNews(String newsId, String newsTitle, String coverImg, String source, String newsType, String thereNewsId, String digest, Date postTime, String newsContent, String contentImages, String showInApp, String delFlag, Integer viewNum) {
        this.newsId = newsId;
        this.newsTitle = newsTitle;
        this.coverImg = coverImg;
        this.source = source;
        this.newsType = newsType;
        this.thereNewsId = thereNewsId;
        this.digest = digest;
        this.postTime = postTime;
        this.newsContent = newsContent;
        this.contentImages = contentImages;
        this.showInApp = showInApp;
        this.delFlag = delFlag;
        this.viewNum = viewNum;
    }

    public String getNewsId() {
        return newsId;
    }

    public void setNewsId(String newsId) {
        this.newsId = newsId;
    }

    public String getNewsTitle() {
        return newsTitle;
    }

    public void setNewsTitle(String newsTitle) {
        this.newsTitle = newsTitle;
    }

    public String getCoverImg() {
        return coverImg;
    }

    public void setCoverImg(String coverImg) {
        this.coverImg = coverImg;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getNewsType() {
        return newsType;
    }

    public void setNewsType(String newsType) {
        this.newsType = newsType;
    }

    public String getThereNewsId() {
        return thereNewsId;
    }

    public void setThereNewsId(String thereNewsId) {
        this.thereNewsId = thereNewsId;
    }

    public String getDigest() {
        return digest;
    }

    public void setDigest(String digest) {
        this.digest = digest;
    }

    public Date getPostTime() {
        return postTime;
    }

    public void setPostTime(Date postTime) {
        this.postTime = postTime;
    }

    public String getNewsContent() {
        return newsContent;
    }

    public void setNewsContent(String newsContent) {
        this.newsContent = newsContent;
    }

    public String getContentImages() {
        return contentImages;
    }

    public void setContentImages(String contentImages) {
        this.contentImages = contentImages;
    }

    public String getShowInApp() {
        return showInApp;
    }

    public void setShowInApp(String showInApp) {
        this.showInApp = showInApp;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public Integer getViewNum() {
        return viewNum;
    }

    public void setViewNum(Integer viewNum) {
        this.viewNum = viewNum;
    }
}
