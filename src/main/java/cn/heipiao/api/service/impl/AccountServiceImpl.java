/**
 * 
 */
package cn.heipiao.api.service.impl;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.heipiao.api.constant.ApiConstant;
import cn.heipiao.api.mapper.AccountMapper;
import cn.heipiao.api.mapper.AccountRecordMapper;
import cn.heipiao.api.mapper.BindAccountListMapper;
import cn.heipiao.api.mapper.BindAccountRelevanceMapper;
import cn.heipiao.api.mapper.EmpMapper;
import cn.heipiao.api.mapper.FishShopMapper;
import cn.heipiao.api.mapper.FishSiteMapper;
import cn.heipiao.api.mapper.ModuleMapper;
import cn.heipiao.api.mapper.PartnerMapper;
import cn.heipiao.api.mapper.PartnerWithdrawOrdersMapper;
import cn.heipiao.api.mapper.PlatformAccountConstraintMapper;
import cn.heipiao.api.mapper.ShopAccountMapper;
import cn.heipiao.api.mapper.ShopEmpMapper;
import cn.heipiao.api.mapper.ShopWithdrawOrdersMapper;
import cn.heipiao.api.mapper.UserGoldCoinMapper;
import cn.heipiao.api.mapper.UserMapper;
import cn.heipiao.api.mapper.WithdrawConfigMapper;
import cn.heipiao.api.mapper.WithdrawOrdersMapper;
import cn.heipiao.api.pay.PayConfig;
import cn.heipiao.api.pay.PayParams;
import cn.heipiao.api.pay.PayService;
import cn.heipiao.api.pojo.Account;
import cn.heipiao.api.pojo.AccountExt;
import cn.heipiao.api.pojo.AccountExtSite;
import cn.heipiao.api.pojo.AccountRecord;
import cn.heipiao.api.pojo.BindAccountList;
import cn.heipiao.api.pojo.Emp;
import cn.heipiao.api.pojo.FishShop;
import cn.heipiao.api.pojo.FishSite;
import cn.heipiao.api.pojo.GoldCoinOrders;
import cn.heipiao.api.pojo.PartnerWithdrawOrders;
import cn.heipiao.api.pojo.PlatformAccountConstraint;
import cn.heipiao.api.pojo.Refund;
import cn.heipiao.api.pojo.ShopAccount;
import cn.heipiao.api.pojo.ShopEmp;
import cn.heipiao.api.pojo.ShopFinance;
import cn.heipiao.api.pojo.ShopWithdrawOrders;
import cn.heipiao.api.pojo.User;
import cn.heipiao.api.pojo.UserGoldCoin;
import cn.heipiao.api.pojo.WithdrawConfig;
import cn.heipiao.api.pojo.WithdrawOrders;
import cn.heipiao.api.resources.Status;
import cn.heipiao.api.service.AccountBillService;
import cn.heipiao.api.service.AccountService;
import cn.heipiao.api.service.DepositFishService;
import cn.heipiao.api.service.SystemMsgService;
import cn.heipiao.api.service.UserOpService;
import cn.heipiao.api.utils.ArithUtil;
import cn.heipiao.api.utils.ExDateUtils;
import cn.heipiao.api.utils.ExDigestUtils;
import cn.heipiao.api.utils.RandomNumberUtils;

/**
 * @author wzw
 * @date 2016年6月30日
 * @version 1.0
 */
@Service
@Transactional(readOnly = true)
public class AccountServiceImpl implements AccountService {

	@Resource
	private AccountMapper accountMapper;
	
	@Resource
	private ShopAccountMapper shopAccountMapper;

	@Resource
	private AccountRecordMapper accountRecordMapper;

	@Resource
	private DepositFishService depositFishService;
	
	@Resource
	private BindAccountListMapper bindAccountListMapper;
	
	@Resource
	private BindAccountRelevanceMapper bindAccountRelevanceMapper;
	
	@Resource
	private SystemRecordGeneratorServiceImpl srgs;
	
	@Resource
	private UserMapper userMapper;
	
	@Resource
	private UserOpService userOpService;
	
	@Resource
	private UserGoldCoinMapper userGoldCoinMapper;
	
	@Resource(name = "PayService")
	private PayService pay;
	
	@Resource
	private PayConfig payConfig;

	@Resource
	private WithdrawOrdersMapper withdrawOrdersMapper;
	
	@Resource
	private ShopWithdrawOrdersMapper shopWithdrawOrdersMapper;
	
	@Resource
	private PlatformAccountConstraintMapper pac;
	
	@Resource
	private FishSiteMapper fishSiteMapper;
	
	@Resource
	private EmpMapper empMapper;
	
	@Resource
	private ShopEmpMapper<ShopEmp> shopEmpMapper;
	
	@Resource
	private ModuleMapper moduleMapper;
	
	@Resource
	private PartnerMapper partnerMapper;
	
	@Resource
	private PartnerWithdrawOrdersMapper partnerWithdrawOrdersMapper;
	
	@Resource
	private WithdrawConfigMapper withdrawConfigMapper;
	
	@Resource
	private FishShopMapper fishShopMapper;
	
	@Resource
	private AccountBillService accountBillService;
	
	
	@Resource
	private SystemMsgService systemMsgService;
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.heipiao.api.service.AccountService#selectAccountRecordByUid()
	 */
	@Override
	public List<AccountRecord> selectAccountRecordByUid(Map<String, Object> map) {
		return accountRecordMapper.selectByUid(map);
	}

	@Override
	public int counts(Integer uid) {
		int total = accountRecordMapper.countRecords(uid);
		return total;
	}

	@Override
	public List<AccountExt> selectAccountExtForCp(Map<String, Object> param)
			throws Exception {
		List<AccountExt> list = accountRecordMapper.selectList(param);
		return list;
	}

	@Override
	public int countAccountExts() throws Exception {
		int total = accountRecordMapper.countAccountExts();
		return total;
	}

	@Override
	public List<AccountExtSite> selectAccountExtSiteForCp(
			Map<String, Object> param) throws Exception {

		List<AccountExtSite> list = accountRecordMapper.selectListExt(param);
		return list;
	}

	@Override
	public int countAccountExtSites(Integer regionId) throws Exception {
		int total = accountRecordMapper.countAccountExtSites(regionId);
		return total;
	}

	@Override
	public Account getAccountByUid(Long userId) throws Exception {
		Account account = accountMapper.selectByUid(userId);
		// 不查询支付密码等敏感信息
		if (account != null) {
			account.setPayPwd(null);
		}
		return account;
	}

	/* (non-Javadoc)
	 * @see cn.heipiao.api.service.AccountService#bindAccount(cn.heipiao.api.pojo.BindAccountList)
	 */
	@Override
	@Transactional(readOnly = false,rollbackFor = {Exception.class})
	public int bindAccount(BindAccountList bal) {
//		Emp emp = empMapper.selectById(bal.getUid());
//		if(emp == null || !emp.getEmpStatus().equals("2")){
//			return Status.emp_not_permission;
//		}
		int rs = verifySiteEmp(bal.getUid());
		if(rs != Status.success){
			return rs;
		}
		return bindAccount0(bal);
	}

