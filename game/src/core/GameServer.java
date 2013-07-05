package core;

import com.wcj.core.Acceptor;

public class GameServer {

	public static void main(String[] args) {
		Acceptor acceptor = new Acceptor();
		acceptor.start();
	}

}
