package com.commonrpg.event;


public class PlayerEventListener extends EventListener<PlayerActionEvent> {

	@Override
	public void onEvent(PlayerActionEvent event) {
		System.out.println("event fired:" + event);
	}


}