package com.wcj.util;

import java.io.File;
import java.net.URL;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

public class Utils {
	private static final Logger log = Logger.getLogger(Utils.class);
	
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
	
	public static List<Class<?>> scanPackage(String packageName){
		try {
			URL resource = Object.class.getResource("/" + packageName.replace(".", "/"));
			File file = new File(resource.toURI());
			return listDirectory(packageName, file);
		} catch (Exception e) {
			log.error("scanPackage error.", e);
		}
		return null;
	}
	
	private static List<Class<?>> listDirectory(String packageName, File directory){
		String package_path = packageName.replace(".", File.separator) + File.separator;
		List<Class<?>> result = new ArrayList<>();
		for (File file : directory.listFiles()) {
			if(file.isDirectory()){
				result.addAll(listDirectory(packageName, file));
			}else{
				if(file.getName().endsWith(".class")){
					try {
						String path = file.getCanonicalPath();
						String classFullname = path.substring(path.indexOf(package_path), path.length() - 6).replace(File.separator, ".");
						result.add(Class.forName(classFullname));
					} catch (Exception e) {
						log.error("scanPackage load class error.", e);
					}
				}
			}
		}
		return result;
	}
	
}
