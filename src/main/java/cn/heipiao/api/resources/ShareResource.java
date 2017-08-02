package cn.heipiao.api.resources;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;

import cn.heipiao.api.constant.ApiConstant;
import cn.heipiao.api.constant.RespMessage;
import cn.heipiao.api.pojo.RespMsg;
import cn.heipiao.api.pojo.Share;
import cn.heipiao.api.pojo.User;
import cn.heipiao.api.service.ShareService;
import cn.heipiao.api.service.UserOpService;
import cn.heipiao.api.service.UserShareRewardService;
import cn.heipiao.api.utils.ResultUtils;

/**
 * @author zf
 * @version 1.0
 * @description 分享或渔获资源类
 * @date 2016年6月13日
 */
//@Path("shares")
//@Consumes(MediaType.APPLICATION_JSON)
//@Produces(MediaType.APPLICATION_JSON)
//@Component
@RestController
@RequestMapping(value = "shares",produces="application/json")
public class ShareResource {
	@Resource
	private ShareService shareService;

	@Resource
	private UserOpService userOpService;

	private static final Logger logger = LoggerFactory
			.getLogger(ShareResource.class);

	@Resource
	private UserShareRewardService userShareRewardService;
	
	/**
	 * 用户分享渔获获得奖励
	 * {"uid":1,"shareId":123}
	 * @return
	 * @since 1.0
	 */
//	@POST
//	@Path("reward")
	@RequestMapping(value = "reward",method = RequestMethod.POST,consumes = "application/json")
	public String shareReward(@RequestBody JSONObject json){
		try {
			logger.info("json:{}",json);
			Long uid = json == null ? null : json.getLong("uid");
			Long shareId = json == null ? null : json.getLong("shareId");
			Map<String,Object> map = new HashMap<String, Object>();
			int reward = 0;
			if(uid != null && shareId != null){
				reward = userShareRewardService.shareReward(uid, shareId);
			}
			map.put("reward", reward);
			return JSONObject.toJSONString(new RespMsg<>(map));
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			return JSONObject.toJSONString(new RespMsg<>(Status.error));
		}
	}
	
	
	/**
	 * 分页查询分享或是渔获列表
	 * 
	 * @param start
	 * @param size
	 * @param order
	 * @return
	 * @since 1.0
	 */
//	@Path("{userId}/{siteId}/{type}/{start}/{size}")
//	@GET
	@RequestMapping(value = "{userId}/{siteId}/{type}/{start}/{size}",method = RequestMethod.GET)
	public String queryShares(@PathVariable("start") final Integer start,
			@PathVariable("userId") final Long userId,
			@PathVariable("size") final Integer size,
			@PathVariable("type") final String type,
			@PathVariable("siteId") final String siteId) {
		try {
			logger.debug("start:{},size:{},userId:{},type:{},siteId:{}", start,
					size, userId, type, siteId);
			if ("2".equals(type)) {
				return ResultUtils.out(Status.update_version);
			}
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("userId", userId);
			params.put("type", type);
			params.put("start", start);
			params.put("size", size);
			params.put("siteId", siteId);
			List<Share> shares = shareService.queryShares(params);
			return JSONObject.toJSONString(new RespMsg<List<Share>>(shares));
		} catch (Exception e) {
			logger.error("ExceptionMessage:{},printStackTrace:",
					e.getMessage(), e);
			return JSONObject.toJSONString(new RespMsg<Share>(Status.error,
					RespMessage.getRespMsg(Status.error)));
		}

	}

