package gen;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class Config {
    private static Log logger = LogFactory.getLog(Config.class);
    private Properties properties;
    
    public Config(String filename){
	try {
	    properties = new Properties();
	    properties.load(new FileInputStream(filename));
	} catch (Exception e) {
	    try {
		properties.load(Config.class.getResourceAsStream("/" + filename));
	    } catch (IOException ee) {
	    	logger.error("config file not exists, configuation properties init error: " + e.getMessage() + ". " + ee.getMessage());
	    }
	}
    }
    
    public String getString(String key){
	return getString(key, null);
    }
    
    public String getString(String key, String defaultValue){
	String property = properties.getProperty(key);
	if(property == null)
	    return defaultValue;
	return property;
    }
    
    public Integer getInt(String key){
	return getInt(key, null);
    }
    
    public Integer getInt(String key, Integer def){
	String property = properties.getProperty(key);
	if(property == null)
	    return def;
	return Integer.parseInt(property);
    }
    
    public Boolean getBoolean(String key){
	return getBoolean(key, null);
    }
    
    public Boolean getBoolean(String key, Boolean def){
	String property = properties.getProperty(key);
	if(property == null)
	    return def;
	return Boolean.valueOf(property);
    }
}
