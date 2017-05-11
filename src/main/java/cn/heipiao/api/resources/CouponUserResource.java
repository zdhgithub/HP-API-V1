package cn.heipiao.api.resources;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;

import cn.heipiao.api.constant.RespMessage;
import cn.heipiao.api.pojo.CouponUser;
import cn.heipiao.api.pojo.Coupons;
import cn.heipiao.api.pojo.RespMsg;
import cn.heipiao.api.pojo.User;
import cn.heipiao.api.service.CouponUserService;
import cn.heipiao.api.service.ShopCouponsService;
import cn.heipiao.api.service.SiteCouponsService;

/**
 * @author zf
 * @version 1.0
 * @description user持有的券操作
 * @date 2016年7月21日
 * @version 1.1
 */
//@Component
//@Consumes(MediaType.APPLICATION_JSON)
//@Produces(MediaType.APPLICATION_JSON)
//@Path("coupon/user")
@RestController
@RequestMapping(value = "coupon/user",produces="application/json")
public class CouponUserResource {
	private static final Logger logger = LoggerFactory
			.getLogger(CouponUserResource.class);
	@Resource
	private CouponUserService couponUserService;
	
	@Resource
	private SiteCouponsService siteCouponsService;
	
	@Resource
	private ShopCouponsService shopCouponsService;
	
	
	/**
	 * 领取钓场券
	 * 
	 * {"uid":1,"siteId":123,"id":1}
	 * 
	 * @return
	 */
//	@POST
//	@Path("getSiteCoupon")
	@RequestMapping(value = "getSiteCoupon",method = RequestMethod.POST,consumes = "application/json")
	public String getSiteCoupon(@RequestBody JSONObject json){
		try {
			logger.info("json:{}",json);
			Long uid = json == null ? null :  json.getLong("uid");
			Long id = json == null ? null :  json.getLong("id");
			Integer siteId = json == null ? null : json.getInteger("siteId");
			if(uid == null || siteId == null || id == null){
				return JSONObject.toJSONString(new RespMsg<>(Status.value_is_null_or_error, RespMessage.getRespMsg(Status.value_is_null_or_error)));
			}
			int rs = siteCouponsService.getSiteCoupon(uid,siteId,id);
			return JSONObject.toJSONString(new RespMsg<>(rs));
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			return JSONObject.toJSONString(new RespMsg<User>(Status.error));
		}
	}
	
	/**
	 * 领取店铺券
	 * 
	 * {"uid":1,"shopId":123,"id":1}
	 * 
	 * @return
	 */
//	@POST
//	@Path("getShopCoupon")
	@RequestMapping(value = "getShopCoupon",method = RequestMethod.POST,consumes = "application/json")
	public String getShopCoupon(@RequestBody JSONObject json){
		try {
			Long uid = json == null ? null :  json.getLong("uid");
			Long id = json == null ? null :  json.getLong("id");
			Long shopId = json == null ? null : json.getLong("shopId");
			if(uid == null || shopId == null || id == null){
				return JSONObject.toJSONString(new RespMsg<>(Status.value_is_null_or_error, RespMessage.getRespMsg(Status.value_is_null_or_error)));
			}
			int rs = shopCouponsService.getShopCoupon(uid,shopId,id);
			return JSONObject.toJSONString(new RespMsg<>(rs));
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			return JSONObject.toJSONString(new RespMsg<User>(Status.error));
		}
	}
	

	/**
	 * 获取店铺可用优惠券
	 * @param uid
	 * @param shopId
	 * @return
	 * @since 1.1
	 */
//	@GET
//	@Path("list/unused/{uid}/{shopId}")
	@RequestMapping(value = "list/unused/{uid}/{shopId}",method = RequestMethod.GET)
	public String getUnusedShopCoupons(@PathVariable("uid")Long uid,@PathVariable("shopId") Long shopId){
		try {
			List<Coupons> list = couponUserService.getUnusedShopCoupons(uid,shopId);
			return JSONObject.toJSONString(new RespMsg<>(list));
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			return JSONObject.toJSONString(new RespMsg<User>(Status.error));
		}	
	}
	
	
	/**
	 * 查询用户持有的券列表
	 * 
	 * @param userId
	 * @param status
	 * @return
	 * @since 1.1
	 */
//	@Path("list/{userId}/{status}/{start}/{size}")
//	@GET
	@RequestMapping(value = "list/{userId}/{status}/{start}/{size}",method = RequestMethod.GET)
	public String getCouponUserList(@PathVariable("userId") Integer userId,
			@PathVariable("status") Integer status,
			@PathVariable("start") Integer start, @PathVariable("size") Integer size) {
		try {
			Map<String, Object> result = new HashMap<String, Object>();
			int total = couponUserService.countUnusedCoupons(userId, status);
			List<CouponUser> data = couponUserService.getListByUser(userId,
					status, start, size);
			result.put("data", data);
			result.put("total", total);
			return JSONObject.toJSONString(new RespMsg<>(result));
		} catch (Exception e) {
			logger.error("ExceptionMessage:{},printStackTrace:",
					e.getMessage(), e);
			return JSONObject.toJSONString(new RespMsg<User>(Status.error));
		}
	}

