package cn.heipiao.api.service.impl;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.heipiao.api.constant.ApiConstant;
import cn.heipiao.api.mapper.BindAccountListMapper;
import cn.heipiao.api.mapper.EmpMapper;
import cn.heipiao.api.mapper.FishShopMapper;
import cn.heipiao.api.mapper.FishSiteMapper;
import cn.heipiao.api.mapper.ModuleMapper;
import cn.heipiao.api.mapper.PlatformAccountConstraintMapper;
import cn.heipiao.api.mapper.RewardAmountMapper;
import cn.heipiao.api.mapper.RewardPlatformWithdrawOrderMapper;
import cn.heipiao.api.mapper.ShopEmpMapper;
import cn.heipiao.api.mapper.UserMapper;
import cn.heipiao.api.mapper.WithdrawConfigMapper;
import cn.heipiao.api.pay.PayParams;
import cn.heipiao.api.pay.PayService;
import cn.heipiao.api.pojo.Account;
import cn.heipiao.api.pojo.BindAccountList;
import cn.heipiao.api.pojo.Emp;
import cn.heipiao.api.pojo.FishShop;
import cn.heipiao.api.pojo.FishSite;
import cn.heipiao.api.pojo.PlatformAccountConstraint;
import cn.heipiao.api.pojo.RewardAmount;
import cn.heipiao.api.pojo.RewardPlatformWithdrawOrder;
import cn.heipiao.api.pojo.ShopEmp;
import cn.heipiao.api.pojo.User;
import cn.heipiao.api.pojo.WithdrawConfig;
import cn.heipiao.api.resources.Status;
import cn.heipiao.api.service.AccountBillService;
import cn.heipiao.api.service.JPushService;
import cn.heipiao.api.service.RewardAmountService;
import cn.heipiao.api.service.SystemMsgService;
import cn.heipiao.api.utils.ArithUtil;
import cn.heipiao.api.utils.ExDateUtils;
import cn.heipiao.api.utils.RandomNumberUtils;

/**
 * 
 * @ClassName: RewardAmountServiceImpl
 * @Description: TODO
 * @author duzh
 * @date 2017年1月17日
 */
