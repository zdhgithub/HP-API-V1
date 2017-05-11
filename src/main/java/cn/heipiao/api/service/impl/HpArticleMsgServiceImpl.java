package cn.heipiao.api.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import cn.heipiao.api.mapper.HpArticleMsgMapper;
import cn.heipiao.api.pojo.HpArticle;
import cn.heipiao.api.pojo.HpArticleComments;
import cn.heipiao.api.pojo.HpArticleLikes;
import cn.heipiao.api.pojo.HpArticleMsg;
import cn.heipiao.api.service.HpArticleMsgService;
import cn.heipiao.api.service.UserProfileService;

/**
 * @author wzw
 * @date 2017年4月1日
 */
@Service
public class HpArticleMsgServiceImpl implements HpArticleMsgService {

	@Resource
	private HpArticleMsgMapper hpArticleMsgMapper;
	
	@Resource
	private UserProfileService userProfileService;
	
	
	@Override
	public List<HpArticleMsg> getListByUid(Map<String, Object> map) {
		return hpArticleMsgMapper.selectListByUid(map);
	}

	@Override
	public void addPojo(HpArticleMsg pojo) {
		hpArticleMsgMapper.insertPojo(pojo);
	}

	@Override
	public void removeAllByUid(Long uid) {
		hpArticleMsgMapper.deleteAllByUid(uid);
	}

	@Override
	public void removePojo(HpArticleMsg pojo) {
		hpArticleMsgMapper.deletePojo(pojo);
	}

	@Override
	public void addCommentMsgPojo(HpArticleComments pojo,HpArticle a) {
		HpArticleMsg msg = new HpArticleMsg();
		if(!setMsg(msg,a)){
			return ;
		}
		msg.setArticleId(pojo.getArticleId());
		msg.setMsgContent(pojo.getCommentContent().substring(0, pojo.getCommentContent().length() > 30 ? 30 : pojo.getCommentContent().length()));
		msg.setMsgType(2);
		msg.setMsgUid(pojo.getCommentUid());
		msg.setUid(a.getArticleUid());
		msg.setMsgTime(pojo.getCommentTime());
		//评论回复消息
		if(a.getArticleUid().longValue() != pojo.getCommentUid()){
			addPojo(msg);
			userProfileService.updateMsgSum(a.getArticleUid(),1);
		}
		//回复评论人的消息
		if(pojo.getCommentRUid() != null){
			msg.setUid(pojo.getCommentRUid());
			addPojo(msg);
			userProfileService.updateMsgSum(pojo.getCommentRUid(),1);
		}
		
	}

	@Override
	public void addLikeMsgPojo(HpArticleLikes pojo,HpArticle a) {
		HpArticleMsg msg = new HpArticleMsg();
		if(!setMsg(msg,a)){
			return ;
		}
		msg.setArticleId(pojo.getArticleId());
		msg.setMsgType(1);
		msg.setMsgUid(pojo.getLikeUid());
		HpArticleMsg m = hpArticleMsgMapper.selectUnique(msg);
		if(m != null){
			return ;
		}
		msg.setUid(a.getArticleUid());
		msg.setMsgTime(pojo.getLikeTime());
		
		if(a.getArticleUid().longValue() != pojo.getLikeUid()){
			addPojo(msg);
			userProfileService.updateMsgSum(a.getArticleUid(),1);
		}
	}
	
	
	private boolean setMsg(HpArticleMsg msg, HpArticle a){
		
		//只有用户才有消息提醒
		if(a == null || a.getCategory().intValue() != 0){
			return false;
		}
		
		if(a.getArticleCategory().intValue() == 1){
			if(StringUtils.isNotBlank(a.getUrl())){
				msg.setMsgArticleType(1);
				msg.setMsgArticle(a.getUrl().split(",")[0]);
			} else {
				msg.setMsgArticleType(2);
				msg.setMsgArticle(a.getContent().substring(0, a.getContent().length() > 30 ? 30 : a.getContent().length()));
			}
		} else if (a.getArticleCategory().intValue() == 2){
			msg.setMsgArticleType(2);
			msg.setMsgArticle(a.getTitle());
		} else {
			return false;
		}
		return true;
	}

	@Override
	public void modifyMsgContentIsNull(HpArticleMsg pojo) {
		hpArticleMsgMapper.updateMsgContentIsNull(pojo);
	}
}
