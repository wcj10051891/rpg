package com.wabao.mogame.net.tcp;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

public class BroadcastGroup {
	private String name;
	private Set<Integer> channelIds;

	public BroadcastGroup(String name) {
		this.name = name;
		this.channelIds = new LinkedHashSet<Integer>();
	}

	public BroadcastGroup(String name, Integer... channelIds) {
		this.name = name;
		this.channelIds = new LinkedHashSet<Integer>(Arrays.asList(channelIds));
	}

	public String getName() {
		return this.name;
	}

	public void add(Integer channelId) {
		this.channelIds.add(channelId);
	}

	public void remove(Integer channelId) {
		this.channelIds.remove(channelId);
	}

	public Set<Integer> getChannelIds() {
		return this.channelIds;
	}

	@Override
	public String toString() {
		return "channel group:" + name + " -> " + channelIds;
	}
}
