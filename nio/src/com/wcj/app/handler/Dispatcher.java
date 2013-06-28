package com.wcj.app.handler;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.wcj.app.protocol.RequestDto;
import com.wcj.util.Utils;

public class Dispatcher {
	
	private static final Logger log = Logger.getLogger(Dispatcher.class);

	private static String modules_package_name = "com.wcj.app.modules";

	private Map<String, Map<String, Invoker>> methods = new HashMap<String, Map<String, Invoker>>();
	
	public Dispatcher() {
		try {
			for (Class<?> clazz : Utils.scanPackage(modules_package_name)) {
				if(clazz.isAnnotationPresent(Controller.class)){
					String clazzName = clazz.getName();
					for (Method method : clazz.getMethods()) {
						int modifiers = method.getModifiers();
						if(Modifier.isPublic(modifiers) && !Modifier.isAbstract(modifiers) && !Modifier.isStatic(modifiers)){
							Map<String, Invoker> methodMap = methods.get(clazzName);
							if(methodMap == null){
								methodMap = new HashMap<>();
								methods.put(clazzName, methodMap);
							}
							methodMap.put(method.getName(), new Invoker(clazz.newInstance(), method));
						}
					}
				}
			}
		} catch (Exception e) {
			log.error("Dispatcher init error.", e);
		}
	}
	
	private static class Invoker{
		public Object instance;
		public Method method;
		public Invoker(Object instance, Method method) {
			this.instance = instance;
			this.method = method;
		}
		public Object execute(List<Object> args) throws Exception{
			if(args == null)
				return method.invoke(instance);
			else
				return method.invoke(instance, args.toArray(new Object[args.size()]));
		}
	}
	
	public Object proccess(RequestDto request){
		try {
			return this.methods.get(request.getClazz()).get(request.getMethod()).execute(request.getParams());
		} catch (Exception e) {
			log.error("dispatcher proccess request error.", e);
		}
		return null;
	}
}
