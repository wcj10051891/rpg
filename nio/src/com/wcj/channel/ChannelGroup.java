package com.wcj.channel;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

import com.wcj.core.Context;

public class ChannelGroup {
	private String name;
	private Set<Integer> channels;

	public ChannelGroup(String name) {
		this.name = name;
		this.channels = new LinkedHashSet<>();
	}

	public ChannelGroup(String name, Integer... channelIds) {
		this.name = name;
		this.channels = new LinkedHashSet<>(Arrays.asList(channelIds));
	}

	public String getName() {
		return this.name;
	}

	public void add(Integer channelId) {
		this.channels.add(channelId);
	}

	public void remove(Integer channelId) {
		this.channels.remove(channelId);
	}

	public Set<Integer> getChannelIds() {
		return this.channels;
	}

	public void broadcast(Object message, Integer... excludeChannelIds) {
		Context.groups.broadcast(name, message, excludeChannelIds);
	}

	@Override
	public String toString() {
		return "channel group:" + name + " -> " + channels;
	}
}
