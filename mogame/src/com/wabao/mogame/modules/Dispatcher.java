package com.wabao.mogame.modules;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import xlib.ClassUtils;

import com.wabao.mogame.core.AppConfig;
import com.wabao.mogame.core.Player;

public class Dispatcher {
	private static final Logger log = LoggerFactory.getLogger(Dispatcher.class);

	private Map<String, Map<String, Invoker>> methods = new HashMap<String, Map<String, Invoker>>();

	public Dispatcher() {
		try {
			for (Class<?> clazz : ClassUtils.scanPackage(AppConfig.MODULE_PACKAGE_NAME)) {
				if (clazz.isAnnotationPresent(Controller.class)) {
					String clazzName = clazz.getName();
					for (Method method : clazz.getDeclaredMethods()) {
						int modifiers = method.getModifiers();
						if (Modifier.isPublic(modifiers) && !Modifier.isAbstract(modifiers)
								&& !Modifier.isStatic(modifiers)) {
							Map<String, Invoker> methodMap = methods.get(clazzName);
							if (methodMap == null) {
								methodMap = new HashMap<String, Invoker>();
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

	private static class Invoker {
		public Object instance;
		public Method method;

		public Invoker(Object instance, Method method) {
			this.instance = instance;
			this.method = method;
		}

		public Object execute(List<Object> args) throws Exception {
			if (args == null)
				return method.invoke(instance);
			else
				return method.invoke(instance, args.toArray());
		}
	}
	
	public Object dispatch(Player player, String service, String method, List<Object> params) throws Exception {
		if(player != null) {
			params = new ArrayList<Object>(params);
			params.add(0, player);
		}
		return this.methods.get(service).get(method).execute(params);
	}
}
