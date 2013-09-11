//package gen.controller;
//
//import java.sql.Timestamp;
//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.Collections;
//import java.util.HashMap;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Map;
//import java.util.Set;
//
//import org.apache.commons.lang.StringUtils;
//
//import com.cndw.rpg.Context;
//import com.cndw.rpg.mvc.controller.Controller;
//import com.cndw.rpg.mvc.model.NoticeException;
//import com.cndw.rpg.util.StringUtil;
//import com.cndw.xianhun.Config;
//import com.cndw.xianhun.mvc.dto.AlertDto;
//import com.cndw.xianhun.mvc.dto.Dto;
//import com.cndw.xianhun.mvc.dto.NoticeDto;
//import com.cndw.xianhun.mvc.dto.association.AssActivityDto;
//import com.cndw.xianhun.mvc.dto.association.AssApplyDto;
//import com.cndw.xianhun.mvc.dto.association.AssConMoneyLogDto;
//import com.cndw.xianhun.mvc.dto.association.AssCrewDto;
//import com.cndw.xianhun.mvc.dto.association.AssGoalDto;
//import com.cndw.xianhun.mvc.dto.association.AssInfoDto;
//import com.cndw.xianhun.mvc.dto.association.AssSkillDto;
//import com.cndw.xianhun.mvc.dto.association.AssSkillInfoDto;
//import com.cndw.xianhun.mvc.dto.association.AssTaskDto;
//import com.cndw.xianhun.mvc.dto.association.RefreshInfoDto;
//import com.cndw.xianhun.mvc.dto.association.RefreshTaskDto;
//import com.cndw.xianhun.mvc.dto.association.SearchAssDto;
//import com.cndw.xianhun.mvc.dto.association.UpBuildShowDto;
//import com.cndw.xianhun.mvc.dto.fall.FallItemDto;
//import com.cndw.xianhun.mvc.dto.mission.MissionDto;
//import com.cndw.xianhun.mvc.model.Logic;
//import com.cndw.xianhun.mvc.model.association.ApplyBean;
//import com.cndw.xianhun.mvc.model.association.AssBossInstance;
//import com.cndw.xianhun.mvc.model.association.AssMeleeInstance;
//import com.cndw.xianhun.mvc.model.association.AssociationManage;
//import com.cndw.xianhun.mvc.model.association.ConLogBean;
//import com.cndw.xianhun.mvc.model.association.CrewBean;
//import com.cndw.xianhun.mvc.model.association.PlayerAss;
//import com.cndw.xianhun.mvc.model.association.PlayerAssSkill;
//import com.cndw.xianhun.mvc.model.association.PlayerAssTask;
//import com.cndw.xianhun.mvc.model.core.Item;
//import com.cndw.xianhun.mvc.model.core.Player;
//import com.cndw.xianhun.mvc.model.core.SystemItem;
//import com.cndw.xianhun.mvc.model.event.PlayerExecuteActionEvent;
//import com.cndw.xianhun.mvc.model.event.PlayerOperation;
//import com.cndw.xianhun.mvc.model.event.PlayerOperationEvent;
//import com.cndw.xianhun.mvc.model.link.PlayerLink;
//import com.cndw.xianhun.mvc.model.mail.MailAttachment;
//import com.cndw.xianhun.mvc.model.mission.goals.JoinAssociationMissionGoal;
//import com.cndw.xianhun.mvc.model.npc.Npc;
//import com.cndw.xianhun.persist.entity.AssActivityConfig;
//import com.cndw.xianhun.persist.entity.AssConfig;
//import com.cndw.xianhun.persist.entity.AssGoalConfig;
//import com.cndw.xianhun.persist.entity.AssSkillConfig;
//import com.cndw.xianhun.persist.entity.PlayerAssociation;
//import com.cndw.xianhun.util.DateUtil;
//
///**
// * 帮会1
// * @author shan
// * @date 2012-08-23
// */
//public class AssociationCtrl {
//	/**
//	 * 创建帮会
//	 * @param name 帮会名称
//	 * @param notice 帮会公告
//	 * @return boolean
//	 * */
//	public boolean createAss(Player player,String name,String notice) throws Exception{
//		String theName = name.trim();
//		if (StringUtils.isEmpty(theName)) 
//			throw new NoticeException("帮会名称不能为空");
//		if (theName.getBytes("GBK").length<4 || theName.getBytes("GBK").length> 12) 
//			throw new NoticeException("帮会名字要求4到12个字符");
//		if (Context.getLogic(Logic.class).getKeywords().hasSensitiveWords(theName) != null)
//			throw new NoticeException("帮会名称不允许包含敏感词");
//		//是否已加入阵营
//		if(player.faction==0)
//			throw new NoticeException("你还没加入阵营不能创建帮会");
//		//等级判断
//		if (player.level < AssociationManage.CREATE_LEVEL_LIMIT) 
//			throw new NoticeException("等级达到"+AssociationManage.CREATE_LEVEL_LIMIT+"级才可以创建");
//		//是否已有帮会
//		if (player.getAssociation()!=null && player.getEntity().assId!=0) 
//			throw new NoticeException("你已经有帮会了");
//		//帮会名已经存在
//		if (AssociationManage.findByName(theName))
//			throw new NoticeException("帮会名称已经存在");
//		//帮会令牌判定
//		int nums = player.countItem(AssociationManage.CREATE_ASS_ITEMID);
//		if (nums < 1) 
//			throw new NoticeException("没有建帮令");
//		//创建帮会
//		AssociationManage.createAss(player, theName,notice);
//		player.removeItem(AssociationManage.CREATE_ASS_ITEMID, 1);
//		//通知title改变
//        player.onAssociationChange();
//        Context.getLogic(Logic.class).getLogFactory().assocaitionCreate(player, theName);
//        player.fireEvent(new PlayerExecuteActionEvent(player, JoinAssociationMissionGoal.actionKey));
//		return true;
//	}
//	
//	/**
//	 * 帮会列表/查找帮会
//	 * @param name 帮会名称
//	 * @param pName 帮主昵称
//	 * @return SearchAssDto[]
//	 * */
//	public SearchAssDto[] searchAss(Player player,String name,String pName){
//		Collection<PlayerAssociation> list=AssociationManage.findByName2(name,pName);
//		List<SearchAssDto> dtos=new ArrayList<SearchAssDto>();
//		if(list.size()>0){
//			for(PlayerAssociation pAss:list)
//				dtos.add(new SearchAssDto().passDto(player,pAss));
//		}
//		Collections.sort(dtos);
//		return dtos.toArray(new SearchAssDto[dtos.size()]);
//	} 
//	
//	/**
//	 * 自己帮会信息
//	 * @return AssInfoDto
//	 * */
//	public AssInfoDto getAssInfo(Player player){
//		if(player.getAssociation()==null)
//			throw new NoticeException("你还没有帮会");
//		return new AssInfoDto().passDto(player);
//	}
//	
//	/**
//	 * 帮会成员列表
//	 * @return AssCrewDto[]
//	 * */
//	public AssCrewDto[] getAssCrew(Player player){
//		PlayerAss pAss=player.getAssociation();
//		if(pAss==null)
//			throw new NoticeException("你还没有帮会");
//		List<AssCrewDto> list=new ArrayList<AssCrewDto>();
//		Collection<CrewBean> coll=pAss.getCrewList();
//		for(CrewBean bean:coll){
//			list.add(new AssCrewDto().passDto(bean));
//		}
//		Collections.sort(list);
//		return list.toArray(new AssCrewDto[list.size()]);
//	}
//	
//	/**
//	 * 帮会申请列表
//	 * @return AssApplyDto[]
//	 * */
//	public AssApplyDto[] getAssApply(Player player){
//		PlayerAss pAss=player.getAssociation();
//		if(pAss==null)
//			throw new NoticeException("你还没有帮会");
//		List<AssApplyDto> list=new ArrayList<AssApplyDto>();
//		Collection<ApplyBean> coll=pAss.getApplyList().values();
//		for(ApplyBean bean:coll){
//			AssApplyDto dto=new AssApplyDto().passDto(bean);
//			list.add(dto);
//		}
//		return list.toArray(new AssApplyDto[list.size()]);
//	}
//	
//	/**
//	 * 帮会捐献资金日志
//	 * @param type 日志类型(0统计 1日志)
//	 * @return AssConMoneyLogDto[]
//	 * */
//	public AssConMoneyLogDto[] getAssConMoneyLog(Player player,int type){
//		PlayerAss pAss=player.getAssociation();
//		if(pAss==null)
//			throw new NoticeException("你还没有帮会");
//		
//		List<AssConMoneyLogDto> list=new ArrayList<AssConMoneyLogDto>();
//		if(type==0){
//			Collection<CrewBean> coll=pAss.getCrewList();
//			for(CrewBean bean:coll){
//				AssConMoneyLogDto dto=new AssConMoneyLogDto();
//				dto.name=bean.name;
//				dto.level=bean.level;
//				dto.post=bean.post;
//				dto.cash=bean.cash;
//				dto.coin=bean.coin;
//				list.add(dto);
//			}
//		}else{
//			Collection<ConLogBean> coll=pAss.getConMoneyLog();
//			for(ConLogBean bean:coll){
//				AssConMoneyLogDto dto=new AssConMoneyLogDto();
//				dto.name=bean.pName;
//				dto.post=bean.post;
//				dto.cash=bean.cash;
//				dto.coin=bean.coin;
//				dto.conTime= new Timestamp(bean.conTime) ;
//				list.add(dto);
//			}
//		}
//		Collections.sort(list);
//		return list.toArray(new AssConMoneyLogDto[list.size()]);
//	}
//	
//	/**
//	 * 修改公告
//	 * @param content 公告内容
//	 * @param qq 帮会qq群
//	 * @param yy 帮会yy群
//	 * @return boolean
//	 * */
//	public boolean updNotice(Player player,String content,String qq,String yy){
//		qq=(qq==null?"":qq);
//		yy=(yy==null?"":yy);
//		PlayerAss pAss=player.getAssociation();
//		if(pAss==null)
//			throw new NoticeException("你还没有帮会");
//		if(pAss.getCrewById(player.id).post<3)
//			throw new NoticeException("权限不足");
//		if(content.length()>300)
//			throw new NoticeException("公告内容不能超过300个字符");
//		synchronized (pAss) {
//			pAss.updNotice(content,qq,yy);
//		}
//		return true;
//	}
//	
//	/**
//	 * 捐献钱
//	 * @param cash 捐献元宝
//	 * @param coin 捐献铜币
//	 * @return int[] 捐献增加的帮贡，捐献增加的帮会资金
//	 * */
//	public int[] donateMoney(Player player,int cash,int coin){
//		PlayerAss pAss=player.getAssociation();
//		if(pAss==null)
//			throw new NoticeException("你还没有帮会");
//		if(cash==0 && coin==0)
//			throw new NoticeException("木有选择额度呀");
//		if(cash==0 && coin<5000)
//			throw new NoticeException("捐献铜钱最少为5000");
//		synchronized (pAss) {
//			int addMoney=cash*AssociationManage.AssMoneyGold+coin/AssociationManage.LowestCoin*AssociationManage.AssMoneyCoin;
//			if(!Config.DEBUG_ENABLE && player.getDailyCount("ass_donate_money")+addMoney>100)
//				throw new NoticeException("每日捐献帮会资金已达100，今日无法在进行捐献");
//			player.addDailyCount("ass_donate_money", addMoney);
//			
//			AssConfig config=AssociationManage.getAssConfig(pAss.getEntity().cofferLevel);
//			if((pAss.getEntity().money+addMoney)>config.cofferMoneyMax)
//				throw new NoticeException("帮会资金已达上限，请升级金库");
//			
//			boolean flag=player.getMoney().change(-100, -coin, 0, -cash, 0, true, 0, 1, "donateMoney");
//			if(!flag)
//				throw new NoticeException("钱不够");
//			player.getMoney().syncToClient();
//			
//			int[] donate=pAss.donateMoney(player,cash, coin);
//			//发消息
//			StringBuffer sb=new StringBuffer();
//			sb.append("【");
//			sb.append(new PlayerLink(player, false)).append("】捐献了");
//			if(cash!=0)
//				sb.append(cash).append("元宝");
//			else if(coin!=0)
//				sb.append(coin).append("铜钱");
//			sb.append("为本帮贡献了").append(donate[1]).append("帮会资金，目前帮会资金为").append(pAss.getEntity().money).append("！<u><a href='event:dialog|31'>我也捐献</a></u>");
//			pAss.assBroadcast(sb.toString());
//			return donate;
//		}
//	}
//	
//	/**
//	 * 设置职务
//	 * @param playerId 接任人ID
//	 * @param post 职务(0帮众、1堂主、2长老、3副帮主、4帮主)
//	 * @return boolean
//	 * */
//	public boolean setPost(Player player,int playerId,int post){
//		PlayerAss pAss=player.getAssociation();
//		if(pAss==null)
//			throw new NoticeException("你还没有帮会");
//		if(player.id==playerId)
//			throw new NoticeException("你不能对自己进行操作");
//		CrewBean bean1=pAss.getCrewById(player.id);
//		CrewBean bean2=pAss.getCrewById(playerId);
//		if(bean2.post>=bean1.post || post>=bean1.post)
//			throw new NoticeException("权限不足");
//		if(pAss.hadsEmptyPost(post))
//			throw new NoticeException("该职位已满");
//		synchronized (pAss) {
//			pAss.setPost(playerId, post);
//			Context.getLogic(Logic.class).getLogFactory().setAssociationJob(player, bean2.name, AssociationManage.postStr[post]);
//		}
//		return true;
//	}
//	
//	/**
//	 * 当前成员帮贡
//	 * @param playerId 开除成员id
//	 * */
//	public int curPlayerCon(Player player,int playerId){
//		PlayerAss pAss=player.getAssociation();
//		if(pAss==null)
//			throw new NoticeException("你还没有帮会");
//		if (pAss.getCrewById(player.id).post<2)
//			throw new NoticeException("权限不足");
//		CrewBean bean1 = pAss.getCrewById(player.id);
//		CrewBean bean2 = pAss.getCrewById(playerId);
//		if (bean2.post >= bean1.post)
//			throw new NoticeException("权限不足");
//		return pAss.threeDayMoney(playerId);
//	}
//	
//	/**
//	 * 开除成员/主动退出帮会
//	 * @param playerId 开除成员id,主动退出为0
//	 * @return boolean
//	 * */
//	public boolean expelPlayer(Player player,int playerId){
//		PlayerAss pAss=player.getAssociation();
//		if(pAss==null)
//			throw new NoticeException("你还没有帮会");
//		synchronized (pAss) {
//			int type=0;//0逐出，1主动退出
//			if (playerId == 0) {
//				if (player.id == pAss.getEntity().playerId)
//					throw new NoticeException("帮主不能退出帮会");
//				playerId=player.id;
//				type=1;
//			} else {
//				if(playerId==player.id)
//					throw new NoticeException("你不能开除自己");
//				CrewBean bean1 = pAss.getCrewById(player.id);
//				CrewBean bean2 = pAss.getCrewById(playerId);
//				if (bean2.post >= bean1.post)
//					throw new NoticeException("权限不足");
//				pAss.getEntity().money -= pAss.threeDayMoney(playerId);
//			}
//			Player p = Context.getLogic(Logic.class).getOnlinePlayer(playerId);
//			if(p!=null && p.isInAssBattle)
//				throw new NoticeException("正在帮会活动中");
//			pAss.expelPlayer(playerId);
//			if(type==0)
//				Context.getLogic(Logic.class).getLogFactory().kickoutAssocaition(player, player.name, pAss.getEntity().name);
//			else if(type==1)
//				Context.getLogic(Logic.class).getLogFactory().quitAssocaition(player, pAss.getEntity().name);
//			// 刷新人物头顶文字
//			if(p!=null)
//				p.onAssociationChange();	
//		}
//		return true;
//	}
//	
//	/**
//	 * 卸任
//	 * @return boolean
//	 * */
//	public boolean relievePost(Player player){
//		PlayerAss pAss=player.getAssociation();
//		if(pAss==null)
//			throw new NoticeException("你还没有帮会");
//		synchronized (pAss) {
//			pAss.setPost(player.id, 0);
//		}
//		return true;
//	}
//	
//	/**
//	 * 处理申请
//	 * @param playerId 申请人id(全部操作填0)
//	 * @param accept 是否同意申请(1同意,0拒绝)
//	 * @return boolean
//	 * */
//	public boolean handleApply(Player player,int playerId,int accept){
//		PlayerAss pAss=player.getAssociation();
//		if(pAss==null)
//			throw new NoticeException("你还没有帮会");
//		if(pAss.getCrewById(player.id).post<2)
//			throw new NoticeException("权限不足");
//		
//		synchronized (pAss) {
//			Set<Integer> set=new HashSet<Integer>();
//			if(playerId==0){//统一操作
//				set.addAll(pAss.getApplyList().keySet());
//			}else{
//				if(!pAss.getApplyList().keySet().contains(playerId))
//					throw new NoticeException("该玩家已取消申请");
//				set.add(playerId);
//			}
//			for(Integer pId:set){
//				Player p=Context.getCacheService().getObject(pId, Player.class);
//				if(p.getEntity().assId!=0){
//					Context.getTcpService().send(new AlertDto(player.name + "已加入其他帮会。"), player.id);
//					pAss.handleApply(player,pId, 0);
//				}else{
//					pAss.handleApply(player,pId, accept);
//				}
//			}
//		}
//		return true;
//	}
//	
//	/**
//	 * 检测是否可领取帮会福利(待做事项)
//	 * @param player
//	 * @return boolean
//	 */
//	public static boolean checkGetWelfare(Player player){
//		boolean flag=false;
//        PlayerAss pAss=player.getAssociation();
//        if(pAss==null)
//           return flag;
//       
//        long onlineTime=player.getonlineTime();
//        for(int i=1;i<=3;i++){
//        	 if(onlineTime>=DateUtil.HOUR*i && player.getDailyCount("ass_welfare_"+i)<1){
//        		 flag=true;
//        		 break;
//        	 }
//        }
//        return flag;
//	}
//	/**
//	 * 领取帮会福利
//	 * @param rewardId领取奖励id(1-3对应1-3小时奖励)
//	 * @return boolean
//	 * */
//	public boolean getWelfare(Player player,int rewardId){
//		if(rewardId<1 || rewardId>3)
//			throw new NoticeException("wrong type");
//		PlayerAss pAss=player.getAssociation();
//        if(pAss==null)
//            throw new NoticeException("你还没有帮会");
//        if(player.getDailyCount("ass_welfare_"+rewardId)>0)
//            throw new NoticeException("你今日已领取过此福利");
//        long onlineTime=player.getonlineTime();
//        if(onlineTime<DateUtil.HOUR*rewardId)
//        	throw new NoticeException("在线时间不足"+rewardId+"小时，无法领取此奖励");
//        
//		SystemItem systemItem=Context.getCacheService().getObject(AssociationManage.onlineReward[rewardId-1][0], SystemItem.class);
//		int count=AssociationManage.onlineReward[rewardId-1][1];
//		Item item = systemItem.createPlayerItemOrSystemItem(count);
//	    player.giveItem(0, item);
//        player.addDailyCount("ass_welfare_"+rewardId);
//        
//        NoticeDto noticeDto=new NoticeDto("恭喜你获得了："+item.getName()+"x"+count);
//		FallItemDto fallItemDto=new FallItemDto(item, player.id);
//		Context.getTcpService().send(new Dto[]{noticeDto,fallItemDto}, player);
//		
//		player.fireEvent(new PlayerOperationEvent(player, PlayerOperation.receiveAssociationGift));
//		Context.getLogic(Logic.class).idleList.update(player);
//		return true;
//	}
//	
//	/**
//	 * 解散帮会
//	 * @return boolean
//	 * */
//	public boolean dismissAss(Player player){
//		PlayerAss pAss=player.getAssociation();
//		if(pAss==null)
//			throw new NoticeException("你还没有帮会");
//		if(pAss.getCrewById(player.id).post!=4)
//			throw new NoticeException("权限不足");
//		if(pAss.isInActivity())
//			throw new NoticeException("帮会活动中不能解散帮会");
//		if(pAss.getCrewList().size()>1)
//			throw new NoticeException("帮会里还有其他成员，不能解散帮会");
//		pAss.dismissAss();
//		//通知title改变
//        player.onAssociationChange();
//		return true;
//	}
//	
//	/**
//	 * 主动弹劾帮主
//	 * @return boolean
//	 * */
//	public boolean impeachMaster(Player player){
//		PlayerAss pAss=player.getAssociation();
//		if(pAss==null)
//			throw new NoticeException("你还没有帮会");
//		Player p=Context.getCacheService().getObject(pAss.getEntity().playerId, Player.class);
//		if(System.currentTimeMillis()<(p.getEntity().loginTime.getTime()+DateUtil.DAY*5))
//			throw new NoticeException("帮主最近有上线喔");
//		if(player.getKnapsack().getItemById(AssociationManage.CREATE_ASS_ITEMID)==null)
//			throw new NoticeException("你没有建帮令");
//		pAss.transferAss(player);
//		//通知title改变
//        player.onAssociationChange();
//		String content="亲爱的玩家：\n    由于您超过5天未登陆游戏，帮会成员XXX为了管理帮会的事务，对您发起帮主弹劾，并成功成为了新帮主，而您被弹劾成功，自动成为普通帮众！";
//        Context.getLogic(Logic.class).getMailService().sendMail("帮会通知", content,  MailAttachment.createByItem(null),String.valueOf(p.id));
//		return true;
//	} 
//	
//	/**
//	 * 快速回城
//	 * */
//	public boolean townPortal(Player player){
//		PlayerAss pAss=player.getAssociation();
//		if(pAss==null)
//			throw new NoticeException("你还没有帮会");
//		CrewBean bean=pAss.getCrewById(player.id);
//		if(bean.con<AssociationManage.TownPortal)
//			throw new NoticeException("帮贡不足");
//		pAss.updMoneyWoodCon(player, 0, 0,  -AssociationManage.TownPortal,false);
//		
//		Npc npc = Context.getCacheService().getObject(AssociationManage.NpcId,
//				Npc.class);
//		player.x = npc.x;
//		player.y = npc.y;
//		player.enterDir = -1;
//		npc.getScene().onEnter(player);
//		return true;
//	}
//	
//	/**群发邮件*/
//	public boolean sendMail(Player player){
//		return true;
//	}
//	
//	/**招募会员*/
//	public boolean recruit(Player player){
//		PlayerAss pAss=player.getAssociation();
//		if(pAss==null)
//			throw new NoticeException("你还没有帮会");
//		if(pAss.getCrewById(player.id).post!=4)
//			throw new NoticeException("权限不足");
//		//发布传闻
//		Map<String, Object> param = new HashMap<String, Object>();
//		param.put("assName", pAss.getEntity().name);
//		param.put("playerName", new PlayerLink(player, false));
//		param.put("id", pAss.getEntity().id);
//		param.put("faction", AssociationManage.getFactionName(pAss.getEntity().faction));
//		Context.getLogic(Logic.class).getReportManager().report(38, param);
//		return true;
//	}
//	
//	//---------------------帮会技能---------------------
//	/**
//	 * 帮会技能列表
//	 * @return AssSkillInfoDto
//	 * */
//	public AssSkillInfoDto assSkillList(Player player){
//		PlayerAss pAss=player.getAssociation();
//		if(pAss==null)
//			throw new NoticeException("你还没有帮会");
//		PlayerAssSkill assSkill=player.getAssSkill();
//		return new AssSkillInfoDto().passDto(player,pAss,getAssSkillDto(assSkill));
//	}
//	
//	/**
//	 * 学习/升级技能
//	 * @param type(一键学习type为"")
//	 * @return AssSkillInfoDto
//	 * */
//	public AssSkillInfoDto useAssSkill(Player player,String type){
//		PlayerAss pAss=player.getAssociation();
//		if(pAss==null)
//			throw new NoticeException("你还没有帮会");
//		
//		PlayerAssSkill assSkill=player.getAssSkill();
//		if(!"".equals(type)){
//			int level=assSkill.getSkillLevel(type);
//			if(level>=AssociationManage.MAX_LEVEL)
//				throw new NoticeException("技能已达到最大等级");
//			AssSkillConfig config=AssociationManage.getSkillConfig(level, type);
//			if(pAss.getEntity().studyLevel<config.needStudy)
//				throw new NoticeException("研究所等级不满足");
//			if(pAss.getCrewById(player.id).con<config.needCon)
//				throw new NoticeException("帮会贡献不足");
//			
//			assSkill.useAssSkill(type);
//			pAss.updMoneyWoodCon(player, 0, 0, -config.needCon,false);
//		}else{//一键学习
//			boolean flag=false;
//			for (String t : PlayerAssSkill.TYPES) {
//				int level = assSkill.getSkillLevel(t);
//				if (level >= AssociationManage.MAX_LEVEL)
//					continue;
//				AssSkillConfig config = AssociationManage.getSkillConfig(level,t);
//				if (pAss.getCrewById(player.id).con >= config.needCon
//						&& pAss.getEntity().studyLevel >= config.needStudy) {
//					assSkill.useAssSkill(t);
//					pAss.updMoneyWoodCon(player, 0, 0, -config.needCon, false);
//					flag=true;
//				}
//			}
//			if(!flag)
//				throw new NoticeException("条件不足无法升级");
//		}
//		return new AssSkillInfoDto().passDto(player,pAss,getAssSkillDto(assSkill));
//	}
//	
//	/**
//	 * 技能类型(maxHp生命上限，phyDef物理防御，magDef法术防御，aim命中，
//	 * tou韧性，av闪避，ppsp攻击，speed速度，vio暴击，metal金抗，wood木抗，water水抗，fire火抗，earth土抗)
//	 */
//	private AssSkillDto[] getAssSkillDto(PlayerAssSkill assSkill){
//		AssSkillDto[] dto=new AssSkillDto[15];
//		dto[0]=new AssSkillDto().passDto(assSkill.getEntity().maxHp,"maxHp");
//		dto[1]=new AssSkillDto().passDto(assSkill.getEntity().phyDef,"phyDef");
//		dto[2]=new AssSkillDto().passDto(assSkill.getEntity().magDef,"magDef");
//		dto[3]=new AssSkillDto().passDto(assSkill.getEntity().aim,"aim");
//		dto[4]=new AssSkillDto().passDto(assSkill.getEntity().tou,"tou");
//		dto[5]=new AssSkillDto().passDto(assSkill.getEntity().av,"av");
//		dto[6]=new AssSkillDto().passDto(assSkill.getEntity().ppsp,"ppsp");
//		dto[7]=new AssSkillDto().passDto(assSkill.getEntity().speed,"speed");
//		dto[8]=new AssSkillDto().passDto(assSkill.getEntity().vio,"vio");
//		dto[9]=new AssSkillDto().passDto(assSkill.getEntity().metal,"metal");
//		dto[10]=new AssSkillDto().passDto(assSkill.getEntity().wood,"wood");
//		dto[11]=new AssSkillDto().passDto(assSkill.getEntity().water,"water");
//		dto[12]=new AssSkillDto().passDto(assSkill.getEntity().fire,"fire");
//		dto[13]=new AssSkillDto().passDto(assSkill.getEntity().earth,"earth");
//		return dto ;
//	}
//	
//	//---------------------帮会建筑---------------------
//	/**
//	 * 帮会营地
//	 * @return UpBuildShowDto
//	 * */
//	public UpBuildShowDto upBuildShow(Player player){
//		PlayerAss pAss=player.getAssociation();
//		if(pAss==null)
//			throw new NoticeException("你还没有帮会");
//		return new UpBuildShowDto().passDto(player);
//	}
//	
//	/**
//	 * 升级建筑
//	 * @param type 建筑类型(0聚义厅、1冶炼坊、2金库、3厢房、4研究院)
//	 * @return UpBuildShowDto
//	 * */
//	public UpBuildShowDto upBuild(Player player,int type){
//		if(type<0 || type>4)
//			throw new NoticeException("wrong type");
//		PlayerAss pAss=player.getAssociation();
//		if(pAss==null)
//			throw new NoticeException("你还没有帮会");
//		if(player.id!=pAss.getMaster().id)
//			throw new NoticeException("你不是帮主");
//		synchronized (pAss) {
//			if(type==0){
//				if(pAss.getEntity().level>=AssociationManage.MAX_ASS_LEVEL-1)
//					throw new NoticeException("聚义厅已达到最大等级");
//				AssConfig assConfig=AssociationManage.getAssConfig(pAss.getEntity().level);
//				if(pAss.getEntity().money<assConfig.hallUpMoney)
//					throw new NoticeException("升级需要的资金不足");
//				pAss.getEntity().money-=assConfig.hallUpMoney;
//				pAss.getEntity().level++;
//				
//				assConfig=AssociationManage.getAssConfig(pAss.getEntity().level);
//				pAss.getEntity().maintainMoney=assConfig.maintainMoney;
//				
//				Context.getLogic(Logic.class).getLogFactory().updateAssociationTech(player, "聚义厅", pAss.getEntity().level, assConfig.hallUpMoney, 0);
//			}else if(type==1){
//				if(pAss.getEntity().smeltLevel==pAss.getEntity().level)
//					throw new NoticeException("冶炼坊等级不能大于聚义厅等级，请先升级聚义厅");
//				if(pAss.getEntity().smeltLevel>=AssociationManage.MAX_ASS_LEVEL)
//					throw new NoticeException("冶炼坊已达到最大等级");
//				AssConfig assConfig=AssociationManage.getAssConfig(pAss.getEntity().smeltLevel);
//				if(pAss.getEntity().money<assConfig.smeltMoney)
//					throw new NoticeException("升级需要的资金不足");
//				if(pAss.getEntity().wood<assConfig.smeltWood)
//					throw new NoticeException("升级需要的木材不足");
//				pAss.getEntity().money-=assConfig.smeltMoney;
//				pAss.getEntity().wood-=assConfig.smeltWood;
//				pAss.getEntity().smeltLevel++;
//				
//				Context.getLogic(Logic.class).getLogFactory().updateAssociationTech(player, "冶炼坊", pAss.getEntity().smeltLevel, assConfig.smeltMoney, assConfig.smeltWood);
//			}else if(type==2){
//				if(pAss.getEntity().cofferLevel==pAss.getEntity().level)
//					throw new NoticeException("金库等级不能大于聚义厅等级，请先升级聚义厅");
//				if(pAss.getEntity().cofferLevel>=AssociationManage.MAX_ASS_LEVEL)
//					throw new NoticeException("金库已达到最大等级");
//				AssConfig assConfig=AssociationManage.getAssConfig(pAss.getEntity().cofferLevel);
//				if(pAss.getEntity().money<assConfig.cofferMoney)
//					throw new NoticeException("升级需要的资金不足");
//				if(pAss.getEntity().wood<assConfig.cofferWood)
//					throw new NoticeException("升级需要的木材不足");
//				pAss.getEntity().money-=assConfig.cofferMoney;
//				pAss.getEntity().wood-=assConfig.cofferWood;
//				pAss.getEntity().cofferLevel++;
//				
//				Context.getLogic(Logic.class).getLogFactory().updateAssociationTech(player, "金库", pAss.getEntity().cofferLevel, assConfig.cofferMoney, assConfig.cofferWood);
//			}else if(type==3){
//				if(pAss.getEntity().roomLevel==pAss.getEntity().level)
//					throw new NoticeException("厢房等级不能大于聚义厅等级，请先升级聚义厅");
//				if(pAss.getEntity().roomLevel>=AssociationManage.MAX_ASS_LEVEL)
//					throw new NoticeException("厢房已达到最大等级");
//				AssConfig assConfig=AssociationManage.getAssConfig(pAss.getEntity().roomLevel);
//				if(pAss.getEntity().money<assConfig.roomMoney)
//					throw new NoticeException("升级需要的资金不足");
//				if(pAss.getEntity().wood<assConfig.roomWood)
//					throw new NoticeException("升级需要的木材不足");
//				pAss.getEntity().money-=assConfig.roomMoney;
//				pAss.getEntity().wood-=assConfig.roomWood;
//				pAss.getEntity().roomLevel++;
//				
//				assConfig=AssociationManage.getAssConfig(pAss.getEntity().roomLevel);
//				pAss.getEntity().crewMax=assConfig.roomPeopleMax;
//				
//				Context.getLogic(Logic.class).getLogFactory().updateAssociationTech(player, "厢房", pAss.getEntity().roomLevel, assConfig.roomMoney, assConfig.roomWood);
//			}else if(type==4){
//				if(pAss.getEntity().studyLevel==pAss.getEntity().level)
//					throw new NoticeException("研究院等级不能大于聚义厅等级，请先升级聚义厅");
//				if(pAss.getEntity().studyLevel>=AssociationManage.MAX_ASS_LEVEL)
//					throw new NoticeException("研究院已达到最大等级");
//				AssConfig assConfig=AssociationManage.getAssConfig(pAss.getEntity().studyLevel);
//				if(pAss.getEntity().money<assConfig.studyMoney)
//					throw new NoticeException("升级需要的资金不足");
//				if(pAss.getEntity().wood<assConfig.studyWood)
//					throw new NoticeException("升级需要的木材不足");
//				pAss.getEntity().money-=assConfig.studyMoney;
//				pAss.getEntity().wood-=assConfig.studyWood;
//				pAss.getEntity().studyLevel++;
//				
//				Context.getLogic(Logic.class).getLogFactory().updateAssociationTech(player, "研究院", pAss.getEntity().studyLevel, assConfig.studyMoney, assConfig.studyWood);
//			}
//			try{
//				pAss.canSave.set(true);
//				pAss.save();
//			}catch (Exception e) {
//				log.error(e, e);
//			}
//		}
//		return new UpBuildShowDto().passDto(player);
//	}
//	
//	//---------------------帮会目标---------------------
//	/**
//	 * 帮会目标
//	 * @return AssGoalDto[]
//	 * */
//	public AssGoalDto[] assGoal(Player player){
//		PlayerAss pAss=player.getAssociation();
//		if(pAss==null)
//			throw new NoticeException("你还没有帮会");
//		Collection<AssGoalConfig> list=AssociationManage.getGoalList();
//		List<AssGoalDto> dtoList=new ArrayList<AssGoalDto>();
//		for(AssGoalConfig cf:list){
//			dtoList.add(new AssGoalDto().passDto(cf,player));
//		}
//		Collections.sort(dtoList);
//		return dtoList.toArray(new AssGoalDto[dtoList.size()]);
//	}
//	
//	/**
//	 * 领取帮会目标奖励
//	 * @param goalId 目标id
//	 * @return boolean
//	 * */
//	public boolean getReward(Player player,int goalId){
//		PlayerAss pAss=player.getAssociation();
//		if(pAss==null)
//			throw new NoticeException("你还没有帮会");
//		if(player.id!=pAss.getMaster().id)
//			throw new NoticeException("你不是帮主");
//		synchronized (pAss) {
//			Set<Integer> set=StringUtil.splitStrToIntegerSet(pAss.getEntity().canReward);
//			if(!set.contains(goalId))
//				throw new NoticeException("你还没完成目标");
//			
//			Set<Integer> set2=StringUtil.splitStrToIntegerSet(pAss.getEntity().finishGoal);
//			if(set2.contains(goalId))
//				throw new NoticeException("你已经领取过奖励");
//			
//			AssGoalConfig config=AssociationManage.getGoalConfig(goalId);
//			pAss.getEntity().money+=config.money;
//			pAss.getEntity().wood+=config.wood;
//			set2.add(goalId);
//			pAss.getEntity().finishGoal=StringUtil.setToSplitStr(set2);
//			try{
//				pAss.canSave.set(true);
//				pAss.save();
//			}catch (Exception e) {
//				log.error(e,e);
//			}
//			
//			String msg="帮会获得了"+config.money+"资金,"+config.wood+"木材";
//			Context.getTcpService().send(new AlertDto(msg),player.id);
//			
//			Map<Integer,Integer> itemAndNum=new HashMap<Integer,Integer>();
//			String[] reward=config.getReward().split(",");
//			for(String str:reward){
//				String[] s=str.split(":");
//				itemAndNum.put(Integer.parseInt(s[0]),Integer.parseInt(s[1]));
//			}
//			for(CrewBean bean:pAss.getCrewList()){
//				String content = "亲爱的玩家：\n    在帮会全体兄弟姐妹的努力下，帮会日益发展壮大，终于达成了“"+config.goal+"”的目标！帮会全体成员共同获得了该目标的奖励，请再接再厉！";
//				Context.getLogic(Logic.class).getMailService().sendMail("完成帮会目标“"+config.goal+"”奖励", content,  MailAttachment.createByItemAndNum(itemAndNum),String.valueOf(bean.id)); 
//			}
//		}
//		return true;
//	}
//	
//	//---------------------帮会历练---------------------
//	/**
//	 * 打开帮会历练面板
//	 * @return AssTaskDto
//	 * */
//	public AssTaskDto openAssTask(Player player){
//		PlayerAss pAss=player.getAssociation();
//		if(pAss==null)
//			throw new NoticeException("你还没有帮会");
//		CrewBean bean=pAss.getCrewById(player.id);
//		if(bean.status!=0){
//			MissionDto dto= bean.status == 1 ? player.getMissionManager().getLastAssoExperMission() : player.getMissionManager().getAssoExperMission();
//			if(dto==null){
//				dto=player.getMissionManager().changeAssoExperMission(bean.quality);
//				bean.taskName=dto.name;
//				bean.status=1;
//			}
//		}
//		return new AssTaskDto().passDto(pAss,player);
//	}
//	
//	/**
//	 * 查看任务/更改任务
//	 * @param type 1查看任务，2更改任务
//	 * @return AssTaskDto
//	 * */
//	public AssTaskDto lookTask(Player player,int type){
//		if(type<1 || type>2)
//			throw new NoticeException("wrong type");
//		PlayerAss pAss=player.getAssociation();
//		if(pAss==null)
//			throw new NoticeException("你还没有帮会");
//		CrewBean bean=pAss.getCrewById(player.id);
//		synchronized (bean) {
//			if(type==1){
//				if(bean.status==4)
//					throw new NoticeException("你已完成今日历练");
//				if(bean.status!=0)
//					throw new NoticeException("你已有任务");
//				//每天查看任务时初始化幸运值
//				bean.luck=PlayerAssTask.getLuck();
//				bean.quality=PlayerAssTask.getQuality(bean.luck);
//			}else if(type==2){
//				if(bean.status==4)
//					throw new NoticeException("你已完成今日任务");
//				if(bean.status==2 || bean.status==3)
//					throw new NoticeException("任务已在进行中");
//				boolean flag=player.getMoney().change(-100, -2000, 0, 0, 0, true, 0, 1, "assTask");
//				if(!flag)
//					throw new NoticeException("铜钱不足");
//				player.getMoney().syncToClient();
//			}
//			MissionDto dto=player.getMissionManager().changeAssoExperMission(bean.quality);
//			bean.taskName=dto.name;
//			bean.status=1;
//			return new AssTaskDto().passDto(pAss,player);
//		}
//	}
//	
//	/**
//	 * 帮他刷列表
//	 * @return RefreshTaskDto
//	 * */
//	public RefreshInfoDto refreshList(Player player,int type){
//		PlayerAss pAss=player.getAssociation();
//		if(pAss==null)
//			throw new NoticeException("你还没有帮会");
//		RefreshInfoDto dto=new RefreshInfoDto();
//		if(type==1){
//			pAss.assBroadcast("【"+player.name+"】请求刷新帮会试练任务品质！<u><a href='event:dialog|30'>帮他/她刷新</a></u>");
//		}else{
//			List<RefreshTaskDto> list=new ArrayList<RefreshTaskDto>();
//			for(CrewBean bean:pAss.getCrewList()){
//				if(bean.id==player.id)
//					continue;
//				if(!"".equals(bean.taskName) && bean.taskName!=null)
//					list.add(new RefreshTaskDto().passDto(bean));
//			}
//			Collections.sort(list);
//			
//			CrewBean bean=pAss.getCrewById(player.id);
//			dto.maxTimes=PlayerAssTask.REFRESH_TIMES;
//			dto.times=PlayerAssTask.REFRESH_TIMES-bean.helpOther;
//			dto.refreshTaskDto=list.toArray(new RefreshTaskDto[list.size()]);
//		}
//		return dto;
//	}
//	
//	/**
//	 * 请别人刷
//	 * @return boolean
//	 * */
//	public boolean inviteRefresh(Player player){
//		PlayerAss pAss=player.getAssociation();
//		if(pAss==null)
//			throw new NoticeException("你还没有帮会");
//		
//		CrewBean bean=pAss.getCrewById(player.id);
//		if(bean.status!=1)
//			throw new NoticeException("你已领取任务不能在刷新");
//		
//		pAss.assBroadcast(player.name + "请求刷新帮会试练任务品质！");
//		return true;
//	}
//	
//	/**
//	 * 帮他刷
//	 * @param playerId 对方id
//	 * @return int 品质
//	 * */
//	public int helpOther(Player player,int playerId){
//		PlayerAss pAss=player.getAssociation();
//		if(pAss==null)
//			throw new NoticeException("你还没有帮会");
//		
//		CrewBean bean=pAss.getCrewById(player.id);
//		if(bean.helpOther>=PlayerAssTask.REFRESH_TIMES)
//			throw new NoticeException("你今日刷新次数已用完");
//		
//		CrewBean otherBean=pAss.getCrewById(playerId);
//		if(otherBean.quality==5)
//			throw new NoticeException("任务品质已经为橙色，不需要再刷新");
//		if(otherBean.status==0)
//			throw new NoticeException("对方还未查看任务");
//		if(otherBean.status!=1)
//			throw new NoticeException("对方已在任务不能刷新");
//		if(otherBean.refLog.split(",").length>=PlayerAssTask.REFRESH_TIMES)
//			throw new NoticeException("对方已被刷10次");
//		
//		PlayerAssTask.upLuck(otherBean);
//		bean.helpOther++;
//		
//		synchronized (otherBean) {
//			int quality=PlayerAssTask.getQuality(otherBean.luck);
//			otherBean.quality=quality;
//			otherBean.refLog+=player.name+"帮你把任务刷成"+quality+"色,";
//			
//			Player p=Context.getCacheService().getObject(playerId, Player.class);
//			MissionDto tarMission = p.getMissionManager().changeAssoExperMissionQuality(quality);
//			Context.getTcpService().send(new NoticeDto("你帮【"+p.name+"】刷新出"+PlayerAssTask.quality[quality-1]+"色品质任务"), player);
//			Context.getLogic(Logic.class).getLogFactory().helpOtherRefreshExperMission(player, p.name, tarMission.goalDesc, PlayerAssTask.quality[quality-1]);
//			return quality;
//		}
//	}
//	
//	/**
//	 * 领取任务/提交任务/放弃任务
//	 * @param type 1领取任务 2提交任务3放弃任务
//	 * @return int 任务状态
//	 * */
//	public int changeTaskStatus(Player player,int type){
//		if(type<1 || type>3)
//			throw new NoticeException("wrong type");
//		PlayerAss pAss=player.getAssociation();
//		if(pAss==null)
//			throw new NoticeException("你还没有帮会");
//		
//		CrewBean bean=pAss.getCrewById(player.id);
//		if(type==1){
//			player.getMissionManager().acceptAssoExperMission();
//			bean.status=2;
//		}else if(type==2){
//			player.getMissionManager().finishAssoExperMission();
//			bean.status=4;
//		}else if(type==3){
//			player.getMissionManager().giveUpAssoExperMission();
//			bean.status=4;
//		}
//		pAss.canSave.set(true);
//		return bean.status;
//	}
//	
//	//---------------------帮会活动----------------------
//	/**
//	 * 帮会活动
//	 * @return AssActivityDto[]
//	 * */
//	public AssActivityDto[] assActivity(Player player){
//		PlayerAss pAss=player.getAssociation();
//		if(pAss==null)
//			throw new NoticeException("你还没有帮会");
//		Collection<AssActivityConfig> coll=AssociationManage.getActivityList();
//		List<AssActivityDto> list=new ArrayList<AssActivityDto>();
//		for(AssActivityConfig cf:coll){
//			list.add(new AssActivityDto().passDto(cf,pAss));
//		}
//		return list.toArray(new AssActivityDto[list.size()]);
//	}
//	
//	/**
//	 * 设置活动时间
//	 * @param id 活动id
//	 * @param time 选择的时间段
//	 * @return boolean
//	 * */
//	public boolean setActivityTime(Player player,int id,String time) throws Exception{
//		PlayerAss pAss=player.getAssociation();
//		if(pAss==null)
//			throw new NoticeException("你还没有帮会");
//		if(player.id!=pAss.getMaster().id)
//			throw new NoticeException("你不是帮主");
//		synchronized (pAss) {
//			switch(id){
//			case AssociationManage.CAMPFIRE:
//				pAss.getEntity().campfire2=time;
//				pAss.assBroadcast("帮主【"+pAss.getEntity().playerName+"】修改了明天帮会篝火开启时间为"+time+"，记得明天准时参加哦！");
//				break;
//			case AssociationManage.GUARD:
//				pAss.getEntity().guard2=time;
//				pAss.assBroadcast("帮主【"+pAss.getEntity().playerName+"】修改了明天守护营地开启时间为"+time+"，记得明天准时参加哦！");
//				break;
//			}
//		}
//		return true;
//	}
//	
//	/**
//	 * 活动剩余时间
//	 * @param actId 活动id
//	 * @return long(-1表示活动未开启，0表示已结束)
//	 * */
//	public long getRemainTime(Player player,int actId){
//		long remainTime=0;
//		if(actId==AssociationManage.MELEE){
//			if(AssMeleeInstance.status.get()==0)
//				remainTime=-1;
//			else if(AssMeleeInstance.status.get()==3)
//				remainTime=0;
//			else{
//				remainTime=AssociationManage.assMelee.getRemainTime();
//				remainTime-=DateUtil.MINUTE*15;
//				if(remainTime<0)
//					remainTime=0;
//			}
//		}
//		return remainTime;
//	}
//	
//	/**
//	 * 参加活动
//	 * @param actId 活动Id(1篝火、2营地、3乱斗、4探险、5地标、6降魔)
//	 * @param other 其他参数（神兽0-7）
//	 * */
//	public boolean joinActivity(Player player,int actId,int other){
//		PlayerAss pAss=player.getAssociation();
//		if(pAss==null)
//			throw new NoticeException("你还没有帮会");
//		if(actId==AssociationManage.CAMPFIRE){
//			if(!pAss.isInCampfire())
//				throw new NoticeException("不在活动时间,不能参加");
//			Npc npc = Context.getCacheService().getObject(AssociationManage.NpcId,
//					Npc.class);
//			player.x = npc.x;
//			player.y = npc.y;
//			player.enterDir = -1;
//			npc.getScene().onEnter(player);
//		}else if(actId==AssociationManage.GUARD){
//			pAss.campsiteInstance.enter(player);
//		}else if(actId==AssociationManage.MELEE){
//			AssociationManage.assMelee.enter(player);
//		}else if(actId==AssociationManage.EXPLORE){
//			pAss.assExploreInstance.enter(player);
//		}else if(actId==AssociationManage.ASSBOSS){
//			if(pAss.assBossInstance.getStatus()==0){
//				if(pAss.getCrewById(player.id).post!=4)
//					throw new NoticeException("权限不足");
//				if(pAss.getEntity().boss>=1)
//					throw new NoticeException("今日已超过开启次数");
//				if(other>pAss.getBossLevel())
//					throw new NoticeException("金库等级不足，无法召唤此神兽");
//				pAss.assBossInstance=new AssBossInstance();
//				pAss.assBossInstance.ready(pAss.getEntity().id, System.currentTimeMillis(), System.currentTimeMillis()+DateUtil.MINUTE*30,other);
//			}
//			pAss.assBossInstance.enter(player);
//		}
//		return true;
//	}
//}
