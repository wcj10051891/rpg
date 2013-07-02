package com.wcj.persist.entity;
//修为技能配置; 
public class CultivationSkill{
	public Integer id;//修为技能id
	public String name="";//名称
	public String desc="";//修为技能描述
	public String unlock="";//开启条件
	public Integer getId(){
		return this.id;
	}
	public String getName(){
		return this.name;
	}
	public String getDesc(){
		return this.desc;
	}
	public String getUnlock(){
		return this.unlock;
	}
	public void setId(Integer id){
		this.id = id;
	}
	public void setName(String name){
		this.name = name;
	}
	public void setDesc(String desc){
		this.desc = desc;
	}
	public void setUnlock(String unlock){
		this.unlock = unlock;
	}
}



