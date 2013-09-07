package gen;

import java.util.HashMap;
import java.util.Map;

import com.thoughtworks.qdox.model.JavaClass;
import com.thoughtworks.qdox.model.Type;

/**
 * Java和AS3类型的映射关系
 */
public abstract class JavaAS3TypeMapping {
	
	private static String[][] j2as = new String[][] {
        {"java.lang.Number", "Number"},
		{"short", "int"},
		{"java.lang.Short", "int"},
		{"int", "int"},
		{"java.lang.Integer", "int"},
		{"long", "Number"},
		{"java.lang.Long", "Number"},
		{"float", "Number"},
		{"java.lang.Float", "Number"},
		{"double", "Number"},
		{"java.lang.Double", "Number"},
		{"byte", "int"},
		{"java.lang.Byte", "int"},
		{"boolean", "Boolean"},
		{"java.lang.String", "String"},
		{"java.lang.Object", "Object"},
		{"java.sql.Date", "Date"},
		{"java.util.Date", "Date"},
		{"java.util.Collection", "Array"},
		{"java.util.List", "Array"},
		{"java.util.ArrayList", "Array"},
		{"java.util.Set", "Array"},
		{"java.util.HashSet", "Array"},
		{"java.util.Map", "Object"},
		{"java.lang.HashMap", "Object"},
        {"dto.Dto", "Object"}
    };
	public static Map<String, String> java2AsMap = new HashMap<String, String>();
	public static Map<String, String> as2JavaMap = new HashMap<String, String>();
	static {
		for (String[] entry : j2as) {
			java2AsMap.put(entry[0], entry[1]);
			as2JavaMap.put(entry[1], entry[0]);
		}
	}
	
	public static String javaToAs3(Type type) {
		JavaClass javaClass = type.getJavaClass();
		if (javaClass.isA("java.util.Date"))
			return "Date";
        if (javaClass.isA("java.util.Collection")) {
            return "Array";
        }
        if (type.isArray()) {
        	if("byte".equalsIgnoreCase(type.getValue()))
                return "ByteArray";
            return "Array";
        }
        if (javaClass.isA("java.util.Map")) {
            return "Object";
        }
		String asType = java2AsMap.get(type.getValue());
		if (asType != null)
			return asType;
		else
			return type.toString();
	}
	
	public static String as3ToJava(String type) {
		String javaType = as2JavaMap.get(type);
		if (javaType != null)
			return javaType;
		else
			return type;
	}
}
