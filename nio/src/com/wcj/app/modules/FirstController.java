package com.wcj.app.modules;

import org.apache.log4j.Logger;

import com.wcj.app.handler.Controller;
import com.wcj.app.protocol.ResponseDto;
import com.wcj.core.Context;

@Controller
public class FirstController {
	
	private static final Logger log = Logger.getLogger(FirstController.class);

	public void firstMethod(int i, boolean b) {
		log.debug("FirstController#firstMethod invoked.");
	}
	
	public void broadcast(String message) {
		log.debug("FirstController#broadcast invoked.");
		ResponseDto result = new ResponseDto();
		result.setResult(message);
		Context.groups.broadcast("world", result);
	}
}