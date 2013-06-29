package com.wcj.channel;

import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

import org.apache.log4j.Logger;

import com.wcj.core.Context;
import com.wcj.protocol.Decoder;
import com.wcj.protocol.Encoder;
import com.wcj.util.ProcessQueue;

public class ChannelContext {
	private static final Logger log = Logger.getLogger(ChannelContext.class);
	public static final ChannelContext NULL = new NullChannelContext(0, null);
	private SocketChannel socket;
	private Integer channelId;
	private Encoder encoder;
	private Decoder decoder;
	private ProcessQueue requests;

	private static class NullChannelContext extends ChannelContext {

		public NullChannelContext(int channelId, SocketChannel socket) {
			super(channelId, socket);
		}

		@Override
		public void onReceive(byte[] data) {
		}

		@Override
		public void send(byte[] message) {
		}

		@Override
		public void send(Object message) {
		}

	}

	public ChannelContext(int channelId, SocketChannel socket) {
		this.channelId = channelId;
		this.socket = socket;
		this.encoder = Context.protocolFactory.getEncoder();
		this.decoder = Context.protocolFactory.getDecoder();
		this.requests = new ProcessQueue(Context.threadPool);
	}

	public SocketChannel getSocket() {
		return socket;
	}

	public Integer getChannelId() {
		return channelId;
	}

	public void onReceive(byte[] data) {
		final Object packet = this.decoder.decode(data);
		if (packet != null) {
			this.requests.submit(new Runnable() {
				public void run() {
					try {
						Context.handler.onReceive(packet, channelId);
					} catch (Exception e) {
						log.error("channel receive message process error.", e);
					}
				}
			});
		}
	}

	public void send(Object message) {
		try {
			socket.write(ByteBuffer.wrap(this.encoder.encode(message)));
		} catch (Exception e) {
			log.error("channel send message error.", e);
		}
	}

	public void send(byte[] message) {
		try {
			socket.write(ByteBuffer.wrap(message));
		} catch (Exception e) {
			log.error("channel send message error.", e);
		}
	}
}
