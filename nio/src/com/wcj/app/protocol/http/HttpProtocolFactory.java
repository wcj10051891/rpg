package com.wcj.app.protocol.http;

import com.wcj.protocol.Decoder;
import com.wcj.protocol.Encoder;
import com.wcj.protocol.ProtocolFactory;

public class HttpProtocolFactory extends ProtocolFactory {

	@Override
	public Encoder getEncoder() {
		return HttpServerEncoder.INSTANCE;
	}

	@Override
	public Decoder getDecoder() {
		return new HttpServerDecoder();
	}

}
