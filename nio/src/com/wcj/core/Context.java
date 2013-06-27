package com.wcj.core;

import com.wcj.app.handler.AppHandler;
import com.wcj.app.protocol.JSONProtocolFactory;
import com.wcj.handler.Handler;
import com.wcj.protocol.ProtocolFactory;

public class Context {
	public static WorkerPool workerPool;
	public static Channels channels;
	public static ProtocolFactory protocolFactory;
	public static Handler handler;
	
	public Context() {
		workerPool = new WorkerPool(3);
		channels = new Channels();
		protocolFactory = new JSONProtocolFactory();
		handler = new AppHandler();
	}
}
