package handler;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.wcj.NetException;
import com.wcj.channel.ChannelContext;
import com.wcj.core.NetContext;
import com.wcj.handler.Handler;
import com.wcj.protocol.RequestDto;
import com.wcj.protocol.ResponseDto;

import core.GameContext;

public class AppHandler extends Handler {
	private static final Log log = LogFactory.getLog(AppHandler.class);

	@Override
	public void onConnect(ChannelContext ctx) {
		super.onConnect(ctx);
		
//		NetContext.groups.join(Groups.World, ctx.getChannelId());
	}

	@Override
	public void onReceive(ChannelContext ctx, Object message) {
		if (!(message instanceof RequestDto))
			throw new NetException("receive message type error.");

		RequestDto request = (RequestDto) message;
		ResponseDto response = null;
		try {
			Object result = GameContext.dispatcher.proccess(ctx, request);
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
			NetContext.channels.get(ctx.getChannelId()).send(response);
		}
	}

	@Override
	public void onClose(ChannelContext ctx) {
//		NetContext.channels.get(ctx.getChannelId()).getAttribute(key);
	}

}
