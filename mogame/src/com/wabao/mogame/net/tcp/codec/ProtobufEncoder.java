package com.wabao.mogame.net.tcp.codec;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.protobuf.Message;
import com.wabao.mogame.core.AppException;

public class ProtobufEncoder implements Encoder {
	private static final Logger log = LoggerFactory.getLogger(ProtobufEncoder.class);
	public ChannelBuffer encode(Object message) {
		if(!(message instanceof Message))
			throw new AppException("only support send protobuf Message.");
		
		byte[] data = ((Message)message).toByteArray();
		ChannelBuffer packet = ChannelBuffers.buffer(4 + data.length);
		packet.writeInt(data.length);
		packet.writeBytes(data);
		log.info("encode message {} -> {}", message, packet);
		return packet;
	}
}