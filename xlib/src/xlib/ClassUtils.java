package xlib;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public abstract class ClassUtils {

	public static List<Class<?>> scanPackage(String packageName) {
		try {
			URL resource = Object.class.getResource("/" + packageName.replace(".", "/"));
			return listDirectory(packageName, new File(resource.toURI()));
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
