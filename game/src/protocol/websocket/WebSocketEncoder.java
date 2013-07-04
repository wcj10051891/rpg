package protocol.websocket;

import com.wcj.protocol.Encoder;

public class WebSocketEncoder extends Encoder{
	
	public static final WebSocketEncoder INSTANCE = new WebSocketEncoder();

	@Override
	public byte[] encode(Object message) {
		return null;
	}

}
