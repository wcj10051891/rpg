package com.wabao.mogame.util;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.Reader;
import java.util.Scanner;

public abstract class IoUtils {
	public static String read(String filepath) {
		StringBuilder result = new StringBuilder();
		Scanner scan = null;
		try {
			scan = new Scanner(bufferedIn(new FileInputStream(new File(filepath))));
			while(scan.hasNextLine()) {
				result.append(scan.nextLine());
				if(scan.hasNextLine())
					result.append(System.lineSeparator());
			}
			return result.toString();
		} catch (Exception e) {
			throw new RuntimeException("IoUtils.read error.", e);
		} finally {
			if(scan != null)
				scan.close();
		}
	}
	
	public static BufferedInputStream bufferedIn(InputStream in) {
		return new BufferedInputStream(in);
	}
	
	public static BufferedReader bufferedReader(Reader reader) {
		return new BufferedReader(reader);
	}
	
	public static void main(String[] args) {
		System.out.println(read("src/com/wabao.mogame/cache/CacheObject.java"));
	}
}
