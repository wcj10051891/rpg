package com.wcj.core;

import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class ChannelContext {
	private SocketChannel socket;
	private Integer channelId;
	private ByteBuffer readBuffer;
	private static final int DEFAULT_READ_BUFFER_SIZE = 1024;

	public ChannelContext(int channelId, SocketChannel socket) {
		this.channelId = channelId;
		this.socket = socket;
		this.readBuffer = ByteBuffer.allocate(DEFAULT_READ_BUFFER_SIZE);
	}
	
	public SocketChannel getSocket() {
		return socket;
	}

	public Integer getChannelId() {
		return channelId;
	}

	public ByteBuffer getReadBuffer() {
		return readBuffer;
	}
}
