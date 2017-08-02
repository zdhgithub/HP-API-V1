/**
 * 
 */
package cn.heipiao.api.resources;

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

import cn.heipiao.api.constant.RespMessage;
import cn.heipiao.api.mapper.ShopCouponsMapper;
import cn.heipiao.api.mapper.SiteCouponsMapper;
import cn.heipiao.api.pojo.FishShop;
import cn.heipiao.api.pojo.FishSite;
import cn.heipiao.api.pojo.PartnerCouponRecord;
import cn.heipiao.api.pojo.RespMsg;
import cn.heipiao.api.pojo.ShopCoupons;
import cn.heipiao.api.pojo.SiteCoupons;
import cn.heipiao.api.pojo.User;
import cn.heipiao.api.pojo.UserRecommend;
import cn.heipiao.api.service.FishShopService;
import cn.heipiao.api.service.FishSiteService;
import cn.heipiao.api.service.WebService;
import cn.heipiao.api.utils.ServiceUtils;

/**
 * @author wzw
 * @date 2016年8月30日
 * @version 1.0
 */
@RestController
@RequestMapping(value = "web/found",produces="application/json")
public class WebResource {

	private final static Logger logger = LoggerFactory.getLogger(WebResource.class);


	@Resource
	private WebService webService;

	@Resource
	private FishSiteService fishSiteService;
	@Resource
	private SiteCouponsMapper siteCouponsMapper;

	@Resource
	private FishShopService fishShopService;
	@Resource
	private ShopCouponsMapper shopCouponsMapper;

	/*
	 * @OPTIONS
	 * 
	 * @Path("partnerGiveOutCoupon") public void opPartnerGiveOutCoupon(){
	 * setResponseAccess(); }
	 * 
	 * @OPTIONS
	 * 
	 * @Path("userRecommend") public void opUserRecommend(){
	 * setResponseAccess(); }
	 */
	/**
	 * 3.0关闭
	 * 
	 * 合伙人发钓场券
	 * 
	 * {"partnerId":123,"phone":"12345678901"}
	 * 
	 * partnerId:为合伙人的uid
	 * 
	 * @param json
	 * @return
	 */
//	@RequestMapping(value = "partnerGiveOutCoupon",method = RequestMethod.POST,consumes = "application/json")
	public String partnerGiveOutCoupon(@RequestBody JSONObject json) {
		try {
			setResponseAccess();
			logger.info("json:{}", json);
			Long uid = json == null ? null : json.getLong("partnerId");
			String phone = json == null ? null : json.getString("phone");
			if (uid == null || StringUtils.isBlank(phone)) {
				return JSONObject.toJSONString(new RespMsg<>(
						Status.value_is_null_or_error, RespMessage
						.getRespMsg(Status.value_is_null_or_error)));
			}
			
			if (!ServiceUtils.verifyPhone(phone)) {
				return JSONObject.toJSONString(new RespMsg<>(
						Status.phonenum_format_error, RespMessage
						.getRespMsg(Status.phonenum_format_error)));
			}
			
//			int rs = webService.giveOutCoupon(uid, phone);
			int rs = webService.giveOutCouponNew(uid, phone,1);
			if (rs != Status.success) {
				return JSONObject.toJSONString(new RespMsg<>(rs, RespMessage
						.getRespMsg(rs)));
			}
			return JSONObject.toJSONString(new RespMsg<>(rs, "恭喜，发券成功！"));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.toJSONString(new RespMsg<>(Status.error,
					RespMessage.getRespMsg(Status.error)));
		}
	}
	
	/**
	 * 3.0关闭
	 * 
	 * 合伙人发店铺券
	 * 
	 * {"uid":123,"phone":"12345678901"}
	 * 
	 * @param json
	 * @return
	 */
//	@RequestMapping(value = "partnerGiveOutShopCoupon",method = RequestMethod.POST,consumes = "application/json")
	public String partnerGiveOutShopCoupon(@RequestBody JSONObject json) {
		try {
			setResponseAccess();
			logger.info("json:{}", json);
			Long uid = json == null ? null : json.getLong("uid");
			String phone = json == null ? null : json.getString("phone");
			if (uid == null || StringUtils.isBlank(phone)) {
				return JSONObject.toJSONString(new RespMsg<>(
						Status.value_is_null_or_error, RespMessage
								.getRespMsg(Status.value_is_null_or_error)));
			}

			if (!ServiceUtils.verifyPhone(phone)) {
				return JSONObject.toJSONString(new RespMsg<>(
						Status.phonenum_format_error, RespMessage
								.getRespMsg(Status.phonenum_format_error)));
			}

			int rs = webService.giveOutCouponNew(uid, phone,2);
			if (rs != Status.success) {
				return JSONObject.toJSONString(new RespMsg<>(rs, 
						rs == Status.partner_giving_coupon_uid_limited ? "您已经发了几次优惠券给他了,也给其他人一些优惠的机会吧" :
						RespMessage.getRespMsg(rs)));
			}
			return JSONObject.toJSONString(new RespMsg<>(rs, "恭喜，发券成功！"));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.toJSONString(new RespMsg<>(Status.error,
					RespMessage.getRespMsg(Status.error)));
		}
	}

