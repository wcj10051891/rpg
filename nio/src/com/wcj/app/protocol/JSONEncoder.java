package com.wcj.app.protocol;

import java.nio.ByteBuffer;

import net.sf.json.JSONObject;

import com.wcj.protocol.Encoder;

public class JSONEncoder extends Encoder {
	
	public static final JSONEncoder INSTANCE = new JSONEncoder();

	@Override
	public byte[] encode(Object message) {
		JSONObject json = new JSONObject();
		json.put("clazz", message.getClass().getName());
		json.put("data", JSONObject.fromObject(message));
		byte[] body = json.toString().getBytes();
		ByteBuffer packet = ByteBuffer.allocate(4 + body.length);
		packet.putInt(body.length);
		packet.put(body);
		return packet.array();
	}
}
