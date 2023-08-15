package com.roydon.community.domain.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * app消息通知对象 app_message
 *
 * @author roydon
 * @date 2023-08-15
 */
public class AppMessage implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long messageId;

    /**
     * 通知用户id
     */
    private Long userId;

    /**
     * 通知内容
     */
    private String messageContent;

    /**
     * 消息状态(0未读1已读)
     */
    private String messageStatus;

    private Date createTime;

    private String createBy;

    private Date updateTime;

    private String updateBy;

    private String remark;

    public AppMessage() {
    }

    public AppMessage(Long messageId, Long userId, String messageContent, String messageStatus, Date createTime, String createBy, Date updateTime, String updateBy, String remark) {
        this.messageId = messageId;
        this.userId = userId;
        this.messageContent = messageContent;
        this.messageStatus = messageStatus;
        this.createTime = createTime;
        this.createBy = createBy;
        this.updateTime = updateTime;
        this.updateBy = updateBy;
        this.remark = remark;
    }

    public Long getMessageId() {
        return messageId;
    }

    public void setMessageId(Long messageId) {
        this.messageId = messageId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getMessageContent() {
        return messageContent;
    }

    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
    }

    public String getMessageStatus() {
        return messageStatus;
    }

    public void setMessageStatus(String messageStatus) {
        this.messageStatus = messageStatus;
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
