package com.wcj;

import com.wcj.core.Acceptor;
import com.wcj.core.Context;

public class Main {
	public static void main(String[] args) {
		Context ctx = new Context();
		Acceptor acceptor = new Acceptor(1234);
		acceptor.start();
	}
}
