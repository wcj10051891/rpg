package com.wcj.bootstrap;

import java.lang.ref.SoftReference;
import java.util.Timer;
import java.util.TimerTask;
import java.util.WeakHashMap;


public class Test {
	
	static class Wcj{
		private long id;
		public Wcj() {
			id = System.currentTimeMillis(); 
		}
		@Override
		public String toString() {
			return "O->" + id;
		}
	}
	private static WeakHashMap<Object, Object> cache = new WeakHashMap<>();
	
	public static void main(String[] args) throws Exception {
//		final WeakReference<Wcj> o1 = new WeakReference<Test.Wcj>(new Wcj()); 
		Thread.sleep(1000);
		final SoftReference<Wcj> o2 = new SoftReference<Test.Wcj>(new Wcj()); 
//		cache.put(new Wcj(), new Wcj());
//		cache.put(new Wcj(), new Wcj());
//		cache.put(new Wcj(), new Wcj());
		new Timer().schedule(new TimerTask() {
			
			@Override
			public void run() {
//				System.out.println(o1.get());
				System.out.println(o2.get());
			}
		}, 1000, 1000);
	}
}
