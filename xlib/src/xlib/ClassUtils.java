package xlib;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

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
				URL url = ClassUtils.class.getResource(packageName.startsWith("/") ? "" : "/" + packageNames.replace(".", "/"));
				if("jar".equals(url.getProtocol())) {
					result.addAll(listDirectoryInJar(url));
				} else {
					result.addAll(listDirectory(packageName, new File(url.toURI())));
				}
			}
			return result;
		} catch (Exception e) {
			throw new IllegalArgumentException("scan package error.", e);
		}
	}
	
	private static List<Class<?>> listDirectoryInJar(URL url) throws Exception {
		String filePath = url.getFile();
		if(!filePath.startsWith("file:"))
			throw new FileNotFoundException(
				url + " cannot be resolved to absolute file path because it does not reside in the file system.");
		
		String[] s = filePath.split("!");
		String jarPath = s[0].replace("file:", "");
		String packagePrefix = s[1].substring(1);
		JarFile jarFile = new JarFile(new File(jarPath));
		try {
			List<Class<?>> result = new ArrayList<Class<?>>();
			Enumeration<JarEntry> entries = jarFile.entries();
			while(entries.hasMoreElements()) {
				JarEntry element = entries.nextElement();
				String elementName = element.getName();
				if(elementName.endsWith(".class") && elementName.startsWith(packagePrefix))
					result.add(Class.forName(elementName.replace("/", ".").replace(".class", "")));
			}
			return result;
		} finally {
			jarFile.close();
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
	
	public static void main(String[] args) throws Exception {
		List<Class<?>> classes = listDirectoryInJar(ClassUtils.class.getResource("/flex/messaging/log"));
		for (Class<?> class1 : classes) {
			System.out.println(class1.getName());
		}
	}
}
