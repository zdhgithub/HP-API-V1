/**
 * 
 */
package cn.heipiao.api.mapper;

import org.apache.ibatis.annotations.Param;

import cn.heipiao.api.pojo.Account;

/**
 * @author wzw
 * @date 2016年6月29日
 * @version 1.0
 */
public interface AccountMapper {
	
	
	Account selectByUid(@Param("uid")Long uid);
	
	
	Account selectByUidAsLock(@Param("uid")Long uid);
	
	void insertPojo(Account pojo);
	
	void updatePayPwd(@Param("uid")Long uid,String payPwd);
	
	
	void updateBalance(@Param("uid")Long uid,@Param("balance") Double balance);


	/**
	 * 除支付密码其它字段都更新
	 * 
	 * @param account
	 */
	void updatePojo(Account account);
	
	void updateRuleId(Long uid, Integer withdrawRuleId);
}
