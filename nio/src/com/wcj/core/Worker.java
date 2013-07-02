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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.log4j.Logger;

import com.wcj.NioException;
import com.wcj.channel.ChannelContext;
import com.wcj.util.Utils;

public class Worker implements Runnable{
	private static final Logger log = Logger.getLogger(Worker.class);
	private Selector selector;
	private AtomicBoolean running = new AtomicBoolean();
	private ExecutorService threadPool;
    private AtomicBoolean wakenUp = new AtomicBoolean();
    private Queue<Runnable> registerTasks = new LinkedBlockingDeque<>();
    private Queue<Runnable> writeTasks = new LinkedBlockingDeque<>();
	
	public Worker() {
		try {
			this.selector = Selector.open();
		} catch (IOException e) {
			throw new NioException("worker selector open error.", e);
		}
		threadPool = Executors.newFixedThreadPool(3, new ThreadFactory() {
		    @Override
		    public Thread newThread(Runnable r) {
		    	return new Thread(r, "worker working thread:" + r.toString());
		    }
		});
	}
	
	public void register(final ChannelContext channelContext){
		registerTasks.offer(new Runnable() {
			@Override
			public void run() {
				Integer channelId = channelContext.getChannelId();
				try {
					channelContext.getSocket().register(selector, SelectionKey.OP_READ, channelId);
					Context.handler.onConnect(channelId);
					Context.channels.put(channelId, channelContext);
				} catch (Exception e) {
					Context.handler.onClose(channelId);
					throw new NioException("socket register error.", e);
				}
			}
		});
			
		if(wakenUp.compareAndSet(false, true))
			this.selector.wakeup();
		if(running.compareAndSet(false, true)){
			threadPool.submit(this);
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
				
				this.processTaskQueue(this.registerTasks);
				this.processTaskQueue(this.writeTasks);
				this.processSelectedKeys(selector.selectedKeys());
				
			} catch (Exception e) {
				log.warn("Unexpected exception in the selector loop.", e);
			}
		}
	}
	
	private void processTaskQueue(Queue<Runnable> queue) {
		if(queue != null){
			Runnable task = null;
			while((task = queue.poll()) != null){
				task.run();
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
							Context.channels.get(channelId).onReceive(Arrays.copyOfRange(all.array(), 0, all.limit()));
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
		ChannelContext channelContext = Context.channels.get(channelId);
		if(channelContext != null) {
			try {
				channelContext.getSocket().close();
			} catch (Exception ex) {
				log.error("channel close error:", ex);
			} finally {
				Context.handler.onClose(channelId);
				Context.channels.remove(channelId);
			}
		}
	}
}
