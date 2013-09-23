package com.wabao.mogame.ai.fsm;

public interface State {
	public BaseFSM getFsm();
	public void onEnter();
	public void execute();
	public void onExit();
}
