package cn.heipiao.api.resources;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;

import cn.heipiao.api.constant.RespMessage;
import cn.heipiao.api.pojo.RespMsg;
import cn.heipiao.api.pojo.Share;
import cn.heipiao.api.service.LikeService;

/**
 * @author zf
 * @version 1.0
 * @description
 * @date 2016年6月13日
 */
@RestController
@RequestMapping(value = "likes",produces="application/json")
public class LikesResource {
	@Resource
	private LikeService likeService;

	private static final Logger logger = LoggerFactory
			.getLogger(LikesResource.class);
//	@Path("")
//	@GET
//	public String whoLikesMe() {
//		
//		return null;
//	}
	/**
	 * 点赞
	 * 
	 * @param jsonObject
	 * @return
	 * @since 1.0
	 */
//	@POST
	@RequestMapping(method = RequestMethod.POST,consumes = "application/json")
	public String iLikes(@RequestBody JSONObject jsonObject) {
		try {
			logger.debug("json:", jsonObject.toJSONString());
			Long userId = jsonObject.getLong("uid");
			Long shareId = jsonObject.getLong("sid");
			String type = jsonObject.getString("type");
			if (userId == null || shareId == null) {
				return JSONObject.toJSONString(new RespMsg<Share>(
						Status.value_is_null_or_error));
			}
			long likeNum = likeService.iLikeIt(userId, shareId, type);
			return JSONObject.toJSONString(new RespMsg<Long>(likeNum));
		} catch (Exception e) {
			logger.error("ExceptionMessage:{},printStackTrace:",
					e.getMessage(), e);
			return JSONObject.toJSONString(new RespMsg<Share>(Status.error));
		}
	}

	/**
	 * 取消点赞
	 * 
	 * @param id
	 * @return
	 * @since 1.0
	 */
//	@Path("{sid:\\d+}/{uid:\\d+}/{type}")
//	@DELETE
	@RequestMapping(value = "{sid:\\d+}/{uid:\\d+}/{type}",method = RequestMethod.POST,consumes = "application/json")
	public String notLikes(@PathVariable("sid") final Long sid,
			@PathVariable("uid") final Long uid,
			@PathVariable("type") final String type) {
		if (uid == null || sid == null || StringUtils.isEmpty(type)) {
			return JSONObject.toJSONString(new RespMsg<Share>(
					Status.value_is_null_or_error, RespMessage
							.getRespMsg(Status.value_is_null_or_error)));
		}
		long likeNum = 0L;
		try {
			likeNum = likeService.unLikeIt(uid, sid, type);
		} catch (Exception e) {
			logger.error("ExceptionMessage:{},printStackTrace:",
					e.getMessage(), e);
			return JSONObject.toJSONString(new RespMsg<Share>(Status.error,
					RespMessage.getRespMsg(Status.error)));
		}
		return JSONObject.toJSONString(new RespMsg<Long>(likeNum));
	}

}
