package com.wcj.handler;


public class Handler {

	public void onConnect(Integer channelId) {
		System.out.println("channel " + channelId +" connect.");
	}

	public void onReceive(Object message, Integer channelId) {
		System.out.println("channel receive:" + message);
//		Context.channels.getChannelContext(channelId).send(message);
	}

	public void onClose(Integer channelId) {
		System.out.println("channel " + channelId + "close.");

	}
}