	/**
	 * @param bal
	 * @return
	 */
	private int bindAccount0(BindAccountList bal) {
		BindAccountList pojo = bindAccountListMapper.selectByAccountNumAndPlatform(bal.getBindAccountNum(), bal.getPlatform());
		if(pojo == null){
			bindAccountListMapper.insertPojo(bal);
		}
		BindAccountList br = bindAccountRelevanceMapper.selectUniqueAccount(bal.getUid(), bal.getPlatform(), bal.getBindAccountNum());
		if(br != null){
			return Status.account_exists;
		}
		bindAccountRelevanceMapper.insertPojo(bal);
		return Status.success;
	}

	/* (non-Javadoc)
	 * 钓场主提现
	 * @see cn.heipiao.api.service.AccountService#accountWithdraw(java.lang.Long, java.lang.Integer)
	 */
	@Override
	@Transactional(readOnly = false,rollbackFor = {Exception.class})
	public int accountWithdrawApply(WithdrawOrders wo) throws Exception {
		Timestamp t = new Timestamp(ExDateUtils.getCalendar().getTimeInMillis());
		
		Emp emp = empMapper.selectById(wo.getUid());
		
		//判断员工是否有财务权限
		int re = empPerssion(emp);
		if(re != 0){
			return re;
		}
		
		//钓场是否认证
		FishSite fs = fishSiteMapper.selectById(emp.getFishSiteId());
		if(fs == null || fs.getUid() == null){
			return Status.fish_site_not_auth;
		}
		
		//设置为钓场主uid
		wo.setUid(fs.getUid());
		
		User user = userMapper.selectById(wo.getUid());
		BindAccountList bal = bindAccountListMapper.selectUniqueAccountAsLock(wo.getUid(),wo.getPlatform(),wo.getTradeAccount());
		Account account = accountMapper.selectByUidAsLock(wo.getUid());
		if(user == null || bal == null || account == null){
			return Status.account_not_exist;
		}
		
		//每天提现一次验证
		if(ExDateUtils.isCurrentDay(account.getWithdrawTime())){
			return Status.withdraw_day_over;
		}
		
		
		//判断是否实名认证
		if(StringUtils.isBlank(user.getRealname())){
			return Status.user_not_realname_auth;
		}
		
		//操作频繁控制
		if(t.getTime() - bal.getTradeUpdateTime().getTime() < 15 * 1000){
			return Status.account_freq_limit;
		}

		//判断余额是否大于提现金额
		if(ArithUtil.compareTo(account.getBalance(),ArithUtil.div(wo.getTradeFee().doubleValue(), 100, 2)) == -1){
			return Status.user_balance_insufficient;
		}

		WithdrawConfig rc = withdrawConfigMapper.selectByRuleIdAndDelayForDay(1,account.getWithdrawRuleId(),wo.getDelayForDay());
		if(rc == null){
			return Status.value_is_null_or_error;
		}
		wo.setDelayForDay(rc.getDelayForDay());
		wo.setWithdrawRate(rc.getWithdrawRate());
		
		//手续费计算
		wo.setPoundageFee((int)Math.ceil(ArithUtil.mul(wo.getTradeFee(), rc.getWithdrawRate())));
		//实际金额
//		wo.setActualFee(wo.getTradeFee() - wo.getPoundageFee());
		//扣除手续费方式1.如果余额中够手续费从余额中扣除，2.如果余额中不够扣除余额+实际提现金额
		if(wo.getTradeFee() + wo.getPoundageFee() <= (int)ArithUtil.mul(account.getBalance(),100)){
			wo.setActualFee(wo.getTradeFee());
		}else{
			wo.setActualFee(wo.getTradeFee() - (wo.getTradeFee() + wo.getPoundageFee() - ((int)ArithUtil.mul(account.getBalance(),100))));
		}
		
		//重新设置交易金额
		wo.setTradeFee(wo.getActualFee() + wo.getPoundageFee());
		
		//获取平台信息
		PlatformAccountConstraint p = pac.selectUniqueAccount(bal.getPlatform());
		if(p == null || p.getUseTime().getTime() > t.getTime()){
			return Status.account_not_support;
		}
		
		//单笔交易上下限，0不限制
		if(p.getSingleMaxFee() > 0 && p.getSingleMaxFee() < wo.getTradeFee() || p.getSingleMinFee() > 0 && p.getSingleMinFee() > wo.getTradeFee())
			return Status.withdraw_fee_range_error;
		
		//判断上次提现日期
		if(ExDateUtils.isCurrentDay(bal.getTradeUpdateTime())){
			//当天单笔交易的次数,0不限制
			if(p.getTradeMaxAmount() > 0 && bal.getTradeAmount() >= p.getTradeMaxAmount())
				return Status.withdraw_trade_max;
			//当天超过日限额，0不限制
			if(p.getDaySumLimited() > 0 && p.getDaySumLimited() < wo.getTradeFee() + bal.getCurrentSumFee())
				return Status.withdraw_fee_top_limit;
			
			bal.setTradeAmount(p.getTradeMaxAmount() > 0 ? bal.getTradeAmount() + 1 : 0);
			bal.setCurrentSumFee(bal.getCurrentSumFee() + wo.getActualFee());
		} else {
			bal.setTradeAmount(p.getTradeMaxAmount() > 0 ? 1 : 0);
			bal.setCurrentSumFee(wo.getActualFee());
		}
		
		//更新时间
		bal.setTradeUpdateTime(t);
		
		wo.setCreateTime(t);
		wo.setRealname(user.getRealname());
		wo.setStatus(0);
		wo.setTradeNo(ExDateUtils.getDateFormatToSecond() + RandomNumberUtils.getVerifyNum(6));
		//创建交易订单
		withdrawOrdersMapper.insertPojo(wo);

		//更新绑定账户信息
		bindAccountListMapper.updatePojo(bal);
		
		//更新账户余额
//		account.setBalance(ArithUtil.sub(account.getBalance(), wo.getTradeFee() / 100.0));
//		account.setBalance(ArithUtil.sub(account.getBalance(), ArithUtil.div(wo.getTradeFee(), 100,2)));
		account.setBalance(ArithUtil.sub(account.getBalance(), ArithUtil.div(wo.getActualFee() + wo.getPoundageFee(), 100,2)));
		account.setWithdrawTime(t);
		accountMapper.updatePojo(account);
		
		//生成交易记录
//		srgs.generateAccountDeRecord(account.getUid().intValue(), wo.getTradeNo(), ApiConstant.TradeType.WITHDRAWS
//				, ArithUtil.div(wo.getTradeFee().doubleValue(), 100, 2), "提现", "微信", 1);
		
		return Status.success;
	}

