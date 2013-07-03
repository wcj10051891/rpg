package com.wcj.cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Cache {
	public Map<Object, CacheObject> cache = new ConcurrentHashMap<>();
	public SaveQueue saveQueue = new SaveQueue();
	
	public <T> CacheObject load(Object key, Class<T> objectClass, boolean loadFromDB) {
		return null;
	}
	
	public void saveAsync(CacheObject object) {
		this.saveQueue.saveAsync(object);
	}
	
	public void saveNow(CacheObject object) {
		this.saveQueue.saveNow(object);
	}
	
}
