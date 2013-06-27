package com.wcj.protocol;

public class StringProtocolFactory extends ProtocolFactory<String> {
	
	private static StringDecoder decoder = new StringDecoder();
	private static StringEncoder encoder = new StringEncoder();

	@Override
	public Encoder<String> getEncoder() {
		return encoder;
	}

	@Override
	public Decoder<String> getDecoder() {
		return decoder;
	}
}
