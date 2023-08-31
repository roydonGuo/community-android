package com.roydon.community.domain.response;

import com.roydon.community.domain.entity.ChatList;

import java.util.List;

public class ChatListListRes extends BaseResponse{

    private int total;
    private List<ChatList> data;

    public ChatListListRes() {
    }

    public ChatListListRes(int total, List<ChatList> data) {
        this.total = total;
        this.data = data;
    }

    public ChatListListRes(String msg, int code, int total, List<ChatList> data) {
        super(msg, code);
        this.total = total;
        this.data = data;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<ChatList> getData() {
        return data;
    }

    public void setData(List<ChatList> data) {
        this.data = data;
    }
}
