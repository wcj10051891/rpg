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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.wcj.NetException;
import com.wcj.channel.ChannelContext;
import com.wcj.util.Utils;

/**
 * acceptor
 * @author wcj
 */
public class Acceptor implements Runnable {
	private static final Log log = LogFactory.getLog(Acceptor.class);
	private Selector selector;
	private AtomicBoolean running = new AtomicBoolean();
	private AtomicInteger channelSerialNo = new AtomicInteger();
	private int portNum;
	private Thread acceptorThread; 
	public Acceptor() {
		this.portNum = NetConfig.acceptorListenPort;
		this.acceptorThread = new Thread(this, "acceptor thread");
	}
	
	public Acceptor(int listenPort) {
		this.portNum = listenPort;
		this.acceptorThread = new Thread(this, "acceptor thread");
	}

	public void start() {
		this.acceptorThread.start();
	}
	
	private void stopSelect(){
		if(running.compareAndSet(true, false)) {
			this.acceptorThread.interrupt();
			Utils.closeSelector(this.selector);
			this.selector = null;
		}
	}

	public void stop() {
		if (!running.get())
			return;

		stopSelect();
	}

	@Override
	public void run() {
		if (running.get())
			return;
		try {
			this.selector = Selector.open();
		} catch (Exception e) {
			throw new NetException("acceptor selector open error.", e);
		}
		try {
			ServerSocketChannel server = ServerSocketChannel.open();
			server.configureBlocking(false);
			server.bind(new InetSocketAddress(portNum));
			server.register(this.selector, SelectionKey.OP_ACCEPT);
		} catch (Exception e) {
			stopSelect();
			throw new NetException("server socket open error.", e);
		}
		if (running.compareAndSet(false, true)) {
			while (running.get()) {
				int count = -1;
				try {
					count = this.selector.select();
				} catch (Exception e) {
					stopSelect();
					throw new NetException("select error.", e);
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
							Worker worker = NetContext.workerPool.take();
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
}
