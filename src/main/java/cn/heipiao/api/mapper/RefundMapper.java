/**
 * 
 */
package cn.heipiao.api.mapper;

import java.util.List;
import java.util.Map;

import cn.heipiao.api.pojo.Refund;

/**
 * @author wzw
 * @date 2016年7月19日
 * @version 1.0
 */
public interface RefundMapper {

	/**
	 * 
	 * @param pojo
	 */
	void insertPojo(Refund pojo);


	/**
	 * @param refundNo
	 * @return
	 */
	Refund selectByRefundNo(String refundNo);
	
	/**
	 * 
	 * @param refundNo
	 * @return
	 */
	Refund selectByRefundNoAsLock(String refundNo);

	/**
	 * @param r
	 */
	void updateStatus(Refund r);
	
	/**
	 * 更新部分字段
	 */
	void updatePart(Refund r);


	/**
	 * 线程调用
	 * @param map
	 * @return
	 */
	List<String> selectByStatus(Map<String, Object> map);
	
}
