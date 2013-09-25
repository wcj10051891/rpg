package com.wabao.mogame.net.tcp.codec;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.frame.FrameDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wabao.mogame.core.AppException;
import com.wabao.mogame.modules.protocol.dto.RequestDtoProto.RequestDto;
import com.wabao.mogame.util.ProtobufUtils;

public class ProtobufDecoder extends FrameDecoder {
	private static final Logger log = LoggerFactory.getLogger(ProtobufDecoder.class);

	@Override
	protected Object decode(ChannelHandlerContext ctx, Channel channel,
			ChannelBuffer buffer) throws Exception {
		if(buffer.readableBytes() < 4)
			return null;
		
		buffer.markReaderIndex();
		int len = buffer.readInt();
		if(buffer.readableBytes() < len) {
			buffer.resetReaderIndex();
			return null;
		}
		byte[] data = new byte[len];
		buffer.readBytes(data, 0, len);
		try {
			RequestDto request = (RequestDto)ProtobufUtils.decode(RequestDto.class.getName(), data);
			log.info("decode message {} -> {}", buffer, request);
			return request;
		} catch (Exception e) {
			throw new AppException("protobuf object decode error.", e);
		}
	}
}