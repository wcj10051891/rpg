package com.commonrpg.service;

import java.util.concurrent.LinkedTransferQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.commonrpg.core.AppConfig;
import com.commonrpg.util.ThreadNameFactory;

public class ThreadService implements Service {
	private ThreadPoolExecutor threadPool; // 线程池

	@Override
	public void start() throws Exception {
		threadPool = new ThreadPoolExecutor(
				AppConfig.THREAD_CORE_POOL_SIZE, 
				AppConfig.THREAD_MAX_POOL_SIZE, 
				10,
				TimeUnit.MINUTES, 
				new LinkedTransferQueue<Runnable>(), 
				new ThreadNameFactory("business logic"));
		threadPool.allowCoreThreadTimeOut(true);
	}

	@Override
	public void stop() throws Exception {
		threadPool.shutdownNow();
	}

	public void submit(Runnable task) {
		threadPool.execute(task);
	}

	public int getActiveCount() {
		return threadPool.getActiveCount();
	}

	public int getPoolSize() {
		return threadPool.getPoolSize();
	}

	public String getStatus() {
		return "poolSize=" + getPoolSize() + " queueSize=" + threadPool.getQueue().size() + " activeSize=" + getActiveCount();
	}
}
