/**
 * 
 */
package cn.heipiao.api.service;

import java.util.List;
import java.util.Map;

import cn.heipiao.api.pojo.Account;
import cn.heipiao.api.pojo.AccountExt;
import cn.heipiao.api.pojo.AccountExtSite;
import cn.heipiao.api.pojo.AccountRecord;
import cn.heipiao.api.pojo.BindAccountList;
import cn.heipiao.api.pojo.GoldCoinOrders;
import cn.heipiao.api.pojo.PartnerWithdrawOrders;
import cn.heipiao.api.pojo.Refund;
import cn.heipiao.api.pojo.ShopAccount;
import cn.heipiao.api.pojo.ShopFinance;
import cn.heipiao.api.pojo.ShopWithdrawOrders;
import cn.heipiao.api.pojo.UserGoldCoin;
import cn.heipiao.api.pojo.WithdrawOrders;

/**
 * @author wzw
 * @date 2016年6月30日
 * @version 1.0
 */
public interface AccountService {

	/**
	 * @param map
	 * @return
	 */
	List<AccountRecord> selectAccountRecordByUid(Map<String, Object> map);

	/**
	 * 
	 * @param uid
	 * @return
	 */
	int counts(Integer uid);

	/**
	 * 查询用户财务列表
	 * 
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public List<AccountExt> selectAccountExtForCp(Map<String, Object> param)
			throws Exception;

	/**
	 * 统计用户财务列表数量
	 * 
	 * @return
	 * @throws Exception
	 */
	public int countAccountExts() throws Exception;

	/**
	 * 查询钓场财务列表
	 * 
	 * @param param
	 * @return
	 */
	public List<AccountExtSite> selectAccountExtSiteForCp(
			Map<String, Object> param) throws Exception;

	/**
	 * 统计钓场财务列表数量
	 * 
	 * @return
	 * @throws Exception
	 */
	public int countAccountExtSites(Integer regionId) throws Exception;

	/**
	 * 查询用户账户余额
	 * 
	 * @return
	 * @throws Exception
	 */
	public Account getAccountByUid(Long userId) throws Exception;

	/**
	 * @param bal
	 * @return
	 */
	int bindAccount(BindAccountList bal);

	/**
	 * @param uid
	 * @param withdrawFee
	 * @param wo
	 * @return
	 * @throws Exception
	 */
	int accountWithdrawApply(WithdrawOrders wo) throws Exception;

	/**
	 * @param wo
	 * @throws Exception
	 */
	int accountWithdraw(WithdrawOrders wo,boolean ...bs) throws Exception;

	/**
	 * @param uid
	 * @return
	 */
	List<BindAccountList> bindAccountList(Long uid);

	/**
	 * @param bal
	 * @return
	 */
	int deleteBindAccount(BindAccountList bal);

	/**
	 * @param map
	 * @return
	 */
	List<WithdrawOrders> withdrawOrderList(Map<String, Object> map);

	/**
	 * @param uid
	 * @return
	 */
	UserGoldCoin getGoldCoin(Long uid);

	/**
	 * 添加漂币
	 * 
	 * @param gco
	 */
	void addGoldCoin(GoldCoinOrders gco);

	/**
	 * @param r
	 */
	void addGoldCoin(Refund r);

	/**
	 *  平台添加收益漂币
	 * @param uid
	 * @param amount
	 */
	void addGoldCoin(Long uid, Integer amount);

	/**
	 *  店铺添加金额
	 * @param uid
	 * @param amount
	 * @return 
	 */
	ShopAccount addShopAccountBalance(Long uid,Double balance);
	
	/**
	 * 查询钓场的交易记录
	 * 
	 * @param params
	 * @return
	 * @throws Exception
	 */
	List<AccountRecord> getRecordBySite(Map<String, Object> params)
			throws Exception;

	/**
	 * @param bal
	 * @return
	 */
	int partnerDeleteBindAccount(BindAccountList bal);

	/**
	 * @param bal
	 * @return
	 */
	int partnerBindAccount(BindAccountList bal);

	/**
	 * @param pwo
	 * @param ...bs 
	 * @return
	 * @throws Exception 
	 * 
	 */
	int partnerAccountWithdraw(PartnerWithdrawOrders pwo, boolean ...bs) throws Exception;

	/**
	 * @param pwo
	 * @return
	 */
	int partnerAccountWithdrawApply(PartnerWithdrawOrders pwo);

	/**
	 * @param map
	 * @return
	 */
	List<PartnerWithdrawOrders> partnerWithdrawOrderList(Map<String, Object> map);

	/**
	 * @param wo
	 * @return
	 */
	int shopAccountWithdrawApply(ShopWithdrawOrders wo);

	/**
	 * @param wo
	 * @return
	 * @throws Exception 
	 */
	int shopAccountWithdraw(ShopWithdrawOrders wo,boolean ...bs) throws Exception;

	/**
	 * @param map
	 * @return
	 */
	List<ShopWithdrawOrders> shopWithdrawOrderList(Map<String, Object> map);

	/**
	 * @param bal
	 * @param shopId 
	 * @return
	 */
	int shopDeleteBindAccount(BindAccountList bal, Long shopId);

	/**
	 * @param bal
	 * @return
	 */
	int shopBindAccount(BindAccountList bal,Long shopId);

	/**
	 * 添加店铺老板财务账号
	 * @param uid
	 * @param shopId
	 */
	void addShopAccount(Long uid, Long shopId);

	/**
	 * @param uid
	 * @param shopId
	 * @return
	 */
	ShopAccount getShopAccountUnique(Long uid, Long shopId);

	/**
	 * @param uid
	 * @param goldCoinMoney 充值漂币
	 * @param earningsGoldCoinMoney 收益漂币
	 */
	void addGoldCoin(Long uid, Integer goldCoinMoney, Integer earningsGoldCoinMoney);

	/**
	 * @param uid
	 * @param shopId
	 * @return
	 */
	ShopAccount getPayCodeShopAccountUnique(Long uid, Long shopId);

	/**
	 * @param payCode
	 * @return
	 */
	ShopAccount getShopByPayCode(String payCode);

	List<ShopFinance> shopAccountList(Map<String, Object> map);

	Integer shopAccountListCount(Map<String, Object> map);

}
