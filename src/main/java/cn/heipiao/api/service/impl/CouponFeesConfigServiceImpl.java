/**
 * 
 */
package cn.heipiao.api.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.heipiao.api.mapper.CouponFeesConfigMapper;
import cn.heipiao.api.pojo.CouponFeesConfig;
import cn.heipiao.api.pojo.GiveCoupons;
import cn.heipiao.api.service.CouponFeesConfigService;

/**
 * @author wzw
 * @date 2016年11月4日
 * @version 1.0
 */
@Service
@Transactional(readOnly = true)
public class CouponFeesConfigServiceImpl implements CouponFeesConfigService {

	@Resource
	private CouponFeesConfigMapper couponFeesConfigMapper;
	
	
	/* (non-Javadoc)
	 * @see cn.heipiao.api.service.CouponFeesConfigService#setFees(cn.heipiao.api.pojo.GiveCoupons)
	 */
	@Override
	@Transactional(readOnly = false,rollbackFor = {Exception.class})
	public void setFees(Integer serviceId ,GiveCoupons pojo) {
//		CouponFeesConfig cfc = couponFeesConfigMapper.selectUnique(serviceId, pojo.getCouponFee());
		
		List<CouponFeesConfig> list = getCouponFeeRule(serviceId, pojo.getCategory());
		
		pojo.setFee(0);
		if(list != null){
			for (CouponFeesConfig cfc : list) {
				if(cfc.getMin() <= pojo.getCouponFee() && (cfc.getMax() == null || cfc.getMax() > pojo.getCouponFee())){
					pojo.setFee(cfc.getFee());
				}
			}
		}
	}

	/* (non-Javadoc)
	 * @see cn.heipiao.api.service.CouponFeesConfigService#getCouponFeeRule(java.lang.Integer)
	 */
	@Override
	public List<CouponFeesConfig> getCouponFeeRule(Integer serviceId,Integer categoryId) {
		return couponFeesConfigMapper.selectById(serviceId,categoryId);
	}

	
	
}
