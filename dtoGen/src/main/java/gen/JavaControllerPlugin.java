package gen;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

import org.generama.QDoxCapableMetadataProvider;
import org.generama.VelocityTemplateEngine;
import org.generama.WriterMapper;

import com.thoughtworks.qdox.model.JavaClass;
import com.thoughtworks.qdox.model.JavaMethod;
import com.thoughtworks.qdox.model.JavaParameter;
import com.thoughtworks.qdox.model.Type;

/**
 * 生成以Json为封包格式的AS3 Service接口
 * 
 * @author <a href="mailto:xiaofeng.l@cndw.com">arbow</a>
 */
public class JavaControllerPlugin extends JavaGeneratingPluginBase {

    protected ArrayList<String> rpcArray = new ArrayList<String>();
    protected Map<String, String> rpcArgMap = new LinkedHashMap<String, String>();
    private String packageregex = "";
    private String packagereplace = "";

    public JavaControllerPlugin(VelocityTemplateEngine templateEngine, QDoxCapableMetadataProvider metadataProvider,
			WriterMapper writerMapper) {
		super(templateEngine, metadataProvider, writerMapper);
	}

    public void setPackageregex(String packageregex) {
        this.packageregex = packageregex;
        super.setPackageregex(packageregex);
    }

    public void setPackagereplace(String packagereplace) {
        this.packagereplace = packagereplace;
        super.setPackagereplace(packagereplace);
    }

    public boolean shouldGenerate(Object metadata) {
		if (!super.shouldGenerate(metadata))
			return false;
		JavaClass jc = (JavaClass)metadata;
        //return jc.isA(GameController.class.getName());
        String mName = jc.getFullyQualifiedName().replaceAll(packageregex, packagereplace);
        rpcArray.add(mName);

        for (JavaMethod jm : jc.getMethods())
        {
            if (! jm.isPublic())
                continue;
            StringBuilder arg = new StringBuilder();
            JavaParameter[] jps = jm.getParameters();
            for (int i=1; i<jps.length; i++)
                arg.append("\"").append(jps[i].getName()).append("\", ");
            if (jps.length > 1)
                arg.delete(arg.length()-2, arg.length());
            rpcArgMap.put(mName + "::" + jm.getName(), arg.toString());
        }

        System.out.println(">>> Processing Controller: " + jc.getFullyQualifiedName());
        return true;
	}
	
	public boolean isControllerMethod(JavaMethod method) {
		Type returnType = method.getReturnType();
		//if (! returnType.isVoid())
			return method.isPublic() && !method.isAbstract() && !method.isStatic();
		//return false;
	}
	
	public JavaMethod[] getAllControllerMethods(JavaClass jc) {
		LinkedList<JavaMethod> result = new LinkedList<JavaMethod>();
		for (JavaMethod method:jc.getMethods()) {
			if (isControllerMethod(method))
				result.add(method);
		}
		return result.toArray(new JavaMethod[result.size()]);
	}

    /**
     * 唯一返回类型的方法
     * */
    public String[] getAllControllerMethodsSole(JavaClass jc) {
        Set<String> result = new HashSet<String>();
        for (JavaMethod method:jc.getMethods()) {
            if (isControllerMethod(method) && !method.getReturnType().isPrimitive() && !method.getReturnType().isArray())
                result.add(JavaAS3TypeMapping.javaToAs3(method.getReturnType()));
        }
        return result.toArray(new String[result.size()]);
    }
		
    public String getAsClassName(JavaClass jc) {
        String fileName = getDestinationFilename(jc);
        return fileName.split("\\.")[0];
    }

	public String getParametersAsString(JavaMethod method) {
		StringBuilder buf = new StringBuilder();
        JavaParameter[] ps = method.getParameters();
        for (int i=1; i<ps.length; i++) {
            JavaParameter parameter = ps[i];
            buf.append(parameter.getName()).append(":")
			        .append(getAs3Type(parameter.getType()))
                    .append(", ");
		}
		return buf.toString();
	}

    public String getParametersTraceString(JavaMethod method)
    {
        StringBuilder buf = new StringBuilder();
        for (JavaParameter parameter:method.getParameters()) {
            buf.append(parameter.getName()).append(",");
        }
        if (buf.length() > 0)
            buf.deleteCharAt(buf.length()-1);
        return buf.toString();
    }

    /*public String getAsReturnType(JavaMethod method) {
		return JavaAS3TypeMapping.javaToAs3(method.getReturnType());
	} */

    public Map<String, String> getParametersMap(JavaMethod method) {
		Map<String, String> result = new HashMap<String, String>();
		int i=1;
		for (JavaParameter parameter:method.getParameters()) {
			result.put(":"+i++, parameter.getName());
			if (parameter.getType().getJavaClass().isEnum()) {
				result.put(":"+(i-1)+".type", "\""+parameter.getType().getJavaClass().getFullyQualifiedName()+"\"");
			}
		}
		return result;
	}

    public String getMethodName(JavaClass clazz, JavaMethod method) {
        String serviceName = clazz.getFullyQualifiedName();
        String methodName = method.getName();
        return serviceName + "." + methodName;
    }

}