	/* (non-Javadoc)
	 * 钓场主付款
	 * @see cn.heipiao.api.service.AccountService#accountWithdraw(cn.heipiao.api.pojo.WithdrawOrders)
	 */
	@Override
	@Transactional(readOnly = false,rollbackFor = {Exception.class})
	public int accountWithdraw(WithdrawOrders wo,boolean ...bs) throws Exception {
		
		wo = withdrawOrdersMapper.selectByTradeNoAsLock(wo.getTradeNo());
		
		if(wo != null && (wo.getStatus().intValue() == 0 || wo.getStatus().intValue() == 1)
				&& (wo.getDelayForDay() == 0 || (wo.getCreateTime().getTime() + wo.getDelayForDay() * 24L * 60 * 60 * 1000) <= ExDateUtils.getCalendar().getTimeInMillis())){
			
			//是否间隔大于15s
			BindAccountList bal = bindAccountListMapper.selectUniqueAccount(wo.getUid(),wo.getPlatform(),wo.getTradeAccount());
			
			/**
			 * 2017.05.16，添加了对bal对象的null判断。
			 * 这里出现过问题：
			 * 提现后不明原因找不到当前记录，原因是提现时绑定的账号与现在表中的记录不一样
			 * 按理说在t+0的情况下，程序执行速度肯定会用户解绑后重新绑定要快得多，可就是出现了这个问题
			 */
			if (bal != null) {
				if(!isInterval(bal)){
					return Status.account_freq_limit;
				}
				//提现
				Map<String, String> m = pay.wechatEnterprisePay(wo.getTradeNo(), wo.getTradeAccount(), wo.getRealname(), wo.getActualFee(), "提现", 
						1);
					
				if(m != null && m.get("return_code").equalsIgnoreCase(PayParams.success)){
					String resultCode = m.get("result_code"); 
					if(resultCode.equalsIgnoreCase(PayParams.success)){
						Timestamp t = new Timestamp(ExDateUtils.getCalendar().getTimeInMillis());
						
						wo.setPayTime(t);
						wo.setPlatformTradeNo(m.get("payment_no"));
						wo.setStatus(2);
						withdrawOrdersMapper.updatePojo(wo);
						//生成交易记录
						//提现实际金额
						srgs.generateAccountDeRecord(wo.getUid().intValue(), wo.getTradeNo(), ApiConstant.TradeType.WITHDRAWS
								, ArithUtil.div(wo.getActualFee().doubleValue(), 100, 2), "提现", "微信", 1,null);
						//提现手续费金额
						srgs.generateAccountDeRecord(wo.getUid().intValue(), wo.getTradeNo(), ApiConstant.TradeType.POUNDAGE
								, ArithUtil.div(wo.getPoundageFee().doubleValue(), 100, 2), "提现手续费", "微信", 1,null);
						//添加账单
						accountBillService.addPojo(wo.getUid(), wo.getTradeNo(), 2, 5, 1,ArithUtil.div(wo.getActualFee().doubleValue(), 100, 2) , "提现金额");
						accountBillService.addPojo(wo.getUid(), wo.getTradeNo(), 2, 5, 1,ArithUtil.div(wo.getPoundageFee().doubleValue(), 100, 2) , "提现手续费");
						
						//消息
						systemMsgService.saveMsg("提现成功", null, "您提现的金额已到账，请您注意查收!", wo.getUid().intValue(), null);
						
						return Status.success;
					} else if(resultCode.equalsIgnoreCase(PayParams.fail)){
						String errCode = m.get("err_code");
						//平台金额不足
						if(errCode.equalsIgnoreCase("NOTENOUGH")){
							//状态为：1支付中   由清算线程处理
							wo.setStatus(1);
							withdrawOrdersMapper.updatePojo(wo);
							if(bs != null && bs.length > 0){
								bs[0] = false;
							}
							return Status.success;
						//姓名校验错误
						} else if (errCode.equalsIgnoreCase("NAME_MISMATCH")){
							wo.setStatus(3);
							wo.setDesc("真实姓名与当前绑定账户的姓名不匹配错误");
						} else {
							wo.setStatus(3);
							wo.setDesc("平台系统维护请您明天重试!,资金已返回到余额中");
						}
						wo.setPlatformDesc(errCode + "=" + m.get("err_code_des"));
					}
				}
			}
			withdrawOrdersMapper.updatePojo(wo);
			//付款失败退回账户
			payFail(wo);
		}
		return Status.account_pay_error;
	}

	/**
	 * @param wo
	 * @throws Exception 
	 */
	private void payFail(WithdrawOrders wo) throws Exception {
		BindAccountList bal = bindAccountListMapper.selectUniqueAccountAsLock(wo.getUid(),wo.getPlatform(),wo.getTradeAccount());
		Account account = accountMapper.selectByUidAsLock(wo.getUid());
		//更新账户余额
//		account.setBalance(ArithUtil.add(account.getBalance(), wo.getTradeFee() / 100.0));
		account.setBalance(ArithUtil.add(account.getBalance(), ArithUtil.div(wo.getActualFee() + wo.getPoundageFee(), 100,2)));
		//将时间设置为前一天
		account.setWithdrawTime(new Timestamp(ExDateUtils.getCalendar().getTimeInMillis() - (24L * 60 * 60 * 1000)));
		accountMapper.updatePojo(account);
		//更新绑定账户信息
		if(ExDateUtils.isCurrentDay(bal.getTradeUpdateTime())){
			bal.setCurrentSumFee(bal.getCurrentSumFee() - wo.getActualFee());
			bal.setTradeAmount(bal.getTradeAmount() > 0 ? bal.getTradeAmount() - 1 : 0);
		}else{
			bal.setCurrentSumFee(0);
			bal.setTradeAmount(0);
		}
		//更新时间
		bal.setTradeUpdateTime(new Timestamp(ExDateUtils.getCalendar().getTimeInMillis()));
		bindAccountListMapper.updatePojo(bal);
		//消息
		systemMsgService.saveMsg("提现失败", null, "很遗憾，您的操作失败，请核实您的账户是否实名认证哦!", wo.getUid().intValue(), null);
	}

	/* (non-Javadoc)
	 * @see cn.heipiao.api.service.AccountService#bindAccountList(java.lang.Long)
	 */
	@Override
	public List<BindAccountList> bindAccountList(Long uid) {
		return bindAccountListMapper.selectByUid(uid);
	}
	
	/**
	 * 验证钓场员工权限
	 * @param emp
	 * @return
	 */
	private int empPerssion(Emp emp){
		if(emp == null || emp.getEmpStatus().equals("0"))
			return Status.EMP_NOT_EXISTS;
		
		//判断是否有财务权限
		if(!moduleMapper.selectAllModuleIds(emp.getUserId()).contains("40")){
			return Status.emp_not_permission;
		}
		return Status.success;
	}
	
	/**
	 * 验证店铺员工权限
	 * @param emp
	 * @return
	 */
	private int shopEmpPerssion(ShopEmp emp){
		if(emp == null || emp.getStatus().intValue() == 0)
			return Status.EMP_NOT_EXISTS;
		
		//判断是否有财务权限
		if(!moduleMapper.selectShopModuleIds(emp.getUid(),emp.getShopId()).contains("401")){
			return Status.emp_not_permission;
		}
		return Status.success;
	}

