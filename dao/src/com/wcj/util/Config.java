package com.wcj.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Config {
    private static Logger logger = LoggerFactory.getLogger(Config.class);
    private Properties properties;
    
    public Config(String filename){
	try {
	    properties = new Properties();
	    properties.load(new FileInputStream(filename));
	} catch (Exception e) {
	    try {
		properties.load(Config.class.getResourceAsStream("/" + filename));
	    } catch (IOException ee) {
		logger.error("config file not exists, configuation properties init error: ", e.getMessage() + ". " + ee.getMessage());
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
