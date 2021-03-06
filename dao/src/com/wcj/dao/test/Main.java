package com.wcj.dao.test;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.wcj.dao.core.DaoContext;
import com.wcj.dao.core.DaoFactory;

public class Main {
	private static Log log = LogFactory.getLog(Main.class);

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		DaoContext daoContext = new DaoContext();
		DaoFactory daoFactory = daoContext.daoFactory;
//		ActiveDao dao = daoFactory.get(ActiveDao.class);
//		log.debug(dao.getAciveById(20));
//		log.debug(dao.getAll());
//		log.debug(dao.getBeanList());
//		log.debug(dao.getById(1));
//		log.debug(dao.getByIds(new int[]{1,2,3,4,5}));
//		log.debug(dao.getCount());
//		log.debug(dao.getDescs());
//		log.debug(dao.getIds());
//		PageResult page = new PageResult(1, 10);
//		log.debug(dao.getPage(page).getData());
//		log.debug(dao.getPage2(page, Arrays.asList("1", "ff")));
//		log.debug(dao.getPage3(page, "select * from player where id=:id", 3000001));
//		ActiveDao dao = daoFactory.get(ActiveDao.class);
//		Active a = new Active();
//		dao.insert(a);
		
		final CustomerDao customerDao = daoFactory.get(CustomerDao.class);
		final Customer customer = customerDao.get(1);
		daoContext.jdbc.doInTransaction(new Runnable() {
			@Override
			public void run() {
				customer.setCustomer_id(0);
				customerDao.insert(customer);
				customerDao.insert(customer);
			}
		});
	}
}
