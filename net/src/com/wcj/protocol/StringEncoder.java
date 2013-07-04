package com.wcj.protocol;

import java.nio.charset.Charset;

public class StringEncoder extends Encoder {

	private Charset charset = Charset.defaultCharset();

	public StringEncoder() {
	}

	public StringEncoder(String charset) {
		this.charset = Charset.forName(charset);
	}

	@Override
	public byte[] encode(Object message) {
		return String.valueOf(message).getBytes(charset);
	}

}
