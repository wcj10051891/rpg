package com.wabao.mogame.net.tcp;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jboss.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wabao.mogame.service.Services;

public class Groups {
	private static final Logger log = LoggerFactory.getLogger(Groups.class);
	
	public static final String World = "world";
	
	private Map<String, BroadcastGroup> groups = new HashMap<String, BroadcastGroup>();

	public BroadcastGroup create(String groupName) {
		synchronized (groups) {
			if(groups.containsKey(groupName))
				return groups.get(groupName);
			
			BroadcastGroup group = new BroadcastGroup(groupName);
			groups.put(groupName, group);
			return group;
		}
	}

	public void join(String groupName, Integer channelId) {
		synchronized (groups) {
			BroadcastGroup group = groups.get(groupName);
			if (group == null) {
				groups.put(groupName, new BroadcastGroup(groupName, channelId));
			} else {
				group.add(channelId);
			}
		}
	}

	public void leave(String groupName, Integer channelId) {
		synchronized (groups) {
			BroadcastGroup group = groups.get(groupName);
			if (group != null)
				group.remove(channelId);
		}
	}

	public BroadcastGroup get(String groupName) {
		return groups.get(groupName);
	}

	@SuppressWarnings("unchecked")
	public void broadcast(String groupName, Object message, Integer... excludeChannelIds) {
		BroadcastGroup group = groups.get(groupName);
		List<Integer> excludeIds = excludeChannelIds.length == 0 ? Collections.EMPTY_LIST : Arrays.asList(excludeChannelIds);
		for (Integer channelId : group.getChannelIds()) {
			if (!excludeIds.contains(channelId)) {
				Channel channel = Services.tcpService.channels.getChannel(channelId);
				if(channel != null)
					channel.write(message);
			}
		}
		log.info("broadcast to {}:{}, exclude:{}", groupName, message, Arrays.toString(excludeChannelIds));
	}
}