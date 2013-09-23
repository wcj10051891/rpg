package com.wabao.mogame.service;

import java.lang.instrument.Instrumentation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import premain.InstrumentAgent;

import com.wabao.mogame.core.AppException;
import com.wabao.mogame.core.Players;
import com.wabao.mogame.modules.Dispatcher;
import com.wabao.mogame.remote.RpcClient;

public class AppService implements Service {
	private static final Logger log = LoggerFactory.getLogger(AppService.class);
	public Instrumentation instrument;
	public RpcClient rpcClient;
	public Players players;
	public Dispatcher dispatcher;

	@Override
	public void start() throws Exception {
		instrument = InstrumentAgent.instrument;
		if(instrument == null)
			throw new AppException("instrument init error.");
		
		rpcClient = new RpcClient();
		players = new Players();
		dispatcher = new Dispatcher();
	}

	@Override
	public void stop() throws Exception {
	}
}
