package com.wabao.mogame.protocol.service;

import com.wabao.mogame.core.Player;
import com.wabao.mogame.modules.Controller;
import com.wabao.mogame.protocol.dto.primitive.StringDtoProto.StringDto;

@Controller
public class PlayerCtrl {
	public StringDto getName(Player player, int i, boolean b, String s) {
		return StringDto.newBuilder().setValue("这是返回值.").build();
	}
	
	public void rpc(Player player, int i, boolean b, String s) {
		System.out.println("来了哦");
	}
}
