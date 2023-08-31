package com.roydon.community.chat.domain;

import java.util.Set;

public class ChatUserList {

    private Set<ChatUser> users;

    public ChatUserList() {
    }

    public ChatUserList(Set<ChatUser> users) {
        this.users = users;
    }

    public Set<ChatUser> getUsers() {
        return users;
    }

    public void setUsers(Set<ChatUser> users) {
        this.users = users;
    }

    @Override
    public String toString() {
        return "ChatUserList{" +
                "users=" + users +
                '}';
    }
}
