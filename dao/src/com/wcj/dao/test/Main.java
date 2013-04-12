package com.wcj.dao.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wcj.dao.core.Context;
import com.wcj.dao.core.DaoFactory;

public class Main
{
    private static Logger logger = LoggerFactory.getLogger(Main.class);

    /**
     * @param args
     */
    public static void main(String[] args)
    {
	DaoFactory daoFactory = Context.instance().daoFactory;
	ActiveDao activeDao = daoFactory.get(ActiveDao.class);
//	logger.debug(activeDao.getAciveById(1).toString());
//	logger.debug(activeDao.getAll().toString());
//	logger.debug(activeDao.getBeanList().toString());
//	logger.debug(activeDao.getById(1).toString());
//	logger.debug(activeDao.getByIds(new int[]{1,2,3,4,5}).toString());
//	logger.debug(activeDao.getCount().toString());
//	logger.debug(activeDao.getDescs().toString());
//	logger.debug(activeDao.getIds().toString());
//	activeDao.update(1, String.valueOf(System.currentTimeMillis()));
//	logger.debug(activeDao.getAciveById(1).toString());
	Active active = activeDao.getAciveById(1);
	active.id = null;
	logger.debug(activeDao.insert(active).toString());
    }
}
