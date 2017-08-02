package cn.heipiao.api.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.heipiao.api.mapper.HpArticleCommentsMapper;
import cn.heipiao.api.mapper.HpArticleMapper;
import cn.heipiao.api.pojo.HpArticle;
import cn.heipiao.api.pojo.HpArticleComments;
import cn.heipiao.api.pojo.HpArticleMsg;
import cn.heipiao.api.service.HpArticleCommentsService;
import cn.heipiao.api.service.HpArticleMsgService;
import cn.heipiao.api.utils.ExDateUtils;

/**
 * @author wzw
 * @date 2017年3月29日
 */
@Service
public class HpArticleCommentsServiceImpl implements HpArticleCommentsService {

	@Resource
	private HpArticleCommentsMapper hpArticleCommentsMapper;
	
	@Resource
	private HpArticleMapper hpArticleMapper;
	
	@Resource
	private HpArticleMsgService hpArticleMsgService;
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void addComment(HpArticleComments pojo) {
		HpArticle a = hpArticleMapper.selectByIdAsLock(pojo.getArticleId());
		if(a != null){
			pojo.setCommentTime(ExDateUtils.getCalendar().getTimeInMillis());
			int i = hpArticleCommentsMapper.insertPojo(pojo);
			if(i > 0){
				a.setCommentCount(a.getCommentCount() + 1);
				hpArticleMapper.updatePojo(a);
				hpArticleMsgService.addCommentMsgPojo(pojo,a);
			}
		}
	}

	@Override
	public List<HpArticleComments> getCommentListByArticleId(Map<String, Object> map) {
		return hpArticleCommentsMapper.selectCommentListByArticleId(map);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void removeComment(HpArticleComments pojo) {
		HpArticle a = hpArticleMapper.selectByIdAsLock(pojo.getArticleId());
		if(a != null){
			int i = hpArticleCommentsMapper.deletePojo(pojo);
			if(i > 0){
				a.setCommentCount(a.getCommentCount() - 1);
				hpArticleMapper.updatePojo(a);
				HpArticleMsg msg = new HpArticleMsg();
				msg.setArticleId(pojo.getArticleId());
				msg.setMsgUid(pojo.getCommentUid());
				msg.setMsgTime(pojo.getCommentTime());
				hpArticleMsgService.modifyMsgContentIsNull(msg);
			}
		}
	}

	
}
