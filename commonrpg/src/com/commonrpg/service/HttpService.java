package com.commonrpg.service;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.Servlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.commonrpg.core.AppConfig;
import com.commonrpg.net.http.NettyHttpServer;
import com.commonrpg.remote.servlet.Online;
import com.commonrpg.remote.servlet.PhpRpcDispatcher;

/**
 * User: kofboy@163.com
 * Date: 2010-5-4
 */
public class HttpService implements Service {
	private static final Logger log = LoggerFactory.getLogger(HttpService.class);

    private Map<Integer, NettyHttpServer> serverMap = new HashMap<Integer, NettyHttpServer>();

//    private int payPort = Config.HTTP_PAY_PORT > 0 ? Config.HTTP_PAY_PORT : 9001;

    @Override
    public void start() throws Exception
    {
            NettyHttpServer httpServer = new NettyHttpServer();
            httpServer.setHttpPort(AppConfig.HTTP_PORT);
            httpServer.setDefaultCharset(AppConfig.HTTP_DEFAULT_CHARSET);
            httpServer.setKeepAlive(AppConfig.HTTP_KEEP_ALIVE ? 300 : 0);
            httpServer.setSessionDefaultActiveTime(AppConfig.HTTP_SESSION_ACTIVE_TIME);
//            httpServer.registerServlet("/crossdomain.xml", new Crossdomain());
//            httpServer.registerServlet("/adminremote", new AdminRemoteServlet());
//            httpServer.registerServlet("/login", new Login());
//            httpServer.registerServlet("/gmlogin", new GmLogin());
//            httpServer.registerServlet("/qqapi/login", new QqLogin());
//            httpServer.registerServlet("/pay", new Pay());
            httpServer.registerServlet("/online", new Online());
//            httpServer.registerServlet("/reg", new RegCount());
//            httpServer.registerServlet("/delay", new Delay());
//            httpServer.registerServlet("/userstat", new Userstat());
//            httpServer.registerServlet("/serverstat", new Serverstat());
//            httpServer.registerServlet("/moneytype", new MoneyType());
//            httpServer.registerServlet("/create", new CreateCharactor());
            httpServer.registerServlet("/rpc", new PhpRpcDispatcher());
            httpServer.start();
            serverMap.put(AppConfig.HTTP_PORT, httpServer);
//
//        try {
//            NettyHttpServer server = new NettyHttpServer();
//            server.setHttpPort(payPort);
//            server.setDefaultCharset(Config.HTTP_DEFAULT_CHARSET);
//            server.setKeepAlive(Config.HTTP_KEEP_ALIVE ? 3000 : 0);
//            server.setSessionDefaultActiveTime(Config.HTTP_SESSION_ACTIVE_TIME);
//            server.start();
//            serverMap.put(payPort, server);
//        } catch (Exception e) {
//            throw new RuntimeException("http server at " + payPort + " can not start: ", e);
//        }
    }
    
    @Override
    public void stop() throws Exception 
    {
    	for (NettyHttpServer server : serverMap.values()) {
    		server.stop();
    	}
    }

    public NettyHttpServer getHttpServer(int port)
    {
        return serverMap.get(port);
    }

    public Servlet getServlet(String name)
    {
        for (NettyHttpServer server : serverMap.values())
        {
            Servlet servlet = server.getServlet(name);
            if (servlet != null)
                return servlet;
        }
        return null;
    }
}
