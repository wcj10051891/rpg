package com.wcj.handler;

import com.wcj.channel.ChannelContext;


public abstract class Handler {

	public void onConnect(ChannelContext ctx) {
		System.out.println("channel " + ctx.getChannelId() +" connect.");
	}

	public void onReceive(ChannelContext ctx, Object message) {
		System.out.println("channel receive:" + message);
	}

	public void onClose(ChannelContext ctx) {
		System.out.println("channel " + ctx.getChannelId() + " close.");

	}
}
