package gen.controller{


		/**
		 * 帮会1
		 * 帮会2
		 * 帮会3
		 * 帮会4
		 * @author shan
		 * @date 2012-08-23
		 */

	public class AssociationCtrl
	{
		/**
		 * 创建帮会
		 * @param name 帮会名称
		 * @param notice 帮会公告
		 * @return boolean
		 */
		public static function createAss(name:String, notice:String, callback:Function):Boolean{
				callRPC(
				"AssociationCtrl.createAss",arguments);
				return null;
		}
		/**
		 * 帮会列表/查找帮会
		 * @param name 帮会名称
		 * @param pName 帮主昵称
		 * @return SearchAssDto[]
		 */
		public static function searchAss(name:String, pName:String, callback:Function):Array{
				callRPC(
				"AssociationCtrl.searchAss",arguments);
				return null;
		}
		/**
		 * 自己帮会信息
		 * @return AssInfoDto
		 */
		public static function getAssInfo(callback:Function):com.cndw.xianhun.mvc.dto.association.AssInfoDto{
				callRPC(
				"AssociationCtrl.getAssInfo",arguments);
				return null;
		}
		/**
		 * 帮会成员列表
		 * @return AssCrewDto[]
		 */
		public static function getAssCrew(callback:Function):Array{
				callRPC(
				"AssociationCtrl.getAssCrew",arguments);
				return null;
		}
		/**
		 * 帮会申请列表
		 * @return AssApplyDto[]
		 */
		public static function getAssApply(callback:Function):Array{
				callRPC(
				"AssociationCtrl.getAssApply",arguments);
				return null;
		}
		/**
		 * 帮会捐献资金日志
		 * @param type 日志类型(0统计 1日志)
		 * @return AssConMoneyLogDto[]
		 */
		public static function getAssConMoneyLog(type:int, callback:Function):Array{
				callRPC(
				"AssociationCtrl.getAssConMoneyLog",arguments);
				return null;
		}
		/**
		 * 修改公告
		 * @param content 公告内容
		 * @param qq 帮会qq群
		 * @param yy 帮会yy群
		 * @return boolean
		 */
		public static function updNotice(content:String, qq:String, yy:String, callback:Function):Boolean{
				callRPC(
				"AssociationCtrl.updNotice",arguments);
				return null;
		}
		/**
		 * 捐献钱
		 * @param cash 捐献元宝
		 * @param coin 捐献铜币
		 * @return int[] 捐献增加的帮贡，捐献增加的帮会资金
		 */
		public static function donateMoney(cash:int, coin:int, callback:Function):Array{
				callRPC(
				"AssociationCtrl.donateMoney",arguments);
				return null;
		}
		/**
		 * 设置职务
		 * @param playerId 接任人ID
		 * @param post 职务(0帮众、1堂主、2长老、3副帮主、4帮主)
		 * @return boolean
		 */
		public static function setPost(playerId:int, post:int, callback:Function):Boolean{
				callRPC(
				"AssociationCtrl.setPost",arguments);
				return null;
		}
		/**
		 * 当前成员帮贡
		 * @param playerId 开除成员id
		 */
		public static function curPlayerCon(playerId:int, callback:Function):int{
				callRPC(
				"AssociationCtrl.curPlayerCon",arguments);
				return null;
		}
		/**
		 * 开除成员/主动退出帮会
		 * @param playerId 开除成员id,主动退出为0
		 * @return boolean
		 */
		public static function expelPlayer(playerId:int, callback:Function):Boolean{
				callRPC(
				"AssociationCtrl.expelPlayer",arguments);
				return null;
		}
		/**
		 * 卸任
		 * @return boolean
		 */
		public static function relievePost(callback:Function):Boolean{
				callRPC(
				"AssociationCtrl.relievePost",arguments);
				return null;
		}
		/**
		 * 处理申请
		 * @param playerId 申请人id(全部操作填0)
		 * @param accept 是否同意申请(1同意,0拒绝)
		 * @return boolean
		 */
		public static function handleApply(playerId:int, accept:int, callback:Function):Boolean{
				callRPC(
				"AssociationCtrl.handleApply",arguments);
				return null;
		}
		/**
		 * 领取帮会福利
		 * @param rewardId领取奖励id(1-3对应1-3小时奖励)
		 * @return boolean
		 */
		public static function getWelfare(rewardId:int, callback:Function):Boolean{
				callRPC(
				"AssociationCtrl.getWelfare",arguments);
				return null;
		}
		/**
		 * 解散帮会
		 * @return boolean
		 */
		public static function dismissAss(callback:Function):Boolean{
				callRPC(
				"AssociationCtrl.dismissAss",arguments);
				return null;
		}
		/**
		 * 主动弹劾帮主
		 * @return boolean
		 */
		public static function impeachMaster(callback:Function):Boolean{
				callRPC(
				"AssociationCtrl.impeachMaster",arguments);
				return null;
		}
		/**
		 * 快速回城
		 */
		public static function townPortal(callback:Function):Boolean{
				callRPC(
				"AssociationCtrl.townPortal",arguments);
				return null;
		}
		/**
		 * 群发邮件
		 */
		public static function sendMail(callback:Function):Boolean{
				callRPC(
				"AssociationCtrl.sendMail",arguments);
				return null;
		}
		/**
		 * 招募会员
		 */
		public static function recruit(callback:Function):Boolean{
				callRPC(
				"AssociationCtrl.recruit",arguments);
				return null;
		}
		/**
		 * 帮会技能列表
		 * @return AssSkillInfoDto
		 */
		public static function assSkillList(callback:Function):com.cndw.xianhun.mvc.dto.association.AssSkillInfoDto{
				callRPC(
				"AssociationCtrl.assSkillList",arguments);
				return null;
		}
		/**
		 * 学习/升级技能
		 * @param type(一键学习type为"")
		 * @return AssSkillInfoDto
		 */
		public static function useAssSkill(type:String, callback:Function):com.cndw.xianhun.mvc.dto.association.AssSkillInfoDto{
				callRPC(
				"AssociationCtrl.useAssSkill",arguments);
				return null;
		}
		/**
		 * 帮会营地
		 * @return UpBuildShowDto
		 */
		public static function upBuildShow(callback:Function):com.cndw.xianhun.mvc.dto.association.UpBuildShowDto{
				callRPC(
				"AssociationCtrl.upBuildShow",arguments);
				return null;
		}
		/**
		 * 升级建筑
		 * @param type 建筑类型(0聚义厅、1冶炼坊、2金库、3厢房、4研究院)
		 * @return UpBuildShowDto
		 */
		public static function upBuild(type:int, callback:Function):com.cndw.xianhun.mvc.dto.association.UpBuildShowDto{
				callRPC(
				"AssociationCtrl.upBuild",arguments);
				return null;
		}
		/**
		 * 帮会目标
		 * @return AssGoalDto[]
		 */
		public static function assGoal(callback:Function):Array{
				callRPC(
				"AssociationCtrl.assGoal",arguments);
				return null;
		}
		/**
		 * 领取帮会目标奖励
		 * @param goalId 目标id
		 * @return boolean
		 */
		public static function getReward(goalId:int, callback:Function):Boolean{
				callRPC(
				"AssociationCtrl.getReward",arguments);
				return null;
		}
		/**
		 * 打开帮会历练面板
		 * @return AssTaskDto
		 */
		public static function openAssTask(callback:Function):com.cndw.xianhun.mvc.dto.association.AssTaskDto{
				callRPC(
				"AssociationCtrl.openAssTask",arguments);
				return null;
		}
		/**
		 * 查看任务/更改任务
		 * @param type 1查看任务，2更改任务
		 * @return AssTaskDto
		 */
		public static function lookTask(type:int, callback:Function):com.cndw.xianhun.mvc.dto.association.AssTaskDto{
				callRPC(
				"AssociationCtrl.lookTask",arguments);
				return null;
		}
		/**
		 * 帮他刷列表
		 * @return RefreshTaskDto
		 */
		public static function refreshList(type:int, callback:Function):com.cndw.xianhun.mvc.dto.association.RefreshInfoDto{
				callRPC(
				"AssociationCtrl.refreshList",arguments);
				return null;
		}
		/**
		 * 请别人刷
		 * @return boolean
		 */
		public static function inviteRefresh(callback:Function):Boolean{
				callRPC(
				"AssociationCtrl.inviteRefresh",arguments);
				return null;
		}
		/**
		 * 帮他刷
		 * @param playerId 对方id
		 * @return int 品质
		 */
		public static function helpOther(playerId:int, callback:Function):int{
				callRPC(
				"AssociationCtrl.helpOther",arguments);
				return null;
		}
		/**
		 * 领取任务/提交任务/放弃任务
		 * @param type 1领取任务 2提交任务3放弃任务
		 * @return int 任务状态
		 */
		public static function changeTaskStatus(type:int, callback:Function):int{
				callRPC(
				"AssociationCtrl.changeTaskStatus",arguments);
				return null;
		}
		/**
		 * 帮会活动
		 * @return AssActivityDto[]
		 */
		public static function assActivity(callback:Function):Array{
				callRPC(
				"AssociationCtrl.assActivity",arguments);
				return null;
		}
		/**
		 * 设置活动时间
		 * @param id 活动id
		 * @param time 选择的时间段
		 * @return boolean
		 */
		public static function setActivityTime(id:int, time:String, callback:Function):Boolean{
				callRPC(
				"AssociationCtrl.setActivityTime",arguments);
				return null;
		}
		/**
		 * 活动剩余时间
		 * @param actId 活动id
		 * @return long(-1表示活动未开启，0表示已结束)
		 */
		public static function getRemainTime(actId:int, callback:Function):Number{
				callRPC(
				"AssociationCtrl.getRemainTime",arguments);
				return null;
		}
		/**
		 * 参加活动
		 * @param actId 活动Id(1篝火、2营地、3乱斗、4探险、5地标、6降魔)
		 * @param other 其他参数（神兽0-7）
		 */
		public static function joinActivity(actId:int, other:int, callback:Function):Boolean{
				callRPC(
				"AssociationCtrl.joinActivity",arguments);
				return null;
		}

	}
}