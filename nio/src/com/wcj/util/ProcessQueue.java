package com.wcj.util;

import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingDeque;

public class ProcessQueue {
	private Queue<Runnable> tasks;
	private ExecutorService threadPool;
	private Future<?> processing;

	public ProcessQueue(ExecutorService threadPool) {
		this.tasks = new LinkedBlockingDeque<>();
		this.threadPool = threadPool;
	}

	public void submit(Runnable task){
		this.tasks.offer(task);
		if(processing == null || processing.isDone())
			processing = this.threadPool.submit(new Runnable() {
				@Override
				public void run() {
					Runnable element;
					while((element = tasks.poll()) != null)
						element.run();
				}
			});
	}
}