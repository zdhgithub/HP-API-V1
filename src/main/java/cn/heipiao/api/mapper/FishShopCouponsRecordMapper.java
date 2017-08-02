/**
 * 
 */
package cn.heipiao.api.mapper;

import cn.heipiao.api.pojo.FishShopCouponsRecord;

/**
 * @author wzw
 * @date 2016年9月22日
 * @version 1.0
 */
public interface FishShopCouponsRecordMapper {

	
	FishShopCouponsRecord selectByCid(Long cid);
	
	
	void insertPojo(FishShopCouponsRecord pojo);
	
}
