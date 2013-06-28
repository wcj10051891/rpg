package com.wcj.core;

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
	
	public Context() {
		workerPool = new WorkerPool(3);
		channels = new Channels();
		groups = new Groups();
		world = groups.create("world");
		protocolFactory = new JSONProtocolFactory();
		handler = new AppHandler();
		dispather = new Dispatcher();
	}
}
