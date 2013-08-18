package gen.model;

import gen.JavaGeneratingPluginBase;

import org.generama.QDoxCapableMetadataProvider;
import org.generama.VelocityTemplateEngine;
import org.generama.WriterMapper;

public class AsClassAliasRegisterPlugin extends JavaGeneratingPluginBase {

	protected AsClassAliasRegisterPlugin(VelocityTemplateEngine templateEngine,
			QDoxCapableMetadataProvider metadataProvider, WriterMapper writerMapper) {
		super(templateEngine, metadataProvider, writerMapper);
        System.out.println("AsClassAliasRegisterPlugin()");
    }
}
