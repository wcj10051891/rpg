package com.wabao.mogame.remote.phprpc;

import java.util.jar.JarFile;

import com.wabao.mogame.dynamic.Dynamic;
import com.wabao.mogame.service.Services;

public class HotSwaper {

	public boolean redefineClass(String sourceCode) throws Exception {
		return Dynamic.redifineClass(sourceCode);
	}

	public Object run(String sourceCode) throws Exception {
		return Dynamic.run(sourceCode);
	}

	// 添加一个jar文件到类搜索路径中
	public String addJarFile(String jarPath) throws Exception {
		JarFile jf = new JarFile(jarPath);
		Services.appService.instrument.appendToBootstrapClassLoaderSearch(jf);
		Services.appService.instrument.appendToSystemClassLoaderSearch(jf);
		return "add " + jarPath;
	}
}
