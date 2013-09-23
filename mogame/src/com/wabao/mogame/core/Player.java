package com.wabao.mogame.core;

import java.util.List;
import java.util.Map;

import com.wabao.mogame.cache.CacheObject;
import com.wabao.mogame.modules.scene.Zone;
import com.wabao.mogame.net.tcp.Groups;
import com.wabao.mogame.service.Services;

public class Player extends Unit implements CacheObject<Integer> {
	
	public int id;
	public Integer channelId;
	public Zone currentZone;
	
	public void onLogin() {
		Services.appService.players.add(this);
		Services.tcpService.joinGroup(Groups.World, this);
	}
	
	public void onLogout() {
		Services.appService.players.remove(this);
		Services.tcpService.leaveGroup(Groups.World, this);
		channelId = 0;
	}

	@Override
	public CacheObject<Integer> load(Integer id) {
		return null;
	}

	@Override
	public Map<Integer, CacheObject<Integer>> gets(List<Integer> ids) {
		return null;
	}

	@Override
	public void save() {
	}

	@Override
	public void delete() {
	}

	@Override
	public Integer getId() {
		return id;
	}
}
