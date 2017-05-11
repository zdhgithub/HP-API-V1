/**
 * 
 */
package cn.heipiao.api.service;

import java.util.List;
import java.util.Map;

import cn.heipiao.api.pojo.AccountBill;
import cn.heipiao.api.pojo.AccountExt;
import cn.heipiao.api.pojo.AccountExtSite;

/**
 * @author wzw
 * @date 2016年10月17日
 * @version 1.0
 */
public interface AccountBillService {

	List<AccountBill> getByUid(Map<String,Object> map);
	
	List<AccountBill> getByOrderId(String orderId);
	
	/**
	 * 具体查看 AccountBill 类
	 * 
	 * 此方法为总账单记录，所有与交易有关联的记录都会记录到此，
	 * 
	 * 分别以type字段为区分
	 * 
	 * @param uid
	 * @param orderId
	 * @param inOut 1:进账，2：出账
	 * @param type
	 * @param subType 1:第三方，2:漂币，3:存鱼
	 * @param tradeFee
	 * @param desc
	 */
	void addPojo(Long uid, String orderId, Integer inOut, Integer type,Integer subType,Double tradeFee, String desc);
	
	/**
	 * CP-钓场财务交易记录
	 */
	List<AccountBill> getRecordBySite(Map<String,Object> map);
	
	/**
	 * cp-钓场及其财务列表
	 */
	List<AccountExtSite> selectListExt(Map<String,Object> map);
	/**cp 用户财务列表*/
	List<AccountExt> selectList(Map<String, Object> param);
	/** 用户财务交易记录 */
	List<AccountBill> selectRecordByUid(Map<String, Object> map);
}
