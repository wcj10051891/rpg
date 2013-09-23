package com.wabao.mogame.persist.entity;
/** 玩家物品1 */
public class PlayerItem{
	/** 玩家物品id */
	public Long id;
	/** 系统物品id */
	public Integer itemId=0;
	/** 是否已绑定 */
	public Boolean binding=false;
	/** 物品来源 */
	public Integer source=0;
	/** 拥有玩家id */
	public Integer playerId=0;
	/** 堆叠数量 */
	public Integer stackNum;
	/** 当前耐久度 */
	public Integer currDurability;
	/** 是否在使用 */
	public Boolean used;
	/** 创建时间 */
	public java.sql.Timestamp createTime=new java.sql.Timestamp(System.currentTimeMillis());
	/** 额外配置数据 */
	public String extAttribute;
	/** 人民币 */
	public Integer rmb;
	/** 金币 */
	public Integer gold;
	/** 金票 */
	public Integer goldTicket;
	/** 图标 */
	public String icon;
	/** HP */
	public Integer hp=0;
	/** MP */
	public Integer mp=0;
	/** 普通伤害 */
	public Integer normalHurt;
	/** 攻击速度 */
	public Integer attackSpeed;
	/** 物理强度 */
	public Integer pp;
	/** 法术强度 */
	public Integer sp;
	/** 物理防御 */
	public Integer phyDef;
	/** 法术防御 */
	public Integer magDef;
	/** 命中 */
	public Integer aim;
	/** 闪避 */
	public Integer av;
	/** 暴击率 */
	public Integer vio;
	/** 韧性 */
	public Integer speed;
	/** 当前强化等级 */
	public Integer forgeLevel=0;
	/** 强化失败次数 */
	public Integer forgeFailTimes=0;
	/** 镶嵌的宝石孔1 */
	public Integer enchaseHole1=0;
	/** 镶嵌的宝石孔2 */
	public Integer enchaseHole2=0;
	/** 镶嵌的宝石孔3 */
	public Integer enchaseHole3=0;
	/** 强化的宝石孔1 */
	public Integer forgeHole1=0;
	/** 强化的宝石孔2 */
	public Integer forgeHole2=0;
	/** 洗炼属性 */
	public String recastValues;
	/** 批量洗炼属性 */
	public String multiRecast;
	/** 套装魂石id */
	public Integer suitItemId;
	/** 祝福值 */
	public Integer wish;
	/** 最大强化奖励等级 */
	public Integer forgeAddLv;
	public Long getId(){
		return this.id;
	}
	public Integer getItemId(){
		return this.itemId;
	}
	public Boolean getBinding(){
		return this.binding;
	}
	public Integer getSource(){
		return this.source;
	}
	public Integer getPlayerId(){
		return this.playerId;
	}
	public Integer getStackNum(){
		return this.stackNum;
	}
	public Integer getCurrDurability(){
		return this.currDurability;
	}
	public Boolean getUsed(){
		return this.used;
	}
	public java.sql.Timestamp getCreateTime(){
		return this.createTime;
	}
	public String getExtAttribute(){
		return this.extAttribute;
	}
	public Integer getRmb(){
		return this.rmb;
	}
	public Integer getGold(){
		return this.gold;
	}
	public Integer getGoldTicket(){
		return this.goldTicket;
	}
	public String getIcon(){
		return this.icon;
	}
	public Integer getHp(){
		return this.hp;
	}
	public Integer getMp(){
		return this.mp;
	}
	public Integer getNormalHurt(){
		return this.normalHurt;
	}
	public Integer getAttackSpeed(){
		return this.attackSpeed;
	}
	public Integer getPp(){
		return this.pp;
	}
	public Integer getSp(){
		return this.sp;
	}
	public Integer getPhyDef(){
		return this.phyDef;
	}
	public Integer getMagDef(){
		return this.magDef;
	}
	public Integer getAim(){
		return this.aim;
	}
	public Integer getAv(){
		return this.av;
	}
	public Integer getVio(){
		return this.vio;
	}
	public Integer getSpeed(){
		return this.speed;
	}
	public Integer getForgeLevel(){
		return this.forgeLevel;
	}
	public Integer getForgeFailTimes(){
		return this.forgeFailTimes;
	}
	public Integer getEnchaseHole1(){
		return this.enchaseHole1;
	}
	public Integer getEnchaseHole2(){
		return this.enchaseHole2;
	}
	public Integer getEnchaseHole3(){
		return this.enchaseHole3;
	}
	public Integer getForgeHole1(){
		return this.forgeHole1;
	}
	public Integer getForgeHole2(){
		return this.forgeHole2;
	}
	public String getRecastValues(){
		return this.recastValues;
	}
	public String getMultiRecast(){
		return this.multiRecast;
	}
	public Integer getSuitItemId(){
		return this.suitItemId;
	}
	public Integer getWish(){
		return this.wish;
	}
	public Integer getForgeAddLv(){
		return this.forgeAddLv;
	}
	public void setId(Long id){
		this.id = id;
	}
	public void setItemId(Integer itemId){
		this.itemId = itemId;
	}
	public void setBinding(Boolean binding){
		this.binding = binding;
	}
	public void setSource(Integer source){
		this.source = source;
	}
	public void setPlayerId(Integer playerId){
		this.playerId = playerId;
	}
	public void setStackNum(Integer stackNum){
		this.stackNum = stackNum;
	}
	public void setCurrDurability(Integer currDurability){
		this.currDurability = currDurability;
	}
	public void setUsed(Boolean used){
		this.used = used;
	}
	public void setCreateTime(java.sql.Timestamp createTime){
		this.createTime = createTime;
	}
	public void setExtAttribute(String extAttribute){
		this.extAttribute = extAttribute;
	}
	public void setRmb(Integer rmb){
		this.rmb = rmb;
	}
	public void setGold(Integer gold){
		this.gold = gold;
	}
	public void setGoldTicket(Integer goldTicket){
		this.goldTicket = goldTicket;
	}
	public void setIcon(String icon){
		this.icon = icon;
	}
	public void setHp(Integer hp){
		this.hp = hp;
	}
	public void setMp(Integer mp){
		this.mp = mp;
	}
	public void setNormalHurt(Integer normalHurt){
		this.normalHurt = normalHurt;
	}
	public void setAttackSpeed(Integer attackSpeed){
		this.attackSpeed = attackSpeed;
	}
	public void setPp(Integer pp){
		this.pp = pp;
	}
	public void setSp(Integer sp){
		this.sp = sp;
	}
	public void setPhyDef(Integer phyDef){
		this.phyDef = phyDef;
	}
	public void setMagDef(Integer magDef){
		this.magDef = magDef;
	}
	public void setAim(Integer aim){
		this.aim = aim;
	}
	public void setAv(Integer av){
		this.av = av;
	}
	public void setVio(Integer vio){
		this.vio = vio;
	}
	public void setSpeed(Integer speed){
		this.speed = speed;
	}
	public void setForgeLevel(Integer forgeLevel){
		this.forgeLevel = forgeLevel;
	}
	public void setForgeFailTimes(Integer forgeFailTimes){
		this.forgeFailTimes = forgeFailTimes;
	}
	public void setEnchaseHole1(Integer enchaseHole1){
		this.enchaseHole1 = enchaseHole1;
	}
	public void setEnchaseHole2(Integer enchaseHole2){
		this.enchaseHole2 = enchaseHole2;
	}
	public void setEnchaseHole3(Integer enchaseHole3){
		this.enchaseHole3 = enchaseHole3;
	}
	public void setForgeHole1(Integer forgeHole1){
		this.forgeHole1 = forgeHole1;
	}
	public void setForgeHole2(Integer forgeHole2){
		this.forgeHole2 = forgeHole2;
	}
	public void setRecastValues(String recastValues){
		this.recastValues = recastValues;
	}
	public void setMultiRecast(String multiRecast){
		this.multiRecast = multiRecast;
	}
	public void setSuitItemId(Integer suitItemId){
		this.suitItemId = suitItemId;
	}
	public void setWish(Integer wish){
		this.wish = wish;
	}
	public void setForgeAddLv(Integer forgeAddLv){
		this.forgeAddLv = forgeAddLv;
	}
}

