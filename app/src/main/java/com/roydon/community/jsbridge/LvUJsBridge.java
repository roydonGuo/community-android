package com.roydon.community.jsbridge;


public interface LvUJsBridge {
	
	public void send(String data);
	public void send(String data, CallBackFunction responseCallback);

}
