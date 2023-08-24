package com.roydon.community.domain.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 系统反馈对象 sys_feedback
 *
 * @author roydon
 * @date 2023-08-24
 */
public class SysFeedback implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 反馈id
     */
    private Long feedbackId;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 用户
     */
    private String username;

    /**
     * 联系电话
     */
    private String telephone;

    /**
     * 反馈平台（0:app,1:web）
     */
    private String feedbackPlatform;

    /**
     * 反馈图片地址
     */
    private String feedbackImage;

    private Date createTime;

    private String createBy;

    private Date updateTime;

    private String updateBy;

    private String remark;

    public SysFeedback() {
    }

    public SysFeedback(Long feedbackId, Long userId, String username, String telephone, String feedbackPlatform, String feedbackImage, Date createTime, String createBy, Date updateTime, String updateBy, String remark) {
        this.feedbackId = feedbackId;
        this.userId = userId;
        this.username = username;
        this.telephone = telephone;
        this.feedbackPlatform = feedbackPlatform;
        this.feedbackImage = feedbackImage;
        this.createTime = createTime;
        this.createBy = createBy;
        this.updateTime = updateTime;
        this.updateBy = updateBy;
        this.remark = remark;
    }

    public Long getFeedbackId() {
        return feedbackId;
    }

    public void setFeedbackId(Long feedbackId) {
        this.feedbackId = feedbackId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getFeedbackPlatform() {
        return feedbackPlatform;
    }

    public void setFeedbackPlatform(String feedbackPlatform) {
        this.feedbackPlatform = feedbackPlatform;
    }

    public String getFeedbackImage() {
        return feedbackImage;
    }

    public void setFeedbackImage(String feedbackImage) {
        this.feedbackImage = feedbackImage;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
