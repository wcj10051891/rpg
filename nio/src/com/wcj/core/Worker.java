package com.wcj.core;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicBoolean;

import com.wcj.NioException;
import com.wcj.util.Utils;

public class Worker {
	private Selector selector;
	private AtomicBoolean running = new AtomicBoolean();
	private ExecutorService selectPool;
	private ExecutorService workerPool;
	
	public Worker() {
		try {
			this.selector = Selector.open();
		} catch (IOException e) {
			throw new NioException("worker selector open error.", e);
		}
		selectPool = Executors.newFixedThreadPool(1, new ThreadFactory() {
		    @Override
		    public Thread newThread(Runnable r) {
			return new Thread(r, "worker select thread #" + r.toString());
		    }
		});
		workerPool = Executors.newFixedThreadPool(3, new ThreadFactory() {
		    @Override
		    public Thread newThread(Runnable r) {
			return new Thread(r, "worker working thread #" + r.toString());
		    }
		});
	}
	
	public void register(ChannelContext channelContext){
		try {
			channelContext.getSocket().register(selector, SelectionKey.OP_READ, channelContext.getChannelId());
		} catch (ClosedChannelException e) {
			throw new NioException("socket register error.", e);
		}
		if(running.compareAndSet(false, true)){
		    selectPool.submit(new Sel(this));
		}
	}
	

	private static class Sel implements Runnable{
	    
	    private Worker worker;
	    public Sel(Worker worker) {
		this.worker = worker;
	    }

	    @Override
	    public void run() {
		while (this.worker.running.get()) {
			int count = -1;
			try {
				count = this.worker.selector.select();
			} catch (IOException e) {
				throw new NioException("select error.", e);
			}
			if (count <= 0)
				continue;
			this.worker.workerPool.submit(new Work(this.worker.selector.selectedKeys()));
		}		
	    }
	    
	}
	
	private static class Work implements Runnable{
	    
	    private Set<SelectionKey> selectedKeys;
	    
	    public Work(Set<SelectionKey> selectedKeys) {
		this.selectedKeys = selectedKeys;
	    }

	    @Override
	    public void run() {
		for (Iterator<SelectionKey> it = selectedKeys.iterator(); it.hasNext();) {
			SelectionKey k = it.next();
			SocketChannel channel = (SocketChannel) k.channel();
			if (k.isReadable()) {
				try {
					ByteBuffer readBuffer = Context.channels.getChannelContext((Integer)k.attachment()).getReadBuffer();
					ByteBuffer tempReadBuffer = ByteBuffer.allocate(128);
					int rCount = -1;
					while((rCount = channel.read(tempReadBuffer)) > 0){
						tempReadBuffer.flip();
						Utils.ensureCapacity(readBuffer, rCount);
						while(tempReadBuffer.hasRemaining()){
							readBuffer.put(tempReadBuffer);
						}
					}
						readBuffer.flip();
						if(readBuffer.hasRemaining()){
						    byte[] data = Arrays.copyOfRange(readBuffer.array(), readBuffer.position(), readBuffer.limit());
							System.out.println("read from socket:" + new String(data));
							readBuffer.clear();
						}
				} catch (IOException e) {
					System.out.println("read from socket error:" + e);
					try {
						channel.close();
					} catch (IOException e1) {
						System.out.println("channel close error:" + e1);
					}
				}
			}
			if (k.isWritable()) {
//				Scanner scanner = new Scanner(System.in);
//				while(scanner.hasNextLine()){
//					String send = scanner.nextLine();
//					int writeCount = channel.write(ByteBuffer.wrap(send.getBytes("GBK")));
//					System.out.println("send to socket:" + send + ", write count:" + writeCount);
//				}
			}
			it.remove();
		}
	    }
	    
	}
}
