/**
 * 
 */
package cn.heipiao.api.resources;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;

import cn.heipiao.api.constant.RespMessage;
import cn.heipiao.api.pojo.Account;
import cn.heipiao.api.pojo.AccountExt;
import cn.heipiao.api.pojo.AccountExtSite;
import cn.heipiao.api.pojo.AccountRecord;
import cn.heipiao.api.pojo.BindAccountList;
import cn.heipiao.api.pojo.FishShop;
import cn.heipiao.api.pojo.FishSite;
import cn.heipiao.api.pojo.PartnerWithdrawOrders;
import cn.heipiao.api.pojo.RespMsg;
import cn.heipiao.api.pojo.ShopAccount;
import cn.heipiao.api.pojo.ShopWithdrawOrders;
import cn.heipiao.api.pojo.UserGoldCoin;
import cn.heipiao.api.pojo.WithdrawOrders;
import cn.heipiao.api.service.AccountService;
import cn.heipiao.api.service.FishShopService;
import cn.heipiao.api.service.FishSiteService;
import cn.heipiao.api.utils.ExDateUtils;
import io.swagger.annotations.Api;

/**
 * @author wzw
 * @date 2016年6月30日
 * @version 1.0
 */
@Api(tags = "财务模块")
@RestController
@RequestMapping(value = "account",produces="application/json")
public class AccountResource {

	private static final Logger logger = LoggerFactory.getLogger(AccountResource.class);

	@Resource
	private AccountService accountService;

	@Resource
	private FishShopService fishShopService;
	
	@Resource
	private FishSiteService fishSiteService;
	
	/**
	 * 用户财务交易记录
	 * 
	 * @param uid
	 * @param start
	 * @param size
	 * @param source
	 * @return
	 */
//	@GET
//	@Path("list/{uid}/{start}/{size}/{source}/{type}")
	@RequestMapping(value = "list/{uid}/{start}/{size}/{source}/{type}",method = RequestMethod.GET)
	public RespMsg<Map<String, Object>> getList(@PathVariable("uid") Integer uid,
			@PathVariable("start") Integer start, @PathVariable("size") Integer size,
			@PathVariable("source") Integer source, @PathVariable("type") Integer type) {
		try {
			logger.debug("uid:{},start:{},size:{},source:{},type:{}", uid,
					start, size, source, type);
			if (source == 1) {
				start = (start - 1) * size;
			}
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("uid", uid);
			map.put("start", start);
			map.put("size", size);
			if (type.intValue() == 15) {
				type = null;
			}
			map.put("type", type);
			List<AccountRecord> pojos = accountService
					.selectAccountRecordByUid(map);
			int total = accountService.counts(uid);
			Map<String, Object> result = new HashMap<String, Object>();
			result.put("data", pojos);
			result.put("total", total);
			return new RespMsg<Map<String, Object>>(result);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return new RespMsg<>(Status.error,
					RespMessage.getRespMsg(Status.error));
		}
	}

	/**
	 * CP-钓场财务交易记录
	 * 
	 * @param siteId
	 * @param start
	 * @param size
	 * @return
	 */
//	@GET
//	@Path("site/{siteId}/{status}/{type}/{searchfor}/{start}/{size}")
//	@Deprecated
	@RequestMapping(value = "site/{siteId}/{status}/{type}/{searchfor}/{start}/{size}",method = RequestMethod.GET)
	public RespMsg<Map<String, Object>> getSiteList(
			@PathVariable("siteId") Integer siteId,@PathVariable("type")Integer type,
			@PathVariable("status") Integer status,@PathVariable("searchfor")String searchfor,
			@PathVariable("start") Integer start, @PathVariable("size") Integer size) {
		try {
			logger.debug("siteId:{},start:{},size:{},type:{},status{}",
					siteId, start, size,type,status);
			if(searchfor.equals("_")){
				searchfor=null;
			}
			 if(type==15){
				 type =null;
			 }
			 if(status == 2){
				 status = null;
			 }
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("siteId", siteId);
			map.put("type", type);
			map.put("status", status);
			map.put("searchfor", searchfor);
			List<AccountRecord> total = accountService.getRecordBySite(map);
			map.put("start", (start - 1) * size);
			map.put("size", size);
			List<AccountRecord> data = accountService.getRecordBySite(map);
			Map<String, Object> result = new HashMap<String, Object>();
			result.put("data", data);
			result.put("total", total.size());
			return new RespMsg<Map<String, Object>>(result);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return new RespMsg<>(Status.error,
					RespMessage.getRespMsg(Status.error));
		}
	}

