package com.wabao.mogame.core;

import com.wabao.mogame.util.Config;

public abstract class AppConfig {
	/**
	 * 是否是开发服务器
	 */
	public static final boolean development;
	/**
	 * 业务模块包名
	 */
	public static final String MODULE_PACKAGE_NAME;
	/**
	 * tcp监听端口
	 */
	public static final int TCP_PORT;
	/**
	 * 业务线程数量
	 */
	public static final int THREAD_CORE_POOL_SIZE;
	/**
	 * 业务线程最大数量
	 */
	public static final int THREAD_MAX_POOL_SIZE;
	/**
	 * JDK定时器线程数量
	 */
	public static final int JDK_SCHEDULE_CORE_POOL_SIZE;
	/**
	 * Quartz日历定时器线程数量
	 */
	public static final String QUARTZ_SCHEDULE_CORE_POOL_SIZE;
	/**
	 * http监听端口
	 */
	public static final int HTTP_PORT;
	/**
	 * http默认字符集
	 */
	public static final String HTTP_DEFAULT_CHARSET;
	/**
	 * http keep-alive是否长连接
	 */
	public static final boolean HTTP_KEEP_ALIVE;
	/**
	 * http session存活时间
	 */
	public static final int HTTP_SESSION_ACTIVE_TIME;
	/**
	 * phprpc服务包名
	 */
	public static final String PHPRPC_BEAN_PACKAGE;
	/**
	 * phprpc private key
	 */
	public static final String PHPRPC_PRIVATE_KEY;
	
	public static String configFileName = "config.properties";
	public static Config config;
	
	static {
		config = new Config(configFileName);
		development = config.getBoolean("development");
		MODULE_PACKAGE_NAME = config.getString("module.package.name");
		TCP_PORT = config.getInt("tcp.port");
		THREAD_CORE_POOL_SIZE = config.getInt("thread.core.pool.size");
		THREAD_MAX_POOL_SIZE = config.getInt("thread.max.pool.size");
		JDK_SCHEDULE_CORE_POOL_SIZE = config.getInt("jdk.schedule.core.pool.size", 10);
		QUARTZ_SCHEDULE_CORE_POOL_SIZE = config.getString("quartz.schedule.core.pool.size", "2");
		HTTP_PORT = config.getInt("http.port");
		HTTP_DEFAULT_CHARSET = config.getString("http.defaultCharset", "UTF-8");
		HTTP_KEEP_ALIVE = config.getBoolean("http.keep-alive", true);
		HTTP_SESSION_ACTIVE_TIME = config.getInt("http.sessionActiveTime", 1800);
		PHPRPC_BEAN_PACKAGE = config.getString("phprpc.bean.package");
		PHPRPC_PRIVATE_KEY = config.getString("phprpc.private.key");
	}
}
