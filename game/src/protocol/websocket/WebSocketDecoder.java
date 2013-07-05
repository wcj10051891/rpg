package protocol.websocket;

import java.nio.ByteBuffer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.wcj.NetException;
import com.wcj.channel.ChannelContext;
import com.wcj.protocol.Decoder;
import com.wcj.util.Utils;

public class WebSocketDecoder extends Decoder{
	private static final Log log = LogFactory.getLog(WebSocketDecoder.class);
	private ByteBuffer sum = ByteBuffer.allocate(1024);

	@Override
	public Object decode(ChannelContext session, byte[] message) {
		Utils.ensureCapacity(sum, message.length);
		sum.put(message);
		int mark = sum.position();
		sum.flip();
		short head = -1;
		try {
			head = sum.getShort();
		} catch (Exception e) {
			sum.position(mark);
			return null;
		}
		int opcode = head >> 8 & 				1111;
		boolean isMask = (head &	0000000010000000) == 1;
		long payloadLen = head &		0000000001111111;
		int maskKey = -1;
		if(payloadLen == 126){
			try {
				payloadLen = sum.getShort();
			} catch (Exception e) {
				sum.position(mark);
				return null;
			}
		}else if(payloadLen == 127){
			try {
				payloadLen = sum.getLong();
			} catch (Exception e) {
				sum.position(mark);
				return null;
			}
		}
		if(isMask){
			try {
				maskKey = sum.getInt();
			} catch (Exception e) {
				sum.position(mark);
				return null;
			}
		}
		if(sum.remaining() < payloadLen){
			sum.position(mark);
			return null;
		}
		if(payloadLen <= Integer.MAX_VALUE){
			byte[] data = new byte[(int)payloadLen];
			sum.get(data, 0, (int)payloadLen);
			sum.compact();
			
			
			
		}else{
			throw new NetException("data too long.");
		}
		return null;
	}

}
