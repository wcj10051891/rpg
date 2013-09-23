package com.wabao.mogame.protocol.dto;

import java.util.List;

/**
 * 服务请求
 * @author E430c
 */
public class RequestDto {
	/**
	 * 请求序号
	 */
	public int sn;
	/**
	 * 服务类
	 */
	public String service;
	/**
	 * 方法
	 */
	public String method;
	/**
	 * 参数列表:json数组，按照service方法的参数个数及类型一一匹配
	 */
	public String params;
}