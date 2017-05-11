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
import cn.heipiao.api.oss.StsGetToken;
import cn.heipiao.api.pojo.RespMsg;
import cn.heipiao.api.pojo.Token;
import cn.heipiao.api.service.TokenService;
import io.swagger.annotations.Api;

/**
 * @author wzw
 * @date 2016年7月11日
 * @version 1.0
 */
//@Component
//@Path("token")
//@Produces({ MediaType.APPLICATION_JSON + ";charset=utf-8" })
//@Consumes({ MediaType.APPLICATION_JSON + ";charset=utf-8" })
@Api(tags = "文件存储oss模块")
@RestController
@RequestMapping(value = "token",produces="application/json")
public class TokenResource {

	private final static Logger logger  = LoggerFactory.getLogger(TokenResource.class);
	
	@Resource
	private TokenService tokenService;
	
	/**
	 * 
	 * @param json
	 * @return
	 */
//	@POST
//	@Path("oss_sts")
	@RequestMapping(value = "oss_sts",method = RequestMethod.POST,consumes = "application/json")
	public String getTokenBySts(@RequestBody JSONObject json){
		try {
			logger.debug("json:{}",json);
			String roleSessionName = json == null ? null : json.getString("roleSessionName");
			if(roleSessionName == null){
				return JSONObject.toJSONString(new RespMsg<>(Status.value_is_null_or_error,RespMessage.getRespMsg(Status.value_is_null_or_error)));
			}
			Token t = tokenService.selectByName(StsGetToken.oss_sts,roleSessionName);
			return JSONObject.toJSONString(new RespMsg<>(t));
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			return JSONObject.toJSONString(new RespMsg<>(Status.error,RespMessage.getRespMsg(Status.error)));
		}
	}
	
	
	/**
	 * {"bucket":"fs-profile","dir":"hello/"}
	 * @param json
	 * @return
	 */
//	@POST
//	@Path("oss_sign")
	@RequestMapping(value = "oss_sign",method = RequestMethod.POST,consumes = "application/json")
	public String getTokenBySign(@RequestBody JSONObject json){
		try {
			logger.debug("json:{}",json);
//			String roleSessionName = json == null ? null : json.getString("roleSessionName");
			String dir = json == null ? null : json.getString("dir");
			String bucket = json == null ? null : json.getString("bucket");
			if(bucket == null || dir == null){
				return JSONObject.toJSONString(new RespMsg<>(Status.value_is_null_or_error,RespMessage.getRespMsg(Status.value_is_null_or_error)));
			}		
			return JSONObject.toJSONString(new RespMsg<>(tokenService.getTokenBySign(bucket,dir)));
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			return JSONObject.toJSONString(new RespMsg<>(Status.error,RespMessage.getRespMsg(Status.error)));
		}
	}
	
}
