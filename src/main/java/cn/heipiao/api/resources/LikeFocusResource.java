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

import cn.heipiao.api.service.LikeFocusService;
import cn.heipiao.api.utils.ResultUtils;

/**
 * 
 * @ClassName: LikeFocusResource
 * @Description: TODO
 * @author zf
 * @version 2.0
 */
//@Path("brand")
////FIXME 这里注解不全，可以？
//@Consumes({ MediaType.APPLICATION_JSON })
//@Produces({ MediaType.APPLICATION_JSON })
//@Controller
@RestController
@RequestMapping(value = "brand",produces="application/json")
public class LikeFocusResource {
	private static Logger log = LoggerFactory
			.getLogger(LikeFocusResource.class);

	@Resource
	private LikeFocusService likeFocusService;
	/**
	 * 个人品牌统计信息
	 * @param uid
	 * @param uid2
	 * @return
	 * @since 2.0
	 */
//	@Path("statistic/{uid}/{uid2}")
//	@GET
	@RequestMapping(value = "statistic/{uid}/{uid2}",method = RequestMethod.GET)
	public String countStatistics(@PathVariable("uid") final Integer uid,
			@PathVariable("uid2") final Integer uid2) {
		try {
			log.debug("uid:{},uid2:{}", uid, uid2);
			return ResultUtils.out(this.likeFocusService.countstatistic(uid,uid2));
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return ResultUtils.out(Status.error);
		}
	}
	/**
	 * 给店主点赞
	 * @param json
	 * @return
	 * @since 2.0
	 */
//	@Path("likeUser")
//	@POST
	@RequestMapping(value = "likeUser",method = RequestMethod.POST,consumes = "application/json")
	public String addLikeUser(@RequestBody JSONObject json) {
		try {
			log.debug("json:{}", json);
			if (json == null || json.getInteger("uid") == null
					|| json.getInteger("targetId") == null) {
				return ResultUtils.out(Status.value_is_null_or_error);
			}
			json.put("type", 1);
			return ResultUtils.out(this.likeFocusService.addLikeArticle(json));
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return ResultUtils.out(Status.error);
		}
	}
	/**
	 * 关注店主
	 * @param json
	 * @return
	 * @since 2.0
	 */
//	@Path("focusUser")
//	@POST
	@RequestMapping(value = "focusUser",method = RequestMethod.POST,consumes = "application/json")
	public String addFocusUser(@RequestBody JSONObject json) {
		try {
			log.debug("json:{}", json);
			if (json == null || json.getInteger("uid") == null
					|| json.getInteger("targetId") == null) {
				return ResultUtils.out(Status.value_is_null_or_error);
			}
			json.put("type", 2);
			return ResultUtils.out(this.likeFocusService.addLikeArticle(json));
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return ResultUtils.out(Status.error);
		}
	}
	/**
	 * 查询关注的人数
	 * @param uid
	 * @return
	 * @since 2.0
	 */
//	@Path("list/focusUser/{uid}")
//	@GET
	@RequestMapping(value = "list/focusUser/{uid}",method = RequestMethod.GET)
	public String getListFocusUser(@PathVariable("uid") final Integer uid) {
		try {
			log.debug("uid:{}", uid);
			if (uid == null) {
				return ResultUtils.out(Status.value_is_null_or_error);
			}
			return ResultUtils.out(this.likeFocusService.getFocusUsers(uid));
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return ResultUtils.out(Status.error);
		}
	}
	/**
	 * 给店主点赞
	 * @param json
	 * @return
	 * @since 2.0
	 */
//	@Path("likeArticle/new")
//	@POST
	@RequestMapping(value = "likeArticle/new",method = RequestMethod.POST,consumes = "application/json")
	public String addLikeArticleForNew(@RequestBody JSONObject json) {
		try {
			log.debug("json:{}", json);
			if (json == null || json.getInteger("uid") == null
					|| json.getInteger("targetId") == null) {
				return ResultUtils.out(Status.value_is_null_or_error);
			}
			json.put("type", 3);
			return ResultUtils.out(this.likeFocusService.addLikeFocus(json));
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return ResultUtils.out(Status.error);
		}
	}
	/**
	 * 点赞文章
	 * @param json
	 * @return
	 * @since 2.0
	 */
//	@Path("likeArticle")
//	@POST
	@RequestMapping(value = "likeArticle",method = RequestMethod.POST,consumes = "application/json")
	public String addLikeArticle(@RequestBody JSONObject json) {
		try {
			log.debug("json:{}", json);
			if (json == null || json.getInteger("uid") == null
					|| json.getInteger("targetId") == null) {
				return ResultUtils.out(Status.value_is_null_or_error);
			}
			json.put("type", 3);
			return ResultUtils.out(this.likeFocusService.addLikeArticle(json));
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return ResultUtils.out(Status.error);
		}
	}
}
