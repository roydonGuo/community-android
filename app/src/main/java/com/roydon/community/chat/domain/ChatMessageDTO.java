package com.roydon.community.chat.domain;

public class ChatMessageDTO {

    private String sender;
    private String senderAvatar;
    private String receiver;
    private String receiverAvatar;
    private String content;
    private boolean sendType; // true-发送，false-接收
    private String type; // 0文本1图片
    private Long time;

    public ChatMessageDTO() {
    }

    public ChatMessageDTO(String sender, String senderAvatar, String receiver, String receiverAvatar, String content, boolean sendType, String type, Long time) {
        this.sender = sender;
        this.senderAvatar = senderAvatar;
        this.receiver = receiver;
        this.receiverAvatar = receiverAvatar;
        this.content = content;
        this.sendType = sendType;
        this.type = type;
        this.time = time;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getSenderAvatar() {
        return senderAvatar;
    }

    public void setSenderAvatar(String senderAvatar) {
        this.senderAvatar = senderAvatar;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getReceiverAvatar() {
        return receiverAvatar;
    }

    public void setReceiverAvatar(String receiverAvatar) {
        this.receiverAvatar = receiverAvatar;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isSendType() {
        return sendType;
    }

    public void setSendType(boolean sendType) {
        this.sendType = sendType;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }
}
