package com.wcj.dao.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wcj.dao.core.DaoContext;
import com.wcj.dao.core.DaoFactory;
import com.wcj.dao.core.page.PageResult;

public class Main {
	private static Logger logger = LoggerFactory.getLogger(Main.class);

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		DaoFactory daoFactory = new DaoContext().daoFactory;
		ActiveDao activeDao = daoFactory.get(ActiveDao.class);
//		logger.debug(activeDao.getAciveById(1).toString());
//		logger.debug(activeDao.getAciveById(1).toString());
//		logger.debug(activeDao.getAciveById(1).toString());
//		logger.debug(activeDao.getAciveById(1).toString());
//		logger.debug(activeDao.getAll().toString());
//		logger.debug(activeDao.getBeanList().toString());
//		logger.debug(activeDao.getById(1).toString());
//		logger.debug(activeDao.getByIds(new int[] { 1, 2, 3, 4, 5 }).toString());
//		logger.debug(activeDao.getCount().toString());
//		logger.debug(activeDao.getDescs().toString());
//		logger.debug(activeDao.getIds().toString());
		// activeDao.update(1, String.valueOf(System.currentTimeMillis()));
		// logger.debug(activeDao.getAciveById(1).toString());
		// Active active = activeDao.getAciveById(1);
		// active.id = null;
		// logger.debug(activeDao.insert(active).toString());
		
//		long start = System.currentTimeMillis();
//		for(int i = 0; i < 20000; i++)
//			activeDao.getAciveById(1);
//		long end = System.currentTimeMillis();
//		
//		System.out.println(end - start);
		PageResult p1 = new PageResult(2, 10);
		PageResult p2 = activeDao.getPage2(p1);
		logger.debug("p1:" + p1.getData());
		logger.debug("p2:" + p2.getData());
	}
}
