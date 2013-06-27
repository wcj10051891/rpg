package com.wcj;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class Client {
	static ByteBuffer readBuffer = ByteBuffer.allocate(1024);

	public static void main(String[] args) throws Exception {
		SocketChannel socket = SocketChannel.open(new InetSocketAddress(1234));
		socket.configureBlocking(false);

		Selector selector = Selector.open();
		socket.register(selector, SelectionKey.OP_READ | SelectionKey.OP_WRITE);

		while (true) {
			int n = selector.select();
			if (n <= 0)
				continue;

			for (Iterator<SelectionKey> it = selector.selectedKeys().iterator(); it.hasNext();) {
				SelectionKey k = it.next();
				SocketChannel channel = (SocketChannel) k.channel();
				if (k.isReadable()) {
					readBuffer.clear();
					List<Byte> in = new ArrayList<>();
					while(channel.read(readBuffer) > 0){
						readBuffer.flip();
						while(readBuffer.hasRemaining()){
							in.add(readBuffer.get());
						}
					}
					if(!in.isEmpty()){
						byte[] b = new byte[in.size()];
						for(int i = 0; i < in.size(); i++){
							b[i] = in.get(i);
						}
						System.out.println("read from socket:" + new String(b));
					}
				}
				if (k.isWritable()) {
					Scanner scanner = new Scanner(System.in);
					while(scanner.hasNextLine()){
						String send = scanner.nextLine();
						int writeCount = channel.write(ByteBuffer.wrap(send.getBytes("GBK")));
						System.out.println("send to socket:" + send + ", write count:" + writeCount);
						break;
					}
				}
				it.remove();
			}
		}

	}
}
