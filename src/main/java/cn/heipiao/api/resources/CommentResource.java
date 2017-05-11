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
import cn.heipiao.api.pojo.Comment;
import cn.heipiao.api.pojo.RespMsg;
import cn.heipiao.api.pojo.Share;
import cn.heipiao.api.service.CommentService;

/**
 * @author zf
 * @version 1.0
 * @description 评论资源类
 * @date 2016年6月13日
 * @version 1.0
 */
//@Path("comments")
//@Consumes(MediaType.APPLICATION_JSON)
//@Produces(MediaType.APPLICATION_JSON)
//@Component
@RestController
@RequestMapping(value = "comments",produces="application/json")
public class CommentResource {
	@Resource
	private CommentService commentService;
	private static final Logger logger = LoggerFactory
			.getLogger(CommentResource.class);

	/**
	 * 分页查询评论列表
	 * 
	 * @param start
	 * @param size
	 * @param order
	 * @return
	 * @since 1.0
	 */
//	@Path("{userId}/{sid}/{start}/{size}")
//	@GET
	@RequestMapping(value = "{userId}/{sid}/{start}/{size}",method = RequestMethod.GET)
	public String queryComments(@PathVariable("sid") final Long sid,
			@PathVariable("userId") final Long userId,
			@PathVariable("start") final Integer start,
			@PathVariable("size") final Integer size) {
		if (sid == null || start == null || size == null || userId == null) {
			return JSONObject.toJSONString(new RespMsg<Comment>(
					Status.value_is_null_or_error));
		}
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("userId", userId);
			params.put("id", sid);
			params.put("start", start);
			params.put("size", size);
			List<Comment> coms = commentService.queryComments(params);
			// int num = commentService.countComments(sid);
			return JSONObject.toJSONString(new RespMsg<List<Comment>>(coms));
		} catch (Exception e) {
			logger.error("ExceptionMessage:{},printStackTrace:",
					e.getMessage(), e);
			return JSONObject.toJSONString(new RespMsg<Share>(Status.error));
		}
	}

	/**
	 * 发表评论
	 * 
	 * @param jsonObject
	 * @return
	 * @since 1.0
	 */
//	@POST
	@RequestMapping(method = RequestMethod.POST,consumes = "application/json")
	public String publishComment(@RequestBody JSONObject jsonObject) {
		try {
			logger.debug("json:{}", jsonObject);
			commentService.publishComment( jsonObject);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.toJSONString(new RespMsg<Share>(Status.error));
		}
		return JSONObject.toJSONString(new RespMsg<Comment>(Status.success));
	}

	/**
	 * 审核评论，非法评论取消显示
	 * 
	 * @param jsonObject
	 * @return
	 * @since 1.0
	 */
//	@PUT
	@Deprecated
	@RequestMapping(method = RequestMethod.PUT,consumes = "application/json")
	public String checkComment(@RequestBody JSONObject jsonObject) {
		Comment com = new Comment();
		com.setId( jsonObject.getLong("id"));
		if ( jsonObject.getBoolean("flag")) {
			com.setFlag(ApiConstant.CommentConstant.comment_status_able);
		} else {
			com.setFlag(ApiConstant.CommentConstant.comment_status_unable);
		}
		try {
			commentService.checkComment(com);
		} catch (Exception e) {
			logger.error("ExceptionMessage:{},printStackTrace:",
					e.getMessage(), e);
			return JSONObject.toJSONString(new RespMsg<Share>(Status.error,
					RespMessage.getRespMsg(Status.error)));
		}
		return JSONObject.toJSONString(new RespMsg<Comment>(Status.success,
				RespMessage.getRespMsg(Status.success)));
	}
}
