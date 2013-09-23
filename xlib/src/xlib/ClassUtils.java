package xlib;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public abstract class ClassUtils {
	
	@SuppressWarnings("unchecked")
	public static <T> T cast(Object value, Class<T> toType) {
		if(toType == Byte.class || toType == byte.class)
			return value instanceof Byte ? (T)value : (T) Byte.valueOf(String.valueOf(value));
		if(toType == Boolean.class || toType == boolean.class)
			return value instanceof Boolean ? (T)value : (T) Byte.valueOf(String.valueOf(value));
		if(toType == Short.class || toType == short.class)
			return value instanceof Short ? (T)value : (T) Byte.valueOf(String.valueOf(value));
		if(toType == Integer.class || toType == int.class)
			return value instanceof Integer ? (T)value : (T) Byte.valueOf(String.valueOf(value));
		if(toType == Long.class || toType == long.class)
			return value instanceof Long ? (T)value : (T) Byte.valueOf(String.valueOf(value));
		if(toType == Float.class || toType == float.class)
			return value instanceof Float ? (T)value : (T) Byte.valueOf(String.valueOf(value));
		if(toType == Double.class || toType == double.class)
			return value instanceof Double ? (T)value : (T) Byte.valueOf(String.valueOf(value));

		return (T)value;
	}

	public static List<Class<?>> scanPackage(String packageNames) {
		try {
			List<Class<?>> result = new ArrayList<Class<?>>();
			for(String packageName : packageNames.split(",")) {
				result.addAll(listDirectory(packageName, 
					new File(ClassUtils.class.getResource("/" + packageName.replace(".", "/")).toURI())));
			}
			return result;
		} catch (Exception e) {
			throw new IllegalArgumentException("scan package error.", e);
		}
	}

	private static List<Class<?>> listDirectory(String packageName, File directory) throws Exception {
		String package_path = packageName.replace(".", File.separator) + File.separator;
		List<Class<?>> result = new ArrayList<Class<?>>();
		for (File file : directory.listFiles()) {
			if(file.isDirectory()){
				result.addAll(listDirectory(packageName, file));
			}else{
				if(file.getName().endsWith(".class")){
					String path = file.getCanonicalPath();
					String classFullname = path.substring(path.indexOf(package_path), path.length() - 6).replace(File.separator, ".");
					result.add(Class.forName(classFullname));
				}
			}
		}
		return result;
	}
}
