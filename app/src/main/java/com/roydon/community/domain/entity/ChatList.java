package com.roydon.community.domain.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

public class ChatList implements Serializable {
    private static final long serialVersionUID=1L;

    /** id */
    private Long listId;

    /** 发送者 */
    private String sender;
    private String senderImage;

    /** 接收者 */
    private String receiver;
    private String receiverImage;

    /** 新消息内容 */
    private String newContent;

    /** 聊天类型,0:私聊 */
    private String listType;

    /** 状态，0:正常,1已读,2:删除,9:置顶 */
    private String status;

    /** 更新时间 */
    private LocalDateTime updateTime;

    public ChatList() {
    }

    public ChatList(Long listId, String sender, String senderImage, String receiver, String receiverImage, String newContent, String listType, String status, LocalDateTime updateTime) {
        this.listId = listId;
        this.sender = sender;
        this.senderImage = senderImage;
        this.receiver = receiver;
        this.receiverImage = receiverImage;
        this.newContent = newContent;
        this.listType = listType;
        this.status = status;
        this.updateTime = updateTime;
    }

    public String getSenderImage() {
        return senderImage;
    }

    public void setSenderImage(String senderImage) {
        this.senderImage = senderImage;
    }

    public String getReceiverImage() {
        return receiverImage;
    }

    public void setReceiverImage(String receiverImage) {
        this.receiverImage = receiverImage;
    }

    public Long getListId() {
        return listId;
    }

    public void setListId(Long listId) {
        this.listId = listId;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getNewContent() {
        return newContent;
    }

    public void setNewContent(String newContent) {
        this.newContent = newContent;
    }

    public String getListType() {
        return listType;
    }

    public void setListType(String listType) {
        this.listType = listType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }
}
