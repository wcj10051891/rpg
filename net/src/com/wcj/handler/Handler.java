package com.wcj.handler;


public abstract class Handler {

	public void onConnect(Integer channelId) {
		System.out.println("channel " + channelId +" connect.");
	}

	public void onReceive(Object message, Integer channelId) {
		System.out.println("channel receive:" + message);
	}

	public void onClose(Integer channelId) {
		System.out.println("channel " + channelId + " close.");

	}
}
