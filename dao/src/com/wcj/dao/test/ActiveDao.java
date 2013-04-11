package com.wcj.dao.test;

import java.util.List;
import java.util.Map;

import com.wcj.dao.annotation.Arg;
import com.wcj.dao.annotation.Dao;
import com.wcj.dao.annotation.Sql;


@Dao
public interface ActiveDao
{
    @Sql("select * from active where id=#p.id and name=#p.name")
    Map<String, Object> get(@Arg("p")Active active);
    
    @Sql("select count(*) from active")
    int getCount();
    
    @Sql("select * from active")
    List<Active> getAll();

    @Sql("select * from active where id in(#ids)")
    List<Map<String, Object>> getByIds(@Arg("ids")int[] ids);
    
    @Sql("select b.* from npc a inner join npc_dialog_script b on a.functionScript=b.id where a.id in(#ids)")
    List<Map<String, Object>> getNpcByIds(@Arg("ids")int[] ids);
}
