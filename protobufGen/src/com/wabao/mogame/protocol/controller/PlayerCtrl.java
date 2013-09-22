package com.wabao.mogame.protocol.controller;

import com.wabao.mogame.protocol.dto.player.PlayerDto;

public class PlayerCtrl {
	public void noResult(String arg1, String arg2, String arg3) {
	}

	public boolean booleanResult(String arg1, String arg2, String arg3) {
		return false;
	}

	public double doubleResult(String arg1, String arg2, String arg3) {
		return 0.0D;
	}

	public int intResult(String arg1, String arg2, String arg3) {
		return 0;
	}

	public long longResult(String arg1, String arg2, String arg3) {
		return 0L;
	}

	public String stringResult(String arg1, String arg2, String arg3) {
		return null;
	}

	public PlayerDto getPlayerdDto(String arg1, String arg2, String arg3) {
		return null;
	}
}