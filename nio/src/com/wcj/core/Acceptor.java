package com.wcj.core;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicBoolean;

import com.wcj.NioException;

/**
 * acceptor
 * @author wcj
 */
public class Acceptor {
    private Selector selector;
    private AtomicBoolean starting = new AtomicBoolean();
    private ByteBuffer echo = ByteBuffer.allocate(12);

    public Acceptor() {
	try {
	    this.selector = Selector.open();
	    echo.put("hello world!".getBytes());
	} catch (IOException e) {
	    throw new NioException("selector open error.", e);
	}
    }

    public void start() {
	if (starting.get())
	    return;
	if (starting.compareAndSet(false, true)) {
	    try {
		ServerSocketChannel server = ServerSocketChannel.open();
		server.configureBlocking(false);
		server.bind(new InetSocketAddress(1234));
		server.register(this.selector, SelectionKey.OP_ACCEPT);
	    } catch (IOException e) {
		starting.set(false);
		throw new NioException("server socket open error.", e);
	    }
	    try {
		while (starting.get()) {
		    int count = this.selector.select();
		    if (count <= 0)
			continue;

		    for (Iterator<SelectionKey> it = this.selector.selectedKeys().iterator(); it.hasNext();) {
			SelectionKey k = it.next();
			
			if(k.isAcceptable()){
			    SocketChannel socket = ((ServerSocketChannel)k.channel()).accept();
			    socket.configureBlocking(false);
			    socket.register(this.selector, SelectionKey.OP_READ);
			    echo.flip();
			    System.out.println("write to socket:" + socket.write(echo));
			}
			if(k.isReadable()){
			    SocketChannel socket = (SocketChannel)k.channel();
			    ByteBuffer dst = ByteBuffer.allocate(12);
			    socket.read(dst);
			    dst.flip();
			    System.out.println(dst.asCharBuffer().toString());
			}
			it.remove();
		    }
		}
	    } catch (IOException e) {
		starting.set(false);
		throw new NioException("select error.", e);
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
