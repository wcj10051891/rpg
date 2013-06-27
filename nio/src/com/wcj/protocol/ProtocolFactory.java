package com.wcj.protocol;

public abstract class ProtocolFactory<T> {
	public abstract Encoder<T> getEncoder();
	public abstract Decoder<T> getDecoder();
}
