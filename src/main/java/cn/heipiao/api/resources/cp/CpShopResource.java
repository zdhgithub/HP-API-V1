/**
 * 
 */
package cn.heipiao.api.resources.cp;

import java.util.HashMap;
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

import cn.heipiao.api.constant.FishShopConstant;
import cn.heipiao.api.constant.RespMessage;
import cn.heipiao.api.pojo.DictConfig;
import cn.heipiao.api.pojo.FishShop;
import cn.heipiao.api.pojo.PartnerShopRateConfig;
import cn.heipiao.api.pojo.RespMsg;
import cn.heipiao.api.pojo.ShopFinance;
import cn.heipiao.api.pojo.User;
import cn.heipiao.api.resources.Status;
import cn.heipiao.api.service.AccountService;
import cn.heipiao.api.service.ConfigService;
import cn.heipiao.api.service.FishShopService;
import cn.heipiao.api.service.ShopTradeOrdersService;
import cn.heipiao.api.service.UserOpService;
import io.swagger.annotations.Api;

/**
 * @author wzw
 * @date 2016年11月30日
 * @version 2.2
 */
@Api(tags = "店铺管理模块")
@RestController
@RequestMapping(value = "cp/fshop",produces="application/json")
public class CpShopResource {
	
	private static final Logger logger = LoggerFactory.getLogger(CpShopResource.class);

	
	
	@Resource
	private FishShopService fishShopService;
	
	@Resource
	private UserOpService userService;
	
	@Resource
	private ConfigService configService;
	
	@Resource
	private AccountService accountService;
	