	/**
	 * 查询用户持有的券列表（新的）
	 * 
	 * @param userId
	 * @param status
	 * @return
	 * @since 1.2
	 */
//	@Path("list/new/{userId}/{status}/{start}/{size}")
//	@GET
	@RequestMapping(value = "list/new/{userId}/{status}/{start}/{size}",method = RequestMethod.GET)
	public String getCouponUserNewList(@PathVariable("userId") Integer userId,
			@PathVariable("status") Integer status,
			@PathVariable("start") Integer start, @PathVariable("size") Integer size) {
		try {
//			Map<String, Object> result = new HashMap<String, Object>();
//			int total = couponUserService.countUnusedCoupons(userId, status);
			List<Coupons> data = couponUserService.getNewListByUser(userId,
					status, start, size);
//			result.put("data", data);
//			result.put("total", total);
			return JSONObject.toJSONString(new RespMsg<>(data));
		} catch (Exception e) {
			logger.error("ExceptionMessage:{},printStackTrace:",
					e.getMessage(), e);
			return JSONObject.toJSONString(new RespMsg<User>(Status.error));
		}
	}
	
	/**
	 * 查询用户满足使用条件的券
	 * 
	 * @param userId
	 * @param status
	 * @return
	 * @since 1.1
	 */
//	@Path("usablelist/{userId}/{smoney}/{siteId}")
//	@GET
	@RequestMapping(value = "usablelist/{userId}/{smoney}/{siteId}",method = RequestMethod.GET)
	public String getUsableCouponUserList(@PathVariable("userId") Integer userId,
			@PathVariable("smoney") Double smoney,@PathVariable("siteId") Integer siteId) {
		try {
			Map<String, Object> result = new HashMap<String, Object>();
//			List<CouponUser> data = couponUserService.getUsableCouponsByUser(
//					userId, smoney,siteId);
			
			Map<String, Object> m = new HashMap<String, Object>();
			m.put("uid", userId);
			m.put("smoney", smoney);
			m.put("siteId", siteId);
			List<CouponUser> data = couponUserService.getUsableCouponsByUser(m);
			result.put("data", data);
			result.put("total", data.size());
			return JSONObject.toJSONString(new RespMsg<Map<String, Object>>(
					result));
		} catch (Exception e) {
			logger.error("ExceptionMessage:{},printStackTrace:",
					e.getMessage(), e);
			return JSONObject.toJSONString(new RespMsg<User>(Status.error));
		}
	}

	/**
	 * 查询某个券的用户持有记录列表
	 * 
	 * @param couponId
	 * @return
	 */
//	@Deprecated
//	@Path("list/{couponId}/{start}/{size}")
//	@GET
//	public String getCouponUsedList(@PathVariable("couponId") Integer couponId,
//			@PathVariable("start") Integer start, @PathVariable("size") Integer size) {
//		try {
//			Map<String, Object> param = new HashMap<String, Object>();
//			param.put("couponId", couponId);
//			List<CouponUser> total = couponUserService.getListByCoupon(param);
//			param.put("start", (start - 1) * size);
//			param.put("size", size);
//			List<CouponUser> list = couponUserService.getListByCoupon(param);
//			Map<String, Object> result = new HashMap<String, Object>();
//			result.put("total", total.size());
//			result.put("data", list);
//			return JSONObject.toJSONString(new RespMsg<Map<String, Object>>(
//					result));
//		} catch (Exception e) {
//			logger.error("ExceptionMessage:{},printStackTrace:",
//					e.getMessage(), e);
//			return JSONObject.toJSONString(new RespMsg<User>(Status.error));
//		}
//	}
}
