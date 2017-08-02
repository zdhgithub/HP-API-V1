/**
 * 
 */
package cn.heipiao.api.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.heipiao.api.mapper.FishSiteMapper;
import cn.heipiao.api.mapper.FocusMapper;
import cn.heipiao.api.pojo.FishSite;
import cn.heipiao.api.pojo.Focus;
import cn.heipiao.api.service.FocusService;

/**
 * @author wzw
 * @date 2016年6月13日
 * @version 1.0
 */
@Service
@Transactional(readOnly = true)
public class FocusServiceImpl implements FocusService {

	@Resource
	private FocusMapper focusMapper;
	
	@Resource
	private FishSiteMapper fishSiteMapper;
	

	/* (non-Javadoc)
	 * @see cn.heipiao.api.service.FocusService#deleteFollow(cn.heipiao.api.pojo.Focus)
	 */
	@Override
	@Transactional(readOnly = false,rollbackFor = {Exception.class})
	public void deleteFollow(Focus f) {
		FishSite pojo = fishSiteMapper.selectByIdAsLock(f.getFid());
		pojo.setFocusCount(pojo.getFocusCount() > 0 ? pojo.getFocusCount() - 1 : 0);
		fishSiteMapper.updateFocusCount(pojo);
		focusMapper.deletePojo(f);
	}

	/* (non-Javadoc)
	 * @see cn.heipiao.api.service.FocusService#addFollow(cn.heipiao.api.pojo.Focus)
	 */
	@Override
	@Transactional(readOnly = false,rollbackFor = {Exception.class})
	public void addFollow(Focus f) {
		FishSite pojo = fishSiteMapper.selectByIdAsLock(f.getFid());
		pojo.setFocusCount(pojo.getFocusCount() + 1);
		fishSiteMapper.updateFocusCount(pojo);
		focusMapper.insertPojo(f);
	}

	/* (non-Javadoc)
	 * @see cn.heipiao.api.service.FocusService#selectByFocus(java.util.Map)
	 */
	@Override
	public List<FishSite> selectByFocus(Map<String, Object> map) {
		return fishSiteMapper.selectByFocus(map);
	}

	/* (non-Javadoc)
	 * @see cn.heipiao.api.service.FocusService#selectByUidAndFid(java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public Focus selectByUidAndFid(Long uid, Integer fid) {
		return focusMapper.selectByUidAndFid(uid,fid);
	}
	
	
}
