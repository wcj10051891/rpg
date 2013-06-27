package com.wcj.protocol;

public abstract class Encoder<T> {
	public abstract byte[] encode(T data);
}
