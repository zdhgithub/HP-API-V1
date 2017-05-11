/**
 * 
 */
package cn.heipiao.api.mapper;

import java.util.List;
import java.util.Map;

import cn.heipiao.api.pojo.AccountBill;
import cn.heipiao.api.pojo.AccountExt;
import cn.heipiao.api.pojo.AccountExtSite;
import cn.heipiao.api.pojo.AccountRecord;

/**
 * @author wzw
 * @date 2016年10月17日
 * @version 1.0
 */
public interface AccountBillMapper {

	List<AccountBill> selectByUid(Map<String,Object> map);
	
//	List<AccountBill> selectByOrderId(String orderId);
	
	void insertPojo(AccountBill pojo);
	
	/**
	 * CP-钓场财务交易记录
	 */
	List<AccountBill> selectRecordBySite(Map<String,Object> map);
	/**
	 * cp 钓场财务列表
	 */
	List<AccountExtSite> selectListExt(Map<String, Object> map);
	/**cp 用户财务列表*/
	List<AccountExt> selectList(Map<String, Object> param);
	/** 用户财务交易记录 */
	List<AccountBill> selectRecordByUid(Map<String, Object> map);
}
