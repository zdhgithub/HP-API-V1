/**
 * 
 */
package cn.heipiao.api.resources.cp;

import java.util.Date;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;

import cn.heipiao.api.constant.RespMessage;
import cn.heipiao.api.pojo.RespMsg;
import cn.heipiao.api.pojo.SystemMsg;
import cn.heipiao.api.pojo.Welcome;
import cn.heipiao.api.resources.Status;
import cn.heipiao.api.service.PartnerDBService;
import cn.heipiao.api.service.SystemMsgService;
import cn.heipiao.api.service.TokenService;
import cn.heipiao.api.service.WelcomeService;
import cn.heipiao.api.utils.ResultUtils;

/**
 * @author wzw
 * @date 2016年11月30日
 * @version 2.2
 */
@RestController
@RequestMapping(value = "cp",produces="application/json")
public class CpOtherResource {

	
	private static final Logger logger = LoggerFactory
			.getLogger(CpOtherResource.class);
	
	
	@Resource
	private PartnerDBService partnerDBService;
	
	@Resource
	private SystemMsgService systemMsgService;
	
	
	@Resource
	private TokenService tokenService;
	
	@Resource
	private WelcomeService welcomeService;
	
	
//	@Path("pdb/ad")
//	@POST
	@RequestMapping(value = "pdb/ad",method = RequestMethod.POST,consumes = "application/json")
	public String addDB(@RequestBody JSONObject json) {
		try {
			logger.debug("json:{}", json);
			if (json.getString("title") == null
					|| json.getString("url") == null) {
				return ResultUtils.out(Status.value_is_null_or_error);
			}
			int result = this.partnerDBService.adDB(json);
			return ResultUtils.out(result == 0 ? 0 : result + "");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return ResultUtils.out(Status.error);
		}
	}
	
//	@Path("pdb/rm/{id}")
//	@DELETE
	@RequestMapping(value = "pdb/rm/{id}",method = RequestMethod.POST,consumes = "application/json")
	public String rmDB(@PathVariable("id") Integer id) {
		try {
			logger.debug("id:{]", id);
			if (id == null) {
				return ResultUtils.out(Status.value_is_null_or_error);
			}
			return ResultUtils.out(this.partnerDBService.rmDB(id));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return ResultUtils.out(Status.error);
		}
	}
	
//	@Path("pdb/up")
//	@PUT
	@RequestMapping(value = "pdb/up",method = RequestMethod.PUT,consumes = "application/json")
	public String upDB(@RequestBody JSONObject json) {
		try {
			logger.debug("json:{}", json);
			if (json.getInteger("id") == null) {
				return ResultUtils.out(Status.value_is_null_or_error);
			}
			return ResultUtils.out(this.partnerDBService.upDB(json));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return ResultUtils.out(Status.error);
		}
	}
	
	
//	@Path("systemsg/sd")
//	@POST
	@RequestMapping(value = "systemsg/sd",method = RequestMethod.POST,consumes = "application/json")
	public String sd(@RequestBody JSONObject json) {
		try {
			if (json.getString("title") == null) {
				return ResultUtils.out(Status.value_is_null_or_error);
			}
			SystemMsg s = JSONObject.toJavaObject(json, SystemMsg.class);
			Object[] os = json.getJSONArray("uids").toArray();
			return ResultUtils.out(this.systemMsgService.saveMsgBatch(s, os));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return ResultUtils.out(Status.error);
		}
	}
	
	/**
	 * 修改欢迎启动页的配置信息(cp)
	 * @param json
	 * @return
	 */
	@SuppressWarnings("rawtypes")
//	@Path("welcome/modification")
//	@PUT
	@RequestMapping(value = "welcome/modification",method = RequestMethod.PUT,consumes = "application/json")
	public String modification(@RequestBody JSONObject json) {
		try {
			logger.debug("json:{}", json);
			Integer terminal = json.getInteger("terminal");
			Integer butShowFlag = json.getInteger("butShowFlag");
			Integer delayTime = json.getInteger("delayTime");
			String content = json.getString("content");
			if (terminal == 1 || terminal == 2) {
				Welcome welcome = new Welcome();
				welcome.setTerminal(terminal);
				if(content != null && content.length() > 0){
					welcome.setContent(content);
					welcome.setUpdatetime(new Date());
				}
				welcome.setButShowFlag(butShowFlag);
				welcome.setDelayTime(delayTime);
				welcomeService.setWelcomeByTerminal(welcome);
				return JSONObject.toJSONString(new RespMsg());
			}else{
				return JSONObject.toJSONString(new RespMsg<>(
						Status.value_is_null_or_error, RespMessage
								.getRespMsg(Status.value_is_null_or_error)));
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.toJSONString(new RespMsg(Status.error,
					RespMessage.getRespMsg(Status.error)));
		}
	}
	/**
	 * 
	 * @param json
	 * @return
	 * @since 2.2
	 */
//	@Path("systemsg/send")
//	@POST
	@RequestMapping(value = "systemsg/send",method = RequestMethod.POST,consumes = "application/json")
	public String sendMsg(@RequestBody JSONObject json) {
		try {
			if (json.getString("title") == null
					|| json.getString("content") == null
					|| json.getInteger("num") == null
					|| json.getBoolean("ispush")) {
				return ResultUtils.out(Status.value_is_null_or_error);
			}
			SystemMsg s = JSONObject.toJavaObject(json, SystemMsg.class);
			return ResultUtils.out(this.systemMsgService.saveMsgBatch(s,
					json.getInteger("num"), json.getBoolean("ispush")));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return ResultUtils.out(Status.error);
		}
	}
	
	/**
	 * 获取小程序页面二维码
	 * @param json
	 * @return
	 */
//	@POST
//	@Path("token/wxaqrcode")
	@RequestMapping(value = "token/wxaqrcode",method = RequestMethod.POST,consumes = "application/json")
	public String getwxaqrcode(@RequestBody JSONObject json){
		try {
			logger.info("json:{}",json);
			String path = json == null ? null : json.getString("path");
			if(path == null){
				return JSONObject.toJSONString(new RespMsg<>(Status.value_is_null_or_error,RespMessage.getRespMsg(Status.value_is_null_or_error)));
			}
			String rs = tokenService.getWxaQrcode(path);
			return JSONObject.toJSONString(new RespMsg<>(rs));
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			return JSONObject.toJSONString(new RespMsg<>(Status.error,RespMessage.getRespMsg(Status.error)));
		}
	}
	
}
