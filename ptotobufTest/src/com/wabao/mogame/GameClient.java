package com.wabao.mogame;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.util.Scanner;
import java.util.concurrent.Executors;

import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelFutureListener;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;
import org.jboss.netty.channel.socket.oio.OioClientSocketChannelFactory;
import org.jboss.netty.handler.codec.frame.FrameDecoder;
import org.jboss.netty.handler.codec.oneone.OneToOneEncoder;

import com.msj.chat.ChatDto;

public class GameClient {
	public static void main(String[] args) {
		final ClientBootstrap boot = new ClientBootstrap(new OioClientSocketChannelFactory(Executors.newCachedThreadPool()));
		boot.setPipelineFactory(new ChannelPipelineFactory() {
			@Override
			public ChannelPipeline getPipeline() throws Exception {
				ChannelPipeline pipeline = Channels.pipeline();
				pipeline.addLast("decoder", new Decoder());
				pipeline.addLast("encoder", new Encoder());
				pipeline.addLast("handler", new Handler());
				return pipeline;
			}
		});
		boot.connect(new InetSocketAddress(9999)).addListener(new ChannelFutureListener() {
			
			@Override
			public void operationComplete(ChannelFuture future) throws Exception {
				if(future.isSuccess() && future.getChannel().isConnected()) {
					Scanner scan = new Scanner(System.in);
					while(scan.hasNextLine()) {
						String next = scan.next();
						if("quit".equals(next)) {
							boot.releaseExternalResources();
							System.exit(0);
						}
						ChatDto chat = new ChatDto();
						chat.content = new String(next.getBytes("GBK"));
						chat.time = (int)(System.currentTimeMillis() / 1000);
						chat.nickname = "yama";
						future.getChannel().write(chat);
					}
				}
			}
		});
	}
	
	public static class Decoder extends FrameDecoder {

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
			
			ByteArrayInputStream bin = new ByteArrayInputStream(data);
			ObjectInputStream in = new ObjectInputStream(bin);
			return in.readObject();
		}
		
	}
	
	public static class Encoder extends OneToOneEncoder{
		@Override
		protected Object encode(ChannelHandlerContext ctx, Channel channel,
				Object message) throws Exception {
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			ObjectOutputStream oo = new ObjectOutputStream(out);
			oo.writeObject(message);
			byte[] data = out.toByteArray();
			
			ChannelBuffer buffer = ChannelBuffers.buffer(4 + data.length);
			buffer.writeInt(data.length);
			buffer.writeBytes(data);
			return buffer;
		}
	}
	
	public static class Handler extends SimpleChannelHandler {
		@Override
		public void channelConnected(ChannelHandlerContext ctx,
				ChannelStateEvent e) throws Exception {
			System.out.println("channel connected.");
		}
		
		@Override
		public void channelClosed(ChannelHandlerContext ctx, ChannelStateEvent e)
				throws Exception {
			System.out.println("channel closed.");
		}
		
		@Override
		public void messageReceived(ChannelHandlerContext ctx, MessageEvent e)
				throws Exception {
			System.out.println("message received:" + ((ChatDto)e.getMessage()).content);
		}
	}
}
