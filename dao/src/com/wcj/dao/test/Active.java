package com.wcj.dao.test;
//活跃度配置; 
public class Active{
	public Integer id;//活动id
	public String name="";//活动名称
	public String description="";//活动描述
	public Integer level=0;//等级
	public Integer activeType=0;//活动分类
	public Integer npcId=0;//npcId
	public Integer type=0;//类型
	public Integer point=0;//基本活跃点
	public Integer times=0;//需要完成的次数
	public String evilId;//怪物ID
	public Boolean flag=false;//是否有效
	public String icon;//图标
	public String funcName;//客户端功能id
	public Integer getId(){
		return this.id;
	}
	public String getName(){
		return this.name;
	}
	public String getDescription(){
		return this.description;
	}
	public Integer getLevel(){
		return this.level;
	}
	public Integer getActiveType(){
		return this.activeType;
	}
	public Integer getNpcId(){
		return this.npcId;
	}
	public Integer getType(){
		return this.type;
	}
	public Integer getPoint(){
		return this.point;
	}
	public Integer getTimes(){
		return this.times;
	}
	public String getEvilId(){
		return this.evilId;
	}
	public Boolean getFlag(){
		return this.flag;
	}
	public String getIcon(){
		return this.icon;
	}
	public String getFuncName(){
		return this.funcName;
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
	public void setLevel(Integer level){
		this.level = level;
	}
	public void setActiveType(Integer activeType){
		this.activeType = activeType;
	}
	public void setNpcId(Integer npcId){
		this.npcId = npcId;
	}
	public void setType(Integer type){
		this.type = type;
	}
	public void setPoint(Integer point){
		this.point = point;
	}
	public void setTimes(Integer times){
		this.times = times;
	}
	public void setEvilId(String evilId){
		this.evilId = evilId;
	}
	public void setFlag(Boolean flag){
		this.flag = flag;
	}
	public void setIcon(String icon){
		this.icon = icon;
	}
	public void setFuncName(String funcName){
		this.funcName = funcName;
	}
}



