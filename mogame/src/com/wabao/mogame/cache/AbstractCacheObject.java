package com.wabao.mogame.cache;

import java.util.List;
import java.util.Map;

public abstract class AbstractCacheObject<T> implements CacheObject<T> {
	
	public T id;

	public CacheObject<T> load(T id) {
		return null;
	}

	public Map<T, CacheObject<T>> gets(List<T> ids) {
		return null;
	}

	public void save() {
	}

	public void delete() {
	}
	
	@Override
	public T getId() {
		return id;
	}
}
