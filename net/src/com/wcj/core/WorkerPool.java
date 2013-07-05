package com.wcj.core;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class WorkerPool {
	private int size;
	private List<Worker> workers;
	private AtomicInteger count;
	public ExecutorService workersThreadPool;

	public WorkerPool(int size) {
		this.size = size;
		this.count = new AtomicInteger();
		workers = new ArrayList<>(size);
		for (int i = 0; i < size; i++)
			workers.add(new Worker());

		workersThreadPool = Executors.newFixedThreadPool(size, 
			new ThreadFactory() {
			    @Override
			    public Thread newThread(Runnable r) {
			    	return new Thread(r, "worker working thread:" + r.toString());
			    }
			}
		);
	}
	
	public void submit(Worker worker) {
		this.workersThreadPool.execute(worker);
	}

	public Worker take() {
		return workers.get(Math.abs(this.count.incrementAndGet() % this.size));
	}
}