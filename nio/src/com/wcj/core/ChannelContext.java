package com.wcj.core;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

import com.wcj.NioException;
import com.wcj.protocol.Decoder;
import com.wcj.protocol.Encoder;

public class ChannelContext {
	private SocketChannel socket;
	private Integer channelId;
	private Encoder encoder;
	private Decoder decoder;

	public ChannelContext(int channelId, SocketChannel socket) {
		this.channelId = channelId;
		this.socket = socket;
		this.encoder = Context.protocolFactory.getEncoder();
		this.decoder = Context.protocolFactory.getDecoder();
	}
	
	public SocketChannel getSocket() {
		return socket;
	}

	public Integer getChannelId() {
		return channelId;
	}
	
	public void onReceive(byte[] data){
		Context.handler.onReceive(this.decoder.decode(data), channelId);
	}
	
	public void send(Object message){
		try {
			socket.write(ByteBuffer.wrap(this.encoder.encode(String.valueOf(message))));
		} catch (IOException e) {
			e.printStackTrace();
			new NioException("send message error.", e);
		}
	}
}
