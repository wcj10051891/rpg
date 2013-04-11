package com.wcj.dao.test;
//玩家寄售物品; 
public class PlayerConsignmentItem{
	public Integer id;//唯一id
	public Integer playerId=0;//玩家id
	public String nickname="";//玩家昵称
	public Long playerItemId=0l;//玩家物品id
	public Integer num=0;//数量
	public String itemName="";//物品名称
	public Integer rootTypeId=0;//物品根类型id
	public Integer typeId=0;//物品类型id
	public Integer level=0;//物品等级
	public Integer price=0;//价格
	public java.sql.Timestamp startTime=new java.sql.Timestamp(System.currentTimeMillis());//开始时间
	public java.sql.Timestamp endTime=new java.sql.Timestamp(System.currentTimeMillis());//结束时间
	public Integer getId(){
		return this.id;
	}
	public Integer getPlayerId(){
		return this.playerId;
	}
	public String getNickname(){
		return this.nickname;
	}
	public Long getPlayerItemId(){
		return this.playerItemId;
	}
	public Integer getNum(){
		return this.num;
	}
	public String getItemName(){
		return this.itemName;
	}
	public Integer getRootTypeId(){
		return this.rootTypeId;
	}
	public Integer getTypeId(){
		return this.typeId;
	}
	public Integer getLevel(){
		return this.level;
	}
	public Integer getPrice(){
		return this.price;
	}
	public java.sql.Timestamp getStartTime(){
		return this.startTime;
	}
	public java.sql.Timestamp getEndTime(){
		return this.endTime;
	}
	public void setId(Integer id){
		this.id = id;
	}
	public void setPlayerId(Integer playerId){
		this.playerId = playerId;
	}
	public void setNickname(String nickname){
		this.nickname = nickname;
	}
	public void setPlayerItemId(Long playerItemId){
		this.playerItemId = playerItemId;
	}
	public void setNum(Integer num){
		this.num = num;
	}
	public void setItemName(String itemName){
		this.itemName = itemName;
	}
	public void setRootTypeId(Integer rootTypeId){
		this.rootTypeId = rootTypeId;
	}
	public void setTypeId(Integer typeId){
		this.typeId = typeId;
	}
	public void setLevel(Integer level){
		this.level = level;
	}
	public void setPrice(Integer price){
		this.price = price;
	}
	public void setStartTime(java.sql.Timestamp startTime){
		this.startTime = startTime;
	}
	public void setEndTime(java.sql.Timestamp endTime){
		this.endTime = endTime;
	}
}

