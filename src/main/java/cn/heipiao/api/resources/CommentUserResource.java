package cn.heipiao.api.resources;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;

import cn.heipiao.api.service.CommentUserService;
import cn.heipiao.api.utils.ResultUtils;
/**
 * 评论管理
 * @ClassName: CommentUserResource
 * @Description: TODO
 * @author zf
 * @version 2.2
 */
//@Component
//@Consumes(MediaType.APPLICATION_JSON)
//@Produces(MediaType.APPLICATION_JSON)
//@Path("cu")
@RestController
@RequestMapping(value = "cu",produces="application/json")
public class CommentUserResource {

	private final static Logger log = LoggerFactory
			.getLogger(CommentUserResource.class);
	@Resource
	private CommentUserService commentUserService;

	/**
	 * 用户回复
	 * 
	 * @param json
	 * @return
	 * @since 2.2
	 */
//	@Path("ad")
//	@POST
	@RequestMapping(value = "ad",method = RequestMethod.POST,consumes = "application/json")
	public String addCu(@RequestBody JSONObject json) {
		try {
			log.debug("json:{}", json);
			if (json == null || json.getString("content") == null
					|| json.getLong("id") == null
					|| json.getInteger("reply_uid") == null
					|| json.getInteger("replyed_uid") == null) {
				return ResultUtils.out(Status.value_is_null_or_error);
			}
			this.commentUserService.addCommentUser(json);
			return ResultUtils.out(Status.success);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return ResultUtils.out(Status.error);
		}
	}
}
