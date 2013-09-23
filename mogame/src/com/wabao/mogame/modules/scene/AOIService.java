package com.wabao.mogame.modules.scene;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import com.wabao.mogame.core.Player;
import com.wabao.mogame.core.Unit;
import com.wabao.mogame.service.Services;

public class AOIService {
	
	public void onMove(Unit unit, Scene targetScene, int targetX, int targetY) {
		Zone targetZone = targetScene.getZone(targetX, targetY);
		if(unit.currentZone != targetZone) {
			Set<Zone> oldZones = Collections.emptySet();
			//离开旧的zone计算
			if(unit.currentZone != null) {
				oldZones = unit.currentZone.groupZones;
				unit.currentZone.remove(unit);
			}
			Set<Zone> tempOldZone = new HashSet<Zone>(oldZones);
			oldZones.removeAll(targetZone.groupZones);
			Set<Zone> newZones = new HashSet<Zone>(targetZone.groupZones);
			newZones.removeAll(tempOldZone);
			
			for (Zone oldZone : oldZones) {
				if(unit instanceof Player)
					Services.tcpService.leaveGroup(oldZone.groupName, (Player)unit);
				//调用离开视野事件
				for (Set<Unit> us : oldZone.units.values())
					for(Unit u : us) {
						u.onOutSight(unit);
						unit.onOutSight(u);
					}
			}
			
			//新看见的组，需要收集这组内的其他player状态发给player自己
			for (Zone newZone : newZones) {
				if(unit instanceof Player)
					Services.tcpService.joinGroup(newZone.groupName, (Player)unit);
//				
//				newPlayerIds.addAll(zone.playerIds);
				//调用进入视野事件
				for (Set<Unit> us : newZone.units.values())
					for(Unit u : us) {
						u.onInSight(unit);
						unit.onInSight(u);
					}
			}
//			Services.netService.send(msg, player);
			//在新group中组播自己的状态，告诉他们我来了
//			Services.netService.broadcast(msg, groupName, player);
			
		}
		unit.currentZone.add(unit);
	}
}
