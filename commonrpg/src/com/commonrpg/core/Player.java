package com.commonrpg.core;

import java.util.List;
import java.util.Map;

import com.commonrpg.cache.CacheObject;
import com.commonrpg.modules.scene.Zone;
import com.commonrpg.net.tcp.Groups;
import com.commonrpg.service.Services;

public class Player extends Unit implements CacheObject<Integer> {
	
	public int id;
	public Integer channelId;
	public Zone currentZone;
	
	public void onLogin() {
		Services.tcpService.joinGroup(Groups.World, this);
	}
	
	public void onLogout() {
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
