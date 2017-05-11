package cn.heipiao.api.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.heipiao.api.mapper.LikeMapper;
import cn.heipiao.api.pojo.Likes;
import cn.heipiao.api.service.LikeService;
import cn.heipiao.api.utils.ExDateUtils;

/**
 * @author zf
 * @version 1.0
 * @description
 * @date 2016年6月15日
 */
@Service
public class LikeServiceImpl implements LikeService {
	@Resource
	private LikeMapper likeMapper;

	@Override
	@Transactional(readOnly = false)
	public long iLikeIt(Long userId, Long shareId, String type)
			throws Exception {
		Likes oldLike = queryLike(userId, shareId, type);
		if (oldLike == null) {
			Likes like = new Likes();
			like.setLid(shareId);
			like.setUserId(userId);
			like.setCreateTime(ExDateUtils.getDate());
			like.setType(type);
			likeMapper.insert(like);
		}
		long likeNum = countLikes(shareId, type);
		return likeNum;
	}

	@Override
	@Transactional(readOnly = false)
	public long unLikeIt(Long userId, Long shareId, String type)
			throws Exception {
		Likes like = new Likes();
		like.setLid(shareId);
		like.setUserId(userId);
		like.setType(type);
		likeMapper.deleteLikeById(like);
		long likeNum = countLikes(shareId, type);
		return likeNum;
	}

	@Override
	public long countLikes(Long id, String type) throws Exception {
		long likeNum = likeMapper.countLikes(id, type);
		return likeNum;
	}

	@Override
	public Likes queryLike(Long userId, Long shareId, String type)
			throws Exception {
		Likes like = likeMapper.selectByUnionId(userId, shareId, type);
		return like;
	}

}
