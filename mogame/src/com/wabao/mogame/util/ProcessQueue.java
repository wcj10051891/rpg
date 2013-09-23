package com.wabao.mogame.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProcessQueue {
	private static final Logger log = LoggerFactory.getLogger(ProcessQueue.class);
	private LockFreeDeque<Runnable> taskQueue;
	private ExecutorService threadPool;
	private Future<?> processing;

	public ProcessQueue(ExecutorService threadPool) {
		this.taskQueue = new LockFreeDeque<Runnable>();
		this.threadPool = threadPool;
	}

	public void submit(Runnable task) {
		this.taskQueue.offer(task);
		if (processing == null || processing.isDone())
			processing = this.threadPool.submit(new Runnable() {
				@Override
				public void run() {
					while (true) {
						try {
							taskQueue.peek().run();
						} catch (Exception e) {
							log.error("process queue run error.", e);
						}
						if (taskQueue.isEmptyAfterPoll())
							break;
					}
				}
			});
	}
}