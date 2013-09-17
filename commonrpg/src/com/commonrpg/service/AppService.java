package com.commonrpg.service;

import java.lang.instrument.Instrumentation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import premain.InstrumentAgent;

import com.commonrpg.core.AppException;
import com.commonrpg.remote.RpcClient;

public class AppService implements Service {
	private static final Logger log = LoggerFactory.getLogger(AppService.class);
	public Instrumentation instrument;
	public RpcClient rpcClient;

	@Override
	public void start() throws Exception {
		instrument = InstrumentAgent.instrument;
		if(instrument == null)
			throw new AppException("instrument init error.");
		
		rpcClient = new RpcClient();
	}

	@Override
	public void stop() throws Exception {
	}
}
