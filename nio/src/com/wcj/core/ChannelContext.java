package com.wcj.core;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

import com.wcj.NioException;

public class ChannelContext {
	private SocketChannel socket;
	private Integer channelId;

	public ChannelContext(int channelId, SocketChannel socket) {
		this.channelId = channelId;
		this.socket = socket;
	}
	
	public SocketChannel getSocket() {
		return socket;
	}

	public Integer getChannelId() {
		return channelId;
	}
	
	public void onReceive(byte[] data){
		Context.handler.onReceive(Context.protocolFactory.getDecoder().decode(data), channelId);
	}
	
	public void send(Object message){
		try {
			socket.write(ByteBuffer.wrap(Context.protocolFactory.getEncoder().encode(String.valueOf(message))));
		} catch (IOException e) {
			e.printStackTrace();
			new NioException("send message error.", e);
		}
	}
}
