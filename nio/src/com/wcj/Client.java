package com.wcj;

import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;
import java.util.concurrent.CountDownLatch;


public class Client {
    public static void main(String[] args) throws Exception {
	SocketChannel socket = SocketChannel.open(new InetSocketAddress(1234));
	CountDownLatch c = new CountDownLatch(1);
	c.await();
    }
}
