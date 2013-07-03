package com.wcj.cache;

public abstract class CacheObject {
	public Object key;
	public abstract CacheObject load(Object key);
	public abstract void save();
}
