package com.wcj.app.protocol;

import java.nio.ByteBuffer;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import com.wcj.protocol.Decoder;
import com.wcj.util.Utils;

public class JSONDecoder extends Decoder {
	private static final Logger log = Logger.getLogger(JSONDecoder.class);
	private ByteBuffer sum = ByteBuffer.allocate(1024);

	@Override
	public Object decode(byte[] message) {
		Utils.ensureCapacity(sum, message.length);
		sum.put(message);
		sum.mark();
		sum.flip();
		int length = 0;
		try {
			length = sum.getInt();
		} catch (Exception e) {
			try {
				sum.reset();
			} catch (Exception ex) {
				log.warn("decode buffer reset mark error.", ex);
			}
			return null;
		}
		try {
			if(sum.remaining() < length){
				try {
					sum.reset();
				} catch (Exception ex) {
					log.warn("decode buffer reset mark error.", ex);
				}
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
