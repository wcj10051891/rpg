package com.wcj.channel;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Channels {
	private Map<Integer, ChannelContext> channels = new ConcurrentHashMap<>();

	public void put(Integer channelId, ChannelContext channelContext) {
		this.channels.put(channelId, channelContext);
	}

	public ChannelContext get(Integer channelId) {
		return channels.get(channelId);
	}

	public void remove(Integer channelId) {
		this.channels.remove(channelId);
	}
}
