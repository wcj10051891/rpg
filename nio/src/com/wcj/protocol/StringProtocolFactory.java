package com.wcj.protocol;

public class StringProtocolFactory extends ProtocolFactory {
	
	private static StringDecoder decoder = new StringDecoder();
	private static StringEncoder encoder = new StringEncoder();

	@Override
	public Encoder getEncoder() {
		return encoder;
	}

	@Override
	public Decoder getDecoder() {
		return decoder;
	}
}
