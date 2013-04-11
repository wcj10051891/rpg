package com.wcj.dao.test;

import java.util.List;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.wcj.dao.core.AppLogger;
import com.wcj.dao.core.DaoFactory;

public class Main
{

    /**
     * @param args
     */
    public static void main(String[] args)
    {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
        ctx.scan("com.wcj");
        ctx.refresh();

        ActiveDao dao = ctx.getBean(DaoFactory.class).get(ActiveDao.class);
        List<Active> all = dao.getAll();
        AppLogger.debug("all:" + all);
        ctx.close();
    }
}
