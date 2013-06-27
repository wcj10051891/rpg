package com.wcj.protocol;


public abstract class Decoder<T> {
	public abstract T decode(byte[] data);
}
