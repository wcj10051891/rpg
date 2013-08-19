package com.wcj.dao.test;
import com.wcj.dao.annotation.Arg;
import com.wcj.dao.annotation.Dao;
import com.wcj.dao.annotation.IdModSharding;
import com.wcj.dao.annotation.Sql;
@Dao
public interface PlayerItemDao {

	@Sql(value="insert into player_item_1(	`id`,	`itemId`,	`binding`,	`source`,	`playerId`,	`stackNum`,	`currDurability`,	`used`,	`createTime`,	`extAttribute`,	`rmb`,	`gold`,	`goldTicket`,	`icon`,	`hp`,	`mp`,	`normalHurt`,	`attackSpeed`,	`pp`,	`sp`,	`phyDef`,	`magDef`,	`aim`,	`av`,	`vio`,	`speed`,	`forgeLevel`,	`forgeFailTimes`,	`enchaseHole1`,	`enchaseHole2`,	`enchaseHole3`,	`forgeHole1`,	`forgeHole2`,	`recastValues`,	`multiRecast`,	`suitItemId`,	`wish`,	`forgeAddLv`,	`score`	) values(	:playerItem.id,	:playerItem.itemId,	:playerItem.binding,	:playerItem.source,	:playerItem.playerId,	:playerItem.stackNum,	:playerItem.currDurability,	:playerItem.used,	:playerItem.createTime,	:playerItem.extAttribute,	:playerItem.rmb,	:playerItem.gold,	:playerItem.goldTicket,	:playerItem.icon,	:playerItem.hp,	:playerItem.mp,	:playerItem.normalHurt,	:playerItem.attackSpeed,	:playerItem.pp,	:playerItem.sp,	:playerItem.phyDef,	:playerItem.magDef,	:playerItem.aim,	:playerItem.av,	:playerItem.vio,	:playerItem.speed,	:playerItem.forgeLevel,	:playerItem.forgeFailTimes,	:playerItem.enchaseHole1,	:playerItem.enchaseHole2,	:playerItem.enchaseHole3,	:playerItem.forgeHole1,	:playerItem.forgeHole2,	:playerItem.recastValues,	:playerItem.multiRecast,	:playerItem.suitItemId,	:playerItem.wish,	:playerItem.forgeAddLv,	:playerItem.score	)")
	void insert(@Arg(value="playerItem") PlayerItem entity, @IdModSharding(tableName="player_item_1")Integer shardArg);
	
	@Sql(value="delete from player_item_1 where id=:id")
	void delete(@Arg(value="id")Long id, @IdModSharding(tableName="player_item_1")Integer shardArg);
	
	@Sql(value="update player_item_1 set 	`id`=:playerItem.id,	`itemId`=:playerItem.itemId,	`binding`=:playerItem.binding,	`source`=:playerItem.source,	`playerId`=:playerItem.playerId,	`stackNum`=:playerItem.stackNum,	`currDurability`=:playerItem.currDurability,	`used`=:playerItem.used,	`createTime`=:playerItem.createTime,	`extAttribute`=:playerItem.extAttribute,	`rmb`=:playerItem.rmb,	`gold`=:playerItem.gold,	`goldTicket`=:playerItem.goldTicket,	`icon`=:playerItem.icon,	`hp`=:playerItem.hp,	`mp`=:playerItem.mp,	`normalHurt`=:playerItem.normalHurt,	`attackSpeed`=:playerItem.attackSpeed,	`pp`=:playerItem.pp,	`sp`=:playerItem.sp,	`phyDef`=:playerItem.phyDef,	`magDef`=:playerItem.magDef,	`aim`=:playerItem.aim,	`av`=:playerItem.av,	`vio`=:playerItem.vio,	`speed`=:playerItem.speed,	`forgeLevel`=:playerItem.forgeLevel,	`forgeFailTimes`=:playerItem.forgeFailTimes,	`enchaseHole1`=:playerItem.enchaseHole1,	`enchaseHole2`=:playerItem.enchaseHole2,	`enchaseHole3`=:playerItem.enchaseHole3,	`forgeHole1`=:playerItem.forgeHole1,	`forgeHole2`=:playerItem.forgeHole2,	`recastValues`=:playerItem.recastValues,	`multiRecast`=:playerItem.multiRecast,	`suitItemId`=:playerItem.suitItemId,	`wish`=:playerItem.wish,	`forgeAddLv`=:playerItem.forgeAddLv,	`score`=:playerItem.score	 where id=:playerItem.id")
	void update(@Arg(value="playerItem") PlayerItem entity, @IdModSharding(tableName="player_item_1")Integer shardArg);

	@Sql(value="select * from player_item_1 where id=:id")
	PlayerItem get(@Arg(value="id") Long id, @IdModSharding(tableName="player_item_1")Integer shardArg);
}



