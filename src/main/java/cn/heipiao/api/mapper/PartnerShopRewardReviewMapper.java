package cn.heipiao.api.mapper;

import cn.heipiao.api.pojo.PartnerShopRewardReview;

public interface PartnerShopRewardReviewMapper {
	/**
	 * 通过shopId查询
	 */
	PartnerShopRewardReview selectByShopId(Long shopId);
	/**
	 * 添加记录
	 */
	int insert(PartnerShopRewardReview partnerShopRewardReview);
	/**
	 * 更新 
	 * @param shopId
	 */
	int updateByShopId(PartnerShopRewardReview partnerShopRewardReview);
	/**
	 * 删除
	 */
	int deleteByShopId(Long shopId);
}
