package cn.heipiao.api.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.heipiao.api.mapper.HpArticleLikesMapper;
import cn.heipiao.api.mapper.HpArticleMapper;
import cn.heipiao.api.pojo.HpArticle;
import cn.heipiao.api.pojo.HpArticleLikes;
import cn.heipiao.api.service.HpArticleLikesService;
import cn.heipiao.api.service.HpArticleMsgService;
import cn.heipiao.api.utils.ExDateUtils;

/**
 * @author wzw
 * @date 2017年3月29日
 */
@Service
public class HpArticleLikesServiceImpl implements HpArticleLikesService {

	@Resource
	private HpArticleLikesMapper hpArticleLikesMapper;
	
	@Resource
	private HpArticleMapper hpArticleMapper;
	
	@Resource
	private HpArticleMsgService hpArticleMsgService;
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void addLike(HpArticleLikes pojo) {
		HpArticleLikes likes = hpArticleLikesMapper.selectUnique(pojo);
		if(likes != null){
			return ;
		}
		HpArticle a = hpArticleMapper.selectByIdAsLock(pojo.getArticleId());
		if(a != null){
			pojo.setLikeTime(ExDateUtils.getCalendar().getTimeInMillis());
			int i = hpArticleLikesMapper.insertPojo(pojo);
			if(i > 0){
				a.setLikeCount(a.getLikeCount() + 1);
				hpArticleMapper.updatePojo(a);
				hpArticleMsgService.addLikeMsgPojo(pojo, a);
			}
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void removeLike(HpArticleLikes pojo) {
		HpArticle a = hpArticleMapper.selectByIdAsLock(pojo.getArticleId());
		if(a != null){
			int i = hpArticleLikesMapper.deletePojo(pojo);
			if(i > 0){
				a.setLikeCount(a.getLikeCount() - 1);
				hpArticleMapper.updatePojo(a);
			}
		}
	}

	@Override
	public List<HpArticleLikes> getLikeListByArticleId(Map<String, Object> map) {
		return hpArticleLikesMapper.selectLikeListByArticleId(map);
	}

}
