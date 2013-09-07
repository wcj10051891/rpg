package gen;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

import com.thoughtworks.qdox.JavaDocBuilder;
import com.thoughtworks.qdox.model.JavaClass;
import com.thoughtworks.qdox.model.JavaMethod;
import com.thoughtworks.qdox.model.JavaParameter;
import com.thoughtworks.qdox.model.Type;

public class JavaCtrlToAS {
	private static final String javaInputDir = "src";
	private static final Pattern javaClassPattern = Pattern.compile(".*Ctrl$");
	private static String asCtrlTemplate = "src/gen/template/as3Ctrl.vm";
	private static String asOutputDir = "src/gen/out";
	private static String asSuffix = ".as";
	private static String rpcServiceArrayTemplate = "src/gen/template/RpcServiceArray.vm";
	private static String rpcServiceArrayFileName = "RpcServiceArray.as";
	static {
		try {
			Velocity.init();
		} catch (Exception e) {
			throw new RuntimeException("init exception", e);
		}
	}
	
	public static void main(String[] arg) throws Exception {
	    List<String> rpcArray = new ArrayList<String>();
	    Map<String, String> rpcArgMap = new LinkedHashMap<String, String>();
		JavaDocBuilder builder = new JavaDocBuilder();
		builder.addSourceTree(new File(javaInputDir));
		for (JavaClass javaClass : builder.getClasses()) {
			String classSimpleName = javaClass.getName();
			if(javaClassPattern.matcher(classSimpleName).matches()) {
				Map<String, Object> contextParams = new HashMap<String, Object>();
				String packageName = javaClass.getPackageName();
				//process package
				contextParams.put("packageName", packageName);
				String classFqn = javaClass.getFullyQualifiedName();
				rpcArray.add(classFqn);
				
				//process import
				Set<String> imports = new TreeSet<String>();
				//process class comment
				contextParams.put("classComment", AsGenUtils.getFullComment(javaClass));
				contextParams.put("className", classSimpleName);
				//process extends superClass
				String superClassFqn = javaClass.getSuperJavaClass().getFullyQualifiedName();
				if(!Object.class.getName().equals(superClassFqn))
					contextParams.put("superClassName", superClassFqn);
				else
					contextParams.put("superClassName", "");
				//process method
				StringBuilder methods = new StringBuilder();
				for (JavaMethod method : javaClass.getMethods()) {
		            if (!method.isPublic() || method.isStatic() || method.isAbstract() || method.isConstructor())
		                continue;
		            StringBuilder args = new StringBuilder();
		            JavaParameter[] methodParameters = method.getParameters();
		            for (int i = 1; i < methodParameters.length; i++)
		                args.append("\"").append(methodParameters[i].getName()).append("\", ");
		            if (methodParameters.length > 1)
		                args.delete(args.length() - 2, args.length());
		            rpcArgMap.put(classFqn + "::" + method.getName(), args.toString());
		            
		            Type returnType = method.getReturnType();
		            if(returnType.isVoid() && JavaAS3TypeMapping.javaToAs3(returnType).indexOf(".") != -1)
						imports.add("import " + returnType.getValue() + ";" + System.lineSeparator());
		            
		            String methodComment = AsGenUtils.getFullComment(method);
		            if(methodComment.length() > 0)
		            	methods.append(methodComment);
		            
		            StringBuilder params = new StringBuilder();
		            JavaParameter[] ps = method.getParameters();
		            for (int i = 1; i < ps.length; i++) {
		                JavaParameter parameter = ps[i];
		                params.append(parameter.getName()).append(":");
		                String type = JavaAS3TypeMapping.javaToAs3(parameter.getType());
		                if(type.indexOf(".") != -1)
							imports.add("import " + parameter.getType().getValue() + ";" + System.lineSeparator());
		                params.append(type);
    			        params.append(", ");
		    		}
		            methods
		            .append("\t\t").append("public static function ")
		            .append(method.getName()).append("(")
		            .append(params.toString())
		            .append("callback:Function):")
		            .append(JavaAS3TypeMapping.javaToAs3(returnType))
		            .append("{")
		            .append(System.lineSeparator())
		            .append("\t\t\t\t").append("callRPC(")
		            .append(System.lineSeparator())
		            .append("\t\t\t\t").append("\"").append(javaClass.getName()).append(".").append(method.getName()).append("\"")
		            .append(",arguments);")
		            .append(System.lineSeparator())
		            .append("\t\t\t\t").append("return null;")
		            .append(System.lineSeparator())
		            .append("\t\t").append("}")
		            .append(System.lineSeparator());
		        }
				
				contextParams.put("methods", methods.toString());
				contextParams.put("imports", StringUtils.join(imports, System.lineSeparator()));
				
				File outFile = new File(asOutputDir + File.separator + classFqn.replace(".", File.separator) + asSuffix);
				if(!outFile.exists()) {
					 outFile.getParentFile().mkdirs();
					 outFile.createNewFile();
				}
				BufferedWriter writer = new BufferedWriter(new FileWriter(outFile));
				BufferedReader reader = new BufferedReader(new FileReader(new File(asCtrlTemplate)));
				Velocity.evaluate(new VelocityContext(contextParams), writer, "", reader);
					
				writer.flush();
				writer.close();
				
				reader.close();
				System.out.println("generated 【" + javaClass.getFullyQualifiedName() + "】 to 【" + outFile.getCanonicalPath() + "】");
			}
		}
		
		if(!rpcArray.isEmpty()) {
			File targetFile = new File(asOutputDir, rpcServiceArrayFileName);
			BufferedWriter writer = new BufferedWriter(new FileWriter(targetFile));
			BufferedReader reader = new BufferedReader(new FileReader(new File(rpcServiceArrayTemplate)));
			Map<String, Object> contextParams = new HashMap<String, Object>(2);
			contextParams.put("rpcArray", rpcArray);
			contextParams.put("rpcArgMap", rpcArgMap);
			Velocity.evaluate(new VelocityContext(contextParams), writer, "", reader);
			
			writer.flush();
			writer.close();
			
			reader.close();
			System.out.println("generated 【" + rpcServiceArrayFileName + "】 to 【" + targetFile.getCanonicalPath() + "】");
		}
	}
}