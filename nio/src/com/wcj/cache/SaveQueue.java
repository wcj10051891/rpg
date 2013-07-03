package com.wcj.cache;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import com.wcj.core.Context;

public class SaveQueue {
	private Map<CacheObject, Future<?>> queue = new LinkedHashMap<>();
	
	public void saveAsync(final CacheObject object) {
		synchronized(queue){
			Future<?> future = queue.get(object);
			if(future != null && !future.isDone())
				return;
			
			int delaySeconds = 0;
			if(object.getClass().isAnnotationPresent(SaveDelay.class))
				delaySeconds = object.getClass().getAnnotation(SaveDelay.class).seconds();
			
			if(delaySeconds < 0)
				delaySeconds = 0;
			
			queue.put(object, Context.timer.schedule(new Runnable() {
				@Override
				public void run() {
					synchronized (queue) {
						queue.remove(object);
					}
					object.save();
				}
			}, delaySeconds, TimeUnit.SECONDS));
		}
	}
	
	public void saveNow(CacheObject object) {
		synchronized(queue){
			Future<?> future = queue.get(object);
			if(future != null) {
				 if(!future.isDone())
					 future.cancel(false);
				 
				 queue.remove(object);
			}
		}
		object.save();
	}
	
	public void saveAllNow() {
		synchronized (queue) {
			for(Entry<CacheObject,Future<?>> entry : queue.entrySet()) {
				if(entry.getValue().cancel(false))
					entry.getKey().save();
			}
			queue.clear();
		}
	}
}
