/**
 * 
 */
package cn.heipiao.api.resources;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;

import cn.heipiao.api.constant.RespMessage;
import cn.heipiao.api.pojo.RespMsg;
import cn.heipiao.api.service.RefundService;

/**
 * @author wzw
 * @date 2016年7月18日
 * @version 1.0
 */
//@Path("pay/refund/")
//@Produces({ MediaType.APPLICATION_JSON + ";charset=utf-8" })
//@Consumes({ MediaType.APPLICATION_JSON + ";charset=utf-8" })
//@Component
@RestController
@RequestMapping(value = "pay/refund",produces="application/json")
public class RefundResource {

	private static final Logger logger = LoggerFactory.getLogger(RefundResource.class);
	
	@Resource
	private RefundService refundService;
	
	/**
	 * 确认退款
	 * 
	 * 退款接口，原来是为cp设计的接口
	 * 调用此接口必须在退款单号已经生成后才能调用
	 * 
	 * 
	 * @param json
	 * @return
	 */
//	@POST
//	@Path("confirm")
	@Deprecated	
	@RequestMapping(value = "confirm",method = RequestMethod.POST,consumes = "application/json")
	public String confirm(@RequestBody JSONObject json){
		try {
			logger.debug("json:{}", json);
			String refundNo = json == null ? null : json.getString("refundNo");
			if(refundNo == null){
				return JSONObject.toJSONString(new RespMsg<>(Status.value_is_null_or_error,RespMessage.getRespMsg(Status.value_is_null_or_error)));
			}
			//确认退款
			if(refundService.confirmRefund(refundNo)){
				return JSONObject.toJSONString(new RespMsg<>());
			}
			return JSONObject.toJSONString(new RespMsg<>(Status.refund_fail,RespMessage.getRespMsg(Status.refund_fail)));
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			return JSONObject.toJSONString(new RespMsg<>(Status.error,RespMessage.getRespMsg(Status.error)));
		}
	}
	
	
}