	/**
	 * 用户财务列表
	 * 
	 * @param start
	 * @param size
	 * @return
	 */
//	@GET
//	@Path("list/{start}/{size}")
	@RequestMapping(value = "list/{start}/{size}",method = RequestMethod.GET)
	public String getList(@PathVariable("start") Integer start,
			@PathVariable("size") Integer size) {
		try {
			Map<String, Object> param = new HashMap<String, Object>();
			start = (start - 1) * size;
			param.put("start", start);
			param.put("size", size);
			List<AccountExt> results = accountService
					.selectAccountExtForCp(param);
			int total = accountService.countAccountExts();
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("data", results);
			map.put("total", total);
			return JSONObject
					.toJSONString(new RespMsg<Map<String, Object>>(map));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject
					.toJSONString(new RespMsg<AccountExt>(Status.error));
		}
	}

	/**
	 * 钓场财务列表
	 * 
	 * @param start
	 * @param size
	 * @return
	 */
//	@GET
//	@Path("list/site/{regionId}/{start}/{size}")
//	@Deprecated
	@RequestMapping(value = "list/site/{regionId}/{start}/{size}",method = RequestMethod.GET)
	public String getSiteList(@PathVariable("regionId") Integer regionId,@PathVariable("start") Integer start,
			@PathVariable("size") Integer size) {
		try {
			Map<String, Object> param = new HashMap<String, Object>();
			start = (start - 1) * size;
			param.put("start", start);
			param.put("size", size);
			param.put("regionId", regionId);
			List<AccountExtSite> results = accountService
					.selectAccountExtSiteForCp(param);
			int total = accountService.countAccountExtSites(regionId);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("data", results);
			map.put("total", total);
			return JSONObject
					.toJSONString(new RespMsg<Map<String, Object>>(map));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject
					.toJSONString(new RespMsg<AccountExt>(Status.error));
		}
	}

	/**
	 * 查询钓场主账户余额
	 * 
	 * @param userId
	 * @return
	 */
//	@Path("{userId}")
//	@GET
	@RequestMapping(value = "{userId}",method = RequestMethod.GET)
	public String getUserAccount(@PathVariable("userId") Long userId) {
		try {
			Account account = accountService.getAccountByUid(userId);
			return JSONObject.toJSONString(new RespMsg<Account>(account));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject
					.toJSONString(new RespMsg<AccountExt>(Status.error));
		}
	}

	/**
	 * 查询店铺账户余额
	 * 
	 * @param uid
	 * @return
	 */
//	@Path("shop/{uid}/{shopId}")
//	@GET
	@RequestMapping(value = "shop/{uid}/{shopId}",method = RequestMethod.GET)
	public String getShopAccount(@PathVariable("uid") Long uid,@PathVariable("shopId") Long shopId) {
		try {
			ShopAccount account = accountService.getShopAccountUnique(uid, shopId);
			return JSONObject.toJSONString(new RespMsg<ShopAccount>(account));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject
					.toJSONString(new RespMsg<AccountExt>(Status.error));
		}
	}
	
	/**
	 * 查询店铺收款码
	 * 
	 * @param uid
	 * @return
	 */
//	@Path("shop/payCode/{uid}/{shopId}")
//	@GET
	@RequestMapping(value = "shop/payCode/{uid}/{shopId}",method = RequestMethod.GET)
	public String getPayCodeShopAccount(@PathVariable("uid") Long uid,@PathVariable("shopId") Long shopId) {
		try {
			ShopAccount account = accountService.getPayCodeShopAccountUnique(uid, shopId);
			if(account == null){
				return JSONObject.toJSONString(new RespMsg<>(Status.emp_not_permission,RespMessage.getRespMsg(Status.emp_not_permission)));
			}
			//店铺二维码生成串 FIXME 换https，以及放到配置中去
			account.setPayCode("http://heipiaola.com?shopKey=" + account.getPayCode());
			return JSONObject.toJSONString(new RespMsg<ShopAccount>(account));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject
					.toJSONString(new RespMsg<AccountExt>(Status.error));
		}
	}
	
