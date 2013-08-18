package gen.controller;

import gen.JavaGeneratingPluginBase;
import org.generama.VelocityTemplateEngine;
import org.generama.QDoxCapableMetadataProvider;
import org.generama.WriterMapper;

/**
 * User: kofboy@163.com
 * Date: 2010-11-9
 */
public class RpcServiceArrayPlugin extends JavaGeneratingPluginBase
{

	protected RpcServiceArrayPlugin(VelocityTemplateEngine templateEngine,
			QDoxCapableMetadataProvider metadataProvider, WriterMapper writerMapper) {
		super(templateEngine, metadataProvider, writerMapper);
        System.out.println("RpcServiceArrayPlugin()");
    }
}
