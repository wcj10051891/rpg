package handler;

import protocol.http.api.DefaultHttpResponse;
import protocol.http.api.HttpResponse;
import protocol.http.api.HttpStatus;
import protocol.http.api.HttpVersion;

import com.wcj.handler.Handler;

public class HttpHandler extends Handler {
	public void onConnect(Integer channelId) {
		System.out.println("channel " + channelId + " connect.");
	}

	public void onReceive(Object message, Integer channelId) {
		System.out.println("channel receive:" + message);
		HttpResponse response = new DefaultHttpResponse(HttpVersion.HTTP_1_1, HttpStatus.SUCCESS_OK, null);
	}

	public void onClose(Integer channelId) {
		System.out.println("channel " + channelId + " close.");
	}

}
