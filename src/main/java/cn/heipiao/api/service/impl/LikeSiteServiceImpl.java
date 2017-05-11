/**
 * 
 */
package cn.heipiao.api.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.heipiao.api.mapper.FishSiteMapper;
import cn.heipiao.api.mapper.LikeSiteMapper;
import cn.heipiao.api.mapper.UserMapper;
import cn.heipiao.api.pojo.FishSite;
import cn.heipiao.api.pojo.LikeSite;
import cn.heipiao.api.pojo.User;
import cn.heipiao.api.resources.Status;
import cn.heipiao.api.service.LikeSiteService;

/**
 * @author wzw
 * @date 2016年11月29日
 * @version 1.0
 */
@Service
@Transactional(readOnly = true)
public class LikeSiteServiceImpl implements LikeSiteService {

	@Resource
	private LikeSiteMapper likeSiteMapper;
	
	@Resource
	private FishSiteMapper fishSiteMapper;
	
	@Resource
	private UserMapper userMapper;
	
	/* (non-Javadoc)
	 * @see cn.heipiao.api.service.LikeSiteService#likeSite(cn.heipiao.api.pojo.LikeSite)
	 */
	@Override
	@Transactional(readOnly = false,rollbackFor = {Exception.class})
	public int likeSite(LikeSite pojo) {
		if(likeSiteMapper.selectIsLike(pojo.getSiteId(), pojo.getUid()) == null){
			FishSite fs = fishSiteMapper.selectById(pojo.getSiteId());
			if(fs == null || fs.getStatus().intValue() != 1){
				return Status.fish_site_not_exists;
			}
			 User u = userMapper.selectById(pojo.getUid());
			 if(u == null){
				 return Status.no_such_user;
			 }
			likeSiteMapper.insertPojo(pojo);
		}
		return Status.success;
	}

	/* (non-Javadoc)
	 * @see cn.heipiao.api.service.LikeSiteService#unLikeSite(cn.heipiao.api.pojo.LikeSite)
	 */
	@Override
	@Transactional(readOnly = false,rollbackFor = {Exception.class})
	public void unLikeSite(LikeSite pojo) {
		likeSiteMapper.deletePojo(pojo.getSiteId(), pojo.getUid());
	}

	/* (non-Javadoc)
	 * @see cn.heipiao.api.service.LikeSiteService#countSite(java.lang.Integer)
	 */
	@Override
	public int countSite(Integer siteId) {
		return likeSiteMapper.countSite(siteId);
	}

	/* (non-Javadoc)
	 * @see cn.heipiao.api.service.LikeSiteService#getLikeUser(java.lang.Integer, java.lang.Long)
	 */
	@Override
	public LikeSite getLikeUser(Integer siteId, Long uid) {
		return likeSiteMapper.selectIsLike(siteId,uid);
	}

}
