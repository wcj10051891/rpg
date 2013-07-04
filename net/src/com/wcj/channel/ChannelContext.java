package com.wcj.channel;

import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedTransferQueue;
import java.util.concurrent.atomic.AtomicBoolean;

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
	private ConcurrentHashMap<Object, Object> attributes = new ConcurrentHashMap<Object, Object>(4);
	private Queue<Runnable> writeQueue = new LinkedTransferQueue<>();
	private AtomicBoolean isInWriteTaskQueue = new AtomicBoolean();

	public ChannelContext(int channelId, SocketChannel socket, Worker worker) {
		this.channelId = channelId;
		this.socket = socket;
		this.worker = worker;
		this.encoder = NetContext.protocolFactory.getEncoder();
		this.decoder = NetContext.protocolFactory.getDecoder();
		this.requests = new ProcessQueue(NetContext.appThreadPool);
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
						NetContext.handler.onReceive(packet, channelId);
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
		this.writeQueue.offer(new Runnable() {
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
		if(this.isInWriteTaskQueue.compareAndSet(false, true)) {
			this.worker.addWriteTask(new Runnable() {
				@Override
				public void run() {
					isInWriteTaskQueue.set(false);
					Utils.processTaskQueue(writeQueue);
				}
			});
		}
	}
	
	public Object getAttribute(Object key) {
		return getAttribute(key, null);
	}
	
	public Object getAttribute(Object key, Object defaultValue) {
        if (key == null) {
            throw new IllegalArgumentException("key");
        }

        if (defaultValue == null) {
            return attributes.get(key);
        }

        Object object = attributes.putIfAbsent(key, defaultValue);

        if (object == null) {
            return defaultValue;
        } else {
            return object;
        }
    }

    public Object setAttribute(Object key, Object value) {
        if (key == null) {
            throw new IllegalArgumentException("key");
        }

        if (value == null) {
            return attributes.remove(key);
        }

        return attributes.put(key, value);
    }

    public Object setAttributeIfAbsent(Object key, Object value) {
        if (key == null) {
            throw new IllegalArgumentException("key");
        }

        if (value == null) {
            return null;
        }

        return attributes.putIfAbsent(key, value);
    }

    public Object removeAttribute(Object key) {
        if (key == null) {
            throw new IllegalArgumentException("key");
        }

        return attributes.remove(key);
    }

    public boolean removeAttribute(Object key, Object value) {
        if (key == null) {
            throw new IllegalArgumentException("key");
        }

        if (value == null) {
            return false;
        }

        try {
            return attributes.remove(key, value);
        } catch (NullPointerException e) {
            return false;
        }
    }
}
