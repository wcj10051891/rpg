package com.commonrpg.net.tcp;

import org.jboss.netty.buffer.ChannelBuffer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import flex.messaging.io.SerializationContext;

public class AmfEncoder {
	private static final Logger log = LoggerFactory.getLogger(AmfEncoder.class);
	public static SerializationContext encodeContext = new SerializationContext();
	static {
		encodeContext.legacyCollection = true; // 将list映射成数组
	}

	public static ChannelBuffer encode(Object msg) {
		return null;
	}
}
