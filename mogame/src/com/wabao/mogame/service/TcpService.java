package com.wabao.mogame.service;

import org.jboss.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wabao.mogame.core.Player;
import com.wabao.mogame.net.tcp.Channels;
import com.wabao.mogame.net.tcp.Groups;
import com.wabao.mogame.net.tcp.NettyTcpServer;
import com.wabao.mogame.net.tcp.codec.Encoder;
import com.wabao.mogame.net.tcp.codec.ProtobufEncoder;

public class TcpService implements Service {
	private static final Logger log = LoggerFactory.getLogger(TcpService.class);
	public Channels channels;
	public Groups groups;
	public NettyTcpServer nettyTcpServer;
	public Encoder encoder;
	
	@Override
	public void start() throws Exception {
		channels = new Channels();
		groups = new Groups();
		encoder = new ProtobufEncoder();
		nettyTcpServer = new NettyTcpServer();
		nettyTcpServer.start();
	}
	
	@Override
	public void stop() throws Exception {
		nettyTcpServer.stop();
	}

	public void send(Object message, Channel client) {
		client.write(Services.tcpService.encoder.encode(message));
		log.info("send to {}:{}", client, message);
	}
	
	public void send(Object message, Player player) {
		channels.getChannel(player.channelId).write(Services.tcpService.encoder.encode(message));
		log.info("send to {}:{}", player.id, message);
	}
	
	public void broadcast(Object message, String groupName) {
		groups.broadcast(groupName, Services.tcpService.encoder.encode(message));
	}
	
	public void broadcast(Object message, String groupName, Player excludePlayer) {
		groups.broadcast(groupName, Services.tcpService.encoder.encode(message), excludePlayer.channelId);
	}
	
	public void joinGroup(String groupName, Player player) {
		groups.join(groupName, player.channelId);
		log.info("{} join group {}.", player.id, groupName);
	}
	
	public void leaveGroup(String groupName, Player player) {
		groups.leave(groupName, player.channelId);
		log.info("{} leave group {}.", player.id, groupName);
	}
	
	public void world(Object message) {
		broadcast(message, Groups.World);
	}
	
	/**
	 * 主动踢掉player
	 * @param player
	 */
	public void disconnect(Player player) {
		channels.getChannel(player.channelId).close();
		log.info("disconnect player {}.", player.id);
	}
}
