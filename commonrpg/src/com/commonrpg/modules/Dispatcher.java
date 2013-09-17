package com.commonrpg.modules;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.commonrpg.core.AppConfig;
import com.commonrpg.util.ClassUtils;

public class Dispatcher {
	private static final Logger log = LoggerFactory.getLogger(Dispatcher.class);

	private Map<String, Map<String, Invoker>> methods = new HashMap<String, Map<String, Invoker>>();

	public Dispatcher() {
		try {
			for (Class<?> clazz : ClassUtils.scanPackage(AppConfig.MODULE_PACKAGE_NAME)) {
				if (clazz.isAnnotationPresent(Controller.class)) {
					String clazzName = clazz.getName();
					for (Method method : clazz.getMethods()) {
						int modifiers = method.getModifiers();
						if (Modifier.isPublic(modifiers) && !Modifier.isAbstract(modifiers)
								&& !Modifier.isStatic(modifiers)) {
							if (method.getParameterTypes().length > 0) {
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
				return method.invoke(instance, args.toArray(new Object[args.size()]));
		}
	}
}
