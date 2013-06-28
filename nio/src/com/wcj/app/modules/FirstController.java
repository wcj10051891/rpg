package com.wcj.app.modules;

import org.apache.log4j.Logger;

import com.wcj.app.handler.Controller;

@Controller
public class FirstController {
	
	private static final Logger log = Logger.getLogger(FirstController.class);

	public void firstMethod(int i, boolean b) {
		log.debug("FirstController#firstMethod invoked.");
	}
	
	public String secondMethod(int i, boolean b) {
		log.debug("FirstController#secondMethod invoked.");
		return "yes";
	}
}