	/* (non-Javadoc)
	 * @see cn.heipiao.api.service.AccountService#deleteBindAccount(cn.heipiao.api.pojo.BindAccountList)
	 */
	@Override
	@Transactional(readOnly = false,rollbackFor = {Exception.class})
	public int deleteBindAccount(BindAccountList bal) {
//		Emp emp = empMapper.selectById(bal.getUid());
//		if(emp == null || !emp.getEmpStatus().equals("2")){
//			return Status.emp_not_permission;
//		}
		int rs = verifySiteEmp(bal.getUid());
		if(rs != Status.success){
			return rs; 
		}
		return deleteBindAccount0(bal);
	}

	
	private int verifySiteEmp(Long uid){
		Emp emp = empMapper.selectById(uid);
		if(emp == null || !emp.getEmpStatus().equals("2")){
			return Status.emp_not_permission;
		}
		return Status.success;
	}
	
	/**
	 * @param bal
	 * @return
	 */
	private int deleteBindAccount0(BindAccountList bal) {
		bindAccountRelevanceMapper.deleteBindAccount(bal);
		return Status.success;
	}

	/* (non-Javadoc)
	 * @see cn.heipiao.api.service.AccountService#withdrawOrderList(java.util.Map)
	 */
	@Override
	public List<WithdrawOrders> withdrawOrderList(Map<String, Object> map) {
		return withdrawOrdersMapper.selectListByLimit(map);
	}

	/* (non-Javadoc)
	 * @see cn.heipiao.api.service.AccountService#getGoldCoin(java.lang.Long)
	 */
	@Override
	public UserGoldCoin getGoldCoin(Long uid) {
		return userGoldCoinMapper.selectByUid(uid);
	}

	/* (non-Javadoc)
	 * @see cn.heipiao.api.service.AccountService#addGoldCoin(cn.heipiao.api.pojo.GoldCoinOrders)
	 */
	@Override
	public void addGoldCoin(GoldCoinOrders gco) {
		UserGoldCoin ugc = userGoldCoinMapper.selectByUidAsLock(gco.getUid());
		//漂币的比例为  :  1分=1漂币
		int addAmount = gco.getOrdersMoney() * 100 + gco.getGivingGoldCoin();
		ugc.setGoldCoin(ugc.getGoldCoin() + addAmount);
		userGoldCoinMapper.updatePojo(ugc);
		//记录交易流水
		srgs.generateAccountInRecord(ugc.getUid().intValue(), gco.getOrderId(), ApiConstant.TradeType.RECHARGE,
				addAmount, "充值", gco.getTradePlatform() == 1 ? "微信" :  gco.getTradePlatform() == 2 ? "支付宝" : "其它", 1,null);
		
	}
	
	@Override
	public void addGoldCoin(Refund r) {
		UserGoldCoin ugc = userGoldCoinMapper.selectByUidAsLock(r.getUid());
		ugc.setGoldCoin(ugc.getGoldCoin() + r.getGoldCoinFee());
		ugc.setEarningsGoldCoin(ugc.getEarningsGoldCoin() + r.getEarningsGoldCoinFee());
		userGoldCoinMapper.updatePojo(ugc);
		srgs.generateAccountInRecord(r.getUid().intValue(), r.getOrderId(), ApiConstant.TradeType.TICKET_REFUND, r.getGoldCoinFee() + r.getEarningsGoldCoinFee(), "退漂币","" , 1,null);
	}

	/* (non-Javadoc)
	 * @see cn.heipiao.api.service.AccountService#addGoldCoin(java.lang.Long, java.lang.Integer)
	 * 添加收益漂币
	 */
	@Override
	@Transactional(readOnly=false,rollbackFor={Exception.class})
	public void addGoldCoin(Long uid, Integer amount) {
		UserGoldCoin ugc = userGoldCoinMapper.selectByUidAsLock(uid);
		ugc.setEarningsGoldCoin(ugc.getEarningsGoldCoin() + amount);
		userGoldCoinMapper.updateEarningsAndWithdrawDate(ugc);
		//记录流水
		srgs.generateAccountInRecord(uid.intValue(), null, ApiConstant.TradeType.RECHARGE, amount, "平台奖励漂币", "", 1, null);
	}

	@Override
	public List<AccountRecord> getRecordBySite(Map<String,Object> params) throws Exception {
		List<AccountRecord> list = accountRecordMapper.selectRecordBySite(params);
		return list;
	}

	/* (non-Javadoc)
	 * @see cn.heipiao.api.service.AccountService#partnerDeleteBindAccount(cn.heipiao.api.pojo.BindAccountList)
	 */
	@Override
	@Transactional(readOnly=false,rollbackFor={Exception.class})
	public int partnerDeleteBindAccount(BindAccountList bal) {
//		Partner p = partnerMapper.selectByUid(bal.getUid());
//		if(p == null){
//			return Status.partner_not_exists;
//		}
		
		boolean b = userOpService.isPartner(bal.getUid());
		if(!b){
			return Status.partner_not_exists;
		}
		return deleteBindAccount0(bal);
	}

	/* (non-Javadoc)
	 * @see cn.heipiao.api.service.AccountService#partnerBindAccount(cn.heipiao.api.pojo.BindAccountList)
	 */
	@Override
	@Transactional(readOnly=false,rollbackFor={Exception.class})
	public int partnerBindAccount(BindAccountList bal) {
//		Partner p = partnerMapper.selectByUid(bal.getUid());
//		if(p == null){
//			return Status.partner_not_exists;
//		}
		
		boolean b = userOpService.isPartner(bal.getUid());
		if(!b){
			return Status.partner_not_exists;
		}
		return bindAccount0(bal);
	}

