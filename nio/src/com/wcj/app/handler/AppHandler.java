package com.wcj.app.handler;

import org.apache.log4j.Logger;

import com.wcj.NioException;
import com.wcj.app.protocol.RequestDto;
import com.wcj.app.protocol.ResponseDto;
import com.wcj.channel.Groups;
import com.wcj.core.Context;
import com.wcj.handler.Handler;

public class AppHandler extends Handler {
	private static final Logger log = Logger.getLogger(AppHandler.class);

	@Override
	public void onConnect(Integer channelId) {
		super.onConnect(channelId);
		Context.groups.join(Groups.World, channelId);
	}

	@Override
	public void onReceive(Object message, Integer channelId) {
		if (!(message instanceof RequestDto))
			throw new NioException("receive message type error.");

		RequestDto request = (RequestDto) message;
		ResponseDto response = null;
		try {
			Object result = Context.dispather.proccess(request);
			if (result != null && result.getClass() != void.class) {
				response = new ResponseDto();
				response.setResult(result);
			}
		} catch (Exception e) {
			log.error("request process error.", e);
			response = new ResponseDto();
			response.setResult(e.getMessage());
		}
		if(response != null){
			response.setSn(request.getSn());
			Context.channels.get(channelId).send(response);
		}
	}

	@Override
	public void onClose(Integer channelId) {
		super.onClose(channelId);
	}

}
