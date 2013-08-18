package gen.controller;

import gen.model.TransferModel;
import net.sf.json.JSONObject;

/**
 * 在<code>GameController</code>中接口返回值类型的定义
 * 
 * @author <a href="mailto:xiaofeng.l@cndw.com">arbow</a>
 *
 */
public interface JsonResponse extends TransferModel {

	public JSONObject toJson();
}
