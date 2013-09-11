package {

    import gen.controller.AssociationCtrl;                

	public class RpcServiceArray {

        public static var rpcs:Array = [
            gen.controller.AssociationCtrl 
        ];

        public static var rpcArgs:Object = {
            "gen.controller.AssociationCtrl::createAss" : ["name", "notice"] ,
            "gen.controller.AssociationCtrl::searchAss" : ["name", "pName"] ,
            "gen.controller.AssociationCtrl::getAssInfo" : [] ,
            "gen.controller.AssociationCtrl::getAssCrew" : [] ,
            "gen.controller.AssociationCtrl::getAssApply" : [] ,
            "gen.controller.AssociationCtrl::getAssConMoneyLog" : ["type"] ,
            "gen.controller.AssociationCtrl::updNotice" : ["content", "qq", "yy"] ,
            "gen.controller.AssociationCtrl::donateMoney" : ["cash", "coin"] ,
            "gen.controller.AssociationCtrl::setPost" : ["playerId", "post"] ,
            "gen.controller.AssociationCtrl::curPlayerCon" : ["playerId"] ,
            "gen.controller.AssociationCtrl::expelPlayer" : ["playerId"] ,
            "gen.controller.AssociationCtrl::relievePost" : [] ,
            "gen.controller.AssociationCtrl::handleApply" : ["playerId", "accept"] ,
            "gen.controller.AssociationCtrl::getWelfare" : ["rewardId"] ,
            "gen.controller.AssociationCtrl::dismissAss" : [] ,
            "gen.controller.AssociationCtrl::impeachMaster" : [] ,
            "gen.controller.AssociationCtrl::townPortal" : [] ,
            "gen.controller.AssociationCtrl::sendMail" : [] ,
            "gen.controller.AssociationCtrl::recruit" : [] ,
            "gen.controller.AssociationCtrl::assSkillList" : [] ,
            "gen.controller.AssociationCtrl::useAssSkill" : ["type"] ,
            "gen.controller.AssociationCtrl::upBuildShow" : [] ,
            "gen.controller.AssociationCtrl::upBuild" : ["type"] ,
            "gen.controller.AssociationCtrl::assGoal" : [] ,
            "gen.controller.AssociationCtrl::getReward" : ["goalId"] ,
            "gen.controller.AssociationCtrl::openAssTask" : [] ,
            "gen.controller.AssociationCtrl::lookTask" : ["type"] ,
            "gen.controller.AssociationCtrl::refreshList" : ["type"] ,
            "gen.controller.AssociationCtrl::inviteRefresh" : [] ,
            "gen.controller.AssociationCtrl::helpOther" : ["playerId"] ,
            "gen.controller.AssociationCtrl::changeTaskStatus" : ["type"] ,
            "gen.controller.AssociationCtrl::assActivity" : [] ,
            "gen.controller.AssociationCtrl::setActivityTime" : ["id", "time"] ,
            "gen.controller.AssociationCtrl::getRemainTime" : ["actId"] ,
            "gen.controller.AssociationCtrl::joinActivity" : ["actId", "other"] 
        };
	}
}
