package gen;

import java.util.HashMap;
import java.util.Map;

import com.thoughtworks.qdox.model.Type;

/**
 * Java和AS3类型的映射关系
 * 
 * @author <a href="mailto:xiaofeng.l@cndw.com">arbow</a>
 */
public class JavaAS3TypeMapping {
	
	private static String[][] mapping = new String[][] {
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
		for (String[] map:mapping) {
			java2AsMap.put(map[0], map[1]);
			as2JavaMap.put(map[1], map[0]);
		}
	}

	public static String javaToAs3(Type type) {
		if (type.getJavaClass().isA("java.util.Date"))
			return "Date";
        if (type.getJavaClass().isA("java.util.Collection")) {
            return "Array";
        }
        if (type.isArray() && "byte".equalsIgnoreCase(type.getValue())) {
            return "ByteArray";
        }
        if (type.isArray()) {
            return "Array";
        }
        if (type.getJavaClass().isA("java.util.Map")) {
            return "Object";
        }
		String asType = java2AsMap.get(type.getValue());
		if (asType != null)
			return asType;
		else
			return type.getFullyQualifiedName();//.getValue();
	}
	
	public static String as3ToJava(String type) {
		String javaType = as2JavaMap.get(type);
		if (javaType != null)
			return javaType;
		else
			return type;
	}
}
