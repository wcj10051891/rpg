package com.commonrpg.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.commonrpg.core.Player;
import com.commonrpg.net.tcp.AmfEncoder;
import com.commonrpg.net.tcp.Channels;
import com.commonrpg.net.tcp.Groups;
import com.commonrpg.net.tcp.NettyTcpServer;

public class TcpService implements Service {
	private static final Logger log = LoggerFactory.getLogger(TcpService.class);
	public Channels channels;
	public Groups groups;
	public NettyTcpServer nettyTcpServer;
	
	@Override
	public void start() throws Exception {
		channels = new Channels();
		nettyTcpServer = new NettyTcpServer();
		nettyTcpServer.start();
	}
	
	@Override
	public void stop() throws Exception {
		nettyTcpServer.stop();
	}
	
	public void send(Object message, Player player) {
		channels.getChannel(player.channelId).write(AmfEncoder.encode(message));
	}
	
	public void broadcast(Object message, String groupName) {
		groups.broadcast(groupName, AmfEncoder.encode(message));
	}
	
	public void broadcast(Object message, String groupName, Player excludePlayer) {
		groups.broadcast(groupName, AmfEncoder.encode(message), excludePlayer.channelId);
	}
	
	public void joinGroup(String groupName, Player player) {
		groups.join(groupName, player.channelId);
	}
	
	public void leaveGroup(String groupName, Player player) {
		groups.leave(groupName, player.channelId);
	}
	
	/**
	 * 主动踢掉player
	 * @param player
	 */
	public void disconnect(Player player) {
		channels.getChannel(player.channelId).close();
	}
}