@Service
public class RewardAmountServiceImpl implements RewardAmountService{
	@Resource
	private RewardAmountMapper rewardAmountMapper;
	@Resource
	private ShopEmpMapper<ShopEmp> shopEmpMapper;
	@Resource
	private ModuleMapper moduleMapper;
	@Resource
	private FishShopMapper fishShopMapper;
	@Resource
	private UserMapper userMapper;
	@Resource
	private BindAccountListMapper bindAccountListMapper;
	@Resource
	private PlatformAccountConstraintMapper pac;
	@Resource
	private WithdrawConfigMapper withdrawConfigMapper;
	@Resource
	private RewardPlatformWithdrawOrderMapper rewardPlarformWithdrawOrderMapper;
	@Resource(name = "PayService")
	private PayService pay;
	@Resource
	private SystemRecordGeneratorServiceImpl srgs;
	@Resource
	private SystemMsgService systemMsgService;
	@Resource
	private AccountBillService accountBillService;
	@Resource
	private EmpMapper empMapper;
	@Resource
	private FishSiteMapper fishSiteMapper;
	@Resource
	private JPushService jPushService;
	/*
	 * 商家获取平台奖励账户信息
	 */
	@Override
	public RewardAmount findRewardAmount(Long bid, int type) {
		RewardAmount rewardAmount=null;
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("bid", bid);
		params.put("type", type);
		rewardAmount = rewardAmountMapper.selectByBid(params);
		return rewardAmount;
	}
	/*
	 * 修改商家平台奖励账户信息
	 */
	@Override
	public int updateRewardAmount(RewardAmount rewardAmount) {
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("bid", rewardAmount.getBid());
		params.put("type", rewardAmount.getType());
		params.put("balance", rewardAmount.getBalance());
		params.put("remain", rewardAmount.getRemain());
		params.put("total", rewardAmount.getTotal());
		rewardAmountMapper.updateByBid(params);
		return Status.success;
	}
	@Override
	public int insertRewardAmount(RewardAmount rewardAmount) {
		rewardAmountMapper.insertRewardPlatformAmount(rewardAmount);
		return Status.success;
	}
	/**(non-Javadoc)
	 * 渔具店提现申请
	 * 
	 */
	@Override
	@Transactional(readOnly=false,rollbackFor={Exception.class})
	public int bussinessAccountWithdrawApply(RewardPlatformWithdrawOrder wo) throws Exception {
		Timestamp t = new Timestamp(ExDateUtils.getCalendar().getTimeInMillis());
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("uid", wo.getUid());
		params.put("shopId", wo.getBid());
		ShopEmp emp = shopEmpMapper.selectOne(params);
		
		//判断员工是否有财务权限
		int re = shopEmpPerssion(emp);
		if(re != 0){
			return re;
		}
		 
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", wo.getBid());
		//店铺是否认证
		FishShop fs = fishShopMapper.selectFishShopById(map);
		if(fs == null || fs.getUid() == null){
			return Status.fish_site_not_auth;
		}
		
		//设置为店铺老板uid
		wo.setUid(fs.getUid().longValue());
		
		User user = userMapper.selectById(wo.getUid());
		BindAccountList bal = bindAccountListMapper.selectUniqueAccountAsLock(wo.getUid(),wo.getPlatform(),wo.getTradeAccount());
		Map<String,Object> param = new HashMap<String, Object>();
		param.put("type", wo.getType());
		param.put("bid", wo.getBid());
		RewardAmount account = rewardAmountMapper.selectByBidAsLock(param);
		if(user == null || bal == null || account == null){
			return Status.account_not_exist;
		}
		
		// 每月提现一次
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");

		if (sdf.parse(sdf.format(account.getWithdrawTime())).equals(
				sdf.parse(sdf.format(t)))) {
			return Status.WITHDRAW_MONTH_OVER;
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
		if(ArithUtil.compareTo(account.getBalance(),ArithUtil.div(wo.getTradeFee(), 100, 2)) == -1){
			return Status.user_balance_insufficient;
		}
		
		wo.setActualFee(wo.getTradeFee());
		
		//更新时间
		bal.setTradeUpdateTime(t);
		
		wo.setCreateTime(t);
		wo.setRealname(user.getRealname());
		wo.setStatus(0);
		wo.setTradeNo(ExDateUtils.getDateFormatToSecond() + RandomNumberUtils.getVerifyNum(6));
		//创建交易订单
		rewardPlarformWithdrawOrderMapper.insert(wo);

		//更新绑定账户信息
		bindAccountListMapper.updatePojo(bal);
		
		//更新账户余额
		account.setBalance(ArithUtil.sub(account.getBalance(), ArithUtil.div(wo.getActualFee(), 100,2)));
		account.setWithdrawTime(t);
		rewardAmountMapper.updatePojo(account);
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
	/**
	 * 提现
	 */
	@Override
	@Transactional(readOnly=false,rollbackFor={Exception.class})
	public int bussinessAccountWithdraw(RewardPlatformWithdrawOrder wo,boolean ...bs) throws Exception {
		Timestamp t = new Timestamp(ExDateUtils.getCalendar().getTimeInMillis());

		wo = rewardPlarformWithdrawOrderMapper.selectByTradeNoAsLock(wo.getTradeNo());
		
		if(wo != null && (wo.getStatus().intValue() == 0 || wo.getStatus().intValue() == 1)){
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
					rewardPlarformWithdrawOrderMapper.update(wo);
					//生成交易记录
					//提现实际金额
					srgs.generateAccountDeRecord(wo.getUid().intValue(), wo.getTradeNo(), ApiConstant.TradeType.WITHDRAWS
							, ArithUtil.div(wo.getActualFee().doubleValue(), 100, 2), "提现", "微信", 1,null);
					
					//添加账单
					if(wo.getType()==0){
						accountBillService.addPojo(wo.getUid(), wo.getTradeNo(), 2, 17, 1,ArithUtil.div(wo.getActualFee(), 100, 2) , "平台奖励提现金额");
					}else{
						accountBillService.addPojo(wo.getUid(), wo.getTradeNo(), 2, 19, 1,ArithUtil.div(wo.getActualFee(), 100, 2) , "平台奖励提现金额");
					}
					//消息
					systemMsgService.saveMsg("提现成功", null, "您提现的金额已到账，请您注意查收!", wo.getUid().intValue(), null);
					return Status.success;
				} else if(resultCode.equalsIgnoreCase(PayParams.fail)){
					String errCode = m.get("err_code");
					//平台金额不足
					if(errCode.equalsIgnoreCase("NOTENOUGH")){
						//状态为：1支付中   由清算线程处理
						wo.setStatus(1);
						rewardPlarformWithdrawOrderMapper.update(wo);
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
			rewardPlarformWithdrawOrderMapper.update(wo);
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
	private void shopPayFail(RewardPlatformWithdrawOrder wo) throws Exception {
		BindAccountList bal = bindAccountListMapper.selectUniqueAccountAsLock(wo.getUid(),wo.getPlatform(),wo.getTradeAccount());
		Map<String,Object> params= new HashMap<String, Object>();
		params.put("bid", wo.getBid());
		params.put("type", wo.getType());
		RewardAmount account = rewardAmountMapper.selectByBidAsLock(params);
		//更新账户余额
//		account.setBalance(ArithUtil.add(account.getBalance(), wo.getTradeFee() / 100.0));
		account.setBalance(ArithUtil.add(account.getBalance(), wo.getActualFee().doubleValue()));
		account.setWithdrawTime(new Timestamp(ExDateUtils.getCalendar().getTimeInMillis() - (24L * 60 * 60 * 1000)));
		rewardAmountMapper.updatePojo(account);
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
	/**
	 * 提现订单
	 */
	@Override
	public List<RewardPlatformWithdrawOrder> selectListByLimit(Map<String, Object> map){
		return rewardPlarformWithdrawOrderMapper.selectListByLimit(map);
	}
	@Override
	public int fishSiteBussinessAccountWithdrawApply(
			RewardPlatformWithdrawOrder wo) throws Exception {
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
		Map<String,Object> param = new HashMap<String, Object>();
		param.put("type", wo.getType());
		param.put("bid", wo.getBid());
		RewardAmount account = rewardAmountMapper.selectByBidAsLock(param);
		if(user == null || bal == null || account == null){
			return Status.account_not_exist;
		}
		
		// 每月提现一次
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");

				if (sdf.parse(sdf.format(account.getWithdrawTime())).equals(
						sdf.parse(sdf.format(t)))) {
					return Status.WITHDRAW_MONTH_OVER;
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
		if(ArithUtil.compareTo(account.getBalance(),ArithUtil.div(wo.getTradeFee(), 100, 2)) == -1){
			return Status.user_balance_insufficient;
		}
		
		//更新时间
		bal.setTradeUpdateTime(t);
		wo.setActualFee(wo.getTradeFee());
		wo.setCreateTime(t);
		wo.setRealname(user.getRealname());
		wo.setStatus(0);
		wo.setTradeNo(ExDateUtils.getDateFormatToSecond() + RandomNumberUtils.getVerifyNum(6));
		//创建交易订单
		rewardPlarformWithdrawOrderMapper.insert(wo);

		//更新绑定账户信息
		bindAccountListMapper.updatePojo(bal);
		account.setBalance(ArithUtil.sub(account.getBalance(),ArithUtil.div(wo.getActualFee(), 100, 2)));
		account.setWithdrawTime(t);
		rewardAmountMapper.updatePojo(account);
		
		return Status.success;
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
}