	/* (non-Javadoc)
	 * @see cn.heipiao.api.service.AccountService#partnerAccountWithdraw(cn.heipiao.api.pojo.PartnerWithdrawOrders)
	 * 
	 * 合伙人付款
	 * 
	 */
	@Override
	@Transactional(readOnly=false,rollbackFor={Exception.class})
	public int partnerAccountWithdraw(PartnerWithdrawOrders pwo,boolean ...bs) throws Exception {
		pwo = partnerWithdrawOrdersMapper.selectByTradeNoAsLock(pwo.getTradeNo());
		
		if(pwo != null && (pwo.getStatus().intValue() == 0 || pwo.getStatus().intValue() == 1)){
			//提现
			Map<String, String> m = pay.wechatEnterprisePay(pwo.getTradeNo(), pwo.getTradeAccount(), pwo.getRealname(), pwo.getActualFee(), "提现", 
					2);
				
			if(m != null && m.get("return_code").equalsIgnoreCase(PayParams.success)){
				String resultCode = m.get("result_code"); 
				if(resultCode.equalsIgnoreCase(PayParams.success)){
					pwo.setPayTime(new Timestamp(ExDateUtils.getCalendar().getTimeInMillis()));
					pwo.setPlatformTradeNo(m.get("payment_no"));
					pwo.setStatus(2);
					partnerWithdrawOrdersMapper.updatePojo(pwo);
					//生成交易记录
					//提现实际金额
					srgs.generateAccountDeRecord(pwo.getUid().intValue(), pwo.getTradeNo(), ApiConstant.TradeType.WITHDRAWS
							, ArithUtil.div(pwo.getActualFee().doubleValue(), 100, 2), "提现", "微信", 1,null);
					//提现实际金额
					srgs.generateAccountDeRecord(pwo.getUid().intValue(), pwo.getTradeNo(), ApiConstant.TradeType.WITHDRAWS
							, ArithUtil.div(pwo.getPoundageFee().doubleValue(), 100, 2), "提现手续费", "微信", 1,null);
					
					//添加账单
					accountBillService.addPojo(pwo.getUid(), pwo.getTradeNo(), 2, 12, 1,ArithUtil.div(pwo.getActualFee().doubleValue(), 100, 2) , "提现金额");
					accountBillService.addPojo(pwo.getUid(), pwo.getTradeNo(), 2, 12, 1,ArithUtil.div(pwo.getPoundageFee().doubleValue(), 100, 2) , "提现手续费");
					
					//消息
					systemMsgService.saveMsg("哈哈，提现成功", null, "您提现的金额已到账，请注意查收哦", pwo.getUid().intValue(), null);
					
					return Status.success;
				} else if(resultCode.equalsIgnoreCase(PayParams.fail)){
					String errCode = m.get("err_code");
					//平台金额不足
					if(errCode.equalsIgnoreCase("NOTENOUGH")){
						//状态为：1支付中   由清算线程处理
						pwo.setStatus(1);
						partnerWithdrawOrdersMapper.updatePojo(pwo);
						if(bs != null && bs.length > 0){
							bs[0] = false;
						}
						return Status.success;
					//姓名校验错误
					} else if (errCode.equalsIgnoreCase("NAME_MISMATCH")){
						pwo.setStatus(3);
						pwo.setDesc("真实姓名与当前账户不匹配错误");
					} else {
						pwo.setStatus(3);
						pwo.setDesc("平台系统维护请您明天重试!,资金已返回到余额中");
					}
					pwo.setPlatformDesc(errCode + "=" + m.get("err_code_des"));
				}
			}
			partnerWithdrawOrdersMapper.updatePojo(pwo);
			//付款失败退回账户
			payFail(pwo);
		}
		return Status.account_pay_error;
	}
		
		
	/**
	 * @param pwo
	 * @throws Exception 
	 */
	private void payFail(PartnerWithdrawOrders pwo) throws Exception {
		BindAccountList bal = bindAccountListMapper.selectUniqueAccountAsLock(pwo.getUid(),pwo.getPlatform(),pwo.getTradeAccount());
		
		UserGoldCoin ugc = userGoldCoinMapper.selectByUidAsLock(pwo.getUid());
		//重置提现日期为0
		ugc.setWithdrawDate(0);
		//更新账户余额
		ugc.setEarningsGoldCoin(pwo.getTradeFee() + ugc.getEarningsGoldCoin());
		userGoldCoinMapper.updateEarningsAndWithdrawDate(ugc);
		
		//更新绑定账户信息
		if(ExDateUtils.isCurrentDay(bal.getTradeUpdateTime())){
			bal.setCurrentSumFee(bal.getCurrentSumFee() - pwo.getTradeFee());
			bal.setTradeAmount(bal.getTradeAmount() > 0 ? bal.getTradeAmount() - 1 : 0);
		}else{
			bal.setCurrentSumFee(0);
			bal.setTradeAmount(0);
		}
		//更新时间
		bal.setTradeUpdateTime(new Timestamp(ExDateUtils.getCalendar().getTimeInMillis()));
		bindAccountListMapper.updatePojo(bal);
		
		//消息
		systemMsgService.saveMsg("提现失败", null, "很遗憾，您的操作失败，请核实您的账户是否实名认证哦!", pwo.getUid().intValue(), null);
	}

	/* (non-Javadoc)
	 * @see cn.heipiao.api.service.AccountService#partnerAccountWithdrawApply(cn.heipiao.api.pojo.PartnerWithdrawOrders)
	 * 
	 * 合伙人提现申请
	 */
	@Override
	@Transactional(readOnly=false,rollbackFor={Exception.class})
	public int partnerAccountWithdrawApply(PartnerWithdrawOrders pwo) {
		Timestamp t = new Timestamp(ExDateUtils.getCalendar().getTimeInMillis());
		
		//验证是否为合伙人
//		Partner partner = partnerMapper.selectByUid(pwo.getUid());
//		if(partner == null){
//			return Status.partner_not_exists;
//		}
		
		boolean b = userOpService.isPartner(pwo.getUid());
		if(!b){
			return Status.partner_not_exists;
		}
		
		User user = userMapper.selectById(pwo.getUid());
		BindAccountList bal = bindAccountListMapper.selectUniqueAccountAsLock(pwo.getUid(),pwo.getPlatform(),pwo.getTradeAccount());
		UserGoldCoin ugc = userGoldCoinMapper.selectByUidAsLock(pwo.getUid());
		if(user == null || bal == null || ugc == null){
			return Status.account_not_exist;
		}
		
		//验证每月提现一次的限制
		if(ugc.getWithdrawDate() == Integer.parseInt(ExDateUtils.getDateFormat(new Date(t.getTime()), "yyyyMM"))){
			return Status.partner_withdraw_limit;
		}
		
		//判断是否实名认证
		if(StringUtils.isBlank(user.getRealname())){
			return Status.user_not_realname_auth;
		}
		
		//操作频繁控制
		if(t.getTime() - bal.getTradeUpdateTime().getTime() < 15 * 1000){
			return Status.account_freq_limit;
		}
		
		//判断余额是否大于提现金额
		if(ugc.getEarningsGoldCoin() < pwo.getTradeFee()){
			return Status.user_balance_insufficient;
		}
		
		
		//手续费计算 合伙人6%
//		pwo.setPoundageFee((int)Math.ceil(ArithUtil.mul(pwo.getTradeFee(), 0.05)));
		pwo.setPoundageFee((int)(pwo.getTradeFee() * 0.06));
		//实际金额
//		wo.setActualFee(wo.getTradeFee() - wo.getPoundageFee());
		//扣除手续费方式1.如果余额中够手续费从余额中扣除，2.如果余额中不够扣除余额+实际提现金额
		if(pwo.getTradeFee() + pwo.getPoundageFee() <= ugc.getEarningsGoldCoin()){
			pwo.setActualFee(pwo.getTradeFee());
		}else{
			pwo.setActualFee(pwo.getTradeFee() - (pwo.getTradeFee() + pwo.getPoundageFee() - ugc.getEarningsGoldCoin()));
		}
		
		//重新设置交易金额
		pwo.setTradeFee(pwo.getActualFee() + pwo.getPoundageFee());
		
		//获取平台信息
		PlatformAccountConstraint p = pac.selectUniqueAccount(bal.getPlatform());
		if(p == null || p.getUseTime().getTime() > t.getTime()){
			return Status.account_not_support;
		}
		//设置临时单笔最小金额为2元
		p.setSingleMinFee(200);
		
		//单笔交易上下限，0不限制
		if(p.getSingleMaxFee() > 0 && p.getSingleMaxFee() < pwo.getTradeFee() || p.getSingleMinFee() > 0 && p.getSingleMinFee() > pwo.getTradeFee())
			return Status.withdraw_fee_range_error_p;
		
		//判断上次提现日期
		if(ExDateUtils.isCurrentDay(bal.getTradeUpdateTime())){
			//当天单笔交易的次数,0不限制
			if(p.getTradeMaxAmount() > 0 && bal.getTradeAmount() >= p.getTradeMaxAmount())
				return Status.withdraw_trade_max;
			//当天超过日限额，0不限制
			if(p.getDaySumLimited() > 0 && p.getDaySumLimited() < pwo.getTradeFee() + bal.getCurrentSumFee())
				return Status.withdraw_fee_top_limit;
			
			bal.setTradeAmount(p.getTradeMaxAmount() > 0 ? bal.getTradeAmount() + 1 : 0);
			bal.setCurrentSumFee(bal.getCurrentSumFee() + pwo.getTradeFee());
		} else {
			bal.setTradeAmount(p.getTradeMaxAmount() > 0 ? 1 : 0);
			bal.setCurrentSumFee(pwo.getTradeFee());
		}
		
		//更新时间
		bal.setTradeUpdateTime(t);
		
		pwo.setCreateTime(t);
		pwo.setRealname(user.getRealname());
		pwo.setStatus(0);
		pwo.setTradeNo(ExDateUtils.getDateFormatToSecond() + RandomNumberUtils.getFixedLengthNum(10, pwo.getUid().toString()));
		//创建交易订单
		partnerWithdrawOrdersMapper.insertPojo(pwo);

		//更新绑定账户信息
		bindAccountListMapper.updatePojo(bal);
		
		//更新合伙人提现日期yyyyMM
		ugc.setWithdrawDate(Integer.parseInt(ExDateUtils.getDateFormat(new Date(t.getTime()),"yyyyMM")));
		//更新合伙人漂币账户
		ugc.setEarningsGoldCoin(ugc.getEarningsGoldCoin() - pwo.getTradeFee());
		userGoldCoinMapper.updateEarningsAndWithdrawDate(ugc);
		
		
		return Status.success;
	}

