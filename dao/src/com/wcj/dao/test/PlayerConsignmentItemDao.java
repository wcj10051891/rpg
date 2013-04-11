package com.wcj.dao.test;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.wcj.dao.annotation.Arg;
import com.wcj.dao.annotation.Dao;
import com.wcj.dao.annotation.Sql;
@Dao
public interface PlayerConsignmentItemDao {
	
	@Sql(value="insert into player_consignment_item(	`endTime`,	`itemName`,	`level`,	`nickname`,	`num`,	`playerId`,	`playerItemId`,	`price`,	`rootTypeId`,	`startTime`,	`typeId`	) values(	#playerConsignmentItem.endTime,	#playerConsignmentItem.itemName,	#playerConsignmentItem.level,	#playerConsignmentItem.nickname,	#playerConsignmentItem.num,	#playerConsignmentItem.playerId,	#playerConsignmentItem.playerItemId,	#playerConsignmentItem.price,	#playerConsignmentItem.rootTypeId,	#playerConsignmentItem.startTime,	#playerConsignmentItem.typeId	)")
	Number insert(@Arg(value="playerConsignmentItem") PlayerConsignmentItem o);
	
	@Sql(value="delete from player_consignment_item where id=#id")
	void delete(@Arg(value="id")Integer id);
	
	@Sql(value="update player_consignment_item set 	`endTime`=#playerConsignmentItem.endTime,	`itemName`=#playerConsignmentItem.itemName,	`level`=#playerConsignmentItem.level,	`nickname`=#playerConsignmentItem.nickname,	`num`=#playerConsignmentItem.num,	`playerId`=#playerConsignmentItem.playerId,	`playerItemId`=#playerConsignmentItem.playerItemId,	`price`=#playerConsignmentItem.price,	`rootTypeId`=#playerConsignmentItem.rootTypeId,	`startTime`=#playerConsignmentItem.startTime,	`typeId`=#playerConsignmentItem.typeId	 where id=#playerConsignmentItem.id")
	void update(@Arg(value="playerConsignmentItem") PlayerConsignmentItem o);

	@Sql(value="select * from player_consignment_item where id=#id")
	PlayerConsignmentItem get(@Arg(value="id") Integer id);
	
	@Sql(value="select * from player_consignment_item order by id")
	List<PlayerConsignmentItem> getAll();
	
	@Sql(value="select id,playerId,playerItemId,num from player_consignment_item where endTime<=current_timestamp limit #limit")
	List<Map<String, Object>> getOutofdateItems(@Arg(value="limit")int limit);
	
	@Sql(value="delete from player_consignment_item where id in(#ids)")
	void delOutofdateItems(@Arg(value="ids")Collection<Integer> ids);
	
	@Sql(value="SELECT COUNT(*) FROM player_consignment_item WHERE playerId=#playerId")
	int checkMyLimit(@Arg(value="playerId") Integer playerId);
	
    @Sql(value="SELECT count(*) FROM player_consignment_item WHERE playerItemId=#playerItemId AND DATE_FORMAT(startTime, '%Y%m%d')=DATE_FORMAT(CURRENT_DATE, '%Y%m%d')")
    int getDuplicateItems(@Arg(value="playerItemId")long playerItemId);
}

