package com.commonrpg.persist.dao;
import java.util.List;

import com.commonrpg.persist.entity.CultivationSkill;
import com.wcj.dao.annotation.Arg;
import com.wcj.dao.annotation.Dao;
import com.wcj.dao.annotation.Sql;
@Dao
public interface CultivationSkillDao {
	
	@Sql(value="insert into cultivation_skill(	`id`,	`name`,	`desc`,	`unlock`	) values(	:cultivationSkill.id,	:cultivationSkill.name,	:cultivationSkill.desc,	:cultivationSkill.unlock	)")
	Integer insert(@Arg(value="cultivationSkill") CultivationSkill o);
	
	@Sql(value="delete from cultivation_skill where id=:id")
	void delete(@Arg(value="id")Integer id);
	
	@Sql(value="update cultivation_skill set 	`id`=:cultivationSkill.id,	`name`=:cultivationSkill.name,	`desc`=:cultivationSkill.desc,	`unlock`=:cultivationSkill.unlock	 where id=:cultivationSkill.id")
	void update(@Arg(value="cultivationSkill") CultivationSkill o);

	@Sql(value="select * from cultivation_skill where id=:id")
	CultivationSkill get(@Arg(value="id") Integer id);

	@Sql(value="select * from cultivation_skill")
	List<CultivationSkill> getAll();

	@Sql(value="select * from cultivation_skill where id in(:ids)")
	List<CultivationSkill> gets(@Arg(value="ids") List<Integer> ids);
}

