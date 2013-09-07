package gen;

import gen.anno.GenCode;
import gen.anno.IgnoreGen;

import java.util.LinkedHashMap;
import java.util.Map;

import org.generama.QDoxCapableMetadataProvider;
import org.generama.VelocityTemplateEngine;
import org.generama.WriterMapper;
import org.generama.defaults.JavaGeneratingPlugin;

import com.thoughtworks.qdox.model.AbstractJavaEntity;
import com.thoughtworks.qdox.model.Annotation;
import com.thoughtworks.qdox.model.BeanProperty;
import com.thoughtworks.qdox.model.DocletTag;
import com.thoughtworks.qdox.model.IndentBuffer;
import com.thoughtworks.qdox.model.JavaClass;
import com.thoughtworks.qdox.model.JavaField;
import com.thoughtworks.qdox.model.JavaMethod;
import com.thoughtworks.qdox.model.JavaParameter;
import com.thoughtworks.qdox.model.Type;

/**
 * 代码生成器插件的基类，这里会存放一些共同的代码和配置
 * 
 * @author <a href="mailto:xiaofeng.l@cndw.com">arbow</a>
 */
public class JavaGeneratingPluginBase extends JavaGeneratingPlugin {

	protected JavaGeneratingPluginBase(VelocityTemplateEngine templateEngine,
			QDoxCapableMetadataProvider metadataProvider,
			WriterMapper writerMapper) {
		super(templateEngine, metadataProvider, writerMapper);
	}

	/**
	 * 获取默认编码，这里为UTF-8，需要自定义请覆盖本方法
	 */
    @Override
    public String getEncoding() {
        return "UTF-8";
    }
    
    /**
     * 获取Java类型对应的AS3类型
     * @param javaType Java类型
     * @return AS3类型的字符串表示
     */
	public String getAs3Type(Type javaType) {
		return JavaAS3TypeMapping.javaToAs3(javaType);
	}
	
	/**
	 * 获取指定Java属性对应的AS3类型
	 * @param property Java属性值
	 * @return AS3类型的字符串表示
	 */
    public String getPropertyType(BeanProperty property) {
    	if (property.getType().getJavaClass().isEnum())
    		return "String";
        String as3Type = getAs3Type(property.getType());
        int idx = as3Type.lastIndexOf(".");
        if (idx != -1)
        	as3Type = as3Type.substring(idx+1);
        return as3Type;
    }

	/**
	 * 获取指定Java字段对应的AS3类型
	 * @param field Java字段
	 * @return AS3类型的字符串表示
	 */
    public String getFieldType(JavaField field) {
        return getAs3Type(field.getType());
    }
    
    /**
     * 获取指定方法的返回值对应的AS3类型
     * @param method Java方法
     * @return AS3类型的字符串表示
     */
	public String getMethodReturnType(JavaMethod method) {
        return JavaAS3TypeMapping.javaToAs3(method.getReturnType());
	}
	
	/**
	 * 获取指定Java类的注释，若无注释返回空串
	 * @param jc Java类
	 * @return 注释，不会返回null
	 */
	public String getClassComment(JavaClass jc) {
		String comment = jc.getComment();
		return comment == null ? "" : comment; 
	}
    
    /**
     * 字符串首字母大写，将类似 "name" 转换为  "Name"
     */
    public String capitalize(String name) {
        return name.substring(0,1).toUpperCase() + name.substring(1);
    }
    
    /**
     * 判断传入的Annotation集合中是否存在@IgnoreGen标记
     */
	protected boolean ignoreGenAnnotationExist(Annotation[] annos) {
		for (Annotation anno: annos) {
        	if (anno.getType().getJavaClass().isA(IgnoreGen.class.getName())) {
        		return true;
        	}
		}
		return false;
	}
	
    /**
     * 判断传入的Annotation集合中是否存在@GenCode标记
     */
	protected boolean genCodeAnnotationExist(Annotation[] annos) {
		for (Annotation anno: annos) {
        	if (anno.getType().getJavaClass().isA(GenCode.class.getName())) {
        		return true;
        	}
		}
		return false;
	}

    public String getJavaFieldInitializationExpression(JavaField jf)
    {
        if (jf == null) return null;
        String s = jf.getInitializationExpression();
        if (s == null) return " = null";
        if ("".equals(s)) return "";
        return " = " + s;
    }

    // add by kofboy
    public String getFullComment(AbstractJavaEntity je)
    {
        if (je == null) return "";
        IndentBuffer buffer = new IndentBuffer();
        String prefix = "\t";
        if (! (je instanceof JavaClass))
            prefix += prefix;

        if (!(je instanceof JavaMethod)
                && !(je instanceof JavaField && ((JavaField)je).getType().isArray())
                && !(je instanceof JavaField && ((JavaField)je).getType().isA(new Type("java.util.Map")))
                && je.getComment() == null
                && (je.getTags() == null || je.getTags().length == 0))
            return "";
        else
        {
            buffer.write(prefix + "/**");
            buffer.newline();

            if (je.getComment() != null && je.getComment().length() > 0) {
                String comment = je.getComment();
                buffer.write(prefix + " * ");
                buffer.write(comment.replaceAll("\n", "\n" + prefix + " * "));
                buffer.newline();
            }

            Map<String, Type> typeMap = new LinkedHashMap<String, Type>();
            //增加数组和map的泛型类型描述
            if (je instanceof JavaField)
            {
                if (((JavaField)je).getType().isArray())
                    buffer.write(prefix + " * Array<" + ((JavaField)je).getType() + ">\n");
                else if (((JavaField)je).getType().isA(new Type("java.util.Map")))
                    buffer.write(prefix + " * " + ((JavaField)je).getType().toGenericString() + "\n");
            }

            if (je instanceof JavaMethod)
            {
                JavaMethod jm = (JavaMethod)je;
                JavaParameter[] jps = jm.getParameters();
                for (JavaParameter jp : jps)
                    if (jp.getType().isArray() || jp.getType().isA(new Type("java.util.Map")))
                        typeMap.put(jp.getName(), jp.getType());
                if (jm.getReturnType().isArray() || jm.getReturnType().isA(new Type("java.util.Map")))
                    typeMap.put("", jm.getReturnType());
                if (je.getTags() == null || je.getTags().length == 0)
                {
                    for (String name : typeMap.keySet())
                    {
                        buffer.write(prefix + " * @" + ("".equals(name) ? "return " : "param "));
                        buffer.write(name + "<" + typeMap.get(name).toGenericString() + ">");
                        buffer.newline();
                    }
                }
            }

            if (je.getTags() != null && je.getTags().length > 0)
            {
                for (int i = 0; i < je.getTags().length; i++) {
                    DocletTag docletTag = je.getTags()[i];
                    buffer.write(prefix + " * @");
                    buffer.write(docletTag.getName());
                    if ("return".equals(docletTag.getName()) && typeMap.get("") != null)
                    	buffer.write("<" + typeMap.get("") + ">");
                    if (docletTag.getValue().length() > 0) {
                        buffer.write(' ');
                        String tagVal = docletTag.getValue();
                        String[] sa = docletTag.getValue().split(" ");
                        Type at = typeMap.get(sa[0]);
                        if (at != null)
                            tagVal = tagVal.replaceFirst(sa[0], sa[0] + "<" + at + ">");
                        buffer.write(tagVal);
                    }
                    buffer.newline();
                }
            }
            buffer.write(prefix + " */");
            return buffer.toString();
        }
    }
}
