package cache;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import core.GameContext;

public class SaveQueue {
	private static final Log log = LogFactory.getLog(SaveQueue.class);
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
			
			queue.put(object, GameContext.timer.schedule(new Runnable() {
				@Override
				public void run() {
					synchronized (queue) {
						queue.remove(object);
					}
					save0(object);
				}
			}, delaySeconds, TimeUnit.SECONDS));
		}
	}
	
	public void saveNow(CacheObject object) {
		synchronized(queue){
			Future<?> future = queue.remove(object);
			if(future != null && !future.isDone())
				 future.cancel(true);
		}
		save0(object);
	}
	
	public void saveAllNow() {
		synchronized (queue) {
			for(Entry<CacheObject,Future<?>> entry : queue.entrySet()) {
				if(entry.getValue().cancel(true))
					save0(entry.getKey());
			}
			queue.clear();
		}
	}
	
	private void save0(CacheObject object) {
		try {
			log.info("save object:" + object);
			object.save();
		} catch (Exception e) {
			log.error("save object error:" + object, e);
		}
	}
}
