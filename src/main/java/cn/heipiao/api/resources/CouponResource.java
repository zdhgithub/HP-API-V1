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

import cn.heipiao.api.pojo.Coupon;
import cn.heipiao.api.pojo.RespMsg;
import cn.heipiao.api.pojo.User;
import cn.heipiao.api.service.CouponService;
import cn.heipiao.api.service.CouponUserService;

/**
 * @author zf
 * @version 1.0
 * @description 券资源类
 * @date 2016年7月20日
 */
//@Component
//@Consumes(MediaType.APPLICATION_JSON)
//@Produces(MediaType.APPLICATION_JSON)
//@Path("coupon")
@RestController
@RequestMapping(value = "coupon",produces="application/json")
public class CouponResource {
	private static final Logger logger = LoggerFactory
			.getLogger(CouponResource.class);
	@Resource
	private CouponService couponService;
	@Resource
	private CouponUserService couponUserService;

	/**
	 * 查询黑券或是漂券列表
	 * 
	 * @param source
	 * @param start
	 * @param size
	 * @return
	 * @since 1.1
	 */
//	@Path("list/{source}/{start}/{size}")
//	@GET
	@RequestMapping(value = "list/{source}/{start}/{size}",method = RequestMethod.GET)
	public String getCouponList(@PathVariable("source") Integer source,
			@PathVariable("start") Integer start, @PathVariable("size") Integer size) {
		try {
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("source", source);
			List<Coupon> total = couponService.getCouponList(param);
			param.put("start", (start - 1) * size);
			param.put("size", size);
			List<Coupon> couponList = couponService.getCouponList(param);
			Map<String, Object> result = new HashMap<String, Object>();
			result.put("total", total.size());
			result.put("data", couponList);
			return JSONObject.toJSONString(new RespMsg<Map<String, Object>>(
					result));
		} catch (Exception e) {
			logger.error("ExceptionMessage:{},printStackTrace:",
					e.getMessage(), e);
			return JSONObject.toJSONString(new RespMsg<User>(Status.error));
		}
	}

	/**
	 * 禁用/启用券
	 * 
	 * @param couponId
	 * @param flag
	 * @return
	 * @since 1.1
	 */
//	@Path("{couponId}/{flag}")
//	@PUT
	@RequestMapping(value = "{couponId}/{flag}",method = RequestMethod.PUT,consumes = "application/json")
	public String modifyCouponFlag(@PathVariable("couponId") Integer couponId,
			@PathVariable("flag") Integer flag) {
		if (couponId == null || flag == null) {
			return JSONObject.toJSONString(new RespMsg<User>(
					Status.value_is_null_or_error));
		}
		try {
			int cid = couponService.updateCouponFlag(couponId, flag);
			return JSONObject.toJSONString(new RespMsg<String>(cid + ""));
		} catch (Exception e) {
			logger.error("ExceptionMessage:{},printStackTrace:",
					e.getMessage(), e);
			return JSONObject.toJSONString(new RespMsg<User>(Status.error));
		}
	}

	/**
	 * 新建券
	 * 
	 * @param jsonObject
	 * @return
	 * @since 1.1
	 */
//	@POST
	@RequestMapping(method = RequestMethod.POST,consumes = "application/json")
	public String saveCoupon(@RequestBody JSONObject jsonObject) {
		try {
			logger.debug("json:", jsonObject.toJSONString());
			Coupon coupon = JSONObject.toJavaObject( jsonObject, Coupon.class);
			int result = couponService.saveCoupon(coupon);
			if (Status.INFO_NOT_COMPLETE == result) {
				return JSONObject.toJSONString(new RespMsg<String>(result));
			}
			return JSONObject.toJSONString(new RespMsg<String>(result + ""));
		} catch (Exception e) {
			logger.error("ExceptionMessage:{},printStackTrace:",
					e.getMessage(), e);
			return JSONObject.toJSONString(new RespMsg<User>(Status.error));
		}
	}

	/**
	 * 与券关的数据统计 钓场主共发多少，总金额是多少
	 * 
	 * @return
	 */
//	@Path("statistic")
//	@GET
//	public String couponStatistics() {
//		try {
//			int num = couponUserService.countSendedCoupons();
//			double money = couponUserService.countSendedCouponMoney();
//			Map<String, Object> result = new HashMap<String, Object>();
//			result.put("number", num);
//			result.put("money", money);
//			return JSONObject.toJSONString(new RespMsg<Map<String, Object>>(
//					result));
//		} catch (Exception e) {
//			logger.error("ExceptionMessage:{},printStackTrace:",
//					e.getMessage(), e);
//			return JSONObject.toJSONString(new RespMsg<User>(Status.error));
//		}
//
//	}

	/**
	 * 搜索
	 * 
	 * @param condition
	 * @return
	 * @since 1.2
	 */
//	@Path("search/{source}/{condition}")
//	@GET
	@RequestMapping(value = "search/{source}/{condition}",method = RequestMethod.GET)
	public String getCouponByName(@PathVariable("source") String source,
			@PathVariable("condition") String condition) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("source", source);
		param.put("condition", condition);
		try {
			List<Coupon> result = couponService.getCouponListSearch(param);
			return JSONObject.toJSONString(new RespMsg<List<Coupon>>(result));
		} catch (Exception e) {
			logger.error("ExceptionMessage:{},printStackTrace:",
					e.getMessage(), e);
			return JSONObject.toJSONString(new RespMsg<User>(Status.error));
		}
	}
}
