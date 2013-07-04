package com.wcj.protocol;

public abstract class ProtocolFactory {
	public abstract Encoder getEncoder();
	public abstract Decoder getDecoder();
}
