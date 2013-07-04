package com.wcj.bootstrap;

import com.wcj.core.Acceptor;
import com.wcj.core.NetContext;

public class Main {
	public static void main(String[] args) {
		NetContext ctx = new NetContext();
		Acceptor acceptor = new Acceptor(1234);
		acceptor.start();
	}
}
