package com.roydon.community.domain.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * 热点新闻
 */
public class HotNews implements Serializable {
    private static final long serialVersionUID = 1L;

    private String newsId;

    private String newsTitle;

    private String coverImg;

    private String source;

    private String newsType;

    private Date postTime;

    private String showType;

    private Integer viewNum;

    public HotNews() {
    }

    public HotNews(String newsId, String newsTitle, String coverImg, String source, String newsType, Date postTime, String showType, Integer viewNum) {
        this.newsId = newsId;
        this.newsTitle = newsTitle;
        this.coverImg = coverImg;
        this.source = source;
        this.newsType = newsType;
        this.postTime = postTime;
        this.showType = showType;
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

    public Date getPostTime() {
        return postTime;
    }

    public void setPostTime(Date postTime) {
        this.postTime = postTime;
    }

    public String getShowType() {
        return showType;
    }

    public void setShowType(String showType) {
        this.showType = showType;
    }

    public Integer getViewNum() {
        return viewNum;
    }

    public void setViewNum(Integer viewNum) {
        this.viewNum = viewNum;
    }
}