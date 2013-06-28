package com.wcj.core;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class WorkerPool {
	private int size;
	private List<Worker> workers;
	private AtomicInteger count;

	public WorkerPool(int size) {
		this.size = size;
		this.count = new AtomicInteger();
		workers = new ArrayList<>(size);
		for (int i = 0; i < size; i++)
			workers.add(new Worker());
	}

	public Worker take() {
		return workers.get(this.count.incrementAndGet() % this.size);
	}
}