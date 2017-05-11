package cn.heipiao.api.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.heipiao.api.pojo.DepositFish;
import cn.heipiao.api.pojo.DepositFishExtend;

/**
 * @author z
 * @version 1.0
 * @description 存鱼查询mapper
 * @date 2016年6月29日
 */
public interface DepositFishMapper extends BaseMapper<DepositFish> {
	public Double countDepositFishTotalByUserID(Integer uid);
	
	/**
	 * 查询钓场的存鱼总额
	 * 
	 * @param siteId
	 * @return
	 */
	public Double countDepositFishBySite(Integer siteId);

	/**
	 * 查询钓友端的存鱼列表
	 * 
	 * @param param
	 * @return
	 */
	public List<DepositFishExtend> selectExList(Map<String, Object> param);

	/**
	 * 加锁获取
	 * 
	 * @param uid
	 * @param integer
	 * @return
	 */
	public DepositFish selectByUidAndFishSiteIdAsLock(
			@Param("uid") Long uid, @Param("fishSiteId") Integer fishSiteId);

	/**
	 * 
	 * @param uid
	 * @param integer
	 * @return
	 */
	public DepositFish selectByUidAndFishSiteId(@Param("uid") Integer uid,
			@Param("fishSiteId") Integer fishSiteId);

	/**
	 * @param uid
	 * @param fishSiteId
	 */
	public void updateByUidAndFishSiteId(@Param("uid") Long uid,
			@Param("fishSiteId") Integer fishSiteId,
			@Param("balance") Double balance);

	/**
	 * 查询存鱼排行
	 * 
	 * @param param
	 */
	public List<DepositFish> selectListNew(Map<String, Object> param);

}
