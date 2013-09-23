package com.wabao.mogame.persist.entity;
//活跃度配置
public class Active{
	public Integer id;//活动id
	public String name="";//活动名称
	public String description="";//活动描述
	public Integer times=0;//需要完成的次数
	public String icon;//图标
	public String npcId="";//npcId，如果有职业限制就填多个|分隔
	public String evilId;//怪物ID
	public String funcName;//客户端功能id
	public String rewards;//奖励
	public Integer gainType;//产出类型1经验(经)2道具(珍)3修为(修)4铜钱(钱)5帮贡(贡)6阵营声望(声)7灵力(灵)8帮会资源(财)9道具(道)
	public Integer getId(){
		return this.id;
	}
	public String getName(){
		return this.name;
	}
	public String getDescription(){
		return this.description;
	}
	public Integer getTimes(){
		return this.times;
	}
	public String getIcon(){
		return this.icon;
	}
	public String getNpcId(){
		return this.npcId;
	}
	public String getEvilId(){
		return this.evilId;
	}
	public String getFuncName(){
		return this.funcName;
	}
	public String getRewards(){
		return this.rewards;
	}
	public Integer getGainType(){
		return this.gainType;
	}
	public void setId(Integer id){
		this.id = id;
	}
	public void setName(String name){
		this.name = name;
	}
	public void setDescription(String description){
		this.description = description;
	}
	public void setTimes(Integer times){
		this.times = times;
	}
	public void setIcon(String icon){
		this.icon = icon;
	}
	public void setNpcId(String npcId){
		this.npcId = npcId;
	}
	public void setEvilId(String evilId){
		this.evilId = evilId;
	}
	public void setFuncName(String funcName){
		this.funcName = funcName;
	}
	public void setRewards(String rewards){
		this.rewards = rewards;
	}
	public void setGainType(Integer gainType){
		this.gainType = gainType;
	}
}



