/**
 * 
 */
package cn.heipiao.api.mapper;

import cn.heipiao.api.pojo.AliRefundNotify;

/**
 * @author wzw
 * @date 2016年7月21日
 * @version 1.0
 */
public interface AliRefundNotifyMapper {

	AliRefundNotify selectAliRefundNotifyAsLockByBatchNo(String out_trade_no);
	
	AliRefundNotify selectAliRefundNotifyByBatchNo(String out_trade_no);
	
	
	void insertPojo(AliRefundNotify pojo);
	
	void updatePojo(AliRefundNotify pojo);
	
}
