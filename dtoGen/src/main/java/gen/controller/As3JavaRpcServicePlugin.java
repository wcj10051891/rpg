package gen.controller;

import gen.JavaControllerPlugin;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Map;

import org.generama.QDoxCapableMetadataProvider;
import org.generama.VelocityTemplateEngine;
import org.generama.WriterMapper;

import com.thoughtworks.qdox.model.JavaClass;
import com.thoughtworks.qdox.model.JavaMethod;

/**
 * 输出Json样式的AS3 Service代码
 * 
 * @author <a href="mailto:xiaofeng.l@cndw.com">arbow</a>
 */
public class As3JavaRpcServicePlugin extends JavaControllerPlugin {

    private static final String DEFAULT_PACKAGE_NAME = "^.*controller";
    private boolean createRpcServiceArray = false;
    private VelocityTemplateEngine templateEngine;

    public As3JavaRpcServicePlugin(VelocityTemplateEngine templateEngine,
			QDoxCapableMetadataProvider metadataProvider,
			WriterMapper writerMapper) {
		super(templateEngine, metadataProvider, writerMapper);
        this.templateEngine = templateEngine;
        setMultioutput(true);
        setEncoding("UTF-8");
        setFileregex("\\.java");
        setFilereplace(".as");
        setPackageregex(DEFAULT_PACKAGE_NAME);
        setPackagereplace("rpc");
    }

    public String getSimpleMethodName(JavaClass clazz, JavaMethod method) {
        String serviceName = clazz.getFullyQualifiedName()
                .replaceAll(DEFAULT_PACKAGE_NAME + "\\.", "");
        String methodName = method.getName();
        return serviceName + "." + methodName;
    }

    public void start() {
        super.start();
        createRpcServiceArray();
    }

    private void createRpcServiceArray() {
        if (createRpcServiceArray)
            return;
        createRpcServiceArray = true;
        try {
            System.out.println(">>> Processing createRpcServiceArray: RpcServiceArray.as");
            Writer writer = getWriter("RpcServiceArray.as");
            Map contextObjects = getContextObjects();
            contextObjects.put("rpcArray", rpcArray);
            contextObjects.put("rpcArgMap", rpcArgMap);
            templateEngine.generate(writer, contextObjects, getEncoding(), RpcServiceArrayPlugin.class);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private Writer getWriter(String filename) throws IOException
    {
        File dir = new File(getDestdirFile(), "");
        dir.mkdirs();

        File out = new File(dir, filename);
        try {
            return new OutputStreamWriter(new FileOutputStream(out), getEncoding());
        } catch (Exception e) {
            throw new IOException(e.toString());
        }
    }
}
