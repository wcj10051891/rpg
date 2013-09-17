package com.commonrpg.util;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class ClassUtils {
	private static final Logger log = LoggerFactory.getLogger(ClassUtils.class);

	public static List<Class<?>> scanPackage(String packageName) {
		try {
			URL resource = Object.class.getResource("/" + packageName.replace(".", "/"));
			return listDirectory(packageName, new File(resource.toURI()));
		} catch (Exception e) {
			log.error("scanPackage error.", e);
		}
		return null;
	}

	private static List<Class<?>> listDirectory(String packageName, File directory){
		String package_path = packageName.replace(".", File.separator) + File.separator;
		List<Class<?>> result = new ArrayList<Class<?>>();
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
