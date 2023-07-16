package com.roydon.community.api;

public interface HttpCallback {

    void onSuccess(String res);

    void onFailure(Exception e);

}
