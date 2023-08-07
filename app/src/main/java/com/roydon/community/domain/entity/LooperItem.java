package com.roydon.community.domain.entity;

import java.io.Serializable;

/**
 * @author roydon
 * @date 2023/6/14 1:23
 */
public class LooperItem implements Serializable {
    private static final long serialVersionUID = 1L;

    private String noticeTitle;
    private int id;

    public LooperItem() {
    }

    public LooperItem(String noticeTitle, int id) {
        this.noticeTitle = noticeTitle;
        this.id = id;
    }

    public String getNoticeTitle() {
        return noticeTitle;
    }

    public void setNoticeTitle(String noticeTitle) {
        this.noticeTitle = noticeTitle;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
