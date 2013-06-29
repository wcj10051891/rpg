package com.wcj.channel;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Channels {
	private Map<Integer, ChannelContext> channels = new ConcurrentHashMap<>();

	public void put(Integer channelId, ChannelContext channelContext) {
		this.channels.put(channelId, channelContext);
	}

	public ChannelContext get(Integer channelId) {
		ChannelContext channelContext = channels.get(channelId);
		if(channelContext == null)
			channelContext = ChannelContext.NULL;
		return channelContext;
	}

	public void remove(Integer channelId) {
		this.channels.remove(channelId);
	}
}