	/**
	 * 获取用户漂币
	 * 
	 * @param uid
	 * @return
	 */
//	@GET
//	@Path("goldCoin/{uid}")
	@RequestMapping(value = "goldCoin/{uid}",method = RequestMethod.GET)
	public String getGoldCoin(@PathVariable("uid") Long uid) {
		try {
			logger.debug("uid:{}", uid);
			if (uid == null) {
				return JSONObject.toJSONString(new RespMsg<>(
						Status.value_is_null_or_error, RespMessage
								.getRespMsg(Status.value_is_null_or_error)));
			}
			UserGoldCoin pojo = accountService.getGoldCoin(uid);
			if (pojo == null) {
				return JSONObject.toJSONString(new RespMsg<>(Status.account_not_exist,RespMessage.getRespMsg(Status.account_not_exist)));
			}
			pojo.setGoldCoin(pojo.getGoldCoin() + pojo.getEarningsGoldCoin());
			pojo.setWithdrawStatus(pojo.getWithdrawDate() != null && pojo.getWithdrawDate().intValue() == Integer.parseInt(ExDateUtils.getCurrentDayFormat("yyyyMM")) ? 1 : 0);
			return JSONObject.toJSONString(new RespMsg<>(pojo));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.toJSONString(new RespMsg<>(Status.error,RespMessage.getRespMsg(Status.error)));
		}
	}

	/**
	 * 获取b端用户绑定账户列表
	 * 2.0
	 * @param uid
	 * @return
	 */
//	@GET
//	@Path("bindAccountList/{uid}")
	@RequestMapping(value = "bindAccountList/{uid}",method = RequestMethod.GET)
	public String getBindAccountList(@PathVariable("uid") Long uid) {
		try {
			logger.debug("uid:{}", uid);
			if (uid == null) {
				return JSONObject.toJSONString(new RespMsg<>(
						Status.value_is_null_or_error, RespMessage
								.getRespMsg(Status.value_is_null_or_error)));
			}
			List<BindAccountList> pojos = accountService.bindAccountList(uid);
			//过滤掉微信平台的c(2)端账户
			Iterator<BindAccountList> it = pojos.iterator();
			while(it.hasNext()){
				BindAccountList b = it.next();
				if(b.getPlatform().intValue() == 1 && b.getFlag() != null && b.getFlag().intValue() == 2)
					it.remove();
			}
			return JSONObject.toJSONString(new RespMsg<>(pojos));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.toJSONString(new RespMsg<>(Status.error,
					RespMessage.getRespMsg(Status.error)));
		}
	}
	/**
	 * 2.0
	 * 获取b端用户绑定账户列表
	 * 
	 * @param category 1:钓场，2:店铺
	 * @param id 根据category确定此值为对应的钓场id或店铺id
	 * @return
	 */
//	@GET
//	@Path("b/bindAccountList/{category}/{id}")
	@RequestMapping(value = "b/bindAccountList/{category}/{id}",method = RequestMethod.GET)
	public String getBindAccountList(@PathVariable("category") Integer category,@PathVariable("id") Long id) {
		try {
			logger.debug("category:{},id:{}", category,id);
			Long uid = null;
			if(category.intValue() == 1){
				FishSite fs = fishSiteService.selectById(id.intValue());
				if(fs == null || fs.getStatus() != 1){
					return JSONObject.toJSONString(new RespMsg<>(Status.fish_site_not_exists,RespMessage.getRespMsg(Status.fish_site_not_exists)));
				}
				uid = fs.getUid();
			}else if(category.intValue() == 2){
				FishShop fss = fishShopService.getFishShopById(id);
				if(fss == null || fss.getStatus() != 1){
					return JSONObject.toJSONString(new RespMsg<>(Status.FISH_SHOP_NOT_EXISTS,RespMessage.getRespMsg(Status.FISH_SHOP_NOT_EXISTS)));
				}
				uid = fss.getUid().longValue();
			}else{
				return JSONObject.toJSONString(new RespMsg<>(Status.value_is_null_or_error,RespMessage.getRespMsg(Status.value_is_null_or_error)));
			}
			List<BindAccountList> pojos = accountService.bindAccountList(uid);
			//过滤掉微信平台的c(2)端账户
			Iterator<BindAccountList> it = pojos.iterator();
			while(it.hasNext()){
				BindAccountList b = it.next();
				if(b.getPlatform().intValue() == 1 && b.getFlag() != null && b.getFlag().intValue() == 2)
					it.remove();
			}
			return JSONObject.toJSONString(new RespMsg<>(pojos));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.toJSONString(new RespMsg<>(Status.error,
					RespMessage.getRespMsg(Status.error)));
		}
	}
	
