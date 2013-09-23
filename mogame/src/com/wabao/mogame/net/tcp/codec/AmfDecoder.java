package com.wabao.mogame.net.tcp.codec;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.frame.FrameDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AmfDecoder extends FrameDecoder {
	private static final Logger log = LoggerFactory.getLogger(AmfDecoder.class);
	/**
	 * 客户端连接后首先请求跨域文件是否已经发送
	 */
	private boolean keySend;
	private int seqNum = 1;

	@Override
	protected Object decode(ChannelHandlerContext ctx, Channel channel, ChannelBuffer buffer) throws Exception {
		return null;
	}
}