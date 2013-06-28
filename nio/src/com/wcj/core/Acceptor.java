package com.wcj.core;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import com.wcj.NioException;

/**
 * acceptor
 * 
 * @author wcj
 */
public class Acceptor {
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
							SocketChannel socket = ((ServerSocketChannel) k.channel()).accept();
							socket.configureBlocking(false);
							int channelId = channelSerialNo.addAndGet(1);
							ChannelContext channelContext = new ChannelContext(channelId, socket);
							Context.workerPool.get().register(channelContext);
							Context.channels.put(channelId, channelContext);
						} catch (Exception e) {
							e.printStackTrace();
							System.out.println("socket accept error:" + e);
						}
					}
					it.remove();
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
