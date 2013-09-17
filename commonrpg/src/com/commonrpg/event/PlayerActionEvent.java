package com.commonrpg.event;

import com.commonrpg.core.Player;

public class PlayerActionEvent extends PlayerEvent {

	public String action;
	public PlayerActionEvent(Player player, String action) {
		super(player);
		this.action = action;
	}

}