	/**
	 * 根据ID查询分享或是渔获信息
	 * 
	 * @param id
	 * @return
	 * @since 1.0
	 */
//	@Path("{id:\\d+}")
//	@GET
	@RequestMapping(value = "{id:\\d+}",method = RequestMethod.GET)
	public String queryShareById(@PathVariable("id") final Long id) {
		try {
			logger.debug("id:{}", id);
			if (id == null) {
				return JSONObject.toJSONString(new RespMsg<Share>(
						Status.value_is_null_or_error));
			}
			Share share = shareService.queryShareById(id);
			return JSONObject.toJSONString(new RespMsg<Share>(share));
		} catch (Exception e) {
			logger.error("ExceptionMessage:{},printStackTrace:",
					e.getMessage(), e);
			return JSONObject.toJSONString(new RespMsg<Share>(Status.error));
		}
	}

	/**
	 * 发表分享或是渔获
	 * 
	 * @param jsonObject
	 * @return
	 * @since 1.0
	 */
//	@POST
	@RequestMapping(method = RequestMethod.POST,consumes = "application/json")
	public String publishShare(@RequestBody JSONObject jsonObject) {
		try {
			logger.debug("json:{}", jsonObject);
			if ( jsonObject == null) {
				return JSONObject.toJSONString(new RespMsg<Integer>(
						Status.value_is_null_or_error));
			}
			Long userId = jsonObject.getLong("userId");
			User user = userOpService.queryUserById(userId);
			if (user == null) {
				return JSONObject.toJSONString(new RespMsg<Integer>(
						Status.emp_not_permission));
			}
			Share share = new Share();
			share.setImg( jsonObject.getString("img"));
			share.setAddress( jsonObject.getString("address"));
			share.setContent( jsonObject.getString("content"));
			share.setType( jsonObject.getString("type"));
			share.setUserId(userId);
			// 增加主图
			share.setMainImg( jsonObject.getString("mainImg"));
			// 增加钓场Id
			share.setSiteId( jsonObject.getInteger("siteId"));
			share.setUserId( jsonObject.getLong("userId"));
			shareService.publishShare(share);
			return JSONObject.toJSONString(new RespMsg<Share>(Status.success));
		} catch (Exception e) {
			logger.error("ExceptionMessage:{},printStackTrace:",
					e.getMessage(), e);
			return JSONObject.toJSONString(new RespMsg<Share>(Status.error,
					RespMessage.getRespMsg(Status.error)));
		}
	}

	/**
	 * 审核分享或是渔获
	 * 
	 * @param jsonObject
	 * @return
	 * @since 1.0
	 */
//	@PUT
	@Deprecated
	@RequestMapping(method = RequestMethod.PUT,consumes = "application/json")
	public String checkShare(@RequestBody JSONObject jsonObject) {
		Share share = new Share();
		share.setId(jsonObject.getLong("id"));
		if (jsonObject.getBoolean("flag")) {
			share.setFlag(ApiConstant.ShareConstant.share_status_able);
		} else {
			share.setFlag(ApiConstant.ShareConstant.share_status_unable);
		}
		try {
			shareService.modifyShare(share);
		} catch (Exception e) {
			logger.error("ExceptionMessage:{},printStackTrace:",
					e.getMessage(), e);
			return JSONObject.toJSONString(new RespMsg<Share>(Status.error,
					RespMessage.getRespMsg(Status.error)));
		}
		return JSONObject.toJSONString(new RespMsg<Share>(Status.success,
				RespMessage.getRespMsg(Status.success)));
	}

	/**
	 * +1
	 * 
	 * @param jsonObject
	 * @return
	 * @since 1.2
	 */
//	@PUT
//	@Path("addone/{sid}")
	@RequestMapping(value = "addone/{sid}",method = RequestMethod.PUT,consumes = "application/json")
	public String addClickNum(@PathVariable("sid") final Integer sid) {
		try {
			shareService.addClickNum(sid);
			return JSONObject.toJSONString(new RespMsg<Share>(Status.success));
		} catch (Exception e) {
			logger.error("ExceptionMessage:{},printStackTrace:",
					e.getMessage(), e);
			return JSONObject.toJSONString(new RespMsg<Share>(Status.error,
					RespMessage.getRespMsg(Status.error)));
		}
	}

}
