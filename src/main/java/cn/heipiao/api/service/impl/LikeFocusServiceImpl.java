package cn.heipiao.api.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;

import cn.heipiao.api.mapper.LikeFocusMapper;
import cn.heipiao.api.mapper.UserMapper;
import cn.heipiao.api.pojo.LikeFocus;
import cn.heipiao.api.pojo.User;
import cn.heipiao.api.resources.Status;
import cn.heipiao.api.service.LikeFocusService;
import cn.heipiao.api.utils.ExDateUtils;

@Service
public class LikeFocusServiceImpl implements LikeFocusService {
	@Resource
	private LikeFocusMapper<LikeFocus> likeFocusMapper;

	@Resource
	private UserMapper userMapper;

	@Override
	public Map<String, Object> countstatistic(Integer uid, Integer uid2)
			throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		Integer likes = this.likeFocusMapper.countLikeUser(uid);
		result.put("likes", likes);
		Integer focus = this.likeFocusMapper.countFocus(uid);
		result.put("focus", focus);
		if (this.likeFocusMapper.selectOneFocus(uid, uid2) != null) {
			result.put("isfocus", 1);//关注
		} else {
			result.put("isfocus", 0);//未关注
		}
		if (this.likeFocusMapper.selectOneLike(uid, uid2) != null) {
			result.put("islike", 1);//点赞
		} else {
			result.put("islike", 0);//未点赞
		}
		return result;
	}

	@Override
	@Transactional
	public Map<String,Object> addLikeFocus(JSONObject json) throws Exception {
		Integer type = json.getInteger("type");
		LikeFocus pojo = JSONObject.toJavaObject(json, LikeFocus.class);
		Map<String,Object> result = new HashMap<String, Object>();
		if (type == 1) {
			if(this.likeFocusMapper.selectOneLike(pojo.getTargetId(), pojo.getUid())!=null) {
				this.likeFocusMapper.deleteLikeUser(pojo.getUid(), pojo.getTargetId());
				return null;
			}
			this.likeFocusMapper.insertLikeUser(pojo);
		} else if (type == 2) {
			pojo.setCreateTime(ExDateUtils.getDate());
			if(this.likeFocusMapper.selectOneFocus(pojo.getTargetId(), pojo.getUid())!=null) {
				this.likeFocusMapper.deleteFocusUser(pojo.getUid(), pojo.getTargetId());
				return null;
			}
			this.likeFocusMapper.insertFocusUser(pojo);
		} else if (type == 3) {
			pojo.setCreateTime(ExDateUtils.getDate());
			User user = userMapper.selectById(pojo.getUid());
			
			// FIXME 2016-12-18，发生错误：NullPointerException: null
			result.put("nickName", user.getNickname());
			pojo.setNickname(user.getNickname());
			if(this.likeFocusMapper.selectOneLikeArticle(pojo.getUid(),pojo.getTargetId().longValue())!=null) {
				this.likeFocusMapper.deleteLikeArticle(pojo.getUid(), pojo.getTargetId());
				result.put("likeNum", this.likeFocusMapper.countLikeArticle(pojo.getTargetId().longValue()));
				return result;
			}
			this.likeFocusMapper.insertLikeArticle(pojo);
			result.put("likeNum", this.likeFocusMapper.countLikeArticle(pojo.getTargetId().longValue()));
			return result;
		}
		return null;
	}
	@Override
	@Transactional
	public Integer addLikeArticle(JSONObject json) throws Exception {
		Integer type = json.getInteger("type");
		LikeFocus pojo = JSONObject.toJavaObject(json, LikeFocus.class);
		if (type == 1) {
			if(this.likeFocusMapper.selectOneLike(pojo.getTargetId(), pojo.getUid())!=null) {
				this.likeFocusMapper.deleteLikeUser(pojo.getUid(), pojo.getTargetId());
				return Status.success;
			}
			this.likeFocusMapper.insertLikeUser(pojo);
		} else if (type == 2) {
			pojo.setCreateTime(ExDateUtils.getDate());
			if(this.likeFocusMapper.selectOneFocus(pojo.getTargetId(), pojo.getUid())!=null) {
				this.likeFocusMapper.deleteFocusUser(pojo.getUid(), pojo.getTargetId());
				return Status.success;
			}
			this.likeFocusMapper.insertFocusUser(pojo);
		} else if (type == 3) {
			pojo.setCreateTime(ExDateUtils.getDate());
			User user = userMapper.selectById(pojo.getUid());
			pojo.setNickname(user.getNickname());
			if(this.likeFocusMapper.selectOneLikeArticle(pojo.getUid(),pojo.getTargetId().longValue())!=null) {
				this.likeFocusMapper.deleteLikeArticle(pojo.getUid(), pojo.getTargetId());
				return Status.success;
			}
			// FIXME 2016-12-21，发生错误：Duplicate entry '3398-513' for key 'PRIMARY'
			this.likeFocusMapper.insertLikeArticle(pojo);
		}
		return Status.success;
	}

	@Override
	public List<User> getFocusUsers(Integer uid) {
		return this.likeFocusMapper.selectFocusUsers(uid);
	}

}
