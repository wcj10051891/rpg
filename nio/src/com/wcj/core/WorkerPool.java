package com.wcj.core;

import java.util.LinkedList;

import com.wcj.util.RandomUtils;

public class WorkerPool {
	private LinkedList<Worker> workers;

	public WorkerPool(int size) {
		workers = new LinkedList<>();
		for (int i = 0; i < size; i++)
			workers.add(new Worker());
	}

	public Worker get() {
		return workers.get(RandomUtils.nextRandomInt(0, workers.size() - 1));
	}
}