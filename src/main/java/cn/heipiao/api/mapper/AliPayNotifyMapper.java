/**
 * 
 */
package cn.heipiao.api.mapper;

import java.util.List;

import cn.heipiao.api.pojo.AliPayNotify;

/**
 * @author wzw
 * @date 2016年7月21日
 * @version 1.0
 */
public interface AliPayNotifyMapper {

	AliPayNotify selectAliPayNotifyAsLockByOutTradeNo(String out_trade_no);
	
	AliPayNotify selectAliPayNotifyByOutTradeNo(String out_trade_no);
	
	void insertPojo(AliPayNotify pojo);

	void updatePojo(AliPayNotify pojo);

	/**
	 * @return
	 */
	List<AliPayNotify> selectAll();

}
