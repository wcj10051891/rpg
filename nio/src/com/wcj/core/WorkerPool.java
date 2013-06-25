package com.wcj.core;

import java.util.LinkedList;

public class WorkerPool {
	private LinkedList<Worker> workers;

	public WorkerPool(int size) {
		workers = new LinkedList<>();
		for (int i = 0; i < size; i++)
			workers.add(new Worker());
	}

	public Worker aquire() {
		synchronized (workers) {
			return workers.removeFirst();
		}
	}

	public void returnWorker(Worker worker) {
		synchronized (workers) {
			workers.addLast(worker);
		}
	}
}
