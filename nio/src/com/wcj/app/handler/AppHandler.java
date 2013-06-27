package com.wcj.app.handler;

import com.wcj.handler.Handler;

public class AppHandler extends Handler {
	
	@Override
	public void onConnect(Integer channelId) {
		super.onConnect(channelId);
	}
	
	@Override
	public void onReceive(Object message, Integer channelId) {
		super.onReceive(message, channelId);
	}
	
	@Override
	public void onClose(Integer channelId) {
		super.onClose(channelId);
	}

}
