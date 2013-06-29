package com.wcj.core;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketOption;
import java.net.SocketOptions;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.log4j.Logger;

import com.wcj.NioException;
import com.wcj.channel.ChannelContext;

/**
 * acceptor
 * @author wcj
 */
public class Acceptor {
	private static final Logger log = Logger.getLogger(Acceptor.class);
	private Selector selector;
	private AtomicBoolean starting = new AtomicBoolean();
	private int portNo;
	private AtomicInteger channelSerialNo = new AtomicInteger();
	
	public Acceptor(int portNo) {
		this.portNo = portNo;
	}

	public void start() {
		if (starting.get())
			return;
		try {
			this.selector = Selector.open();
		} catch (IOException e) {
			throw new NioException("acceptor selector open error.", e);
		}
		try {
			ServerSocketChannel server = ServerSocketChannel.open();
			server.configureBlocking(false);
			server.bind(new InetSocketAddress(portNo));
			server.register(this.selector, SelectionKey.OP_ACCEPT);
		} catch (IOException e) {
			throw new NioException("server socket open error.", e);
		}
		if (starting.compareAndSet(false, true)) {
			while (starting.get()) {
				int count = -1;
				try {
					count = this.selector.select();
				} catch (IOException e) {
					starting.set(false);
					throw new NioException("select error.", e);
				}
				if (count <= 0)
					continue;

				for (Iterator<SelectionKey> it = this.selector.selectedKeys().iterator(); it.hasNext();) {
					SelectionKey k = it.next();
					if (k.isAcceptable()) {
						try {
							SocketChannel socketChannel = ((ServerSocketChannel) k.channel()).accept();
							socketChannel.configureBlocking(false);
							socketChannel.socket().setTcpNoDelay(false);
							int channelId = channelSerialNo.addAndGet(1);
							ChannelContext channelContext = new ChannelContext(channelId, socketChannel);
							Context.workerPool.take().register(channelContext);
							Context.channels.put(channelId, channelContext);
						} catch (Exception e) {
							log.error("socket accept error:", e);
						}
					}
					try {
						it.remove();
					} catch (Exception e) {
						log.error("acceptor remove key error:", e);
					}
				}
			}
		}
	}

	public void stop() {
		if (!starting.get())
			return;

		if (starting.compareAndSet(true, false)) {
			try {
				this.selector.close();
			} catch (IOException e) {
				throw new NioException("select close error.", e);
			}
		}
	}
}