	/**
	 * 获取c端用户绑定账户列表
	 * 
	 * @param uid
	 * @return
	 */
//	@GET
//	@Path("c/bindAccountList/{uid}")
	@RequestMapping(value = "c/bindAccountList/{uid}",method = RequestMethod.GET)
	public String getCBindAccountList(@PathVariable("uid") Long uid) {
		try {
			logger.debug("uid:{}", uid);
			if (uid == null) {
				return JSONObject.toJSONString(new RespMsg<>(
						Status.value_is_null_or_error, RespMessage
						.getRespMsg(Status.value_is_null_or_error)));
			}
			List<BindAccountList> pojos = accountService.bindAccountList(uid);
			//过滤掉微信平台的b(1)端账户
			Iterator<BindAccountList> it = pojos.iterator();
			while(it.hasNext()){
				BindAccountList b = it.next();
				if(b.getPlatform().intValue() == 1){
					b.setSingleMinFee(200);
				}
				if(b.getPlatform().intValue() == 1 && b.getFlag() != null && b.getFlag().intValue() == 1)
					it.remove();
			}
			return JSONObject.toJSONString(new RespMsg<>(pojos));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.toJSONString(new RespMsg<>(Status.error,
					RespMessage.getRespMsg(Status.error)));
		}
	}

	/**
	 * 钓场提现
	 * 
	 * {"uid":1,"withdrawFee":23,"platform":1,"bindAccountNum":""}
	 * 
	 * withdrawFee: 提现金额(单位：分)
	 * 
	 * @param json
	 * @return
	 */
//	@POST
//	@Path("withdraw")
	@RequestMapping(value = "withdraw",method = RequestMethod.POST,consumes = "application/json")
	public String accountWithdraw(@RequestBody JSONObject json) {
		try {
			logger.debug("json:{}", json);
			Long uid = json == null ? null : json.getLong("uid");
			Integer withdrawFee = json == null ? null : json
					.getInteger("withdrawFee");
			Integer platform = json == null ? null : json
					.getInteger("platform");
			String bindAccountNum = json == null ? null : json
					.getString("bindAccountNum");
			
//			Integer delayForDay = json ==null ? 0:json.getInteger("delayForDay");
			Integer delayForDay = 0;
			if (json != null && json.containsKey("delayForDay")) {
				delayForDay = json.getInteger("delayForDay");
			}
			
			if (uid == null || withdrawFee == null || platform == null
					|| bindAccountNum == null) {
				return JSONObject.toJSONString(new RespMsg<>(
						Status.value_is_null_or_error, RespMessage
								.getRespMsg(Status.value_is_null_or_error)));
			}
			// 创建申请订单业务
			WithdrawOrders wo = new WithdrawOrders();
			wo.setUid(uid);
			wo.setTradeFee(withdrawFee);
			wo.setPlatform(platform);
			wo.setTradeAccount(bindAccountNum);
			wo.setDelayForDay(delayForDay);
			int rs = accountService.accountWithdrawApply(wo);
			// 判断如果是0表示提现申请成功
			if (rs == 0 && wo.getDelayForDay() == 0) {
				// 防止因为平台资金不够导致付款失败
				accountService.accountWithdraw(wo);
			}
			return JSONObject.toJSONString(new RespMsg<>(rs, RespMessage.getRespMsg(rs)));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.toJSONString(new RespMsg<>(Status.error,RespMessage.getRespMsg(Status.error)));
		}
	}
	
