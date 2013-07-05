package com.wcj.core;

import com.wcj.util.Config;

public class NetConfig {
	// 协议实现工厂类
	public static String protocalFactoryClass;
	// 协议解包后的业务处理类
	public static String handlerClass;
	// server监听端口号
	public static int acceptorListenPort;
	// 解包后交给业务处理的线程池配置
	public static int applicationThreadPoolCoreSize;
	public static int applicationThreadPoolMaximumSize;
	public static int applicationThreadPoolKeepAliveSeconds;
	// workers数量，即多少个select线程
	public static int workersNum;
	
	public static Config config;
	public static String configFileName = "config.properties";
	
	static {
		config = new Config(configFileName);
		protocalFactoryClass = config.getString("protocalFactoryClass");
		handlerClass = config.getString("handlerClass");
		acceptorListenPort = config.getInt("acceptorListenPort");
		applicationThreadPoolCoreSize = config.getInt("applicationThreadPoolCoreSize");
		applicationThreadPoolMaximumSize = config.getInt("applicationThreadPoolMaximumSize");
		applicationThreadPoolKeepAliveSeconds = config.getInt("applicationThreadPoolKeepAliveSeconds");
		workersNum = config.getInt("workersNum");
	}
}