	/**
	 * 推荐app获取券 推荐人 ：用户登录获得一部分漂币，用户购买消费后获得一部分漂币 被推荐人：如果是已注册用户没有任何获得，如果未注册获取一张优惠券
	 * 
	 * {"uid":12313,"phone":""} uid：推荐人uid phone ：领券人电话
	 * 
	 * FIXME 2016-12-16，日志报错：问题是推荐插入用户记录的时候报错，说用户已存在，暂时原因不明
	 * 
	 * @return
	 */
//	@POST
//	@Produces({ MediaType.APPLICATION_JSON + ";charset=utf-8" })
//	@Consumes({ MediaType.APPLICATION_JSON + ";charset=utf-8" })
//	@Path("userRecommend")
	@RequestMapping(value = "userRecommend",method = RequestMethod.POST,consumes = "application/json")
	public String userRecommend(@RequestBody JSONObject json) {
		try {
			setResponseAccess();
			logger.info("json:{}", json);
			Long uid = json == null ? null : json.getLong("uid");
			String phone = json == null ? null : json.getString("phone");

			if (uid == null || phone == null) {
				return JSONObject.toJSONString(new RespMsg<>(
						Status.value_is_null_or_error, RespMessage
								.getRespMsg(Status.value_is_null_or_error)));
			}

			if (!ServiceUtils.verifyPhone(phone)) {
				return JSONObject.toJSONString(new RespMsg<>(
						Status.phonenum_format_error, RespMessage
								.getRespMsg(Status.phonenum_format_error)));
			}

			int rs = webService.userRecommend(uid, phone.trim());
			if (rs == Status.success) {
				return JSONObject.toJSONString(new RespMsg<>(rs, "恭喜，领取成功！"));
			}
			return JSONObject.toJSONString(new RespMsg<>(rs,
					"您是老用户哟，我们准备了更多的获取奖励的方式，打开APP去看看吧"));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.toJSONString(new RespMsg<>(Status.error,
					RespMessage.getRespMsg(Status.error)));
		}
	}
	
	
	/**
	 * 商家优惠券分享后，页面获取商家优惠券功能
	 * @param json
	 * @return
	 */
//	@POST
//	@Produces({ MediaType.APPLICATION_JSON + ";charset=utf-8" })
//	@Consumes({ MediaType.APPLICATION_JSON + ";charset=utf-8" })
//	@Path("couponsShareGet")
	@RequestMapping(value = "couponsShareGet",method = RequestMethod.POST,consumes = "application/json")
	public String couponsShareGet(@RequestBody JSONObject json) {
		try {
			logger.info("json:{}", json);
			String phone = json == null ? null : json.getString("phone");
			Long business = json == null ? null : json.getLong("business");
			Long coupons = json == null ? null : json.getLong("coupons");

			//判断手机号码合法性
			if (!ServiceUtils.verifyPhone(phone)) {
				return getErrorRet(Status.phonenum_format_error);
			}
			//判断此商家是钓场或是店铺
			boolean isSiteId = false;
			if(ServiceUtils.isFishSiteId(business)){
				isSiteId = true;
				FishSite site = fishSiteService.selectById(business.intValue());
				SiteCoupons siteCoupons = siteCouponsMapper.selectById(coupons);
				if(site == null || siteCoupons == null){
					return getErrorRet(Status.value_is_null_or_error);
				}
			}else{
				isSiteId = false;
				FishShop site = fishShopService.getFishShopById(business);
				ShopCoupons siteCoupons = shopCouponsMapper.selectById(coupons);
				if(site == null || siteCoupons == null){
					return getErrorRet(Status.value_is_null_or_error);
				}
			}

			int rs = webService.couponsShareGet(phone, business, coupons, isSiteId);
			if (rs == Status.COUPONS_SHARE_GET_SUCCESS) {
				return getRightRet(RespMessage.getRespMsg(Status.COUPONS_SHARE_GET_SUCCESS));
			}
			return getErrorRet(Status.COUPONS_SHARE_GET_FAIL);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.toJSONString(new RespMsg<>(Status.error,
					RespMessage.getRespMsg(Status.error)));
		}
	}
	
	
	// 返回对象
	private String getRightRet(Object o){
		return JSONObject.toJSONString(new RespMsg<>(o));
	}
	
	// 返回错误
	private String getErrorRet(int ERROR_CODE){
		return JSONObject.toJSONString(new RespMsg<>(ERROR_CODE, RespMessage.getRespMsg(ERROR_CODE)));
	}


