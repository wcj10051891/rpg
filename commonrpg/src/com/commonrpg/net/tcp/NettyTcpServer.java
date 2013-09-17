package com.commonrpg.net.tcp;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;

import com.commonrpg.core.AppConfig;

public class NettyTcpServer {
	public ServerBootstrap server;

	public void start() {
		if(server == null) {
			server = new ServerBootstrap(new NioServerSocketChannelFactory(
				Executors.newCachedThreadPool(),
				Executors.newCachedThreadPool()));
			server.setOption("child.tcpNoDelay", true);
			server.setOption("child.keepAlive", true);
			server.setOption("reuseAddress", true);
			server.setPipelineFactory(new ChannelPipelineFactory() {
				@Override
				public ChannelPipeline getPipeline() throws Exception {
					ChannelPipeline pipeline = Channels.pipeline();
					pipeline.addLast("decoder", new AmfDecoder());
					pipeline.addLast("logic", ApplicationHandler.INSTANCE);
					return pipeline;
				}
			});
			server.bind(new InetSocketAddress(AppConfig.TCP_PORT));
		}
	}

	public void stop() {
		server.releaseExternalResources();
	}
}
