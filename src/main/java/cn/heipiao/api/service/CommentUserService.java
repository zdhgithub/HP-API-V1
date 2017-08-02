package cn.heipiao.api.service;

import com.alibaba.fastjson.JSONObject;

public interface CommentUserService {
	/**
	 * 保存用户评论
	 * @param json
	 * @return
	 * @throws Exception
	 */
	public Integer addCommentUser(JSONObject json) throws Exception;
}
