package modules;

import handler.Controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.wcj.channel.Groups;
import com.wcj.core.NetContext;
import com.wcj.protocol.ResponseDto;

@Controller
public class FirstController {

	private static final Log log = LogFactory.getLog(FirstController.class);

	public void firstMethod(int i, boolean b) {
		log.debug("FirstController#firstMethod invoked.");
//		List<Active> all = Context.daoFactory.get(ActiveDao.class).getAll();
//		System.out.println("data:" + all);
	}
	
	public void broadcast(String message) {
		log.debug("FirstController#broadcast invoked.");
		ResponseDto result = new ResponseDto();
		result.setResult(message);
		NetContext.groups.broadcast(Groups.World, result);
	}
}