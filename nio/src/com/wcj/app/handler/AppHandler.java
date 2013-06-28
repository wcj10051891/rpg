package com.wcj.app.handler;

import com.wcj.NioException;
import com.wcj.app.protocol.RequestDto;
import com.wcj.app.protocol.ResponseDto;
import com.wcj.core.Context;
import com.wcj.handler.Handler;

public class AppHandler extends Handler {
	
	@Override
	public void onConnect(Integer channelId) {
		super.onConnect(channelId);
	}
	
	@Override
	public void onReceive(Object message, Integer channelId) {
		if(!(message instanceof RequestDto))
			throw new NioException("receive message type error.");
		
		RequestDto request = (RequestDto)message;
		Object result = Context.dispather.proccess(request);
		if(result != null && result.getClass() != void.class){
			ResponseDto response = new ResponseDto();
			response.setSn(request.getSn());
			response.setResult(result);
			Context.channels.getChannelContext(channelId).send(response);
		}
	}
	
	@Override
	public void onClose(Integer channelId) {
		super.onClose(channelId);
	}

}
