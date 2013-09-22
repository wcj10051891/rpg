package com.msj.chat;

import java.io.Serializable;

public class ChatDto implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 发送者昵称
	 */
	public String nickname;
	/**
	 * 消息内容
	 */
	public String content;
	/**
	 * 消息发送的时间戳，单位秒
	 */
	public int time;
}
