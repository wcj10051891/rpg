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

import org.apache.log4j.Logger;

import com.wcj.NioException;
import com.wcj.channel.ChannelContext;
import com.wcj.util.Utils;

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
			stopSelect();
			throw new NioException("server socket open error.", e);
		}
		if (starting.compareAndSet(false, true)) {
			while (starting.get()) {
				int count = -1;
				try {
					count = this.selector.select();
				} catch (IOException e) {
					stopSelect();
					throw new NioException("select error.", e);
				}
				if (count <= 0)
					continue;

				for (Iterator<SelectionKey> it = this.selector.selectedKeys().iterator(); it.hasNext();) {
					SelectionKey k = it.next();
					if (k.isAcceptable()) {
						SocketChannel socketChannel = null;
						try {
							socketChannel = ((ServerSocketChannel) k.channel()).accept();
							socketChannel.configureBlocking(false);
							socketChannel.socket().setTcpNoDelay(true);
							socketChannel.socket().setKeepAlive(true);
							socketChannel.socket().setReuseAddress(true);
							int channelId = channelSerialNo.addAndGet(1);
							Worker worker = Context.workerPool.take();
							worker.register(new ChannelContext(channelId, socketChannel, worker));
						} catch (Exception e) {
							log.error("socket accept error:", e);
							if(socketChannel != null){
								try {
									socketChannel.close();
								} catch (IOException ex) {
									log.warn("Failed to close a partially accepted socket.", ex);
								}
							}
						}
					}
				}
				this.selector.selectedKeys().clear();
			}
		}
	}
	
	private void stopSelect(){
		this.selector = null;
		Utils.closeSelector(this.selector);
		starting.compareAndSet(true, false);
	}

	public void stop() {
		if (!starting.get())
			return;

		stopSelect();
	}
}