	/* (non-Javadoc)
	 * @see cn.heipiao.api.service.AccountService#partnerWithdrawOrderList(java.util.Map)
	 */
	@Override
	public List<PartnerWithdrawOrders> partnerWithdrawOrderList(Map<String, Object> map) {
		return partnerWithdrawOrdersMapper.selectListByLimit(map);
	}

	/* (non-Javadoc)
	 * 店铺提现申请
	 * @see cn.heipiao.api.service.AccountService#storeAccountWithdrawApply(cn.heipiao.api.pojo.WithdrawOrders)
	 */
	@Override
	@Transactional(readOnly=false,rollbackFor={Exception.class})
	public int shopAccountWithdrawApply(ShopWithdrawOrders wo) {
		Timestamp t = new Timestamp(ExDateUtils.getCalendar().getTimeInMillis());
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("uid", wo.getUid());
		params.put("shopId", wo.getShopId());
		ShopEmp emp = shopEmpMapper.selectOne(params);
		
		//判断员工是否有财务权限
		int re = shopEmpPerssion(emp);
		if(re != 0){
			return re;
		}
		
		//店铺是否认证 
//		FishSite fs = fishSiteMapper.selectById(emp.getFishSiteId());
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", emp.getShopId());
		FishShop fs = fishShopMapper.selectFishShopById(map);
		if(fs == null || fs.getUid() == null){
			return Status.fish_site_not_auth;
		}
		
		//设置为店铺老板uid
		wo.setUid(fs.getUid().longValue());
		
		User user = userMapper.selectById(wo.getUid());
		BindAccountList bal = bindAccountListMapper.selectUniqueAccountAsLock(wo.getUid(),wo.getPlatform(),wo.getTradeAccount());
		ShopAccount account = shopAccountMapper.selectUniqueAsLock(fs.getId());
		if(user == null || bal == null || account == null){
			return Status.account_not_exist;
		}
		
		//每天提现一次验证
		if(ExDateUtils.isCurrentDay(account.getWithdrawTime())){
			return Status.withdraw_day_over;
		}
		
		
		//判断是否实名认证
		if(StringUtils.isBlank(user.getRealname())){
			return Status.user_not_realname_auth;
		}
		
		//操作频繁控制
		if(t.getTime() - bal.getTradeUpdateTime().getTime() < 15 * 1000){
			return Status.account_freq_limit;
		}

		//判断余额是否大于提现金额
		if(ArithUtil.compareTo(account.getBalance(),ArithUtil.div(wo.getTradeFee().doubleValue(), 100, 2)) == -1){
			return Status.user_balance_insufficient;
		}
		
		WithdrawConfig rc = withdrawConfigMapper.selectByRuleIdAndDelayForDay(2,account.getWithdrawRuleId(),wo.getDelayForDay());
		if(rc == null){
			return Status.value_is_null_or_error;
		}
		wo.setDelayForDay(rc.getDelayForDay());
		wo.setWithdrawRate(rc.getWithdrawRate());
		
		//手续费计算
		wo.setPoundageFee((int)Math.ceil(ArithUtil.mul(wo.getTradeFee(), rc.getWithdrawRate())));
		//实际金额
//		wo.setActualFee(wo.getTradeFee() - wo.getPoundageFee());
		//扣除手续费方式1.如果余额中够手续费从余额中扣除，2.如果余额中不够扣除余额+实际提现金额
		if(wo.getTradeFee() + wo.getPoundageFee() <= (int)ArithUtil.mul(account.getBalance(),100)){
			wo.setActualFee(wo.getTradeFee());
		}else{
			wo.setActualFee(wo.getTradeFee() - (wo.getTradeFee() + wo.getPoundageFee() - ((int)ArithUtil.mul(account.getBalance(),100))));
		}
		
		//重新设置交易金额
		wo.setTradeFee(wo.getActualFee() + wo.getPoundageFee());

		//获取平台信息
		PlatformAccountConstraint p = pac.selectUniqueAccount(bal.getPlatform());
		if(p == null || p.getUseTime().getTime() > t.getTime()){
			return Status.account_not_support;
		}
		
		//单笔交易上下限，0不限制
		if(p.getSingleMaxFee() > 0 && p.getSingleMaxFee() < wo.getTradeFee() || p.getSingleMinFee() > 0 && p.getSingleMinFee() > wo.getTradeFee())
			return Status.withdraw_fee_range_error;
		
		//判断上次提现日期
		if(ExDateUtils.isCurrentDay(bal.getTradeUpdateTime())){
			//当天单笔交易的次数,0不限制
			if(p.getTradeMaxAmount() > 0 && bal.getTradeAmount() >= p.getTradeMaxAmount())
				return Status.withdraw_trade_max;
			//当天超过日限额，0不限制
			if(p.getDaySumLimited() > 0 && p.getDaySumLimited() < wo.getTradeFee() + bal.getCurrentSumFee())
				return Status.withdraw_fee_top_limit;
			
			bal.setTradeAmount(p.getTradeMaxAmount() > 0 ? bal.getTradeAmount() + 1 : 0);
			bal.setCurrentSumFee(bal.getCurrentSumFee() + wo.getActualFee());
		} else {
			bal.setTradeAmount(p.getTradeMaxAmount() > 0 ? 1 : 0);
			bal.setCurrentSumFee(wo.getActualFee());
		}
		
		//更新时间
		bal.setTradeUpdateTime(t);
		
		wo.setCreateTime(t);
		wo.setRealname(user.getRealname());
		wo.setStatus(0);
		wo.setTradeNo(ExDateUtils.getDateFormatToSecond() + RandomNumberUtils.getVerifyNum(6));
		//创建交易订单
		shopWithdrawOrdersMapper.insertPojo(wo);

		//更新绑定账户信息
		bindAccountListMapper.updatePojo(bal);
		
		//更新账户余额
//		account.setBalance(ArithUtil.sub(account.getBalance(), wo.getTradeFee() / 100.0));
//		account.setBalance(ArithUtil.sub(account.getBalance(), ArithUtil.div(wo.getTradeFee(), 100,2)));
		account.setBalance(ArithUtil.sub(account.getBalance(), ArithUtil.div(wo.getActualFee() + wo.getPoundageFee(), 100,2)));
		account.setWithdrawTime(t);
		shopAccountMapper.updatePojo(account);
		
		//生成交易记录
//		srgs.generateAccountDeRecord(account.getUid().intValue(), wo.getTradeNo(), ApiConstant.TradeType.WITHDRAWS
//				, ArithUtil.div(wo.getTradeFee().doubleValue(), 100, 2), "提现", "微信", 1);
		
		return Status.success;
	}

