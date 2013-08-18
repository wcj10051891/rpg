package gen;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.generama.QDoxCapableMetadataProvider;
import org.generama.VelocityTemplateEngine;
import org.generama.WriterMapper;

import com.thoughtworks.qdox.model.BeanProperty;
import com.thoughtworks.qdox.model.JavaClass;
import com.thoughtworks.qdox.model.JavaField;
import com.thoughtworks.qdox.model.JavaMethod;
import com.thoughtworks.qdox.model.Type;

/**
 * 模型的JavaGeneratingPlugin插件基类
 * 
 * @author <a href="mailto:xiaofeng.l@cndw.com">arbow</a>
 */
public class JavaModelPlugin extends JavaGeneratingPluginBase {

	protected Map<String, JavaClass> enumMap = new HashMap<String, JavaClass>();
	protected Map<String, String> classAliasMap = new HashMap<String, String>();
	protected Set<String> classPackageSet = new HashSet<String>();
	private boolean acceptunannotationtype = true;

	public JavaModelPlugin(VelocityTemplateEngine templateEngine,
			QDoxCapableMetadataProvider metadataProvider,
			WriterMapper writerMapper) {
		super(templateEngine, metadataProvider, writerMapper);
	}

	@Override
	public boolean shouldGenerate(Object metadata) {
		if (!super.shouldGenerate(metadata))
			return false;
		JavaClass jc = (JavaClass) metadata;
		if (acceptClass(jc) && jc.isPublic() && !jc.isInner()) {
            if (! JavaAS3TypeMapping.java2AsMap.containsKey(jc.getFullyQualifiedName()))
                classAliasMap.put(jc.getFullyQualifiedName(), jc.getName());
			classPackageSet.add(jc.getPackage().getName());
			System.out.println(">>> Processing Model: " + jc.getFullyQualifiedName());
			return true;
		}
		return false;
	}

	/**
	 * 是否接受某类型的class，子类可以覆盖来改变生成规则，这里使用标记来判断是否生成，无标记则使用acceptunannotationtype
	 */
	protected boolean acceptClass(JavaClass clazz) {
		if (ignoreGenAnnotationExist(clazz.getAnnotations()))
			return false;
		if (genCodeAnnotationExist(clazz.getAnnotations()))
			return true;
		return isAcceptunannotationtype();
	}

	/**
	 * 获取这个JavaClass要输出的Import列表，这里会对不需要输出的属性类型做过滤
	 */
	public Set<String> getAllImports(JavaClass jc) {
		HashSet<String> result = new HashSet<String>();
        //System.out.println("-----" + jc.getBeanProperties().length);
        //for (BeanProperty property : jc.getBeanProperties()) {
        for (JavaField property : jc.getFields()) {            
			JavaField field = jc.getFieldByName(property.getName());
			if (field == null) continue;
			if (field.getType().getJavaClass().isEnum()) continue;
            if (field.isPrivate()) continue;
            if (ignoreGenAnnotationExist(field.getAnnotations())) continue;
			if (genCodeAnnotationExist(field.getAnnotations()) || isAcceptunannotationtype()) {
				Type javaType = property.getType();
				String as3Type = JavaAS3TypeMapping.javaToAs3(javaType);
				if (as3Type.indexOf(".") != -1)
					result.add(as3Type);
			}
		}
        return result;
	}

	public void collectEnums() {
		for (Object element : getMetadata()) {
			JavaClass jc = (JavaClass) element;
			processEnumClass(jc);
		}
	}

	protected void processEnumClass(JavaClass jc) {
        if (jc.isEnum()) {
			enumMap.put(jc.getFullyQualifiedName().replace('$', '.'), jc);
		}
	}

	/**
	 * 判断此BeanProperty是否适合输出
	 */
	public boolean isSuitableProperty(JavaClass jc, BeanProperty bp) {
		//1.bp自身合法
		if (bp == null) return false;
		if ("class".equals(bp.getName())) return false;

		//2.field 合法，注解标记为生成，或默认生成无注解属性
		JavaField field = jc.getFieldByName(bp.getName());
        if (field == null) return false;
//		if (field.getType().getJavaClass().isEnum()) return false;
		if (ignoreGenAnnotationExist(field.getAnnotations())) return false;
		
		if (genCodeAnnotationExist(field.getAnnotations()) || isAcceptunannotationtype()) {
			//3. 黑名单无此属性
			if (filterProperty(jc, bp)) return false;
			//4. 可访问getter方法且合法
            JavaMethod getter = bp.getAccessor();
			if (getter == null) return false;
			if (getter.isPublic() && getter.isPropertyAccessor() && !getter.isTransient()) 
				return true;
		}
		return false;
	}

	/**
	 * 是否过滤指定的属性
	 * 
	 * @param property
	 *            属性
	 * @return 返回真则不生成这个属性
	 */
	protected boolean filterProperty(JavaClass jc, BeanProperty property) {
		return false;
	}

	/**
	 * 这个参数配置是否接受没有标记为@IgnoreGen或@GenCode的
	 * 
	 * @param acceptunannotationtype
	 */
	public void setAcceptunannotationtype(boolean acceptunannotationtype) {
		this.acceptunannotationtype = acceptunannotationtype;
	}

	public boolean isAcceptunannotationtype() {
		return acceptunannotationtype;
	}

}