	/**
	 * 
	 * 获取合伙人发券列表
	 * 
	 * @return
	 */
//	@GET
//	@Produces({ MediaType.APPLICATION_JSON + ";charset=utf-8" })
//	@Path("list/partnerId/{uid}")
	@RequestMapping(value = "list/partnerId/{uid}",method = RequestMethod.GET)
	public String partnerGiveOutCouponList(@PathVariable("uid") Long uid) {
		try {
			logger.info("uid:{}", uid);
			List<PartnerCouponRecord> pojos = webService.selectByPartnerId(uid);
			setResponseAccess();
			return JSONObject.toJSONString(new RespMsg<>(pojos));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.toJSONString(new RespMsg<>(Status.error,
					RespMessage.getRespMsg(Status.error)));
		}
	}

	/**
	 * 
	 * 获取用户推荐列表
	 * 
	 * @return
	 */
//	@GET
//	@Produces({ MediaType.APPLICATION_JSON + ";charset=utf-8" })
//	@Path("list/uid/{uid}")
	@RequestMapping(value = "list/uid/{uid}",method = RequestMethod.GET)
	public String userRecommendList(@PathVariable("uid") Long uid) {
		try {
			logger.info("uid:{}", uid);
			List<UserRecommend> pojos = webService.selectListByUid(uid);
			setResponseAccess();
			return JSONObject.toJSONString(new RespMsg<>(pojos));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.toJSONString(new RespMsg<>(Status.error,
					RespMessage.getRespMsg(Status.error)));
		}
	}

	/**
	 * 获取用户头像
	 * 
	 * @param uid
	 * @return
	 */
//	@GET
//	@Produces({ MediaType.APPLICATION_JSON + ";charset=utf-8" })
//	@Path("uid/{uid}")
	@RequestMapping(value = "uid/{uid}",method = RequestMethod.GET)
	public String getUser(@PathVariable("uid") Long uid) {
		try {
			logger.info("uid:{}", uid);
			setResponseAccess();
			User u = webService.selectUserByUid(uid);
			if (u == null) {
				return JSONObject.toJSONString(new RespMsg<>(
						Status.no_such_user, RespMessage
								.getRespMsg(Status.no_such_user)));
			}
			if (u != null && u.getPortriat() != null) {
				u.setPortriat("http://port.res.heipiaola.com/"
						+ u.getPortriat());
			}
			if (u != null) {
				FishSite site = fishSiteService.selectByUid(uid);
				if (site != null) {
					u.setNickname(site.getFishSiteName());
				} else {
					if (u.getRealname() != null) {
						u.setNickname(u.getRealname());
					}
				}
			}
			return JSONObject.toJSONString(new RespMsg<>(u));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.toJSONString(new RespMsg<>(Status.error,
					RespMessage.getRespMsg(Status.error)));
		}
	}

	/**
	 * 统计用户推荐好友数量
	 * 
	 * @param uid
	 * @return
	 */
//	@GET
//	@Produces({ MediaType.APPLICATION_JSON + ";charset=utf-8" })
//	@Path("count/userRecommend/{uid}")
	@RequestMapping(value = "count/userRecommend/{uid}",method = RequestMethod.GET)
	public String getUserRecommendCount(@PathVariable("uid") Long uid) {
		try {
			logger.info("uid:{}", uid);
			setResponseAccess();
			Integer u = webService.selectUserCount(uid);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("count", u);
			return JSONObject.toJSONString(new RespMsg<>(map));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.toJSONString(new RespMsg<>(Status.error,
					RespMessage.getRespMsg(Status.error)));
		}
	}

	/**
	 * 合伙人发券统计
	 * 
	 * @param uid
	 * @return
	 */
//	@GET
//	@Produces({ MediaType.APPLICATION_JSON + ";charset=utf-8" })
//	@Path("count/partnerGiveOutCoupon/{uid}")
	@RequestMapping(value = "count/partnerGiveOutCoupon/{uid}",method = RequestMethod.GET)
	public String getPartnerGiveOutCouponCount(@PathVariable("uid") Long uid) {
		try {
			logger.info("uid:{}", uid);
			setResponseAccess();
			Map<String, Object> map = webService.selectPartnerCount(uid);
			return JSONObject.toJSONString(new RespMsg<>(map));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.toJSONString(new RespMsg<>(Status.error,
					RespMessage.getRespMsg(Status.error)));
		}
	}

	/**
	 * 合伙人剩余的数量
	 * 
	 * @param uid
	 * @return
	 */
//	@GET
//	@Produces({ MediaType.APPLICATION_JSON + ";charset=utf-8" })
//	@Path("count/{uid}")
	@RequestMapping(value = "count/{uid}",method = RequestMethod.GET)
	public String getPartnerPrerogativeCount(@PathVariable("uid") Long uid) {
		try {
			logger.info("uid:{}", uid);
			setResponseAccess();
			Map<String, Object> map = webService
					.selectPartnerPrerogativeCount(uid);
			return JSONObject.toJSONString(new RespMsg<>(map));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.toJSONString(new RespMsg<>(Status.error,
					RespMessage.getRespMsg(Status.error)));
		}
	}

	public void setResponseAccess() {
		// response.addHeader("Access-Control-Allow-Origin", "*");
		// response.addHeader("Access-Control-Allow-Methods", "*");
		// response.addHeader("Access-Control-Allow-Headers", "content-type");
	}
}