	/**
	 * 合伙人提现
	 * 
	 * {"uid":1,"withdrawFee":23,"platform":1,"bindAccountNum":""}
	 * 
	 * withdrawFee: 提现金额(单位：分)
	 * 
	 * @param json
	 * @return
	 */
//	@POST
//	@Path("partner/withdraw")
	@RequestMapping(value = "partner/withdraw",method = RequestMethod.POST,consumes = "application/json")
	public String partnerAccountWithdraw(@RequestBody JSONObject json) {
		try {
			logger.debug("json:{}", json);
//			Long uid = json == null ? null : json.getLong("uid");
//			Integer withdrawFee = json == null ? null : json.getInteger("withdrawFee");
//			Integer platform = json == null ? null : json.getInteger("platform");
//			String bindAccountNum = json == null ? null : json.getString("bindAccountNum");
//			
//			if (uid == null || withdrawFee == null || platform == null
//					|| bindAccountNum == null) {
//				return JSONObject.toJSONString(new RespMsg<>(Status.value_is_null_or_error, RespMessage.getRespMsg(Status.value_is_null_or_error)));
//			}
//			// 创建申请订单业务
//			PartnerWithdrawOrders pwo = new PartnerWithdrawOrders();
//			pwo.setUid(uid);
//			pwo.setTradeFee(withdrawFee);
//			pwo.setPlatform(platform);
//			pwo.setTradeAccount(bindAccountNum);
//			int rs = accountService.partnerAccountWithdrawApply(pwo);
//			// 判断如果是0表示提现申请成功
//			if (rs == 0) {
//				// 防止因为平台资金不够导致付款失败
//				accountService.partnerAccountWithdraw(pwo);
//			}
//			return JSONObject.toJSONString(new RespMsg<>(rs, RespMessage.getRespMsg(rs)));
			return JSONObject.toJSONString(new RespMsg<>(Status.value_is_null_or_error,"此功能不开放"));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.toJSONString(new RespMsg<>(Status.error,RespMessage.getRespMsg(Status.error)));
		}
	}

	/**
	 * 店铺提现
	 * 
	 * {"uid":1,"withdrawFee":23,"platform":1,"bindAccountNum":"","shopId":123,"delayForDay":0}
	 * 
	 * withdrawFee: 提现金额(单位：分)
	 * 
	 * @param json
	 * @return
	 */
//	@POST
//	@Path("shop/withdraw")
	@RequestMapping(value = "shop/withdraw",method = RequestMethod.POST,consumes = "application/json")
	public String accountStoreWithdraw(@RequestBody JSONObject json) {
		try {
			logger.debug("json:{}", json);
			Long uid = json == null ? null : json.getLong("uid");
			Integer withdrawFee = json == null ? null : json.getInteger("withdrawFee");
			Integer platform = json == null ? null : json.getInteger("platform");
			String bindAccountNum = json == null ? null : json.getString("bindAccountNum");
			Long shopId = json == null ? null : json.getLong("shopId");
			Integer delayForDay = json ==null ? null : json.getInteger("delayForDay");
			
			if (uid == null || withdrawFee == null || platform == null || shopId == null
					|| bindAccountNum == null || delayForDay == null) {
				return JSONObject.toJSONString(new RespMsg<>(
						Status.value_is_null_or_error, RespMessage
								.getRespMsg(Status.value_is_null_or_error)));
			}
			// 创建申请订单业务
			ShopWithdrawOrders wo = new ShopWithdrawOrders();
			wo.setUid(uid);
			wo.setShopId(shopId);
			wo.setTradeFee(withdrawFee);
			wo.setPlatform(platform);
			wo.setTradeAccount(bindAccountNum);
			wo.setDelayForDay(delayForDay);
			int rs = accountService.shopAccountWithdrawApply(wo);
			// 判断如果是0表示提现申请成功
			if (rs == 0 && wo.getDelayForDay() == 0) {
				// 防止因为平台资金不够导致付款失败
				accountService.shopAccountWithdraw(wo);
			}
			return JSONObject.toJSONString(new RespMsg<>(rs, RespMessage.getRespMsg(rs)));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.toJSONString(new RespMsg<>(Status.error,RespMessage.getRespMsg(Status.error)));
		}
	}
	
