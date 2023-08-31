package com.roydon.community.domain.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

public class ChatMessage implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private Long messageId;

    /**
     * 发送者
     */
    private String sender;
    private String senderImage;

    /**
     * 接收者
     */
    private String receiver;
    private String receiverImage;

    /**
     * 消息内容
     */
    private String content;

    private LocalDateTime createTime;

    public ChatMessage() {
    }

    public ChatMessage(Long messageId, String sender, String senderImage, String receiver, String receiverImage, String content, LocalDateTime createTime) {
        this.messageId = messageId;
        this.sender = sender;
        this.senderImage = senderImage;
        this.receiver = receiver;
        this.receiverImage = receiverImage;
        this.content = content;
        this.createTime = createTime;
    }

    public Long getMessageId() {
        return messageId;
    }

    public void setMessageId(Long messageId) {
        this.messageId = messageId;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getSenderImage() {
        return senderImage;
    }

    public void setSenderImage(String senderImage) {
        this.senderImage = senderImage;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getReceiverImage() {
        return receiverImage;
    }

    public void setReceiverImage(String receiverImage) {
        this.receiverImage = receiverImage;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }
}
