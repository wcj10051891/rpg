package gen.dto.core {




	public class PlayerItemDto extends ItemDto {
		public var int itemId;
		/**
		 * 威力
		 */
		public var int might;
		/**
		 * 当前锻造等级
		 */
		public var int forgeLevel;
		/**
		 * 当前镶嵌孔信息
		 */
		public var Array holes;
		/**
		 * 当前强化孔信息
		 */
		public var Array forgeHoles;
		/**
		 * 当前耐久度
		 */
		public var int currDurability;
		/**
		 * 洗炼属性,
		 * 
		 * 力量 power str 敏捷 agile agi 智力 wit int 耐力 endurance sta
		 * 
		 * 命中值 hit aim 闪避值 dodge av 格挡值 block block 穿透值 penetration penet 暴击值 crit vio 韧性值 toughness tou
		 * 
		 * 物理免伤 phyAvoidInjury phyavo 物伤吸收(免伤率) phyAvoidInjuryR phyavor 法术免伤 magAvoidInjury magavo 法伤吸收(免伤率) magAvoidInjuryR magavor
		 * 
		 * 血量上限 maxHp maxhp 法术上限 maxMp maxmp
		 * 
		 * 物理攻击 physicalStrength pp
		 * 
		 * 法术攻击 magicStrength sp
		 * 
		 * 防御 def（物防，法防） def
		 * 
		 * 增加所有属性 sais(力，敏，智，耐) sais
		 * 
		 * 物理免伤率和法术免伤率 defr 物理免伤率 phydefr 法术免伤率 magdefr 物理攻击最大值 physicalStrengthMax ppmax 物理攻击最小值 physicalStrengthMin ppmin 法术攻击最大值 magicStrengthMax spmax 法术攻击最小值 magicStrengthMin spmin 物理和法术攻击 psp
		 * 物理和法术攻击最大值 pspmax 物理和法术攻击最小值 pspmin
		 */
		public var Array recastValues;
		/**
		 * 强化后属性增量值
		 */
		public var Object forgeAdditionValues;
		/**
		 * 强化奖励属性
		 */
		public var Object forgeExtraValues;
		/**
		 * 是否是攻击部件
		 */
		public var Boolean isAttachItem;
		/**
		 * 神装附加属性值， {key:value}
		 */
		public var Object godExtraValue;
		/**
		 * 剩余多少秒过期
		 */
		public var Number outTime;
		/**
		 * 当前祝福值
		 */
		public var int wish;
		/**
		 * 装备套装类型
		 */
		public var int suitType;
		/**
		 * 已镶嵌套装附魂石id
		 */
		public var int suitItemId;

	}
}