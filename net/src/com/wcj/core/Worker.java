package com.wcj.core;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.wcj.NioException;
import com.wcj.channel.ChannelContext;
import com.wcj.util.Utils;

public class Worker implements Runnable{
	private static final Log log = LogFactory.getLog(Worker.class);
	private Selector selector;
	private AtomicBoolean running = new AtomicBoolean();
    private AtomicBoolean wakenUp = new AtomicBoolean();
    private Queue<Runnable> registerTasks = new LinkedBlockingDeque<>();
    private Queue<Runnable> writeTasks = new LinkedBlockingDeque<>();
	
	public Worker() {
		try {
			this.selector = Selector.open();
		} catch (IOException e) {
			throw new NioException("worker selector open error.", e);
		}
	}
	
	public void register(final ChannelContext channelContext){
		registerTasks.offer(new Runnable() {
			@Override
			public void run() {
				Integer channelId = channelContext.getChannelId();
				try {
					channelContext.getSocket().register(selector, SelectionKey.OP_READ, channelId);
					NetContext.handler.onConnect(channelId);
					NetContext.channels.put(channelId, channelContext);
				} catch (Exception e) {
					NetContext.handler.onClose(channelId);
					throw new NioException("socket register error.", e);
				}
			}
		});
			
		if(wakenUp.compareAndSet(false, true))
			this.selector.wakeup();
		if(running.compareAndSet(false, true)){
			NetContext.workersThreadPool.submit(this);
		}
	}
	
	public void addWriteTask(Runnable task){
		this.writeTasks.offer(task);
	}

	@Override
	public void run() {
		while (running.get()) {
			wakenUp.set(false);
			try {
				selector.select(500);
				if(wakenUp.get())
					selector.wakeup();
				
				Utils.processTaskQueue(this.registerTasks);
				Utils.processTaskQueue(this.writeTasks);
				this.processSelectedKeys(selector.selectedKeys());
				
			} catch (Exception e) {
				log.warn("Unexpected exception in the selector loop.", e);
			}
		}
	}

	private void processSelectedKeys(Set<SelectionKey> selectedKeys) {
		for (Iterator<SelectionKey> it = selectedKeys.iterator(); it.hasNext();) {
			SelectionKey k = it.next();
			try {
				it.remove();
			} catch (Exception e) {
				log.error("worker remove key error:", e);
			}
			SocketChannel channel = (SocketChannel) k.channel();
			Integer channelId = (Integer)k.attachment();
			if (k.isReadable()) {
				try {
					ByteBuffer all = null;
					ByteBuffer temp = ByteBuffer.allocate(128);
					int rCount = -1;
					while((rCount = channel.read(temp)) > 0){
						if(all == null){
							all = ByteBuffer.allocate(1024);
						}
						temp.flip();
						Utils.ensureCapacity(all, rCount);
						while(temp.hasRemaining()){
							all.put(temp);
						}
						temp.clear();
					}
					if(all != null){
						all.flip();
						if(all.hasRemaining()){
							NetContext.channels.get(channelId).onReceive(Arrays.copyOfRange(all.array(), 0, all.limit()));
						}
					}
				} catch (Exception e) {
					log.error("read from socket error:", e);
					closeChannel(channelId);
				}
			}
		}
	}
	
	public void closeChannel(Integer channelId) {
		ChannelContext channelContext = NetContext.channels.get(channelId);
		if(channelContext != null) {
			try {
				channelContext.getSocket().close();
			} catch (Exception ex) {
				log.error("channel close error:", ex);
			} finally {
				NetContext.handler.onClose(channelId);
				NetContext.channels.remove(channelId);
			}
		}
	}
}
