/**
 * 
 */
package cn.heipiao.api.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.heipiao.api.constant.FishSiteConstant;
import cn.heipiao.api.mapper.FoundMenuMapper;
import cn.heipiao.api.mapper.PartnerMapper;
import cn.heipiao.api.pojo.FoundMenu;
import cn.heipiao.api.service.FoundMenuService;
import cn.heipiao.api.utils.ExDateUtils;

/**
 * @author wzw
 * @date 2016年6月15日
 * @version 1.0
 */
@Service
@Transactional(readOnly = true)
public class FoundMenuServiceImpl implements FoundMenuService {

	@Resource
	private FoundMenuMapper foundMenuMapper;

	@Resource
	private PartnerMapper partnerMapper;

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.heipiao.api.service.FoundMenuService#selectAllByStatus()
	 */
	@Override
	public List<FoundMenu> selectAll() {
		return foundMenuMapper.selectAll();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.heipiao.api.service.FoundMenuService#selectAllByStatus(java.lang.String
	 * )
	 */
	@Override
	//@Cacheable(value = { "dataCache" }, key = "#root.targetClass")
	public List<FoundMenu> selectAllByStatus(Integer status) {
		return foundMenuMapper.selectAllByStatus(status);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.heipiao.api.service.FoundMenuService#insertPojo(cn.heipiao.api.pojo
	 * .FoundMenu)
	 */
	@Override
	@Transactional(readOnly = false, rollbackFor = { Exception.class })
	//@CachePut(value = { "dataCache" }, key = "#root.targetClass")
	public List<FoundMenu> insertPojo(FoundMenu pojo) {
		pojo.setCreateTime(ExDateUtils.getDate());
		foundMenuMapper.insertPojo(pojo);
		return foundMenuMapper.selectAllByStatus(FishSiteConstant.ONE);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.heipiao.api.service.FoundMenuService#updatePojo(cn.heipiao.api.pojo
	 * .FoundMenu)
	 */
	@Override
	@Transactional(readOnly = false, rollbackFor = { Exception.class })
//	@CachePut(value = { "dataCache" }, key = "#root.targetClass")
	public List<FoundMenu> updatePojo(FoundMenu pojo) {
		foundMenuMapper.updatePojo(pojo);
		return foundMenuMapper.selectAllByStatus(FishSiteConstant.ONE);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.heipiao.api.service.FoundMenuService#deletePojo(java.lang.Integer)
	 */
	@Override
	@Transactional(readOnly = false, rollbackFor = { Exception.class })
//	@CachePut(value = { "dataCache" }, key = "#root.targetClass")
	public List<FoundMenu> deletePojo(Integer fid) {
		foundMenuMapper.deletePojo(fid);
		return foundMenuMapper.selectAllByStatus(FishSiteConstant.ONE);
	}

	@Override
//	@Cacheable(value = { "dataCache" })
	public List<FoundMenu> selectArticles(Map<String, Object> params) {
		return foundMenuMapper.selectArticles(params);
	}

	@Override
	@Transactional
	public Integer topArticle(Integer id) throws Exception {
		foundMenuMapper.updateList();
		FoundMenu pojo = new FoundMenu();
		pojo.setFid(id);
		pojo.setSortNo(Integer.MAX_VALUE);
		foundMenuMapper.updatePojo(pojo);
		return null;
	}

	@Override
	public List<FoundMenu> selectSeconds(Integer pid) {
		return foundMenuMapper.selectSeconds(pid);
	}

}
