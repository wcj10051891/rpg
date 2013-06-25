package com.wcj.util;

import java.nio.ByteBuffer;

public class Utils {
	public static ByteBuffer ensureCapacity(ByteBuffer src){
		if(src.limit() >= src.capacity()){
			int oldPosition = src.position();
			ByteBuffer newBuffer = ByteBuffer.allocate((int)(src.capacity() * 1.5f));
			src.position(0);
			newBuffer.put(src);
			newBuffer.position(oldPosition);
			return newBuffer;
		}
		return src;
	}
	
	public static ByteBuffer ensureCapacity(ByteBuffer src, int remain){
		if(src.remaining() < remain){
			if(src.capacity() - src.limit() >= remain){
				src.limit(src.capacity());
				return src;
			}
			int oldPosition = src.position();
			ByteBuffer newBuffer = ByteBuffer.allocate((int)(src.capacity() * 1.5f));
			src.position(0);
			newBuffer.put(src);
			newBuffer.position(oldPosition);
			newBuffer.limit(src.capacity());
			return newBuffer;
		}
		return src;
	}
}
