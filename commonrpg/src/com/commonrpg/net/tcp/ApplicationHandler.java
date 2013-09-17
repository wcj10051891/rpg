package com.commonrpg.net.tcp;

import java.util.concurrent.TimeUnit;

import org.jboss.netty.channel.ChannelHandler.Sharable;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelUpstreamHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.commonrpg.core.Player;
import com.commonrpg.service.Services;

@Sharable
public class ApplicationHandler extends SimpleChannelUpstreamHandler {
	private static final Logger log = LoggerFactory.getLogger(ApplicationHandler.class);
	public static final ApplicationHandler INSTANCE = new ApplicationHandler();
    public static String crossdomain = "<cross-domain-policy><allow-access-from domain=\"*\" to-ports=\"*\"/></cross-domain-policy>\0";
	
	@Override
	public void channelConnected(final ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
		Services.timerService.jdkScheduler.schedule(new Runnable() {
			@Override
			public void run() {
				if(ctx.getAttachment() == null)
					ctx.getChannel().close();
			}
		}, 30, TimeUnit.SECONDS);
	}

	@Override
	public void messageReceived(final ChannelHandlerContext ctx, final MessageEvent e) throws Exception {
		Services.threadService.submit(new Runnable() {
			@Override
			public void run() {
				//todo message receive process
			}
		});
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) throws Exception {
		ctx.getChannel().close();
	}

	@Override
	public void channelClosed(final ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
		Services.threadService.submit(new Runnable() {
			@Override
			public void run() {
				Player player = (Player)ctx.getAttachment();
				if(player != null) {
					player.onLogout();
					ctx.setAttachment(null);
				}
			}
		});
	}
}
