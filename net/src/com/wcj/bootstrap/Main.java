package com.wcj.bootstrap;

import com.wcj.core.Acceptor;

public class Main {
	public static void main(String[] args) throws Exception {
		Acceptor acceptor = new Acceptor(1234);
		acceptor.start();
		
		Thread.sleep(5000);
		
		acceptor.stop();
	}
}
