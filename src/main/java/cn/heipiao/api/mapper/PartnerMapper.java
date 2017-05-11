package cn.heipiao.api.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.heipiao.api.pojo.Partner;
import cn.heipiao.api.pojo.Region;

/**
 * @author z
 * @version 1.0
 * @description partnermapper
 * @date 2016年6月3日
 */
public interface PartnerMapper {
	
	public List<Integer> getPartnerUIDs();
	/**
	 * 通过user id查询partner
	 * 
	 * @param user 的 id
	 * @return
	 * @throws Exception
	 */
	public Partner getPartnerById(Integer id) throws Exception;

	/**
	 *  查询合伙人包括已解约的
	 * @return
	 */
	Partner selectPartnerByUid(Long uid);
	
	/**
	 * 获取合伙人包括已删除的
	 * @param partnerId
	 * @return
	 */
	Partner getByPartnerId(Integer partnerId) ;
	
	/**
	 * 通过给定条件查询合伙人 
	 * @param map
	 * @return
	 */
	List<Partner> selectListByOneCondition(Map<String,Object> map);
	
	/**
	 * 保存合伙人
	 * 
	 * @param partner
	 * @return
	 * @throws Exception
	 */
	public int savePartner(Partner partner) throws Exception;

	/**
	 * 更新partner
	 * 
	 * @param partner
	 * @return
	 * @throws Exception
	 */
	public int updatePartner(Partner partner) throws Exception;
	
	/**
	 * 更新合伙人的发券金额和数量
	 * @param partner
	 */
	public void updateCouponFeeAndSum(Partner partner ) ;
	
	/**
	 * 更新合伙人提现日期
	 * @param partner
	 */
	public void updateWithdrawDate(Partner partner ) ;
	
	/**
	 * @说明 获取合伙人所在的省份
	 * @return
	 */
	List<Region> getPartnerRegion();

	/**
	 * 获取合伙人数据不包含个人资料
	 * 加锁
	 * @param partnerId
	 * @return
	 */
	Partner selectByPartnerIdAsLock(Integer partnerId);
	
	/**
	 * 获取合伙人数据不包含个人资料
	 * 加锁
	 * @param uid
	 * @return
	 */
	Partner selectByUidAsLock(Long uid);
	
	/**
	 * 获取合伙人数据不包含个人资料
	 * 
	 * @param partnerId
	 * @return
	 */
	Partner selectByPartnerId(Integer partnerId);
	
	/**
	 * 更新合伙人发券和已发券的数量
	 * @param partner
	 */
	void updateCouponCount(Partner partner);

	/**
	 * 获取合伙人数据不包含个人资料
	 * 不包括已解约的
	 * @param uid
	 * @return
	 */
	public Partner selectByUid(Long uid);

	/**
	 * @param partnerId
	 * @param b
	 */
	public void updateIsExists(@Param("partnerId")Integer partnerId, @Param("isExists")Boolean isExists);
}
