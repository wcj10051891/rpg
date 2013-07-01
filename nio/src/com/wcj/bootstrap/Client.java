package com.wcj.bootstrap;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import com.wcj.app.protocol.RequestDto;
import com.wcj.app.protocol.json.JSONEncoder;

public class Client {
	static ByteBuffer readBuffer = ByteBuffer.allocate(1024);

	public static void main(String[] args) throws Exception {
		final SocketChannel socket = SocketChannel.open(new InetSocketAddress(1234));
		socket.configureBlocking(false);

		Selector selector = Selector.open();
		socket.register(selector, SelectionKey.OP_READ);
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				Scanner scanner = new Scanner(System.in);
				while(scanner.hasNextLine()){
					String send = scanner.nextLine();
					if(send.equals("1")){
						RequestDto request = new RequestDto();
						request.setClazz("com.wcj.app.modules.FirstController");
						request.setMethod("firstMethod");
						List<Object> params = new ArrayList<>();
						params.add(1);
						params.add(false);
						request.setParams(params);
						request.setSn(1);
						try {
							socket.write(ByteBuffer.wrap(JSONEncoder.INSTANCE.encode(request)));
						} catch (IOException e) {
							e.printStackTrace();
						}
					}else if(send.equals("all")){
						RequestDto request = new RequestDto();
						request.setClazz("com.wcj.app.modules.FirstController");
						request.setMethod("broadcast");
						List<Object> params = new ArrayList<>();
						params.add("牛逼的大神发话了" + System.currentTimeMillis());
						request.setParams(params);
						request.setSn(2);
						try {
							socket.write(ByteBuffer.wrap(JSONEncoder.INSTANCE.encode(request)));
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}).start();

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
				it.remove();
			}
		}

	}
}
