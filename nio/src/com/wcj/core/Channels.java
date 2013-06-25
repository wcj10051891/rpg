package com.wcj.core;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Channels {
	private Map<Integer, ChannelContext> channels = new ConcurrentHashMap<>();
	
	public void put(Integer channelId, ChannelContext channelContext){
		this.channels.put(channelId, channelContext);
	}
}
