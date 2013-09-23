package com.wabao.mogame.cache;

import java.util.Map.Entry;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wabao.mogame.service.Services;
import com.wabao.mogame.util.LockFreeDeque;

public class SaveQueue {
	private static final Logger log = LoggerFactory.getLogger(SaveQueue.class);
	private ConcurrentHashMap<CacheObject<?>, Future<?>> saveFutures = new ConcurrentHashMap<CacheObject<?>, Future<?>>();
	private LockFreeDeque<Runnable> queue = new LockFreeDeque<Runnable>();
	private static Integer savePerSecondsMax = 1000;
	private static final Future<?> FUTURE_PLACEHOLDER = new FutureTask<Object>(new Callable<Object>() {
		@Override
		public Object call() throws Exception {
			return null;
		}
	});
	
	private Runnable savePerSecond = new Runnable() {
		@Override
		public void run() {
			for(int i = 0; i < savePerSecondsMax; i++) {
				Runnable runnable = null;
				try {
					runnable = queue.pollLast();
					if(runnable == null)
						break;
					runnable.run();
				} catch (Exception e) {
					log.error("per second save error:" + 
							(runnable instanceof SaveTask ? ((SaveTask)runnable).instance.getId() : runnable), e);
				}
			}
		}
	};
	public SaveQueue() {
		//每秒保存
		Services.timerService.jdkScheduler.scheduleAtFixedRate(savePerSecond, 1, 1, TimeUnit.SECONDS);
	}
	
	public void saveAsync(final CacheObject<?> object) {
		Future<?> future = saveFutures.get(object);
		if(future != null && !future.isDone())
			return;
		
		int delaySeconds = 0;
		if(object.getClass().isAnnotationPresent(SaveDelay.class))
			delaySeconds = object.getClass().getAnnotation(SaveDelay.class).seconds();
		
		if(delaySeconds <= 0) {
			saveFutures.put(object, FUTURE_PLACEHOLDER);
			queue.addFirst(new SaveTask(object) {
				@Override
				public void run() {
					saveFutures.remove(object);
					save0(object);
				}
			});
		} else {
			ScheduledFuture<?> sf = Services.timerService.jdkScheduler.schedule(new SaveTask(object) {
				boolean inQueue = false;
				@Override
                public void run() {
                    if (!inQueue) {
                        inQueue = true;
                        queue.addFirst(this);
                        return;
                    }
                    saveFutures.remove(object);
                    save0(object);
                }
			}, delaySeconds, TimeUnit.SECONDS);
			Future<?> put = saveFutures.putIfAbsent(object, sf);
			if(put == null) {
				log.info("[delay {}s save] {} key={}", delaySeconds, object, object.getId());
			} else {
				put.cancel(true);
			}
		}
	}
	
	public void saveNow(CacheObject<?> object) {
		Future<?> future = saveFutures.remove(object);
		if(future != null && !future.isDone())
			 future.cancel(true);
		save0(object);
	}
	
	public void saveAllNow() {
		for(Entry<CacheObject<?>, Future<?>> entry : saveFutures.entrySet()) {
			if(entry.getValue().cancel(true))
				save0(entry.getKey());
		}
		saveFutures.clear();
	}
	
	public void saveOnStop() {
		saveAllNow();
		savePerSecondsMax = Integer.MAX_VALUE;
        while (queue.size() > 0) {
            try {Thread.sleep(1000);} catch (Exception e){}
        }
	}
	
	public void submitSaveTask(Runnable saveTask) {
		queue.addFirst(saveTask);
	}
	
	private void save0(CacheObject<?> object) {
		try {
			log.info("save object:{}", object);
			object.save();
		} catch (Exception e) {
			log.error("save object error:" + object, e);
		}
	}
	
	static abstract class SaveTask implements Runnable{
		public CacheObject<?> instance;
		public SaveTask(CacheObject<?> instance) {
			this.instance = instance;
		}
	}
}
