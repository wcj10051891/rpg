package com.wabao.mogame.net.tcp;

import java.net.InetSocketAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;

import com.wabao.mogame.core.AppConfig;
import com.wabao.mogame.net.tcp.codec.JavaObjectDecoder;

public class NettyTcpServer {
	public ServerBootstrap server;
	private ExecutorService bossPool;
	private ExecutorService workersPool;

	public void start() {
		if(server == null) {
			bossPool = Executors.newCachedThreadPool();
			workersPool = Executors.newCachedThreadPool();
			server = new ServerBootstrap(new NioServerSocketChannelFactory(
				bossPool, workersPool));
			server.setOption("child.tcpNoDelay", true);
			server.setOption("child.keepAlive", true);
			server.setOption("reuseAddress", true);
			server.setPipelineFactory(new ChannelPipelineFactory() {
				@Override
				public ChannelPipeline getPipeline() throws Exception {
					ChannelPipeline pipeline = Channels.pipeline();
//					pipeline.addLast("decoder", new ProtobufDecoder());
					pipeline.addLast("decoder", new JavaObjectDecoder());
					pipeline.addLast("logic", LogicHandler.INSTANCE);
					return pipeline;
				}
			});
			server.bind(new InetSocketAddress(AppConfig.TCP_PORT));
		}
	}

	public void stop() {
		if(bossPool != null)
			bossPool.shutdownNow();
		if(workersPool != null)
			workersPool.shutdownNow();
	}
}
