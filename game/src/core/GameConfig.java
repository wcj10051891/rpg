package core;

import com.wcj.util.Config;

public abstract class GameConfig {
	public static String module_package_name;
	
	public static String configFileName = "config.properties";
	public static Config config;
	
	static {
		config = new Config(configFileName);
		module_package_name = config.getString("module_package_name", "modules");
	}
}
