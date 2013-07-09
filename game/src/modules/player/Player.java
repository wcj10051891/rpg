package modules.player;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cache.CacheObject;

public class Player extends CacheObject {
	private static final Log log = LogFactory.getLog(Player.class);

	public static final String Channel_Context_Key = "player";

	@Override
	public CacheObject load(Object key) {
		return null;
	}

	@Override
	public Map<Object, CacheObject> gets(List<Object> keys) {
		return null;
	}

	@Override
	public void save() {
	}

	@Override
	public void delete() {
	}

	public void onLogin() {
		log.debug("");
	}

	public void onLogOut() {

	}
}
