/**
 * 
 */
package cn.heipiao.api.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.heipiao.api.pojo.ShopAccount;
import cn.heipiao.api.pojo.ShopFinance;

/**
 * @author wzw
 * @date 2016年10月12日
 * @version 1.0
 */
public interface ShopAccountMapper {

	
	ShopAccount selectUniqueAsLock(@Param("shopId")Long shopId);
	
	void insertPojo(ShopAccount pojo);
	
	
	void updateBalance(@Param("shopId")Long shopId,@Param("balance") Double balance);


	void updatePojo(ShopAccount account);
	
	
	void updateRuleId(@Param("shopId")Long shopId, @Param("withdrawRuleId")Integer withdrawRuleId);


	/**
	 * @param uid
	 * @param shopId
	 * @return
	 */
	ShopAccount selectUnique(@Param("shopId")Long shopId);

	/**
	 * @param sam
	 */
	void updateBalance(ShopAccount sam);

	/**
	 * @param uid
	 * @return
	 */
	ShopAccount selectPayCode(@Param("shopId") Long shopId);

	/**
	 * @param payCode
	 * @return
	 */
	ShopAccount getShopByPayCode(String payCode);

	List<ShopFinance> shopAccountList(Map<String, Object> map);

	Integer shopAccountListCount(Map<String, Object> map);
	
}
