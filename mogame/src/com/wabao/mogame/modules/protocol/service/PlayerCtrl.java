package com.wabao.mogame.modules.protocol.service;

import com.wabao.mogame.core.Player;
import com.wabao.mogame.modules.Controller;
import com.wabao.mogame.modules.protocol.dto.PlayerDtoProto.PlayerDto;
import com.wabao.mogame.modules.protocol.dto.PlayerItemDtoProto.PlayerItemDto;
import com.wabao.mogame.modules.protocol.dto.StringDtoProto.StringDto;

@Controller
public class PlayerCtrl {
	public StringDto getName(Player player, int i, boolean b, String s) {
		return StringDto.newBuilder().setValue("这是返回值.").build();
	}
	
	public void rpc(Player player, int i, boolean b, String s) {
		System.out.println("来了哦");
	}
	
	public PlayerDto getPlayerDto(Player player) {
		PlayerDto result = PlayerDto.newBuilder()
			.addPlayeritems(PlayerItemDto.newBuilder().build())
			.addPlayeritems(PlayerItemDto.newBuilder().build())
			.addPlayeritems(PlayerItemDto.newBuilder().build())
			.addPlayeritems(PlayerItemDto.newBuilder().build())
			.addPlayeritems(PlayerItemDto.newBuilder().build())
			.addPlayeritems(PlayerItemDto.newBuilder().build()).build();
		
		add();
		return result;
	}
	
	public void add() {
		System.out.println("你是谁啊，我是加的方法。");
	}
}
