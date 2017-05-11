/**
 * 
 */
package cn.heipiao.api.mapper;

import java.util.List;

import cn.heipiao.api.pojo.WxPayNotify;

/**
 * @author wzw
 * @date 2016年7月21日
 * @version 1.0
 */
public interface WxPayNotifyMapper {

	WxPayNotify selectWxPayNotifyByOutTradeNo(String out_trade_no);
	
	WxPayNotify selectWxPayNotifyAsLockByOutTradeNo(String out_trade_no);
	
	void insertPojo(WxPayNotify pojo);
	
	void updatePojo(WxPayNotify pojo);

	/**
	 * @return
	 */
	List<WxPayNotify> selectAll();
	
}
