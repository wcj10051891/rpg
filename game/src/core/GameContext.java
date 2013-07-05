package core;

import handler.GameDispatcher;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import com.wcj.core.NetContext;

public class GameContext {
	public static NetContext netContext;
	public static GameDispatcher dispatcher;
	public static GameConfig config;
	public static ScheduledExecutorService timer;
	
	static {
		dispatcher = new GameDispatcher();
		timer = Executors.newScheduledThreadPool(1);
	}
}
