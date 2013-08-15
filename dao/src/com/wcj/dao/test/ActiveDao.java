package com.wcj.dao.test;

import java.util.List;
import java.util.Map;

import com.wcj.dao.annotation.Arg;
import com.wcj.dao.annotation.BatchInsert;
import com.wcj.dao.annotation.Dao;
import com.wcj.dao.annotation.Page;
import com.wcj.dao.annotation.Sql;
import com.wcj.dao.core.page.PageResult;


@Dao
public interface ActiveDao
{
	
	@Sql(value="insert into active(	`id`,	`name`,	`description`,	`times`,	`icon`,	`npcId`,	`evilId`,	`funcName`,	`rewards`,	`gainType`	) values(	:active.id,	:active.name,	:active.description,	:active.times,	:active.icon,	:active.npcId,	:active.evilId,	:active.funcName,	:active.rewards,	:active.gainType	)")
	Integer insert(@Arg(value="active") Active o);
	
    @Sql("select * from active where id=:id")
    Map<String, Object> getById(@Arg("id")Integer id);
    
    @Sql("select * from active where id=:id")
    Active getAciveById(@Arg("id")Integer id);
    
    @Sql("select count(*) from active")
    Long getCount();
    
    @Sql("select * from active")
    List<Active> getBeanList();

    @Sql("select * from active")
    List<Map> getAll();

    @Sql("select id from active")
    public List<Integer> getIds();
    
    @Sql("select description from active")
    public List<String> getDescs();

    @Sql("select * from active where id in(:ids)")
    List<Map<String, Object>> getByIds(@Arg("ids")int[] ids);
    
    @Sql("update active set description=:des where id=:id")
    public void update(@Arg("id") Integer id, @Arg("des")String des);

    @Sql("select * from active")
    public PageResult getPage(@Page(Active.class) PageResult page);

    @Sql("select name from active where name in(:names)")
    public PageResult getPage2(@Page(String.class) PageResult page, @Arg("names")List<String> names);

    /**
     * 动态指定sql
     * @param sql	动态sql
     * @return
     */
    public PageResult getPage3(@Page(Map.class) PageResult page, @Sql String sql, @Arg("id")Integer id);
    
    /**
     * 批量插入
     * @param o
     */
    @Sql(value="insert into active(	`id`,	`name`,	`description`,	`times`,	`icon`,	`npcId`,	`evilId`,	`funcName`,	`rewards`,	`gainType`	) values(	:active.id,	:active.name,	:active.description,	:active.times,	:active.icon,	:active.npcId,	:active.evilId,	:active.funcName,	:active.rewards,	:active.gainType	)")
    void insert(@BatchInsert("active") List<Active> o);
}
