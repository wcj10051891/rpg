package com.wcj.core;

import com.wcj.handler.Handler;
import com.wcj.protocol.ProtocolFactory;
import com.wcj.protocol.StringProtocolFactory;

public class Context {
	public static WorkerPool workerPool;
	public static Channels channels;
	public static ProtocolFactory<String> protocolFactory;
	public static Handler handler;
	
	public Context() {
		workerPool = new WorkerPool(3);
		channels = new Channels();
		protocolFactory = new StringProtocolFactory();
		handler = new Handler();
	}
}
