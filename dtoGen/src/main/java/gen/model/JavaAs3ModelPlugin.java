package gen.model;

import gen.JavaModelPlugin;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.generama.QDoxCapableMetadataProvider;
import org.generama.VelocityTemplateEngine;
import org.generama.WriterMapper;

import com.thoughtworks.qdox.model.BeanProperty;
import com.thoughtworks.qdox.model.JavaClass;

/**
 * 输出Java对应的AS3模型的插件实现
 * 
 * @author <a href="mailto:xiaofeng.l@cndw.com">arbow</a>
 */
public class JavaAs3ModelPlugin extends JavaModelPlugin {
	
	protected final String DEFAULT_PACKAGE_NAME = "";
	
	private VelocityTemplateEngine templateEngine;
	
	// custom configures
	private boolean createaliasregister = false;
	private boolean createspecialmodel = false;
	private Set<String> propertiesFilterSet = new HashSet<String>();
	private String packagename;

	public JavaAs3ModelPlugin(VelocityTemplateEngine templateEngine,
			QDoxCapableMetadataProvider metadataProvider,
			WriterMapper writerMapper) {
		super(templateEngine, metadataProvider, writerMapper);
		this.templateEngine = templateEngine;
		setMultioutput(true);
		setEncoding("UTF-8");
		setFileregex(".java");
		setFilereplace(".as");
	}

	public String getDestinationPackage(JavaClass jc) {
//		return DEFAULT_PACKAGE_NAME;
		if (packagename != null)
			return packagename;
		return jc.getPackageName();
	}
	
	@Override
	public void start() {
		collectEnums();
		super.start();
		//createSpecialModel();
		createClassAliasRegister();
	}
	
    private void createClassAliasRegister() {
    	if (!isCreatealiasregister())
    		return;
        try {
            Writer writer = getWriter("ClassAliasRegister.as");
            Map contextObjects = getContextObjects();
            contextObjects.put("classAliasMap", classAliasMap);
            contextObjects.put("classPackageSet", classPackageSet);
            templateEngine.generate(writer, contextObjects, getEncoding(), AsClassAliasRegisterPlugin.class);
        } catch (Exception e) {
        	throw new RuntimeException(e);
        }
    }

    private Writer getWriter(String filename) throws IOException {
        String modelPackage = new String(DEFAULT_PACKAGE_NAME);
        String packagePath = modelPackage.replace('.', '/');
        File dir = new File(getDestdirFile(), packagePath);
        dir.mkdirs();

        File out = new File(dir, filename);
        try {
            return new OutputStreamWriter(new FileOutputStream(out), getEncoding());
        } catch (Exception e) {
            throw new IOException(e.toString());
        }

    }
    
    @Override
    protected boolean filterProperty(JavaClass jc, BeanProperty property) {
    	String propertyName = property.getName();
    	String className = jc.getFullyQualifiedName();
    	String fullName = className + "#" + propertyName;
    	return this.propertiesFilterSet.contains(fullName);
    }
    
    public void setExconfig(String filePath) {
    	try {
    		FileInputStream fis = new FileInputStream(filePath);
    		ByteArrayOutputStream baos = new ByteArrayOutputStream();
    		byte[] buf = new byte[4096];
    		int len = 0;
    		while((len = fis.read(buf)) != -1)
    			baos.write(buf, 0, len);
    		fis.close();
    		String content = new String(baos.toByteArray());
    		JSONObject json = JSONObject.fromObject(content);
    		JSONArray array = json.getJSONArray("exclude-properties");
    		for (int i=0; i<array.size(); i++) {
    			JSONObject excludeItem = array.getJSONObject(i);
    			String object = excludeItem.getString("object");
    			String property = excludeItem.getString("property");
    			propertiesFilterSet.add(object + "#" + property);
    		}
    	} catch (IOException e) {
    		throw new RuntimeException("read exclude-config file error", e);
    	}
    	
    }

	public boolean isCreatealiasregister() {
		return createaliasregister;
	}

	public void setCreatealiasregister(boolean createaliasregister) {
		this.createaliasregister = createaliasregister;
	}

	public boolean isCreatespecialmodel() {
		return createspecialmodel;
	}

	public void setCreatespecialmodel(boolean createspecialmodel) {
		this.createspecialmodel = createspecialmodel;
	}

	public void setPackagename(String packageName) {
		this.packagename = packageName;
	}

	public String getPackagename() {
		return packagename;
	}

}
