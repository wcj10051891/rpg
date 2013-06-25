package com.wcj.core;

public class Context {
	public static WorkerPool workerPool;
	public static Channels channels;
	
	public Context() {
		workerPool = new WorkerPool(3);
		channels = new Channels();
	}
}
