/**
 * 
 */
package cn.heipiao.api.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.heipiao.api.mapper.AccountMapper;
import cn.heipiao.api.mapper.FishPondMapper;
import cn.heipiao.api.mapper.FishSiteMapper;
import cn.heipiao.api.mapper.PartnerMapper;
import cn.heipiao.api.mapper.SequenceMapper;
import cn.heipiao.api.mapper.SiteCouponsMapper;
import cn.heipiao.api.pojo.Account;
import cn.heipiao.api.pojo.Emp;
import cn.heipiao.api.pojo.FSPojo;
import cn.heipiao.api.pojo.FishPond;
import cn.heipiao.api.pojo.FishSite;
import cn.heipiao.api.pojo.FishSiteExt;
import cn.heipiao.api.pojo.Partner;
import cn.heipiao.api.pojo.Sequence;
import cn.heipiao.api.resources.Status;
import cn.heipiao.api.service.DistributionService;
import cn.heipiao.api.service.EmpService;
import cn.heipiao.api.service.FishSiteService;
import cn.heipiao.api.utils.ExDateUtils;

/**
 * @author wzw
 * @date 2016年6月2日
 * @version 1.0
 */
@Service
public class DistributionServiceImpl implements DistributionService {

	@Resource
	private FishSiteMapper fishSiteMapper;

	@Override
	public int deleteDistribution(Map<String, Object> map) {
		fishSiteMapper.deleteDistribution(map);
		return Status.success;
	}

	@Override
	public int insertDistribution(Map<String, Object> map) {
		fishSiteMapper.insertDistribution(map);
		return Status.success;
	}

	@Override
	public List<FishSite> selectByShopId(Map<String, Object> map) {
		List<FishSite> list = fishSiteMapper.selectByShopId(map);
		return list;
	}

	
	
}
