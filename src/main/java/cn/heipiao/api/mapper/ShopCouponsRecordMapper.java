package cn.heipiao.api.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.heipiao.api.pojo.ShopCouponsRecord;

public interface ShopCouponsRecordMapper {

	boolean isGet(@Param("id")Long id,@Param("uid")Long uid);
	
	
	void insertBatch(List<ShopCouponsRecord> pojo);
	
	void insertPojo(ShopCouponsRecord pojo);



	/**
	 * 获取关注店铺个人品牌的用户
	 * @param uid
	 * @return
	 */
	List<ShopCouponsRecord> selectFocusByUid(Long uid);


	/**
	 * 获取已消费的用户
	 * @param shopId
	 * @return
	 */
	List<ShopCouponsRecord> selectConsumeUserByShopId(Long shopId);


	/**
	 * 获取已关注店铺的用户
	 * @param shopId
	 * @return
	 */
	List<ShopCouponsRecord> selectFocusShopByShopId(Long shopId);
	
	
	List<ShopCouponsRecord> selectList(Map<String, Object> map);


	/**
	 * @param cid
	 * @return
	 */
	ShopCouponsRecord selectByCid(Long cid);
	
}
