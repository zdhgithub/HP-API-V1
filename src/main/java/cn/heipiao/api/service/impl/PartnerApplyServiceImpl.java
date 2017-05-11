package cn.heipiao.api.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.heipiao.api.constant.ApiConstant;
import cn.heipiao.api.mapper.PartnerApplyMapper;
import cn.heipiao.api.mapper.PartnerMapper;
import cn.heipiao.api.pojo.Partner;
import cn.heipiao.api.pojo.PartnerApply;
import cn.heipiao.api.resources.Status;
import cn.heipiao.api.service.PartnerApplyService;
import cn.heipiao.api.utils.ExDateUtils;

/**
 * 
 * @author zf
 *
 */
@Service
public class PartnerApplyServiceImpl implements PartnerApplyService {
	@Resource
	private PartnerApplyMapper partnerApplyMapper;

	@Resource
	private PartnerMapper partnerMapper;

	@Override
	@Transactional
	public Integer applyPartner(PartnerApply javabean) throws Exception {
		javabean.setApplyTime(ExDateUtils.getDate());
		javabean.setStatus(1);
		partnerApplyMapper.insert(javabean);
		return null;
	}

	@Override
	public List<PartnerApply> getApplys(Map<String, Object> params)
			throws Exception {
		return partnerApplyMapper.selectList(params);
	}

	@Override
	@Transactional
	public Integer checkApply(Map<String, Object> params) throws Exception {
		partnerApplyMapper.update(params);
		if(ApiConstant.PartnerCheckStatus.PASS==(Integer)params.get("status")) {
			Partner par = partnerMapper.selectByUid((Long)params.get("id"));
			if(par!=null) {
				return Status.partner_true;
			}
			Partner partner = new Partner();
			partner.setpRegionId((Integer)params.get("regionId"));
			partner.setId((Long)params.get("id"));
			partner.setpCreateTime(ExDateUtils.getDate());
			partnerMapper.savePartner(partner);
		}
		return Status.success;
	}

	@Override
	public PartnerApply getApply(Integer uid) throws Exception {
		return partnerApplyMapper.selectOne(uid);
	}

}
