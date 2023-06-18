package com.roydon.community.domain.dto;

/**
 * @author roydon
 * @date 2023/6/6 2:58
 */
public class LoginBody {

    private String username;
    private String password;

    public LoginBody() {
    }

    public LoginBody(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
