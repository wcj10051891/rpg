package protocol.websocket;

import com.wcj.protocol.Decoder;
import com.wcj.protocol.Encoder;
import com.wcj.protocol.ProtocolFactory;

public class WebSocketProtocolFactory extends ProtocolFactory {

	@Override
	public Encoder getEncoder() {
		return WebSocketEncoder.INSTANCE;
	}

	@Override
	public Decoder getDecoder() {
		return new WebSocketDecoder();
	}

}
