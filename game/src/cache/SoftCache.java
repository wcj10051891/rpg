package cache;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@SuppressWarnings("unchecked")
public class SoftCache {
	private static final Log log = LogFactory.getLog(SoftCache.class);
    private ReferenceQueue<CacheObject> refQueue = new ReferenceQueue<>();
	private ConcurrentHashMap<String, SoftReference<CacheObject>> softCache = new ConcurrentHashMap<>();
	private AtomicInteger putCount = new AtomicInteger();
	private static int removeQueuedSize = 1000;
	private Lock cacheLock = new ReentrantLock();
	private SaveQueue saveQueue = new SaveQueue();
	
	public <T extends CacheObject> T get(Object key, Class<T> objectClass, boolean loadEntity) {
		if(key == null || key.equals("0"))
			return null;
		String k = k(key, objectClass);
		try {
			cacheLock.lock();
			SoftReference<CacheObject> ref = softCache.get(k);
			if(ref != null && ref.get() != null)
				return (T) ref.get();
			
			if(!loadEntity)
				return null;
			
			try {
				T newObject = (T) objectClass.newInstance();
				newObject.load(key);
				putIfAbsent(k, new CacheObjectReference<CacheObject>(k, newObject, refQueue));
				return newObject;
			} catch (Exception e) {
				log.error("instance object error.", e);
				return null;
			}
		}finally{
			cacheLock.unlock();
		}
	}
	
	private SoftReference<CacheObject> putIfAbsent(String key, SoftReference<CacheObject> value) {
		SoftReference<CacheObject> old = this.softCache.putIfAbsent(key, value);
		if(putCount.incrementAndGet() % removeQueuedSize == 0)
			removeStaleEntries();
		return old;
	}
	
	private static class CacheObjectReference<T extends CacheObject> extends SoftReference<T> {
		public String key;
		public CacheObjectReference(String key, T referent, ReferenceQueue<? super T> q) {
			super(referent, q);
			this.key = key;
		}
	}
	
	private void removeStaleEntries() {
    	try {
			cacheLock.lock();
	        for (Reference<? extends CacheObject> x; (x = refQueue.poll()) != null; )
            	softCache.remove(((CacheObjectReference<? extends CacheObject>)x).key);
    	}finally{
    		cacheLock.unlock();
        }
    }
	
	public <T extends CacheObject> Map<Object, T> gets(List<Object> keys, Class<T> objectClass, boolean loadFromDB) {
		if(keys == null || keys.isEmpty())
			return null;
		int size = keys.size();
		Map<Object, CacheObject> inCacheObjects = new HashMap<>(size);
		List<Object> notInCache = new ArrayList<>(size);
		for(Object key : keys) {
			if(key == null || key.equals("0"))
				continue;
			T target = get(key, objectClass, false);
			if(target == null)
				notInCache.add(key);
			else
				inCacheObjects.put(key, target);
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
							SoftReference<CacheObject> old = putIfAbsent(k, ref);
							if(old == null)
								inCacheObjects.put(key, value);
							else if(old.get() == null) {
								putIfAbsent(k, ref);
								inCacheObjects.put(key, value);
							} else {
								inCacheObjects.put(key, old.get());
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
		for(Object key : keys) {
			T t = (T) inCacheObjects.get(key);
			if(t != null)
				result.put(key, t);
		}
		return result;
	}
	
	public <T extends CacheObject> T put(T newObject) {
		if(newObject.key == null || newObject.key.equals("0"))
			return null;
		String k = k(newObject.key, newObject.getClass());
		try {
			cacheLock.lock();
			SoftReference<CacheObject> ref = softCache.get(k);
			if(ref != null && ref.get() != null)
				return (T) ref.get();
			
			try {
				putIfAbsent(k, new CacheObjectReference<CacheObject>(k, newObject, refQueue));
				return newObject;
			} catch (Exception e) {
				log.error("instance object error.", e);
				return null;
			}
		}finally{
			cacheLock.unlock();
		}
	}
	
	public <T extends CacheObject> void remove(T object, boolean deleteEntity) {
		remove(object.key, object.getClass(), deleteEntity);
	}
	
	public <T extends CacheObject> void remove(Object key, Class<T> objectClass, boolean deleteEntity) {
		if(key == null || key.equals("0"))
			return;
		String k = k(key, objectClass);
		try {
			cacheLock.lock();
			SoftReference<CacheObject> ref = this.softCache.remove(k);
			if(deleteEntity && ref != null && ref.get() != null) {
				try {
					ref.get().delete();
				} catch (Exception e) {
					log.error("instance object error.", e);
				}
			}
		}finally{
			cacheLock.unlock();
		}
	}

	public void saveAsync(CacheObject object) {
		this.saveQueue.saveAsync(object);
	}

	public void saveNow(CacheObject object) {
		this.saveQueue.saveNow(object);
	}
	
	public void saveAllNow() {
		this.saveQueue.saveAllNow();
	}
	
	private String k(Object key, Class<?> objectClass) {
		return objectClass.getSimpleName() + "_" + key.toString();
	}
}
