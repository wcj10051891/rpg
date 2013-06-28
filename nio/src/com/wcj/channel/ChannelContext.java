package com.wcj.channel;

import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

import org.apache.log4j.Logger;

import com.wcj.core.Context;
import com.wcj.protocol.Decoder;
import com.wcj.protocol.Encoder;

public class ChannelContext {
	private static final Logger log = Logger.getLogger(ChannelContext.class);
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
			socket.write(ByteBuffer.wrap(this.encoder.encode(message)));
		} catch (Exception e) {
			log.error("channel send message error.", e);
		}
	}
}
