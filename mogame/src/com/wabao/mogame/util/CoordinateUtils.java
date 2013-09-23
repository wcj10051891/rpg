package com.wabao.mogame.util;

public abstract class CoordinateUtils {
	public static int toInt(short x, short y) {
		return x << 16 | y;
	}
	
	public static short[] toShort(int coordinate) {
		return new short[]{(short)(coordinate >> 16), (short)coordinate};
	}
}
