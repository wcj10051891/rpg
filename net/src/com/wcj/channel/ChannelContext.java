package com.wcj.channel;

import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.wcj.core.NetContext;
import com.wcj.core.Worker;
import com.wcj.protocol.Decoder;
import com.wcj.protocol.Encoder;
import com.wcj.util.ProcessQueue;
import com.wcj.util.Utils;

public class ChannelContext {
	private static final Log log = LogFactory.getLog(ChannelContext.class);
	private SocketChannel socket;
	private Worker worker;
	private Integer channelId;
	private Encoder encoder;
	private Decoder decoder;
	private ProcessQueue requests;
	private Queue<Runnable> writeQueue = new LinkedList<Runnable>();
	private boolean isInWriteTaskQueue;
	public ConcurrentHashMap<Object, Object> states = new ConcurrentHashMap<Object, Object>();

	public ChannelContext(int channelId, SocketChannel socket, Worker worker) {
		this.channelId = channelId;
		this.socket = socket;
		this.worker = worker;
		this.encoder = NetContext.protocolFactory.getEncoder();
		this.decoder = NetContext.protocolFactory.getDecoder();
		this.requests = new ProcessQueue(NetContext.applicationThreadPool);
	}

	public SocketChannel getSocket() {
		return socket;
	}

	public Integer getChannelId() {
		return channelId;
	}

	public void onReceive(byte[] data) {
		final Object packet = this.decoder.decode(this, data);
		if (packet != null) {
			this.requests.submit(new Runnable() {
				public void run() {
					try {
						NetContext.handler.onReceive(ChannelContext.this, packet);
					} catch (Exception e) {
						log.error("channel receive message process error.", e);
					}
				}
			});
		}
	}

	public void send(Object message) {
		send(ChannelContext.this.encoder.encode(message));
	}

	public void send(final byte[] message) {
		synchronized (writeQueue) {
			writeQueue.offer(new Runnable() {
				@Override
				public void run() {
					try {
						socket.write(ByteBuffer.wrap(message));
					} catch (Exception e) {
						log.error("channel send message error.", e);
						worker.closeChannel(channelId);
					}
				}
			});
			if (!isInWriteTaskQueue) {
				this.worker.addWriteTask(new Runnable() {
					@Override
					public void run() {
						synchronized (writeQueue) {
							Utils.processTaskQueue(writeQueue);
							isInWriteTaskQueue = false;
						}
					}
				});
			}
		}
	}
}
