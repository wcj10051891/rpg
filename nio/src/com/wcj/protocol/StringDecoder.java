package com.wcj.protocol;

import java.nio.charset.Charset;

public class StringDecoder extends Decoder<String> {

	private Charset charset = Charset.defaultCharset();

	public StringDecoder() {
	}

	public StringDecoder(String charset) {
		this.charset = Charset.forName(charset);
	}

	@Override
	public String decode(byte[] data) {
		return new String(data, charset);
	}

}
