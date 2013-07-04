package protocol.json;

import java.nio.ByteBuffer;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import com.wcj.channel.ChannelContext;
import com.wcj.protocol.Decoder;
import com.wcj.util.Utils;

public class JSONDecoder extends Decoder {
	private static final Log log = LogFactory.getLog(JSONDecoder.class);
	private ByteBuffer sum = ByteBuffer.allocate(1024);

	@Override
	public Object decode(ChannelContext session, byte[] message) {
		Utils.ensureCapacity(sum, message.length);
		sum.put(message);
		int mark = sum.position();
		sum.flip();
		int length = 0;
		try {
			length = sum.getInt();
		} catch (Exception e) {
			sum.position(mark);
			return null;
		}
		try {
			if(sum.remaining() < length){
				sum.position(mark);
				return null;
			}
			byte[] body = new byte[length]; 
			sum.get(body, 0, length);
			sum.compact();
			
			String json = new String(body);
			
			JSONObject de = JSONObject.fromObject(json);
			Object instance = Class.forName(String.valueOf(de.get("clazz"))).newInstance();
			BeanWrapper bw = new BeanWrapperImpl(instance);
			JSONObject data = (JSONObject) de.get("data");
			for (Object key : data.keySet()) {
				String k = key.toString();
				bw.setPropertyValue(k, data.get(k));
			}
			return instance;
		} catch (Exception e) {
			log.error("packet decode error.", e);
			sum.clear();
		}
		return null;
	}

}
