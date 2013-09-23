package com.wabao.mogame.net.tcp.codec;

import org.jboss.netty.buffer.ChannelBuffer;

public interface Encoder {
	ChannelBuffer encode(Object message);
}
