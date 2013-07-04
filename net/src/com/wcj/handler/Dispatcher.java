package com.wcj.handler;

import com.wcj.protocol.RequestDto;

public abstract class Dispatcher {
	public abstract Object proccess(RequestDto request);
}
