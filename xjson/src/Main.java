import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import net.sf.json.JSONObject;

import org.apache.commons.lang.math.RandomUtils;


public class Main {
	public static void main(String[] args) {
		final JSONObject o = new JSONObject();
		o.put("abc", 1);
		o.put("abc", 1);
		ScheduledThreadPoolExecutor pool = new ScheduledThreadPoolExecutor(10);
		pool.scheduleWithFixedDelay(new Runnable(){
			@Override
			public void run() {
				o.put("abc", RandomUtils.nextFloat());
				System.out.println(o);
			}
		}, 1, 1, TimeUnit.SECONDS);
		pool.scheduleWithFixedDelay(new Runnable(){
			@Override
			public void run() {
				o.put("abc", RandomUtils.nextFloat());
				System.out.println(o);
			}
		}, 1, 1, TimeUnit.SECONDS);
		pool.scheduleWithFixedDelay(new Runnable(){
			@Override
			public void run() {
				o.put("abc", RandomUtils.nextFloat());
				System.out.println(o);
			}
		}, 1, 1, TimeUnit.SECONDS);
	}
}
