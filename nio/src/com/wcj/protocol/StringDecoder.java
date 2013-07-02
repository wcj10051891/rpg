package com.wcj.protocol;

import java.nio.charset.Charset;

import com.wcj.channel.ChannelContext;

public class StringDecoder extends Decoder {

	private Charset charset = Charset.defaultCharset();

	public StringDecoder() {
	}

	public StringDecoder(String charset) {
		this.charset = Charset.forName(charset);
	}

	@Override
	public Object decode(ChannelContext session, byte[] message) {
		return new String(message, charset);
	}

}
