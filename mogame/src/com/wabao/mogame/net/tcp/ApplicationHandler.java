package com.wabao.mogame.net.tcp;

import net.sf.json.JSONArray;

import org.jboss.netty.channel.ChannelHandler.Sharable;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelUpstreamHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wabao.mogame.core.Player;
import com.wabao.mogame.protocol.dto.RequestDtoProto.RequestDto;
import com.wabao.mogame.protocol.dto.primitive.StringDtoProto.StringDto;
import com.wabao.mogame.service.Services;
import com.wabao.mogame.util.ProtobufUtils;

@Sharable
public class ApplicationHandler extends SimpleChannelUpstreamHandler {
	private static final Logger log = LoggerFactory.getLogger(ApplicationHandler.class);
	public static final ApplicationHandler INSTANCE = new ApplicationHandler();
    public static String crossdomain = "<cross-domain-policy><allow-access-from domain=\"*\" to-ports=\"*\"/></cross-domain-policy>\0";
	
	@Override
	public void channelConnected(final ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
//		Services.timerService.jdkScheduler.schedule(new Runnable() {
//			@Override
//			public void run() {
//				if(ctx.getAttachment() == null)
//					ctx.getChannel().close();
//			}
//		}, 30, TimeUnit.SECONDS);
		
		Services.threadService.submit(new Runnable() {
			@Override
			public void run() {
				log.info("client {} connected.", ctx.getChannel());
				
				int channelId = ctx.getChannel().getId();
				
				Player player = new Player();
				player.id = channelId;
				player.channelId = channelId;
				Services.tcpService.channels.put(player.channelId, ctx);
				player.onLogin();
				ctx.setAttachment(player);
			}
		});
	}

	@Override
	public void messageReceived(final ChannelHandlerContext ctx, final MessageEvent e) throws Exception {
		Services.threadService.submit(new Runnable() {
			@SuppressWarnings("unchecked")
			@Override
			public void run() {
//				ChatDto content = (ChatDto)e.getMessage();
//				Calendar calendar = Calendar.getInstance();
//				calendar.setTimeInMillis(content.time * 1000l);
//				log.info("message received, user {} chat:{}", ctx.getChannel(), content.nickname + ", " + content.content + ", " + calendar.getTime());
//				Services.tcpService.world(content);
				Player player = (Player)ctx.getAttachment();
				RequestDto request = (RequestDto)e.getMessage();
				String service = request.getService();
				String method = request.getMethod();
				String params = request.getParams();
				Object result = null;
				boolean isError = false;
				try {
					result = Services.appService.dispatcher.dispatch(player, service, method, JSONArray.fromObject(request.getParams()));
				} catch (Exception ex) {
					isError = true;
					result = StringDto.newBuilder().setValue(ex.toString()).build();
					log.error("request exception, service:{}, method:{}, params:{}, error:{}.", service, method, params, ex);
				}
				if(result != null) {
					if(player != null)
						Services.tcpService.send(ProtobufUtils.response(request.getSn(), isError, result), player);
					else
						Services.tcpService.send(ProtobufUtils.response(request.getSn(), isError, result), e.getChannel());
				}
			}
		});
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) throws Exception {
		e.getCause().printStackTrace();
		log.error("client {} exception: {}", e.getChannel(), e.getCause());
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
					Services.tcpService.channels.remove(player.channelId);
				}
				log.info("client {} channelClosed.", ctx.getChannel());
			}
		});
	}
}