	@Resource
	private ShopTradeOrdersService shopTradeOrdersService;
	/**
	 * 指定渔具店店主(可多次指定某店店主)
	 * @param json
	 * @return
	 */
	@SuppressWarnings("rawtypes")
//	@Path("/namepass")
//	@PUT
	@RequestMapping(value = "namepass",method = RequestMethod.PUT,consumes = "application/json")
	public String namepass(@RequestBody JSONObject json) {
		try {
			logger.debug("json:{}", json);
			Integer uid = json == null ? null : json.getInteger("uid");
			Long id = json == null ? null : json.getLong("id");
			if (uid == null || id == null) {
				return JSONObject.toJSONString(new RespMsg<>(
						Status.value_is_null_or_error, RespMessage
								.getRespMsg(Status.value_is_null_or_error)));
			}
			User u = userService.queryUserById(uid.longValue());
			if (u == null) {
				return JSONObject.toJSONString(new RespMsg<>(
						Status.no_such_user, RespMessage
								.getRespMsg(Status.no_such_user)));
			}
			FishShop pojo = fishShopService.getFishShopById(id, null);
			if (pojo == null)
				return JSONObject.toJSONString(new RespMsg<>(
						Status.FISH_SHOP_NOT_EXISTS, RespMessage
								.getRespMsg(Status.FISH_SHOP_NOT_EXISTS)));
			if(pojo.getUid() == null){
				//如果已实名，再次实名时，则不修改AuthStatus，原因是有可以已进行签约认证
				pojo.setAuthStatus(FishShopConstant.AUTH_NAME_PASS);
			}
			pojo.setUid(uid);
			int rs = fishShopService.setFishShopUid(pojo);
			if(rs == 1){
				return JSONObject.toJSONString(new RespMsg<>());
			}else{
				return JSONObject.toJSONString(new RespMsg<>(
						Status.UPDATE_DATABASE_FAIL, RespMessage
						.getRespMsg(Status.UPDATE_DATABASE_FAIL)));
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.toJSONString(new RespMsg(Status.error,
					RespMessage.getRespMsg(Status.error)));
		}
	}
	
	
	/**
	 * 指定渔具店置顶
	 * @param json
	 * @return
	 */
	@SuppressWarnings("rawtypes")
//	@Path("/top")
//	@PUT
	@RequestMapping(value = "top",method = RequestMethod.PUT,consumes = "application/json")
	public String top(@RequestBody JSONObject json){
		try{
			logger.debug("json:{}", json);
			Long id = json == null ? null : json.getLong("id");
			if (id == null) {
				return JSONObject.toJSONString(new RespMsg<>(Status.value_is_null_or_error, 
						RespMessage.getRespMsg(Status.value_is_null_or_error)));
			}
			FishShop pojo = fishShopService.getFishShopById(id, null);
			if (pojo == null){
				return JSONObject.toJSONString(new RespMsg<>(Status.FISH_SHOP_NOT_EXISTS, 
						RespMessage.getRespMsg(Status.FISH_SHOP_NOT_EXISTS)));
			}
			Integer top = pojo.getTop();
			if(top == 0){
				top = fishShopService.getMaxTop() + 1;
			}else{
				top = 0;
			}
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", id);
			map.put("top", top);
			int rs = fishShopService.setFishShopTop(map);
			if(rs == 1){
				return JSONObject.toJSONString(new RespMsg<>());
			}else{
				return JSONObject.toJSONString(new RespMsg<>(Status.UPDATE_DATABASE_FAIL, 
						RespMessage.getRespMsg(Status.UPDATE_DATABASE_FAIL)));
			}
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			return JSONObject.toJSONString(new RespMsg(Status.error, RespMessage.getRespMsg(Status.error)));
		}
	}
	
	
	/**
	 * 指定渔具店执行签约认证
	 * @param json
	 * @return
	 */
	
//	@SuppressWarnings("rawtypes")
//	@Path("/pactpass")
//	@PUT
	@RequestMapping(value = "pactpass",method = RequestMethod.PUT,consumes = "application/json")
	public String pactpass(@RequestBody JSONObject json) {
		try {
			logger.debug("json:{}", json);
			Long id = json == null ? null : json.getLong("id");
			Integer uid = json == null ? null : json.getInteger("uid");
			Integer signUid = json == null ? null : json.getInteger("signUid");
			if (id == null) {
				return JSONObject.toJSONString(new RespMsg<>(
						Status.value_is_null_or_error, RespMessage
								.getRespMsg(Status.value_is_null_or_error)));
			}
			
			FishShop pojo = fishShopService.getFishShopById(id, null);
			if (pojo == null)
				return JSONObject.toJSONString(new RespMsg<>(
						Status.FISH_SHOP_NOT_EXISTS, RespMessage
								.getRespMsg(Status.FISH_SHOP_NOT_EXISTS)));
			if(pojo.getSignUid() != null && pojo.getSignUid()!=0){
				return JSONObject.toJSONString(new RespMsg<>(
						Status.FISH_SHOP_SIGN_ALREADY, RespMessage
								.getRespMsg(Status.FISH_SHOP_SIGN_ALREADY)));
			}
			if(pojo.getSignStatus()!=null && pojo.getSignStatus()==2){
			return JSONObject.toJSONString(new RespMsg<>(
			Status.FISH_SHOP_SIGN_ALREADY, RespMessage
					.getRespMsg(Status.FISH_SHOP_SIGN_ALREADY)));
			}
			pojo.setSignUid(signUid);
			
			if(pojo.getUid() == null && uid == null){
				//如果此店未进行实名认证，并且也没有提供实名认证的用户时
				return JSONObject.toJSONString(new RespMsg<>(
						Status.FISH_SHOP_NOT_NAME_PASS, RespMessage
								.getRespMsg(Status.FISH_SHOP_NOT_NAME_PASS)));
			}
			
			int rs = 0;
			if(pojo.getUid() == null && uid != null){
				//提供实名认证的用户时
				User u = userService.queryUserById(uid.longValue());
				if (u == null) {
					return JSONObject.toJSONString(new RespMsg<>(
							Status.no_such_user, RespMessage
									.getRespMsg(Status.no_such_user)));
				}
				namepass(json);
				pojo.setUid(uid);
				fishShopService.setFishShopUid(pojo);
				rs = fishShopService.setFishShopPactPass(pojo);
			}else{
				rs = fishShopService.setFishShopPactPass(pojo);
			}
			if(rs == 1){
				return JSONObject.toJSONString(new RespMsg<>());
			}else{
				return JSONObject.toJSONString(new RespMsg<>(
						Status.UPDATE_DATABASE_FAIL, RespMessage
						.getRespMsg(Status.UPDATE_DATABASE_FAIL)));
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.toJSONString(new RespMsg<>(Status.error,
					RespMessage.getRespMsg(Status.error)));
		}
	}
	
	
	/**
	 * 指定渔具店设置规模分类（cp）
	 * @param json
	 * @return
	 */
	@SuppressWarnings("rawtypes")
//	@Path("/shop/scale")
//	@PUT
	@RequestMapping(value = "shop/scale",method = RequestMethod.PUT,consumes = "application/json")
	public String scale(@RequestBody JSONObject json){
		try{
			logger.debug("json:{}", json);
			Long id = json == null ? null : json.getLong("id");
			Integer scale = json == null ? null : json.getInteger("scale");
			if (id == null || scale == null) {
				return JSONObject.toJSONString(new RespMsg<>(Status.value_is_null_or_error, 
						RespMessage.getRespMsg(Status.value_is_null_or_error)));
			}
			FishShop pojo = fishShopService.getFishShopById(id, null);
			if (pojo == null){
				return JSONObject.toJSONString(new RespMsg<>(Status.FISH_SHOP_NOT_EXISTS, 
						RespMessage.getRespMsg(Status.FISH_SHOP_NOT_EXISTS)));
			}
			DictConfig dconfig = configService.queryConfigByTypeValue("fs_scale", scale.toString());
			if(dconfig == null){
				return JSONObject.toJSONString(new RespMsg<>(Status.FISH_SHOP_SCALE_NOT_EXISTS, 
						RespMessage.getRespMsg(Status.FISH_SHOP_SCALE_NOT_EXISTS)));
			}
			pojo.setScale(scale);
			int cnt = fishShopService.setFishShopScale(pojo);
			logger.debug("cnt:{}", cnt);
			return JSONObject.toJSONString(new RespMsg<>());
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			return JSONObject.toJSONString(new RespMsg(Status.error, 
					RespMessage.getRespMsg(Status.error)));
		}
	}
	
	/**
	 * 指定渔具店设置平台备注及标星（cp）
	 * @param json
	 * @return
	 */
	@SuppressWarnings("rawtypes")
//	@Path("/shop/marking")
//	@PUT
	@RequestMapping(value = "shop/marking",method = RequestMethod.PUT,consumes = "application/json")
	public String shopMarking(@RequestBody JSONObject json){
		try{
			logger.debug("json:{}", json);
			Long id = json == null ? null : json.getLong("id");
			Integer flag = json == null ? null : json.getInteger("flag");
			String sysRemarks = json == null ? null : json.getString("sysRemarks");
			if (id == null || !(flag == null || sysRemarks == null)) {
				return JSONObject.toJSONString(new RespMsg<>(Status.value_is_null_or_error, 
						RespMessage.getRespMsg(Status.value_is_null_or_error)));
			}
			FishShop pojo = fishShopService.getFishShopById(id, null);
			if (pojo == null){
				return JSONObject.toJSONString(new RespMsg<>(Status.FISH_SHOP_NOT_EXISTS, 
						RespMessage.getRespMsg(Status.FISH_SHOP_NOT_EXISTS)));
			}
			List<Integer> fsust = FishShopConstant.getMaps(FishShopConstant.FISH_SHOP_FLAG);
			if(flag != null && !fsust.contains(flag)){
				return JSONObject.toJSONString(new RespMsg<>(Status.FISH_SHOP_FLAG_NOT_EXISTS, 
						RespMessage.getRespMsg(Status.FISH_SHOP_FLAG_NOT_EXISTS)));
			}
			FishShop fishshop = new FishShop();
			fishshop.setId(id);
			fishshop.setFlag(flag);
			fishshop.setSysRemarks(sysRemarks);
			fishShopService.setFishShop(fishshop);
			
			return JSONObject.toJSONString(new RespMsg<>());
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			return JSONObject.toJSONString(new RespMsg(Status.error, 
					RespMessage.getRespMsg(Status.error)));
		}
	}
	
	
	/**
	 * 指定渔具店设置状态（cp）
	 * @param json
	 * @return
	 */
	@SuppressWarnings("rawtypes")
//	@Path("/shop/check")
//	@PUT
	@RequestMapping(value = "shop/check",method = RequestMethod.PUT,consumes = "application/json")
	public String shopCheck(@RequestBody JSONObject json){
		try{
			logger.debug("json:{}", json);
			Long id = json == null ? null : json.getLong("id");
			Integer status = json == null ? null : json.getInteger("status");
			if (id == null || status == null) {
				return JSONObject.toJSONString(new RespMsg<>(Status.value_is_null_or_error, 
						RespMessage.getRespMsg(Status.value_is_null_or_error)));
			}
			FishShop pojo = fishShopService.getFishShopById(id, null);
			if (pojo == null){
				return JSONObject.toJSONString(new RespMsg<>(Status.FISH_SHOP_NOT_EXISTS, 
						RespMessage.getRespMsg(Status.FISH_SHOP_NOT_EXISTS)));
			}
			
			List<Integer> fss = FishShopConstant.getMaps(FishShopConstant.FISH_SHOP_STATUS);
			if(!fss.contains(status)){
				return JSONObject.toJSONString(new RespMsg<>(Status.FISH_SHOP_STATUS_NOT_EXISTS, 
						RespMessage.getRespMsg(Status.FISH_SHOP_STATUS_NOT_EXISTS)));
			}
			
			FishShop fishshop = new FishShop();
			fishshop.setId(id);
			fishshop.setStatus(status);
			
			fishShopService.setFishShop(fishshop);
			
			return JSONObject.toJSONString(new RespMsg<>());
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			return JSONObject.toJSONString(new RespMsg(Status.error, 
					RespMessage.getRespMsg(Status.error)));
		}
	}
	
	/**
	 * 添加渔具店（cp）
	 * @param json
	 * @return
	 */
	@SuppressWarnings("rawtypes")
//	@Path("/create")
//	@POST
	@RequestMapping(value = "create",method = RequestMethod.POST,consumes = "application/json")
	public String create(@RequestBody JSONObject json) {
		try {
			logger.debug("json:{}", json);
			FishShop pojo = json == null ? null : JSONObject.toJavaObject(json, FishShop.class);
			
			if (pojo == null 
					|| StringUtils.isBlank(pojo.getName())
					|| pojo.getRegionId() == null 
					|| pojo.getCityId() == null
					|| pojo.getProvinceId() == null
					|| StringUtils.isBlank(pojo.getAddr())
					|| pojo.getLongitude() == null || pojo.getLongitude() < 0 || pojo.getLongitude() > 180 
					|| pojo.getLatitude() == null || pojo.getLatitude() < 0 || pojo.getLatitude() > 90
					|| pojo.getType() == null) {
				
				return JSONObject.toJSONString(new RespMsg<String>(Status.value_is_null_or_error));
				
			}

			List<Integer> fst = FishShopConstant.getMaps(FishShopConstant.FISH_SHOP_TYPE);
			if(!fst.contains(pojo.getType())){
				return JSONObject.toJSONString(new RespMsg(
						Status.FISH_SHOP_TYPE_NOT_EXISTS, RespMessage
								.getRespMsg(Status.FISH_SHOP_TYPE_NOT_EXISTS)));
			}
			pojo.setFocusCount(0);
			pojo.setAuthStatus(FishShopConstant.AUTH_USER_PASS);
			pojo.setStatus(FishShopConstant.STATUS_0);
			pojo.setFlag(FishShopConstant.FLAG_OF);
			
			pojo.setId(fishShopService.addFishShop(pojo));
			return JSONObject.toJSONString(new RespMsg<FishShop>(pojo));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.toJSONString(new RespMsg(Status.error));
		}
	}
	
	/**
	 * cp
	 * 店铺主变更 
	 * 
	 * {"shopId":123,"newUid":456}
	 * 
	 * shopId:旧店铺id
	 * newUid:新店铺主uid
	 * 
	 * @return
	 */
//	@PUT
//	@Path("change")
	@RequestMapping(value = "change",method = RequestMethod.PUT,consumes = "application/json")
	public String changeFishSite(@RequestBody JSONObject json){
		try {
			logger.info("json:{}",json);
			Long shopId = json == null ? null : json.getLong("shopId");
			Long newUid = json == null ? null : json.getLong("newUid");
			if(shopId == null || newUid == null){
				return JSONObject.toJSONString(new RespMsg<>(Status.value_is_null_or_error, RespMessage.getRespMsg(Status.value_is_null_or_error)));
			}
			int rs = fishShopService.changeFishShop(shopId,newUid);
			return JSONObject.toJSONString(new RespMsg<>(rs));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.toJSONString(new RespMsg<>(Status.error,RespMessage.getRespMsg(Status.error)));
		}
	}
	
	/**
	 * {"shopId":123,"uid":1,"isAuthCoupon":true}
	 * cp
	 * 授权优惠券发放功能
	 * @param json
	 * @return
	 */
//	@PUT
//	@Path("auth/coupon")
	@RequestMapping(value = "auth/coupon",method = RequestMethod.PUT,consumes = "application/json")
	public String isAuthCoupon(@RequestBody JSONObject json){
		try {
			logger.info("json:{}",json);
			Long shopId = json == null ? null : json.getLong("shopId");
			Long  uid = json == null ? null : json.getLong("uid");
			Boolean  isAuthCoupon = json == null ? null : json.getBoolean("isAuthCoupon");
			if(shopId == null || uid == null || isAuthCoupon == null){
				return JSONObject.toJSONString(new RespMsg<>(Status.value_is_null_or_error, RespMessage.getRespMsg(Status.value_is_null_or_error)));
			}
			int rs = fishShopService.modifyIsAuthCoupon(uid,shopId,isAuthCoupon);
			return JSONObject.toJSONString(new RespMsg<>(rs));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.toJSONString(new RespMsg<>(Status.error,RespMessage.getRespMsg(Status.error)));
		}
	}
	/**
	 * 获取渔具店合伙人抽成奖配置
	 * 
	 */
//	@Path("/partner/rate/config/{shopId}/{partnerId}")
//	@GET
	@RequestMapping(value = "/partner/rate/config/{shopId}/{partnerId}",method = RequestMethod.GET)
	public String selectRateConfig(@PathVariable("shopId") Long shopId,
			@PathVariable("partnerId") Integer partnerId){
		logger.debug("shopId:{},partnerId:{}",shopId,partnerId);
		if(shopId == null&&partnerId==null){
			return JSONObject.toJSONString(new RespMsg<>(Status.value_is_null_or_error));
		}
		PartnerShopRateConfig partnershopRateConfig=fishShopService.selectShopRateConfig(shopId,partnerId);
		return JSONObject.toJSONString(new RespMsg<PartnerShopRateConfig>(partnershopRateConfig));
	}
	/**
	 * 添加、修改渔具店合伙人抽成奖配置
	 */
//	@Path("/partner/rate/config")
//	@PUT
	@RequestMapping(value="/partner/rate/config",method = RequestMethod.PUT,consumes="application/json")
	public String updateRateConfig(@RequestBody JSONObject json){
		try {
			logger.debug("json:{}",json);
			Long shopId = json.getLong("shopId");
			Integer partnerId = json.getInteger("partnerId");
			Double shopRate = json.getDouble("shopRate");
			
			if(shopId ==null || partnerId == null){
				return JSONObject.toJSONString(new RespMsg<>(Status.value_is_null_or_error));
			}
			fishShopService.updateShopRateConfig(shopId,partnerId,shopRate);
			return JSONObject.toJSONString(new RespMsg<>(Status.success));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.toJSONString(new RespMsg<>(Status.error,RespMessage.getRespMsg(Status.error)));
		}
		
	}
	/**
	 * 平台收回渔具店
	 */
//	@Path("/partner/moving/{shopId}")
//	@PUT
	@RequestMapping(value="/partner/moving/{shopId}",method = RequestMethod.PUT,consumes="application/json")
	public String deletePartnerOfShop(@PathVariable("shopId")Long shopId){
		try {
			logger.debug("shopId:{}",shopId);
			if(shopId==null){
				JSONObject.toJSONString(
						new RespMsg<>(Status.value_is_null_or_error));
			}
			int rs= fishShopService.updateFishShopSignUser(shopId);
			return JSONObject.toJSONString(
					new RespMsg<>(rs));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.toJSONString(new RespMsg<>(Status.error,RespMessage.getRespMsg(Status.error)));
		}
	}
	
	/**
	 * 渔具店财务列表
	 * @param region 默认为0全部区域,指定区域传区域id
	 * @param sort 0:不排序，1:按照余额排序，2:按照提现总额排序
	 * @param page 页码从1开始
	 * @param size 每页的数量
	 * @param shopName 店铺名称关键字，以(_)开头
	 * @return
	 */
//	@GET
//	@Path("account/{region}/{shopName}/{sort}/{page}/{size}")
	@RequestMapping(value="account/{region}/{shopName}/{sort}/{page}/{size}",method = RequestMethod.GET)
	public String shopAccountList(@PathVariable("region")Integer region,@PathVariable("shopName")String shopName,
			@PathVariable("sort")Integer sort,	@PathVariable("page")Integer page,@PathVariable("size")Integer size){
		try {
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("region", region > 0 ? region : null);
			map.put("sort", sort);
			map.put("shopName", shopName != null && shopName.length() > 1 ? "%" + shopName.substring(1) + "%" : null);
			map.put("page",  page > 0 ? (page - 1) * size : 0);
			map.put("size", size);
			
			Map<String,Object> rm = new HashMap<String, Object>();
			List<ShopFinance> list = accountService.shopAccountList(map);
			Integer total = accountService.shopAccountListCount(map);
			rm.put("data", list);
			rm.put("total", total);
			return JSONObject.toJSONString(new RespMsg<>(rm));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.toJSONString(new RespMsg<>(Status.error,RespMessage.getRespMsg(Status.error)));
		}
	}

	/**
	 * 获取店铺交易订单
	 * @param shopId 店铺id
	 * @param phone 用户电话，以（_）开头
	 * @param page 页码
	 * @param size 记录数
	 * @return
	 */
//	@GET
//	@Path("account/shopOrders/{shopId}/{phone}/{page}/{size}")
	@RequestMapping(value="account/shopOrders/{shopId}/{phone}/{page}/{size}",method = RequestMethod.GET)
	public String getTradeRecord(@PathVariable("shopId") Long shopId,@PathVariable("page") Integer page,
			@PathVariable("size") Integer size,@PathVariable("phone") String phone){
		try {
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("shopId", shopId);
			map.put("phone", phone.length() > 1 ? phone.substring(1) : null);
			map.put("page", page > 0 ? (page - 1) * size : 0);
			map.put("size", size);
			Map<String ,Object> mp = shopTradeOrdersService.getTradeRecord(map);
			return JSONObject.toJSONString(new RespMsg<>(mp));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.toJSONString(new RespMsg<>(Status.error,RespMessage.getRespMsg(Status.error)));
		}
	}
	
}
