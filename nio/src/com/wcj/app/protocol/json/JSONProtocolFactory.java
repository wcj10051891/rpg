package com.wcj.app.protocol.json;

import com.wcj.protocol.Decoder;
import com.wcj.protocol.Encoder;
import com.wcj.protocol.ProtocolFactory;

public class JSONProtocolFactory extends ProtocolFactory {

	@Override
	public Encoder getEncoder() {
		return JSONEncoder.INSTANCE;
	}

	@Override
	public Decoder getDecoder() {
		return new JSONDecoder();
	}

}
