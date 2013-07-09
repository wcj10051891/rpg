package handler;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import modules.player.Player;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.wcj.channel.ChannelContext;
import com.wcj.protocol.RequestDto;
import com.wcj.util.Utils;

import core.GameConfig;

public class GameDispatcher {

	private static final Log log = LogFactory.getLog(GameDispatcher.class);

	private Map<String, Map<String, Invoker>> methods = new HashMap<String, Map<String, Invoker>>();

	public GameDispatcher() {
		try {
			for (Class<?> clazz : Utils.scanPackage(GameConfig.module_package_name)) {
				if (clazz.isAnnotationPresent(Controller.class)) {
					String clazzName = clazz.getName();
					for (Method method : clazz.getMethods()) {
						int modifiers = method.getModifiers();
						if (Modifier.isPublic(modifiers) && !Modifier.isAbstract(modifiers)
								&& !Modifier.isStatic(modifiers)) {
							if (method.getParameterTypes().length > 0) {
								Map<String, Invoker> methodMap = methods.get(clazzName);
								if (methodMap == null) {
									methodMap = new HashMap<>();
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

	public Object proccess(ChannelContext ctx, RequestDto request) throws Exception {
		Invoker invoker = this.methods.get(request.getClazz()).get(request.getMethod());
		if (invoker.method.getParameterTypes()[0] == Player.class && ctx.states.containsKey(Player.Channel_Context_Key)) {
			List<Object> params = new LinkedList<>();
			params.add(ctx.states.get(Player.Channel_Context_Key));
			params.addAll(request.getParams());
			return invoker.execute(params);
		} else
			return invoker.execute(request.getParams());
	}
}
