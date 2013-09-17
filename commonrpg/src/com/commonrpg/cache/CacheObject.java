package com.commonrpg.cache;

import java.util.List;
import java.util.Map;

public interface CacheObject<T> {
	CacheObject<T> load(T id);

	Map<T, CacheObject<T>> gets(List<T> ids);

	void save();

	void delete();

	T getId();
}
