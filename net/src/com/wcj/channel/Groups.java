package com.wcj.channel;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.wcj.core.NetContext;

public class Groups {
	
	public static final String World = "world";
	
	private Map<String, ChannelGroup> groups = new ConcurrentHashMap<>();

	public ChannelGroup create(String groupName) {
		synchronized (groups) {
			ChannelGroup channelGroup = new ChannelGroup(groupName);
			groups.put(groupName, channelGroup);
			return channelGroup;
		}
	}

	public void join(String groupName, Integer channelId) {
		synchronized (groups) {
			ChannelGroup group = groups.get(groupName);
			if (group == null) {
				groups.put(groupName, new ChannelGroup(groupName, channelId));
			} else {
				group.add(channelId);
			}
		}
	}

	public void leave(String groupName, Integer channelId) {
		synchronized (groups) {
			ChannelGroup group = groups.get(groupName);
			if (group != null)
				group.remove(channelId);
		}
	}

	public ChannelGroup get(String groupName) {
		return groups.get(groupName);
	}

	@SuppressWarnings("unchecked")
	public void broadcast(String groupName, Object message, Integer... excludeChannelIds) {
		ChannelGroup group = groups.get(groupName);
		List<Integer> excludeIds = excludeChannelIds.length == 0 ? Collections.EMPTY_LIST : Arrays.asList(excludeChannelIds);
		byte[] msg = NetContext.protocolFactory.getEncoder().encode(message);
		for (Integer channelId : group.getChannelIds()) {
			if (!excludeIds.contains(channelId)) {
				ChannelContext channelContext = NetContext.channels.get(channelId);
				if(channelContext != null)
					channelContext.send(msg);
			}
		}
	}
}
