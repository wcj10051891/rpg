package com.wcj.channel;

import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.concurrent.ConcurrentHashMap;

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
	private ConcurrentHashMap<Object, Object> attributes = new ConcurrentHashMap<Object, Object>(4);

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
		final Object packet = this.decoder.decode(this, data);
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
