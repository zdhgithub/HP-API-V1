package cn.heipiao.api.mapper;

import java.util.List;
import java.util.Map;

import cn.heipiao.api.pojo.RefundActivity;

/**
 * @author wzw
 * @date 2017年3月11日
 */
public interface RefundActivityMapper {

	/**
	 * 
	 * @param pojo
	 */
	void insertPojo(RefundActivity pojo);


	/**
	 * @param refundNo
	 * @return
	 */
	RefundActivity selectByRefundNo(String refundNo);
	
	/**
	 * 
	 * @param refundNo
	 * @return
	 */
	RefundActivity selectByRefundNoAsLock(String refundNo);

	/**
	 * @param r
	 */
	int updateStatus(RefundActivity ra);
	
	
	int updatePart(RefundActivity ra);

	/**
	 * 线程调用
	 * @param map
	 * @return
	 */
	List<String> selectByStatus(Map<String, Object> map);
	
}