	/* (non-Javadoc)
	 * @see cn.heipiao.api.service.AccountService#storeAccountWithdraw(cn.heipiao.api.pojo.WithdrawOrders)
	 */
	@Override
	@Transactional(readOnly=false,rollbackFor={Exception.class})
	public int shopAccountWithdraw(ShopWithdrawOrders wo,boolean ...bs) throws Exception {
		Timestamp t = new Timestamp(ExDateUtils.getCalendar().getTimeInMillis());

		wo = shopWithdrawOrdersMapper.selectByTradeNoAsLock(wo.getTradeNo());
		
		if(wo != null && (wo.getStatus().intValue() == 0 || wo.getStatus().intValue() == 1)
				&& (wo.getDelayForDay() == 0 || (wo.getCreateTime().getTime() + wo.getDelayForDay() * 24L * 60 * 60 * 1000) < ExDateUtils.getCalendar().getTimeInMillis())){
			//判断提现时间间隔15s
			BindAccountList bal = bindAccountListMapper.selectUniqueAccount(wo.getUid(),wo.getPlatform(),wo.getTradeAccount());
			if(!isInterval(bal)){
				return Status.account_freq_limit;
			}
			//提现
			Map<String, String> m = pay.wechatEnterprisePay(wo.getTradeNo(), wo.getTradeAccount(), wo.getRealname(), wo.getActualFee(), "提现",1);
				
			if(m != null && m.get("return_code").equalsIgnoreCase(PayParams.success)){
				String resultCode = m.get("result_code"); 
				if(resultCode.equalsIgnoreCase(PayParams.success)){
					
					wo.setPayTime(t);
					wo.setPlatformTradeNo(m.get("payment_no"));
					wo.setStatus(2);
					shopWithdrawOrdersMapper.updatePojo(wo);
					//生成交易记录
					//提现实际金额
					srgs.generateAccountDeRecord(wo.getUid().intValue(), wo.getTradeNo(), ApiConstant.TradeType.WITHDRAWS
							, ArithUtil.div(wo.getActualFee().doubleValue(), 100, 2), "提现", "微信", 1,null);
					//提现手续费金额
					srgs.generateAccountDeRecord(wo.getUid().intValue(), wo.getTradeNo(), ApiConstant.TradeType.POUNDAGE
							, ArithUtil.div(wo.getPoundageFee().doubleValue(), 100, 2), "提现手续费", "微信", 1,null);
					
					//添加账单
					accountBillService.addPojo(wo.getUid(), wo.getTradeNo(), 2, 6, 1,ArithUtil.div(wo.getActualFee().doubleValue(), 100, 2) , "提现金额");
					accountBillService.addPojo(wo.getUid(), wo.getTradeNo(), 2, 6, 1,ArithUtil.div(wo.getPoundageFee().doubleValue(), 100, 2) , "提现手续费");

					//消息
					systemMsgService.saveMsg("提现成功", null, "您提现的金额已到账，请您注意查收!", wo.getUid().intValue(), null);
					
					return Status.success;
				} else if(resultCode.equalsIgnoreCase(PayParams.fail)){
					String errCode = m.get("err_code");
					//平台金额不足
					if(errCode.equalsIgnoreCase("NOTENOUGH")){
						//状态为：1支付中   由清算线程处理
						wo.setStatus(1);
						shopWithdrawOrdersMapper.updatePojo(wo);
						if(bs != null && bs.length > 0){
							bs[0] = false;
						}
						return Status.success;
					//姓名校验错误
					} else if (errCode.equalsIgnoreCase("NAME_MISMATCH")){
						wo.setStatus(3);
						wo.setDesc("真实姓名与当前绑定账户的姓名不匹配错误");
					} else {
						wo.setStatus(3);
						wo.setDesc("平台系统维护请您明天重试!,资金已返回到余额中");
					}
					wo.setPlatformDesc(errCode + "=" + m.get("err_code_des"));
				}
			}
			shopWithdrawOrdersMapper.updatePojo(wo);
			//付款失败退回账户
			shopPayFail(wo);
		}
		return Status.account_pay_error;
	}

	/**
	 * 判断提现时间间隔15s
	 * @param bal
	 * @return
	 */
	private boolean isInterval(BindAccountList bal) {
		return ExDateUtils.getCalendar().getTimeInMillis() - bal.getTradeUpdateTime().getTime() > 15000L;
	}