	/**
	 * 获取钓场主提现订单列表
	 * 
	 * @param uid
	 * @param index
	 * @param size
	 * @return
	 */
//	@GET
//	@Path("list/withdrawOrder/{uid}/{index}/{size}")
	@RequestMapping(value = "list/withdrawOrder/{uid}/{index}/{size}",method = RequestMethod.GET)
	public String withdrawOrder(@PathVariable("uid") Long uid,
			@PathVariable("index") String index, @PathVariable("size") Integer size) {
		try {
			logger.debug("uid:{},index:{},size:{}", uid, index, size);
			if (uid == null || index == null || size == null) {
				return JSONObject.toJSONString(new RespMsg<>(Status.value_is_null_or_error, RespMessage.getRespMsg(Status.value_is_null_or_error)));
			}
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("uid", uid);
			map.put("index", index);
			map.put("size", size);
			List<WithdrawOrders> list = accountService.withdrawOrderList(map);
			return JSONObject.toJSONString(new RespMsg<>(list));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.toJSONString(new RespMsg<>(Status.error,
					RespMessage.getRespMsg(Status.error)));
		}
	}
	
	/**
	 * 获取合伙人提现订单列表
	 * 
	 * @param uid
	 * @param index
	 * @param size
	 * @return
	 */
//	@GET
//	@Path("list/partnerWithdrawOrder/{uid}/{index}/{size}")
	@RequestMapping(value = "list/partnerWithdrawOrder/{uid}/{index}/{size}",method = RequestMethod.GET)
	public String partnerWithdrawOrder(@PathVariable("uid") Long uid,
			@PathVariable("index") String index, @PathVariable("size") Integer size) {
		try {
			logger.debug("uid:{},index:{},size:{}", uid, index, size);
			if (uid == null || index == null || size == null) {
				return JSONObject.toJSONString(new RespMsg<>(
						Status.value_is_null_or_error, RespMessage
						.getRespMsg(Status.value_is_null_or_error)));
			}
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("uid", uid);
			map.put("index", index);
			map.put("size", size);
			List<PartnerWithdrawOrders> list = accountService.partnerWithdrawOrderList(map);
			return JSONObject.toJSONString(new RespMsg<>(list));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.toJSONString(new RespMsg<>(Status.error,
					RespMessage.getRespMsg(Status.error)));
		}
	}

	
	/**
	 * 获取店铺提现订单列表
	 * 
	 * @param uid
	 * @param index
	 * @param size
	 * @return
	 */
//	@GET
//	@Path("list/shopWithdrawOrder/{uid}/{shopId}/{index}/{size}")
	@RequestMapping(value = "list/shopWithdrawOrder/{uid}/{shopId}/{index}/{size}",method = RequestMethod.GET)
	public String shopWithdrawOrder(@PathVariable("uid") Long uid,@PathVariable("shopId") Long shopId,
			@PathVariable("index") String index, @PathVariable("size") Integer size) {
		try {
			logger.debug("uid:{},shopId:{},index:{},size:{}", uid,shopId, index, size);
			if (uid == null || index == null || size == null) {
				return JSONObject.toJSONString(new RespMsg<>(Status.value_is_null_or_error, RespMessage.getRespMsg(Status.value_is_null_or_error)));
			}
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("uid", uid);
			map.put("shopId", shopId);
			map.put("index", index);
			map.put("size", size);
			List<ShopWithdrawOrders> list = accountService.shopWithdrawOrderList(map);
			return JSONObject.toJSONString(new RespMsg<>(list));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.toJSONString(new RespMsg<>(Status.error,
					RespMessage.getRespMsg(Status.error)));
		}
	}
	
