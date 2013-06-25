package com.wcj.core;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicBoolean;

import com.wcj.NioException;
import com.wcj.util.Utils;

public class Worker {
	private Selector selector;
	private AtomicBoolean running = new AtomicBoolean();
	
	public Worker() {
		try {
			this.selector = Selector.open();
		} catch (IOException e) {
			throw new NioException("worker selector open error.", e);
		}
	}
	
	public void register(ChannelContext channelContext){
		try {
			channelContext.getSocket().register(selector, SelectionKey.OP_READ | SelectionKey.OP_WRITE);
		} catch (ClosedChannelException e) {
			throw new NioException("socket register error.", e);
		}
		if(running.compareAndSet(false, true)){
			while (running.get()) {
				int count = -1;
				try {
					count = this.selector.select();
				} catch (IOException e) {
					throw new NioException("select error.", e);
				}
				if (count <= 0)
					continue;

				for (Iterator<SelectionKey> it = selector.selectedKeys().iterator(); it.hasNext();) {
					SelectionKey k = it.next();
					SocketChannel channel = (SocketChannel) k.channel();
					if (k.isReadable()) {
						int rCount = -1;
						try {
							ByteBuffer readBuffer = channelContext.getReadBuffer();
							ByteBuffer tempReadBuffer = ByteBuffer.allocate(128);
							while((rCount = channel.read(tempReadBuffer)) > 0){
								tempReadBuffer.flip();
								Utils.ensureCapacity(readBuffer, rCount);
								while(tempReadBuffer.hasRemaining()){
									readBuffer.put(tempReadBuffer);
								}
							}
							if(readBuffer.hasRemaining()){
								readBuffer.flip();
								System.out.println("read from socket:" + new String(readBuffer.array()));
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
//						Scanner scanner = new Scanner(System.in);
//						while(scanner.hasNextLine()){
//							String send = scanner.nextLine();
//							int writeCount = channel.write(ByteBuffer.wrap(send.getBytes("GBK")));
//							System.out.println("send to socket:" + send + ", write count:" + writeCount);
//						}
					}
					it.remove();
				}
			}
		}
	}
}
