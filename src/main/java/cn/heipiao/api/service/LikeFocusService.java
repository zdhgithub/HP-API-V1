package cn.heipiao.api.service;

import java.util.List;
import java.util.Map;

import cn.heipiao.api.pojo.User;

import com.alibaba.fastjson.JSONObject;

public interface LikeFocusService {
	/**
	 * 点赞和关注的相关统计
	 * @param uid
	 * @param uid2
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> countstatistic(Integer uid, Integer uid2) throws Exception;
	/**
	 * 添加关注或是赞
	 * @param json
	 * @return
	 * @throws Exception
	 */
	public Map<String,Object> addLikeFocus(JSONObject json) throws Exception;
	/**
	 * 点赞文章
	 * @param json
	 * @return
	 * @throws Exception
	 */
	public Integer addLikeArticle(JSONObject json) throws Exception;
	/**
	 * 获取用户关注的人
	 * @param uid
	 * @return
	 */
	public List<User> getFocusUsers(Integer uid);

}