	/**
	 * 钓场主绑定提现账户
	 * 
	 * {"uid":1,"bindAccountNum":"","platform":1}
	 * 
	 * @param json
	 * @return
	 */
//	@POST
//	@Path("bindAccount")
	@RequestMapping(value = "bindAccount",method = RequestMethod.POST,consumes = "application/json")
	public String bindAccount(@RequestBody JSONObject json) {
		try {
			logger.debug("json:{}", json);
			BindAccountList bal = json == null ? null : JSONObject
					.toJavaObject(json, BindAccountList.class);
			if (bal == null || StringUtils.isBlank(bal.getBindAccountNum())
					|| bal.getUid() == null || bal.getPlatform() == null) {
				return JSONObject.toJSONString(new RespMsg<>(
						Status.value_is_null_or_error, RespMessage
								.getRespMsg(Status.value_is_null_or_error)));
			}
			Timestamp t = new Timestamp(ExDateUtils.getCalendar()
					.getTimeInMillis());
			bal.setCreateTime(t);
			bal.setTradeUpdateTime(t);
			bal.setCurrentSumFee(0);
			bal.setTradeAmount(0);
			bal.setBindTime(t);
			if(bal.getPlatform() == 1){
				bal.setFlag(1);
			}
			int rs = accountService.bindAccount(bal);
			return JSONObject.toJSONString(new RespMsg<>(rs, RespMessage
					.getRespMsg(rs)));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.toJSONString(new RespMsg<>(Status.error,
					RespMessage.getRespMsg(Status.error)));
		}
	}
	
	/**
	 * 合伙人绑定提现账户
	 * 
	 * {"uid":1,"bindAccountNum":"","platform":1}
	 * 
	 * @param json
	 * @return
	 */
//	@POST
//	@Path("partner/bindAccount")
	@RequestMapping(value = "partner/bindAccount",method = RequestMethod.POST,consumes = "application/json")
	public String partnerBindAccount(@RequestBody JSONObject json) {
		try {
			logger.debug("json:{}", json);
//			BindAccountList bal = json == null ? null : JSONObject
//					.toJavaObject(json, BindAccountList.class);
//			if (bal == null || StringUtils.isBlank(bal.getBindAccountNum())
//					|| bal.getUid() == null || bal.getPlatform() == null) {
//				return JSONObject.toJSONString(new RespMsg<>(
//						Status.value_is_null_or_error, RespMessage
//						.getRespMsg(Status.value_is_null_or_error)));
//			}
//			Timestamp t = new Timestamp(ExDateUtils.getCalendar()
//					.getTimeInMillis());
//			bal.setCreateTime(t);
//			bal.setTradeUpdateTime(t);
//			bal.setCurrentSumFee(0);
//			bal.setTradeAmount(0);
//			bal.setBindTime(t);
//			if(bal.getPlatform() == 1){
//				bal.setFlag(2);
//			}
//			int rs = accountService.partnerBindAccount(bal);
//			return JSONObject.toJSONString(new RespMsg<>(rs, RespMessage
//					.getRespMsg(rs)));
			return JSONObject.toJSONString(new RespMsg<>(Status.value_is_null_or_error,"此功能已关闭"));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.toJSONString(new RespMsg<>(Status.error,
					RespMessage.getRespMsg(Status.error)));
		}
	}
	
	/**
	 * 店铺主绑定提现账户
	 * 
	 * {"uid":1,"bindAccountNum":"","platform":1,"shopId":122}
	 * 
	 * @param json
	 * @return
	 */
//	@POST
//	@Path("shop/bindAccount")
	@RequestMapping(value = "shop/bindAccount",method = RequestMethod.POST,consumes = "application/json")
	public String storeBindAccount(@RequestBody JSONObject json) {
		try {
			logger.debug("json:{}", json);
			BindAccountList bal = json == null ? null : JSONObject
					.toJavaObject(json, BindAccountList.class);
			Long shopId = json == null ? null : json.getLong("shopId");
			if (bal == null || StringUtils.isBlank(bal.getBindAccountNum())
					|| bal.getUid() == null || bal.getPlatform() == null
					|| shopId == null) {
				return JSONObject.toJSONString(new RespMsg<>(Status.value_is_null_or_error, RespMessage
						.getRespMsg(Status.value_is_null_or_error)));
			}
			Timestamp t = new Timestamp(ExDateUtils.getCalendar()
					.getTimeInMillis());
			bal.setCreateTime(t);
			bal.setTradeUpdateTime(t);
			bal.setCurrentSumFee(0);
			bal.setTradeAmount(0);
			bal.setBindTime(t);
			if(bal.getPlatform() == 1){
				bal.setFlag(1);
			}
			int rs = accountService.shopBindAccount(bal,shopId);
			return JSONObject.toJSONString(new RespMsg<>(rs, RespMessage
					.getRespMsg(rs)));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.toJSONString(new RespMsg<>(Status.error,
					RespMessage.getRespMsg(Status.error)));
		}
	}

