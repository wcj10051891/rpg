package com.wcj.app.modules;

import java.util.List;

import org.apache.log4j.Logger;

import com.wcj.app.dao.ActiveDao;
import com.wcj.app.handler.Controller;
import com.wcj.app.protocol.ResponseDto;
import com.wcj.channel.Groups;
import com.wcj.core.Context;
import com.wcj.dao.test.Active;

@Controller
public class FirstController {
	
	private static final Logger log = Logger.getLogger(FirstController.class);

	public void firstMethod(int i, boolean b) {
		log.debug("FirstController#firstMethod invoked.");
		List<Active> all = Context.daoFactory.get(ActiveDao.class).getAll();
		System.out.println("data:" + all);
	}
	
	public void broadcast(String message) {
		log.debug("FirstController#broadcast invoked.");
		ResponseDto result = new ResponseDto();
		result.setResult(message);
		Context.groups.broadcast(Groups.World, result);
	}
}