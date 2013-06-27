package com.wcj.app.protocol;

import java.nio.ByteBuffer;

import net.sf.json.JSONObject;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import com.wcj.NioException;
import com.wcj.protocol.Decoder;
import com.wcj.util.Utils;

public class JSONDecoder extends Decoder {
	
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
			sum.reset();
			return null;
		}
		if(sum.remaining() < length){
			sum.reset();
			return null;
		}
		try {

			byte[] body = new byte[length]; 
			sum.get(body, 0, length);
			sum.compact();
			
			String json = new String(body);
			
			JSONObject de = JSONObject.fromObject(json);
			Object instance;
			try {
				instance = Class.forName(String.valueOf(de.get("clazz"))).newInstance();
			} catch (Exception e) {
				throw new NioException("packet decode error.", e);
			}
			BeanWrapper bw = new BeanWrapperImpl(instance);
			JSONObject data = (JSONObject) de.get("data");
			for (Object key : data.keySet()) {
				String k = key.toString();
				bw.setPropertyValue(k, data.get(k));
			}
			return instance;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
