/**
 * 
 */
package cn.heipiao.api.mapper;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.ibatis.annotations.Param;

import cn.heipiao.api.pojo.ShopTradeOrders;

/**
 * @author wzw
 * @date 2016年6月29日
 * @version 1.0
 */
public interface ShopTradeOrdersMapper {

	/**
	 * @param pojo
	 */
	void insertPojo(ShopTradeOrders pojo);

	/**
	 * @param orderId
	 * @return
	 */
	ShopTradeOrders selectByOrderId(@Param("orderId") String orderId);
	
	/**
	 * @param orderId
	 * @param status
	 */
	void updateStatus(@Param("orderId")String orderId, @Param("status")Integer status);
	
	/**
	 * @param pojo
	 */
	void updatePart(ShopTradeOrders pojo);
	

	/**
	 * 加锁
	 * @param out_trade_no
	 * @return
	 */
	ShopTradeOrders selectAsLockByOrderId(String orderId);
	

	/**
	 * 线程调用
	 * @param map
	 * @return
	 */
	List<String> selectTimeoutOrders(Map<String, Object> map);

	/**
	 * @param shopId
	 * @return
	 */
	List<ShopTradeOrders> selectRecentOrders(Long shopId);

	
	List<ShopTradeOrders> selectOrdersByShopId(Map<String,Object> map);
	
	/**
	 * 当前月的用户支付总数
	 * @param shopId
	 * @param time
	 * @return
	 */
	int selectCountCurrentMonthPayUid(@Param("shopId")Long shopId,@Param("time")Timestamp time);
	
	/**
	 * 上个月的支付总金额 
	 * @param shopId
	 * @param minTime
	 * @param maxTime
	 * @return
	 */
	Double selectCountMoneyByMonth(@Param("shopId")Long shopId,@Param("minTime")Timestamp minTime,@Param("maxTime")Timestamp maxTime);

	/**
	 * 当前月的收入金额
	 * @param shopId
	 * @param minTime
	 * @return
	 */
	Double selectCountCurrentMoney(@Param("shopId")Long shopId,@Param("curTime")Timestamp minTime);

	/**
	 * 获取已支付的用户
	 * @param shopId
	 * @return
	 */
	Set<Long> selectUidsByShopId(Long shopId);

	List<ShopTradeOrders> getTradeRecord(Map<String, Object> map);

	Integer getTradeRecordCount(Map<String, Object> map);

}
