package com.wabao.mogame.event;

import com.wabao.mogame.core.Player;

public class PlayerEvent extends Event {
	public Player player;

	public PlayerEvent(Player player) {
		this.player = player;
	}
}