	/**
	 * @param wo
	 * @throws Exception 
	 */
	private void shopPayFail(ShopWithdrawOrders wo) throws Exception {
		BindAccountList bal = bindAccountListMapper.selectUniqueAccountAsLock(wo.getUid(),wo.getPlatform(),wo.getTradeAccount());
		ShopAccount account = shopAccountMapper.selectUniqueAsLock(wo.getShopId());
		//更新账户余额
//		account.setBalance(ArithUtil.add(account.getBalance(), wo.getTradeFee() / 100.0));
		account.setBalance(ArithUtil.add(account.getBalance(), ArithUtil.div(wo.getActualFee() + wo.getPoundageFee(), 100,2)));
		account.setWithdrawTime(new Timestamp(ExDateUtils.getCalendar().getTimeInMillis() - (24L * 60 * 60 * 1000)));
		shopAccountMapper.updatePojo(account);
		//更新绑定账户信息
		if(ExDateUtils.isCurrentDay(bal.getTradeUpdateTime())){
			bal.setCurrentSumFee(bal.getCurrentSumFee() - wo.getActualFee());
			bal.setTradeAmount(bal.getTradeAmount() > 0 ? bal.getTradeAmount() - 1 : 0);
		}else{
			bal.setCurrentSumFee(0);
			bal.setTradeAmount(0);
		}
		//更新时间
		bal.setTradeUpdateTime(new Timestamp(ExDateUtils.getCalendar().getTimeInMillis()));
		bindAccountListMapper.updatePojo(bal);
		//消息
		systemMsgService.saveMsg("提现失败", null, "很遗憾，您的操作失败，请核实您的账户是否实名认证哦!", wo.getUid().intValue(), null);
	}

	/* (non-Javadoc)
	 * @see cn.heipiao.api.service.AccountService#shopWithdrawOrderList(java.util.Map)
	 */
	@Override
	public List<ShopWithdrawOrders> shopWithdrawOrderList(Map<String, Object> map) {
		return shopWithdrawOrdersMapper.selectListByLimit(map);
	}

	/* (non-Javadoc)
	 * @see cn.heipiao.api.service.AccountService#shopDeleteBindAccount(cn.heipiao.api.pojo.BindAccountList)
	 */
	@Override
	@Transactional(readOnly = false,rollbackFor={Exception.class})
	public int shopDeleteBindAccount(BindAccountList bal,Long shopId) {
		int rs = verifyShopEmp(bal.getUid(),shopId);
		if(rs != Status.success){
			return rs;
		}
		return deleteBindAccount0(bal);
	}

	/* (non-Javadoc)
	 * @see cn.heipiao.api.service.AccountService#shopBindAccount(cn.heipiao.api.pojo.BindAccountList)
	 */
	@Override
	@Transactional(readOnly = false,rollbackFor={Exception.class})
	public int shopBindAccount(BindAccountList bal,Long shopId) {
//		Map<String,Object> map = new HashMap<String, Object>();
//		map.put("uid",bal.getUid());
//		map.put("shopId",shopId);
//		ShopEmp emp = shopEmpMapper.selectOne(map);
//		if(emp == null || emp.getStatus().intValue() != 2){
//			return Status.emp_not_permission;
//		}
		int rs = verifyShopEmp(bal.getUid(),shopId);
		if(rs != Status.success){
			return rs;
		}
		return bindAccount0(bal);
	}

	/**
	 * @param uid
	 * @param shopId
	 * @return
	 */
	private int verifyShopEmp(Long uid, Long shopId) {
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("uid",uid);
		map.put("shopId",shopId);
		ShopEmp emp = shopEmpMapper.selectOne(map);
		if(emp == null || emp.getStatus().intValue() != 2){
			return Status.emp_not_permission;
		}
		return Status.success;
	}

	/* (non-Javadoc)
	 * @see cn.heipiao.api.service.AccountService#getShopAccountByUid(java.lang.Long)
	 */
	@Override
	public ShopAccount getShopAccountUnique(Long uid,Long shopId) {
		return shopAccountMapper.selectUnique(shopId);
	}
	
	/**
	 * 
	 */
	@Override
	@Transactional(readOnly = false,rollbackFor = {Exception.class})
	public void addShopAccount(Long uid,Long shopId){
		ShopAccount sa = shopAccountMapper.selectUnique(shopId);
		if(sa == null){
			sa = new ShopAccount();
			sa.setUid(uid);
			sa.setShopId(shopId);
			sa.setBalance(0D);
			sa.setWithdrawRuleId(1);
			sa.setWithdrawTime(new Timestamp(ExDateUtils.getCalendar().getTimeInMillis() - 24L * 60 * 60 * 1000));
			sa.setPayCode(ExDigestUtils.md5(uid.toString() + shopId.toString()).toUpperCase());
			shopAccountMapper.insertPojo(sa);
		}else{
			sa.setUid(uid);
			sa.setWithdrawTime(new Timestamp(ExDateUtils.getCalendar().getTimeInMillis() - 24L * 60 * 60 * 1000));
			sa.setPayCode(ExDigestUtils.md5(uid.toString() + shopId.toString()).toUpperCase());
			shopAccountMapper.updatePojo(sa);
		}
	}

	/* (non-Javadoc)
	 * @see cn.heipiao.api.service.AccountService#addShopAccount(java.lang.Long, java.lang.Long, java.lang.Double)
	 */
	@Override
	@Transactional(readOnly = false,rollbackFor = {Exception.class})
	public ShopAccount addShopAccountBalance(Long shopId, Double balance) {
		ShopAccount sam = shopAccountMapper.selectUniqueAsLock(shopId);
		sam.setBalance(ArithUtil.add(sam.getBalance(),balance));
		shopAccountMapper.updateBalance(sam);
		return sam;
	}

	/* (non-Javadoc)
	 * @see cn.heipiao.api.service.AccountService#addGoldCoin(java.lang.Long, java.lang.Integer, java.lang.Integer)
	 */
	@Override
	@Transactional(readOnly = false,rollbackFor = {Exception.class})
	public void addGoldCoin(Long uid, Integer goldCoinMoney, Integer earningsGoldCoinMoney) {
		UserGoldCoin ugc = userGoldCoinMapper.selectByUidAsLock(uid);
		ugc.setEarningsGoldCoin(ugc.getEarningsGoldCoin() + earningsGoldCoinMoney);
		ugc.setGoldCoin(ugc.getGoldCoin() + goldCoinMoney);
		userGoldCoinMapper.updatePojo(ugc);
	}

	/* (non-Javadoc)
	 * @see cn.heipiao.api.service.AccountService#getPayCodeShopAccountUnique(java.lang.Long, java.lang.Long)
	 */
	@Override
	public ShopAccount getPayCodeShopAccountUnique(Long uid, Long shopId) {
		Boolean b = moduleMapper.isShopPrivilege(shopId,uid,401);
		if(b == null){
			return null;
		}
		return shopAccountMapper.selectPayCode(shopId);
	}

	/* (non-Javadoc)
	 * @see cn.heipiao.api.service.AccountService#getShopByPayCode(java.lang.String)
	 */
	@Override
	public ShopAccount getShopByPayCode(String payCode) {
		return shopAccountMapper.getShopByPayCode(payCode);
	}

	@Override
	public List<ShopFinance> shopAccountList(Map<String, Object> map) {
		return shopAccountMapper.shopAccountList(map);
	}

	@Override
	public Integer shopAccountListCount(Map<String, Object> map) {
		return shopAccountMapper.shopAccountListCount(map);
	}
	
}
