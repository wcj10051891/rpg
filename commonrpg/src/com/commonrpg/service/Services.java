package com.commonrpg.service;

import org.apache.commons.dbutils.QueryRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wcj.dao.core.DaoContext;
import com.wcj.dao.core.DaoFactory;

public class Services {
	private static final Logger log = LoggerFactory.getLogger(Services.class);
	public static ThreadService threadService;
	public static TcpService tcpService;
	public static HttpService httpService;
	public static TimerService timerService;
	public static CacheService cacheService;
	public static AppService appService;
	public static DaoFactory daoFactory;
	public static QueryRunner jdbc;
	
	public static void start() throws Exception {
		timerService = new TimerService();
		timerService.start();
		log.info("[timerService] start ok.");
		
		threadService = new ThreadService();
		threadService.start();
		log.info("[theadService] start ok.");
		
		DaoContext daoContext = new DaoContext();
		daoFactory = daoContext.daoFactory;
		log.info("[daoFactory] start ok.");
		jdbc = daoContext.jdbc;
		log.info("[jdbc] start ok.");
		
		cacheService = new CacheService();
		cacheService.start();
		log.info("[cacheService] start ok.");
		
		appService = new AppService();
		appService.start();
		log.info("[appService] start ok.");
		
		tcpService = new TcpService();
		tcpService.start();
		log.info("[tcpService] start ok.");
		
		httpService = new HttpService();
		httpService.start();
		log.info("[httpService] start ok.");
	}
	
	public static void stop() throws Exception {
		httpService.stop();
		log.info("[httpService] stop ok.");
		
		tcpService.stop();
		log.info("[tcpService] stop ok.");

		appService.stop();
		log.info("[appService] stop ok.");
		
		cacheService.stop();
		log.info("[cacheService] stop ok.");
		
		threadService.stop();
		log.info("[threadService] stop ok.");
		
		timerService.stop();
		log.info("[timerService] stop ok.");
	}
}
