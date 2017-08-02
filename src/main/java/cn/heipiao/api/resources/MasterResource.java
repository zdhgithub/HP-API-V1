package cn.heipiao.api.resources;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;

import cn.heipiao.api.constant.RespMessage;
import cn.heipiao.api.pojo.Master;
import cn.heipiao.api.pojo.RespMsg;
import cn.heipiao.api.service.MasterService;

/**
 * @author asdf3070@163.com
 * @date 2016年11月22日
 * @version 2.2
 */
@RestController
@RequestMapping(value = "master",produces="application/json")
public class MasterResource {
	private static final Logger logger = LoggerFactory.getLogger(MasterResource.class);
	
	@Resource
	private MasterService masterService;

//	@GET
//	@Path("query/{uid}")
	@RequestMapping(value = "query/{uid}",method = RequestMethod.GET)
	public String queryAll(@PathVariable("uid") final Long uid){
		try {
			logger.debug("uid:{} ", uid);
			Master master = masterService.get(uid);
			return JSONObject.toJSONString(new RespMsg<Master>(master));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.toJSONString(new RespMsg<>(Status.error,RespMessage.getRespMsg(Status.error)));
		}
	}

}
