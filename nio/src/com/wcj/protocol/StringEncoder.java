package com.wcj.protocol;

import java.nio.charset.Charset;

public class StringEncoder extends Encoder<String> {

	private Charset charset = Charset.defaultCharset();

	public StringEncoder() {
	}

	public StringEncoder(String charset) {
		this.charset = Charset.forName(charset);
	}

	@Override
	public byte[] encode(String data) {
		return data.getBytes(charset);
	}

}
