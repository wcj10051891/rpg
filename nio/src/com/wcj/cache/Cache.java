package com.wcj.cache;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.log4j.Logger;

public class Cache {
	private static final Logger log = Logger.getLogger(Cache.class);
	private ConcurrentHashMap<String, SoftReference<CacheObject>> softCache = new ConcurrentHashMap<>();
	private Lock cacheLock = new ReentrantLock();
	private SaveQueue saveQueue = new SaveQueue();
	
	@SuppressWarnings("unchecked")
	public <T extends CacheObject> T get(Object key, Class<T> objectClass, boolean loadFromDB) {
		if(key == null || key.equals("0"))
			return null;
		String k = k(key, objectClass);
		try {
			cacheLock.lock();
			SoftReference<CacheObject> ref = softCache.get(k);
			if(ref == null || ref.get() == null) {
				if(!loadFromDB)
					return null;
				
				try {
					T newObject = (T) objectClass.newInstance();
					newObject.get(key);
					softCache.put(k, new SoftReference<CacheObject>(newObject));
					return newObject;
				} catch (Exception e) {
					log.error("instance object error.", e);
					return null;
				}
			}
			return (T) ref.get();
		}finally{
			cacheLock.unlock();
		}
	}
	
	@SuppressWarnings("unchecked")
	public <T extends CacheObject> Map<Object, T> gets(List<Object> keys, Class<T> objectClass, boolean loadFromDB) {
		if(keys == null || keys.isEmpty())
			return null;
		int size = keys.size();
		Map<Object, CacheObject> objects = new HashMap<>(size);
		List<Object> notInCache = new ArrayList<>(size);
		for(Object key : keys) {
			if(key == null)
				continue;
			T target = get(key, objectClass, false);
			if(target == null)
				notInCache.add(key);
			else
				objects.put(key, target);
		}
		if(loadFromDB && !notInCache.isEmpty()) {
			try {
				cacheLock.lock();
				Map<Object, CacheObject> gets = objectClass.newInstance().gets(notInCache);
				if(!gets.isEmpty()) {
					for (Entry<Object, CacheObject> entrys : gets.entrySet()) {
						CacheObject value = entrys.getValue();
						if(value != null) {
							Object key = entrys.getKey();
							String k = k(key, objectClass);
							SoftReference<CacheObject> ref = new SoftReference<CacheObject>(value);
							SoftReference<CacheObject> old = this.softCache.putIfAbsent(k, ref);
							if(old == null)
								objects.put(key, value);
							else if(old.get() == null) {
								this.softCache.put(k, ref);
								objects.put(key, value);
							} else {
								objects.put(key, old.get());
							}
						}
					}
				}
			} catch (Exception e) {
				log.error("instance object error.", e);
			} finally {
				cacheLock.unlock();
			}
		}
		Map<Object, T> result = new LinkedHashMap<>(size);
		for(Object key : keys)
			result.put(key, (T) objects.get(key));

		return result;
	}
	
	private String k(Object key, Class<?> objectClass) {
		return objectClass.getSigners() + "_" + key.toString();
	}
	
	public void saveAsync(CacheObject object) {
		this.saveQueue.saveAsync(object);
	}
	
	public void saveNow(CacheObject object) {
		this.saveQueue.saveNow(object);
	}
	
}
