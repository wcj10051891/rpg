package com.wcj.core;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.wcj.NetException;
import com.wcj.channel.ChannelGroup;
import com.wcj.channel.Channels;
import com.wcj.channel.Groups;
import com.wcj.handler.Handler;
import com.wcj.protocol.ProtocolFactory;

public abstract class NetContext {
	public static WorkerPool workerPool;
	public static Channels channels;
	public static Groups groups;
	public static ChannelGroup world;
	public static ProtocolFactory protocolFactory;
	public static Handler handler;
	public static ExecutorService applicationThreadPool;

	static {
		workerPool = new WorkerPool(NetConfig.workersNum);
		channels = new Channels();
		groups = new Groups();
		world = groups.create(Groups.World);
		try {
			protocolFactory = (ProtocolFactory) Class.forName(NetConfig.protocalFactoryClass).newInstance();
		} catch (Exception e) {
			throw new NetException("'protocalFactoryClass' defined in 'config.properties' not found or cannot instantiate.", e);
		}
		try {
			handler = (Handler) Class.forName(NetConfig.handlerClass).newInstance();
		} catch (Exception e) {
			throw new NetException("'handlerClass' defined in 'config.properties' not found or cannot instantiate.", e);
		}
		applicationThreadPool = new ThreadPoolExecutor(
			NetConfig.applicationThreadPoolCoreSize, 
			NetConfig.applicationThreadPoolMaximumSize, 
			NetConfig.applicationThreadPoolKeepAliveSeconds, TimeUnit.SECONDS, 
			new LinkedBlockingDeque<Runnable>(), 
				new ThreadFactory() {
					@Override
					public Thread newThread(Runnable r) {
						return new Thread(r, "app logic thread:" + r.toString());
					}
				}
			);
	}
}
