package com.wcj.protocol;

import java.nio.charset.Charset;

public class StringDecoder extends Decoder {

	private Charset charset = Charset.defaultCharset();

	public StringDecoder() {
	}

	public StringDecoder(String charset) {
		this.charset = Charset.forName(charset);
	}

	@Override
	public Object decode(byte[] message) {
		return new String(message, charset);
	}

}
