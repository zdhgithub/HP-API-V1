package cn.heipiao.api.mapper;

import cn.heipiao.api.pojo.PartnerSiteRewardReview;

public interface PartnerSiteRewardReviewMapper {
	/**
	 * 通过siteId查询
	 */
	PartnerSiteRewardReview selectBysiteId(Integer siteId);
	/**
	 * 添加记录
	 */
	int insert(PartnerSiteRewardReview partnerSiteRewardReview);
	/**
	 * 更新 
	 * @param siteId
	 */
	int updateBysiteId(PartnerSiteRewardReview partnerSiteRewardReview);
	/**
	 * 删除
	 */
	int deleteBysiteId(Integer siteId);
}
