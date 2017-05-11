package cn.heipiao.api.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.heipiao.api.mapper.CommentUserMapper;
import cn.heipiao.api.mapper.UserMapper;
import cn.heipiao.api.pojo.CommentUser;
import cn.heipiao.api.pojo.User;
import cn.heipiao.api.service.CommentUserService;
import cn.heipiao.api.utils.ExDateUtils;

import com.alibaba.fastjson.JSONObject;

@Service
public class CommentUserServiceImpl implements CommentUserService {
	@Resource
	private CommentUserMapper commentUserMapper;
	@Resource
	private UserMapper userMapper;
	@Transactional
	@Override
	public Integer addCommentUser(JSONObject json) throws Exception {
		CommentUser cu = JSONObject.toJavaObject(json, CommentUser.class);
		cu.setCreateTime(ExDateUtils.getDate());
		cu.setFlag("1");
		//回复人
		User reply = userMapper.selectById(cu.getReply_uid());
		cu.setReply_name(reply.getNickname());
		//被回复人
		User replyed = userMapper.selectById(cu.getReplyed_uid());
		cu.setReplyed_name(replyed.getNickname());
		this.commentUserMapper.insert(cu);
		return null;
	}

}
