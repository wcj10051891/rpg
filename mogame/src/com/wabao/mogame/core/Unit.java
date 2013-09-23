package com.wabao.mogame.core;

import com.wabao.mogame.event.BaseObservable;
import com.wabao.mogame.modules.scene.Zone;

public abstract class Unit extends BaseObservable {

	public enum UnitType {
		Player, Pet, Npc, EntryPoint, Monster;
	}

	public UnitType unitType;
	public Zone currentZone;
	
	public void onInSight(Unit unit) {
		
	}
	
	public void onOutSight(Unit unit) {
		
	}
}
