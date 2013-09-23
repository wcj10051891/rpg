package com.wabao.mogame.persist.dao;
import java.util.List;

import com.wabao.mogame.persist.entity.Active;
import com.wcj.dao.annotation.Arg;
import com.wcj.dao.annotation.Dao;
import com.wcj.dao.annotation.Sql;
@Dao
public interface ActiveDao {
	
	@Sql(value="insert into active(	`id`,	`name`,	`description`,	`times`,	`icon`,	`npcId`,	`evilId`,	`funcName`,	`rewards`,	`gainType`	) values(	:active.id,	:active.name,	:active.description,	:active.times,	:active.icon,	:active.npcId,	:active.evilId,	:active.funcName,	:active.rewards,	:active.gainType	)")
	Integer insert(@Arg(value="active") Active o);
	
	@Sql(value="delete from active where id=:id")
	void delete(@Arg(value="id")Integer id);
	
	@Sql(value="update active set 	`id`=:active.id,	`name`=:active.name,	`description`=:active.description,	`times`=:active.times,	`icon`=:active.icon,	`npcId`=:active.npcId,	`evilId`=:active.evilId,	`funcName`=:active.funcName,	`rewards`=:active.rewards,	`gainType`=:active.gainType	 where id=:active.id")
	void update(@Arg(value="active") Active o);

	@Sql(value="select * from active where id=:id")
	Active get(@Arg(value="id") Integer id);
	
	@Sql(value="select * from active")
	List<Active> getAll();
}

