package com.wcj.dao.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AppLogger
{
    private static Logger logger = LoggerFactory.getLogger(AppLogger.class);

    public static void debug(String message)
    {
        logger.debug(message);
    }

    public static void debug(String message, Throwable throwable)
    {
        logger.debug(message, throwable);
    }

    public static void error(String message)
    {
        logger.error(message);
    }

    public static void error(String message, Throwable throwable)
    {
        logger.error(message);
    }
}
