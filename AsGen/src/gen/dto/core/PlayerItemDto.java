//package gen.dto.core;
//
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.Date;
//import java.util.List;
//import java.util.Map;
//import java.util.concurrent.TimeUnit;
//
//import com.cndw.rpg.Context;
//import com.cndw.rpg.util.CommonUtil;
//import com.cndw.xianhun.mvc.dto.forge.RecastAttrDto;
//import com.cndw.xianhun.mvc.model.Logic;
//import com.cndw.xianhun.mvc.model.adventure.AdventureManager;
//import com.cndw.xianhun.mvc.model.core.Attr;
//import com.cndw.xianhun.mvc.model.core.PlayerItem;
//import com.cndw.xianhun.mvc.model.pet.PlayerPet;
//import com.cndw.xianhun.persist.entity.Pet;
//import com.cndw.xianhun.util.DateUtil;
//import com.cndw.xianhun.util.JsonUtils;
//
//public class PlayerItemDto extends ItemDto
//{
//    public int itemId;
//
//    /**
//     * 威力
//     */
//    public int might;
//
//    /**
//     * 当前锻造等级
//     */
//    public int forgeLevel;
//
//    /**
//     * 当前镶嵌孔信息
//     */
//    public HoleDto[] holes;
//    
//    /**
//     * 当前强化孔信息
//     */
//    public HoleDto[] forgeHoles;
//
//    /**
//     * 当前耐久度
//     */
//    public int currDurability;
//
//    /**
//     * 洗炼属性,
//     * 
//     * 力量 power str 敏捷 agile agi 智力 wit int 耐力 endurance sta
//     * 
//     * 命中值 hit aim 闪避值 dodge av 格挡值 block block 穿透值 penetration penet 暴击值 crit vio 韧性值 toughness tou
//     * 
//     * 物理免伤 phyAvoidInjury phyavo 物伤吸收(免伤率) phyAvoidInjuryR phyavor 法术免伤 magAvoidInjury magavo 法伤吸收(免伤率) magAvoidInjuryR magavor
//     * 
//     * 血量上限 maxHp maxhp 法术上限 maxMp maxmp
//     * 
//     * 物理攻击 physicalStrength pp
//     * 
//     * 法术攻击 magicStrength sp
//     * 
//     * 防御 def（物防，法防） def
//     * 
//     * 增加所有属性 sais(力，敏，智，耐) sais
//     * 
//     * 物理免伤率和法术免伤率 defr 物理免伤率 phydefr 法术免伤率 magdefr 物理攻击最大值 physicalStrengthMax ppmax 物理攻击最小值 physicalStrengthMin ppmin 法术攻击最大值 magicStrengthMax spmax 法术攻击最小值 magicStrengthMin spmin 物理和法术攻击 psp
//     * 物理和法术攻击最大值 pspmax 物理和法术攻击最小值 pspmin
//     */
//    public RecastAttrDto[] recastValues;
//    /**
//     * 强化后属性增量值
//     */
//    public Map forgeAdditionValues;
//
//    /**
//     * 强化奖励属性
//     */
//    public Map forgeExtraValues;
//
//    /**
//     * 是否是攻击部件
//     */
//    public boolean isAttachItem;
//    
//    /**
//     * 神装附加属性值， {key:value}
//     */
//    public Map godExtraValue;
//
//    /**
//     * 剩余多少秒过期
//     */
//    public long outTime;
//    
//    /**
//     * 当前祝福值
//     */
//    public int wish;
//    
//    /**
//     * 装备套装类型
//     */
//    public int suitType;
//    
//    /**
//     * 已镶嵌套装附魂石id
//     */
//    public int suitItemId;
//
//    public PlayerItemDto passPlayerItem(PlayerItem pitem)
//    {
//        if (super.passItem(pitem) == null)
//            return null;
//        this.itemId = pitem.getItemId();
//        this.might = pitem.might;
//        this.currDurability = pitem.currDurability;
//        this.forgeLevel = pitem.getForgeLevel();
//        this.isAttachItem = pitem.isAttackItem();
//        if (pitem.getPlayerItemExtAttribute() != null && pitem.getPlayerItemExtAttribute().size() > 0)
//            this.extAttribute.putAll(pitem.getPlayerItemExtAttribute());
//
//        if (pitem.isWearable())
//        {
//            Map<String, Integer> baseValues = pitem.getBaseValues();
//            this.power = baseValues.get(Attr.power);
//            this.agile = baseValues.get(Attr.agile);
//            this.wit = baseValues.get(Attr.wit);
//            this.con = baseValues.get(Attr.con);
//            this.maxHp = baseValues.get(Attr.maxHp);
//            this.maxMp = baseValues.get(Attr.maxMp);
//            this.pp = baseValues.get(Attr.pp);
//            this.sp = baseValues.get(Attr.sp);
//            this.phyDef = baseValues.get(Attr.phyDef);
//            this.magDef = baseValues.get(Attr.magDef);
//            this.aim = baseValues.get(Attr.aim);
//            this.av = baseValues.get(Attr.av);
//            this.vio = baseValues.get(Attr.vio);
//            this.tou = baseValues.get(Attr.tou);
//            this.speed = baseValues.get(Attr.speed);
//            this.vioh = baseValues.get(Attr.vioh);
//            this.forgeExtraValues = pitem.getForgeExtraValue();
//            this.recastValues = pitem.getRecastAttrDtos();
//            this.forgeAdditionValues = pitem.getForgeAdditionValues();
//            List<HoleDto> holeDtos = new ArrayList<HoleDto>();
//            List<Integer> ehs = pitem.getEnchaseHoles();
//            for(int i = 0; i < ehs.size(); i++)
//            {
//                holeDtos.add(new HoleDto().pass(i, ehs.get(i)));
//            }
//            this.holes = holeDtos.toArray(new HoleDto[holeDtos.size()]);
//            List<HoleDto> forgeHoleDtos = new ArrayList<HoleDto>();
//            List<Integer> fhs = pitem.getForgeHoles();
//            for(int i = 0; i < fhs.size(); i++)
//            {
//                forgeHoleDtos.add(new HoleDto().pass(i, fhs.get(i)));
//            }
//            this.forgeHoles = forgeHoleDtos.toArray(new HoleDto[forgeHoleDtos.size()]);
//            this.godExtraValue = pitem.getGodExtraValue();
//            this.wish = pitem.getWish();
//            this.suitType = pitem.getSuitType();
//            this.suitItemId = CommonUtil.nullIntegerToDef(pitem.getPlayerItemEntity().suitItemId);
//        }
//        else
//        {
//            this.forgeExtraValues = Collections.EMPTY_MAP;
//            this.recastValues = new RecastAttrDto[0];
//            this.godExtraValue = Collections.EMPTY_MAP;
//        }
//        // 彩票用
////        if (pitem.getSystemItemId() == GamebleManager.ITEM_ID)
////        {
////            JSONObject json = pitem.getPlayerItemExtAttribute();
////            long startTime = JsonUtils.getLong(json, "start");
////            if (startTime == 0)
////            {
////                json.put("start", System.currentTimeMillis());
////                Context.getCacheService().saveObject(pitem);
////                startTime = System.currentTimeMillis();
////            }
////            long outTime = TimeUnit.DAYS.toMillis(7) + startTime - System.currentTimeMillis();
////            if (outTime > 0)
////            {
////                this.outTime = outTime;
////            }
////            // 补登记
////            try
////            {
////                Context.getLogic(Logic.class).gamebleManager.register(pitem);
////            }
////            catch (Exception e)
////            {
////            }
////        }
//        //探险券
//        if (pitem.getSystemItemId() == AdventureManager.ticketItemId)
//        {
//            long t = pitem.getCreateTime().getTime() + AdventureManager.ticketExpiredTimes;
//            this.outTime = TimeUnit.MILLISECONDS.toSeconds(t - System.currentTimeMillis());
//            if(this.outTime < 0)
//                this.outTime = 0;
//            String exp = DateUtil.getDateFormat(new Date(t), "M月d日H点m分");
//            desc += "\n\n过期时间：" + exp;
//            if(this.outTime == 0)
//                desc += "<font color=\"#FF0000\">（已过期）</font>";
//        }
//
//        //
////        if (pitem.getSystemItemId() == FightingSoul.skillBookId)
////        {
////            Object id = pitem.getPlayerItemExtAttribute().get("learnSkillId");
////            if (id != null && id instanceof Integer)
////            {
////                Skill skill = Context.getCacheService().getObject(id, Skill.class);
////                if (skill != null)
////                    desc += "<br><br><font color='#ffff00'>技能：" + skill.getPrimaryName() + "<br>" + skill.getSkillDesc() + "</font>";
////            }
////        }
//
//
//        //固定仙魂石特殊处理
//        if (pitem.getItemId() == PlayerPet.PET_EGG_ITEM_ID)
//        {
//            if (extAttribute.containsKey("petId") && extAttribute.get("petId") instanceof Integer)
//            {
//                int id = (Integer)extAttribute.get("petId");
//                Pet pet = Context.getLogic(Logic.class).configInDb.petInfoConfig.getSystemPet(id);
//                if (pet != null)
//                {
//                    this.name = pet.getName() + this.name;
//                    this.color = JsonUtils.getInt(pitem.getPlayerItemExtAttribute(), "color");
//                }
//            }
//        }
//        return this;
//    }
//}
