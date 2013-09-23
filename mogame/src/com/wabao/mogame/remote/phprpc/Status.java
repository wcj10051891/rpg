package com.wabao.mogame.remote.phprpc;



public class Status {
	
	
	
	
    public int online() {
    	try {
			System.out.println("你妈的zz。");
	    	return 1;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e);
	    	return 2;
		}
    }
}
