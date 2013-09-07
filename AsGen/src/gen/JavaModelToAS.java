package gen;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

import com.thoughtworks.qdox.JavaDocBuilder;
import com.thoughtworks.qdox.model.JavaClass;
import com.thoughtworks.qdox.model.JavaField;

public class JavaModelToAS {
	private static final String javaInputDir = "src";
	private static final Pattern javaClassPattern = Pattern.compile(".*Dto$");
	private static String asDtoTemplate = "src/gen/template/as3Dto.vm";
	private static String asClassAliasRegisterTemplate = "src/gen/template/ClassAliasRegister.vm";
	private static String asOutputDir = "src/gen/out";
	private static String asSuffix = ".as";
	private static String classAliasRegisterFileName = "ClassAliasRegister.as";
	private static Map<String, String> classAliasMap = new HashMap<String, String>();
	private static Set<String> classPackageSet = new HashSet<String>();
	static {
		try {
			Velocity.init();
		} catch (Exception e) {
			throw new RuntimeException("init exception", e);
		}
	}
	
	public static void main(String[] args) throws Exception {
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
				if (!JavaAS3TypeMapping.java2AsMap.containsKey(classFqn)) {
					classAliasMap.put(classFqn, classSimpleName);
					classPackageSet.add(packageName);
				}
				
				//process import
				Set<String> imports = new HashSet<String>();
				//process class comment
				contextParams.put("classComment", AsGenUtils.getFullComment(javaClass));
				contextParams.put("className", classSimpleName);
				//process extends superClass
				String superClassFqn = javaClass.getSuperJavaClass().getFullyQualifiedName();
				if(!Object.class.getName().equals(superClassFqn))
					contextParams.put("superClassName", superClassFqn);
				else
					contextParams.put("superClassName", "");
				//process field
				StringBuilder fields = new StringBuilder();
				for (JavaField field : javaClass.getFields()) {
					if(field.isPublic() && !field.isStatic()) {
						String fieldComment = AsGenUtils.getFullComment(field);
						if(fieldComment.length() > 0)
							fields.append(fieldComment);
						String as3Type = JavaAS3TypeMapping.javaToAs3(field.getType());
						fields.append("\t\tpublic var ").append(as3Type).append(" ").append(field.getName()).append(";");
						fields.append(System.lineSeparator());
						
						if (as3Type.indexOf(".") != -1)
							imports.add("import " + field.getType() + ";" + System.lineSeparator());
					}
				}
				contextParams.put("fields", fields.toString());
				contextParams.put("imports", StringUtils.join(imports, System.lineSeparator()));
				
				File outFile = new File(asOutputDir + File.separator + classFqn.replace(".", File.separator) + asSuffix);
				if(!outFile.exists()) {
					 outFile.getParentFile().mkdirs();
					 outFile.createNewFile();
				}
				BufferedWriter writer = new BufferedWriter(new FileWriter(outFile));
				BufferedReader reader = new BufferedReader(new FileReader(new File(asDtoTemplate)));
				Velocity.evaluate(new VelocityContext(contextParams), writer, "", reader);
					
				writer.flush();
				writer.close();
				
				reader.close();
				System.out.println("generated 【" + javaClass.getFullyQualifiedName() + "】 to 【" + outFile.getCanonicalPath() + "】");
			}
		}
		
		if(!classAliasMap.isEmpty()) {
			File targetFile = new File(asOutputDir, classAliasRegisterFileName);
			BufferedWriter writer = new BufferedWriter(new FileWriter(targetFile));
			BufferedReader reader = new BufferedReader(new FileReader(new File(asClassAliasRegisterTemplate)));
			Map<String, Object> contextParams = new HashMap<String, Object>(2);
			contextParams.put("classAliasMap", classAliasMap);
			contextParams.put("classPackageSet", classPackageSet);
			Velocity.evaluate(new VelocityContext(contextParams), writer, "", reader);
			
			writer.flush();
			writer.close();
			
			reader.close();
			System.out.println("generated 【" + classAliasRegisterFileName + "】 to 【" + targetFile.getCanonicalPath() + "】");
		}
	}
}