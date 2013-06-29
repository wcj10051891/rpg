package com.wcj.core;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

import com.wcj.app.handler.AppHandler;
import com.wcj.app.handler.Dispatcher;
import com.wcj.app.protocol.JSONProtocolFactory;
import com.wcj.channel.ChannelGroup;
import com.wcj.channel.Channels;
import com.wcj.channel.Groups;
import com.wcj.handler.Handler;
import com.wcj.protocol.ProtocolFactory;

public class Context {
	public static WorkerPool workerPool;
	public static Channels channels;
	public static Groups groups;
	public static ChannelGroup world;
	public static ProtocolFactory protocolFactory;
	public static Handler handler;
	public static Dispatcher dispather;
	public static ExecutorService threadPool;

	public Context() {
		workerPool = new WorkerPool(3);
		channels = new Channels();
		groups = new Groups();
		world = groups.create(Groups.World);
		protocolFactory = new JSONProtocolFactory();
		handler = new AppHandler();
		dispather = new Dispatcher();
		threadPool = Executors.newCachedThreadPool(new ThreadFactory() {
			@Override
			public Thread newThread(Runnable r) {
				return new Thread(r, "app logic thread:" + r.toString());
			}
		});
	}
}
