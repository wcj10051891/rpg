package com.commonrpg.event;

import com.commonrpg.core.Player;

public class PlayerEvent extends Event {
	public Player player;

	public PlayerEvent(Player player) {
		this.player = player;
	}
}
