package com.wcj.bootstrap;

import java.lang.reflect.Method;


public class Test {
	public static void main(String[] args) throws Exception{
		
		Method[] methods = Test.class.getMethods();
		for (Method method : methods) {
			if(method.getName().equals("main")){
				Class<?> returnType = method.getReturnType();
				System.out.println(returnType == Void.class);
				System.out.println(returnType);
			}
		}
		
	}
}
