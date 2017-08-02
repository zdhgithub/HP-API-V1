package cn.heipiao.api.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.heipiao.api.constant.ApiConstant;
import cn.heipiao.api.mapper.CouponMapper;
import cn.heipiao.api.pojo.Coupon;
import cn.heipiao.api.resources.Status;
import cn.heipiao.api.service.CouponService;
import cn.heipiao.api.utils.ExDateUtils;

/**
 * @author zf
 * @version 1.0
 * @description
 * @date 2016年7月20日
 */
@Service
public class CouponServiceImpl implements CouponService {
	@Resource
	private CouponMapper couponMapper;

	@Override
	@Transactional(readOnly = false)
	public int saveCoupon(Coupon coupon) throws Exception {
		if (!infoNull(coupon)) {
			return Status.INFO_NOT_COMPLETE;
		}
		coupon.setFlag(ApiConstant.CouponFlag.YES);
		coupon.setStatus(ApiConstant.CouponStatus.WAIT);
		coupon.setCreateTime(ExDateUtils.getDate());
		int couponId = couponMapper.insert(coupon);
		return couponId;
	}

	@Override
	public List<Coupon> getCouponList(Map<String, Object> param)
			throws Exception {
		List<Coupon> couponList = couponMapper.selectList(param);
		return couponList;
	}

	@Override
	@Transactional(readOnly = false)
	public int updateCouponFlag(Integer couponId, Integer flag)
			throws Exception {
		Coupon coupon = new Coupon();
		coupon.setId(couponId);
		coupon.setFlag(flag);
		couponMapper.updateById(coupon);
		return coupon.getId();
	}

	private boolean infoNull(Coupon coupon) {
		if (coupon.getType() == null || coupon.getName() == null
				|| coupon.getDescription() == null || coupon.getMoney() == null
				|| coupon.getReceiveWay() == null) {
			return false;
		}
		return true;
	}

	@Override
	public List<Coupon> getCouponListSearch(Map<String, Object> param)
			throws Exception {
		List<Coupon> couponList = couponMapper.selectList(param);
		String condition = (String) param.get("condition");
		List<Coupon> result = new ArrayList<Coupon>();
		for (Coupon c : couponList) {
			if ((StringUtils.isNotEmpty(c.getFishSiteName()) && c
					.getFishSiteName().contains(condition))
					|| (StringUtils.isNotEmpty(c.getPhoneNum()) && c
							.getPhoneNum().contains(condition))) {
				result.add(c);
			}
		}
		return result;
	}

}
