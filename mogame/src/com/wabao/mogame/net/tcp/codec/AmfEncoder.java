package com.wabao.mogame.net.tcp.codec;

import org.jboss.netty.buffer.ChannelBuffer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import flex.messaging.io.SerializationContext;

public class AmfEncoder implements Encoder {
	private static final Logger log = LoggerFactory.getLogger(AmfEncoder.class);
	public static SerializationContext encodeContext = new SerializationContext();
	static {
		encodeContext.legacyCollection = true; // 将list映射成数组
	}
	@Override
	public ChannelBuffer encode(Object message) {
		// TODO Auto-generated method stub
		return null;
	}
}
