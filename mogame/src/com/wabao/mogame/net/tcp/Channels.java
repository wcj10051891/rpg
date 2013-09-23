package com.wabao.mogame.net.tcp;

import java.util.Iterator;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelFutureListener;
import org.jboss.netty.channel.ChannelHandlerContext;

public class Channels {
	private ConcurrentHashMap<Integer, ChannelHandlerContext> channels = new ConcurrentHashMap<Integer, ChannelHandlerContext>();

    private ChannelFutureListener remover = new ChannelFutureListener() {
        public void operationComplete(ChannelFuture future) throws Exception {
            for (Iterator<Entry<Integer, ChannelHandlerContext>> it = channels.entrySet().iterator(); it.hasNext();) {
				if(it.next().getValue().equals(future.getChannel()))
					it.remove();
			}
        }
    };

	public void put(Integer channelId, ChannelHandlerContext channelContext) {
		if(this.channels.putIfAbsent(channelId, channelContext) == null)
			channelContext.getChannel().getCloseFuture().addListener(remover);
	}

	public ChannelHandlerContext getChannelContext(Integer channelId) {
		return channels.get(channelId);
	}
	
	public Channel getChannel(Integer channelId) {
		ChannelHandlerContext channelHandlerContext = channels.get(channelId);
		if(channelHandlerContext == null)
			return null;
		return channelHandlerContext.getChannel();
	}

	public void remove(Integer channelId) {
		ChannelHandlerContext remove = this.channels.remove(channelId);
		if(remove != null)
			remove.getChannel().getCloseFuture().addListener(remover);
	}
}
