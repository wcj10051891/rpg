package cache;

import java.util.List;
import java.util.Map;

public abstract class CacheObject {
	public Object key;
	public abstract CacheObject load(Object key);
	public abstract Map<Object, CacheObject> gets(List<Object> keys);
	public abstract void save();
	public abstract void delete();
	
	public CacheObject() {
	}
}