	/**
	 * 钓场主解除绑定账号
	 * 
	 * @return
	 */
//	@DELETE
//	@Path("{uid}/{platform}/{bindAccountNum}")
	@RequestMapping(value = "{uid}/{platform}/{bindAccountNum}",method = RequestMethod.DELETE)
	public String deleteBindAccount(@PathVariable("uid") Long uid,
			@PathVariable("platform") Integer platform,
			@PathVariable("bindAccountNum") String bindAccountNum) {
		try {
			logger.debug("uid:{},platform:{},bindAccountNum:{}", uid, platform,
					bindAccountNum);
			BindAccountList bal = new BindAccountList();
			bal.setBindAccountNum(bindAccountNum);
			bal.setUid(uid);
			bal.setPlatform(platform);
			int rs = accountService.deleteBindAccount(bal);
			return JSONObject.toJSONString(new RespMsg<>(rs, RespMessage
					.getRespMsg(rs)));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.toJSONString(new RespMsg<>(Status.error,
					RespMessage.getRespMsg(Status.error)));
		}
	}
	
	/**
	 * 合伙人解除绑定账号
	 * 
	 * @return
	 */
//	@DELETE
//	@Path("partner/{uid}/{platform}/{bindAccountNum}")
	@RequestMapping(value = "partner/{uid}/{platform}/{bindAccountNum}",method = RequestMethod.DELETE)
	public String partnerDeleteBindAccount(@PathVariable("uid") Long uid,
			@PathVariable("platform") Integer platform,
			@PathVariable("bindAccountNum") String bindAccountNum) {
		try {
			logger.debug("uid:{},platform:{},bindAccountNum:{}", uid, platform,
					bindAccountNum);
			BindAccountList bal = new BindAccountList();
			bal.setBindAccountNum(bindAccountNum);
			bal.setUid(uid);
			bal.setPlatform(platform);
			int rs = accountService.partnerDeleteBindAccount(bal);
			return JSONObject.toJSONString(new RespMsg<>(rs, RespMessage
					.getRespMsg(rs)));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.toJSONString(new RespMsg<>(Status.error,
					RespMessage.getRespMsg(Status.error)));
		}
	}

	

	/**
	 * 店铺解除绑定账号
	 * 
	 * @return
	 */
//	@DELETE
//	@Path("shop/{uid}/{shopId}/{platform}/{bindAccountNum}")
	@RequestMapping(value = "shop/{uid}/{shopId}/{platform}/{bindAccountNum}",method = RequestMethod.DELETE)
	public String shopDeleteBindAccount(@PathVariable("uid") Long uid,
			@PathVariable("shopId") Long shopId,
			@PathVariable("platform") Integer platform,
			@PathVariable("bindAccountNum") String bindAccountNum) {
		try {
			logger.debug("uid:{},platform:{},bindAccountNum:{},shopId:{}", uid, platform,
					bindAccountNum,shopId);
			BindAccountList bal = new BindAccountList();
			bal.setBindAccountNum(bindAccountNum);
			bal.setUid(uid);
			bal.setPlatform(platform);
			int rs = accountService.shopDeleteBindAccount(bal,shopId);
			return JSONObject.toJSONString(new RespMsg<>(rs, RespMessage
					.getRespMsg(rs)));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.toJSONString(new RespMsg<>(Status.error,
					RespMessage.getRespMsg(Status.error)));
		}
	}
	
	// =================================test=============================
//	@GET
//	@Path("test/{tradeNo}")
//	public String pay(@PathVariable("tradeNo") String tradeNo) throws Exception {
//		try {
//			WithdrawOrders wo = new WithdrawOrders();
//			wo.setTradeNo(tradeNo);
//			int rs = accountService.accountWithdraw(wo);
//			return JSONObject.toJSONString(new RespMsg<>(rs, RespMessage
//					.getRespMsg(rs)));
//		} catch (Exception e) {
//			logger.error(e.getMessage(), e);
//			return JSONObject.toJSONString(new RespMsg<>(Status.error,
//					RespMessage.getRespMsg(Status.error)));
//		}
//	}
}
