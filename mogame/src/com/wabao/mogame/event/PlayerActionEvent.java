package com.wabao.mogame.event;

import com.wabao.mogame.core.Player;

public class PlayerActionEvent extends PlayerEvent {

	public String action;
	public PlayerActionEvent(Player player, String action) {
		super(player);
		this.action = action;
	}

}
