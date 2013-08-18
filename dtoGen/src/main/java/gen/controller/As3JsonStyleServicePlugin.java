package gen.controller;

import gen.JavaControllerPlugin;

import org.generama.QDoxCapableMetadataProvider;
import org.generama.VelocityTemplateEngine;
import org.generama.WriterMapper;

import com.thoughtworks.qdox.model.JavaMethod;

/**
 * 输出Json样式的AS3 Service代码
 * 
 * @author <a href="mailto:xiaofeng.l@cndw.com">arbow</a>
 */
public class As3JsonStyleServicePlugin extends JavaControllerPlugin {
	
	private final String DEFAULT_PACKAGE_NAME = "com.cndw.game.services";

	public As3JsonStyleServicePlugin(VelocityTemplateEngine templateEngine,
			QDoxCapableMetadataProvider metadataProvider,
			WriterMapper writerMapper) {
		super(templateEngine, metadataProvider, writerMapper);
		setMultioutput(true);
        setFileregex("Controller\\.java");
        setFilereplace("Service.as");
        setPackageregex(".+");
        setPackagereplace(DEFAULT_PACKAGE_NAME);
	}

	public boolean isEntityReturnType(JavaMethod method) {
		String returnType = method.getReturnType().getJavaClass().getFullyQualifiedName();
		return !"com.cndw.dwgame.app.model.GenericListWrapper".equals(returnType);
	}
}
