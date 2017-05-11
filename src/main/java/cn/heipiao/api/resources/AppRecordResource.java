package cn.heipiao.api.resources;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;

import cn.heipiao.api.service.UserOpService;
import cn.heipiao.api.utils.ResultUtils;
/**
 * 
 * @ClassName: AppRecordResource
 * @Description: TODO
 * @author zf
 * @version 2.2
 */
@RestController
@RequestMapping(value = "apprecord",produces="application/json")
public class AppRecordResource {
	private static final Logger logger = LoggerFactory
			.getLogger(AppRecordResource.class);

	@Resource
	private UserOpService userOpService;

	/**
	 * 记录APP数据
	 * 
	 * @param json
	 * @return
	 * @since 2.2
	 * 
	 */
//	@POST
	@RequestMapping(method = RequestMethod.POST,consumes = "application/json")
	public String saveAppRecord(@RequestBody JSONObject json) {
		try {
			logger.debug("json:{}", json);
			if (json == null || json.getString("version")==null) {
				ResultUtils.out(Status.value_is_null_or_error);
			}
			return ResultUtils.out(userOpService.saveAppRecord(json));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return ResultUtils.out(Status.error);
		}
	}
	/**
	 * 获取登陆失效时间
	 * @param uid
	 * @param app
	 * @return
	 * @since 2.2
	 */
//	@Path("tos/{uid}/{app}")
//	@GET
	@RequestMapping(value = "tos/{uid}/{app}",method = RequestMethod.GET)
	public String getTokenStatus(@PathVariable("uid") Integer uid,@PathVariable("app") String app) {
		try {
			logger.debug("uid:{},app:{}", uid,app);
			if (uid == null) {
				ResultUtils.out(Status.value_is_null_or_error);
			}
			return ResultUtils.out(this.userOpService.getTokenStatus(uid,app));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return ResultUtils.out(Status.error);
		}
	}
}
