package com.wabao.mogame.core;

import java.util.concurrent.ConcurrentHashMap;


public class Players {
	private ConcurrentHashMap<Integer, Player> players = new ConcurrentHashMap<Integer, Player>();
	
	public boolean isLogin(int playerId) {
		return players.containsKey(playerId);
	}
	public boolean isLogin(Player player) {
		return player == null || players.contains(player.id);
	}
	
	public void add(Player player) {
		players.put(player.id, player);
	}
	
	public void remove(Player player) {
		players.remove(player.id);
	}
}
