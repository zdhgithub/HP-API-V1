/**
 * 
 */
package cn.heipiao.api.resources;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;

import cn.heipiao.api.constant.RespMessage;
import cn.heipiao.api.pojo.CouponsCount;
import cn.heipiao.api.pojo.RespMsg;
import cn.heipiao.api.pojo.ShopCoupons;
import cn.heipiao.api.pojo.ShopCouponsRecord;
import cn.heipiao.api.pojo.SiteCoupons;
import cn.heipiao.api.pojo.SiteCouponsRecord;
import cn.heipiao.api.service.ShopCouponsService;
import cn.heipiao.api.service.SiteCouponsService;
import cn.heipiao.api.utils.ExDateUtils;
import io.swagger.annotations.Api;

/**
 * @author wzw
 * @date 2016年11月3日
 * @version 2.1
 */
//@Component
//@Consumes(MediaType.APPLICATION_JSON)
//@Produces(MediaType.APPLICATION_JSON)
//@Path("b/coupon")
@Api(tags = "优惠券模块")
@RestController
@RequestMapping(value = "b/coupon",produces="application/json")
public class CouponsResource {

	private static final Logger logger = LoggerFactory.getLogger(CouponsResource.class);
	
	@Resource
	private SiteCouponsService siteCouponsService;
	
	@Resource
	private ShopCouponsService shopCouponsService;
	
	//访问页面domain
	@Value("${article.url}")
	private String domain;
	
	//大众券分享页面 - 分享标题
	@Value("${coupons.share.title}")
	private String shareTitle;
	
	//大众券分享页面 - 分享描述
	@Value("${coupons.share.desc}")
	private String shareDesc;
	
	//大众券分享页面 - 分享链接 - 与article.url相关
	@Value("${coupons.share.link}")
	private String shareLink;
	
	//大众券分享页面 - 分享图标 - 与article.url相关
	@Value("${coupons.share.imgUrl}")
	private String shareImgUrl;

	
	/**
	 * 钓场
	 * 发放大众券
	 * @param json
	 * @return
	 */
//	@POST
//	@Path("create/site/general")
	@RequestMapping(value = "create/site/general",method = RequestMethod.POST,consumes = "application/json")
	public String giveSiteGeneralCoupons(@RequestBody JSONObject json){
		try {
			logger.info("json:{}",json);
			SiteCoupons pojo = json == null ? null : JSONObject.toJavaObject(json, SiteCoupons.class);
			if(pojo == null || pojo.getCouponName() == null || pojo.getCouponFee() == null
					|| pojo.getStartTime() == null || pojo.getEndTime() == null || pojo.getLimit() == null
					|| pojo.getSiteId() == null || pojo.getAmount() == null || pojo.getUseRule() == null
					
					|| pojo.getAmount() >= 100 || pojo.getAmount() <= 0
					|| pojo.getStartTime().getTime() > pojo.getEndTime().getTime()
					|| pojo.getEndTime().getTime() < ExDateUtils.getCalendar().getTimeInMillis()
					|| pojo.getLimit() >=100 || pojo.getLimit() <= 0
					|| pojo.getAmount() % pojo.getLimit() != 0
					
					){
				
				return JSONObject.toJSONString(new RespMsg<>(Status.value_is_null_or_error, RespMessage.getRespMsg(Status.value_is_null_or_error)));
			}
			pojo.setCategory(1);
			pojo.setStatus(0);
			pojo.setUseCount(0);
			pojo.setCount(0);
			pojo.setCreateTime(new Timestamp(ExDateUtils.getCalendar().getTimeInMillis()));
			int rs = siteCouponsService.addPojo(pojo);
			return JSONObject.toJSONString(new RespMsg<>(rs));
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			return JSONObject.toJSONString(new RespMsg<>(Status.error,RespMessage.getRespMsg(Status.error)));
		}
	}
	
	/**
	 * {"id":1,"status":1,"uid":1}
	 * 修改优惠券发放状态
	 * @return
	 */
//	@PUT
//	@Path("site/modification/status")
	@RequestMapping(value = "site/modification/status",method = RequestMethod.PUT,consumes = "application/json")
	public String modifySiteCouponStatus(@RequestBody JSONObject json){
		try {
			logger.info("json:{}",json);
			Long id = json == null ? null : json.getLong("id");
			Long uid = json == null ? null : json.getLong("uid");
			Integer status = json == null ? null : json.getInteger("status");
			if(id == null || status == null || uid == null
					|| status < 0 || status > 2
					){
				return JSONObject.toJSONString(new RespMsg<>(Status.value_is_null_or_error, RespMessage.getRespMsg(Status.value_is_null_or_error)));
			}
			int rs = siteCouponsService.modifySiteCouponStatus(uid,id,status);
			return JSONObject.toJSONString(new RespMsg<>(rs));
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			return JSONObject.toJSONString(new RespMsg<>(Status.error,RespMessage.getRespMsg(Status.error)));
		}
	}
	
	/**
	 * 钓场
	 * 发放定向券（常规）
	 * @param json
	 * @return
	 */
//	@POST
//	@Path("create/site/directional/routine")
	@RequestMapping(value = "create/site/directional/routine",method = RequestMethod.POST,consumes = "application/json")
	public String giveSiteRoutineCoupons(@RequestBody JSONObject json){
		try {
			logger.info("json:{}",json);
			SiteCoupons pojo = json == null ? null : JSONObject.toJavaObject(json, SiteCoupons.class);
			if(pojo == null || pojo.getCouponName() == null || pojo.getCouponFee() == null
					|| pojo.getStartTime() == null || pojo.getEndTime() == null
					|| pojo.getSiteId() == null || pojo.getUseRule() == null
					|| pojo.getFlag() == null
					
					|| !verifyFlag(pojo.getFlag())
					|| pojo.getStartTime().getTime() > pojo.getEndTime().getTime()
					
					){
				
				return JSONObject.toJSONString(new RespMsg<>(Status.value_is_null_or_error, RespMessage.getRespMsg(Status.value_is_null_or_error)));
			}
			pojo.setCategory(2);
			pojo.setStatus(0);
			pojo.setUseCount(0);
			pojo.setLimit(1);
			pojo.setCreateTime(new Timestamp(ExDateUtils.getCalendar().getTimeInMillis()));
			int rs = siteCouponsService.addRoutinePojo(pojo);
			return JSONObject.toJSONString(new RespMsg<>(rs));
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			return JSONObject.toJSONString(new RespMsg<>(Status.error,RespMessage.getRespMsg(Status.error)));
		}
	}
	
	
	private boolean verifyFlag(Set<Integer> set){
		if(set == null || set.size() <= 0){
			return false;
		}
		for (Integer flag : set) {
			if(flag > 3 || flag < 1){
				return false;
			}
		}
		return true;
	}
	
	/**
	 * 钓场
	 * 发放定向券（特定）
	 * @param json
	 * @return
	 */
//	@POST
//	@Path("create/site/directional/specify")
	@RequestMapping(value = "create/site/directional/specify",method = RequestMethod.POST,consumes = "application/json")
	public String giveSiteSpecifyCoupons(@RequestBody JSONObject json){
		try {
			logger.info("json:{}",json);
			SiteCoupons pojo = json == null ? null : JSONObject.toJavaObject(json, SiteCoupons.class);
			if(pojo == null || pojo.getCouponName() == null || pojo.getCouponFee() == null
					|| pojo.getStartTime() == null || pojo.getEndTime() == null
					|| pojo.getSiteId() == null || pojo.getUseRule() == null
					|| pojo.getUids() == null
					
					|| pojo.getUids().size() <= 0
					|| pojo.getStartTime().getTime() > pojo.getEndTime().getTime()
					
					){
				
				return JSONObject.toJSONString(new RespMsg<>(Status.value_is_null_or_error, RespMessage.getRespMsg(Status.value_is_null_or_error)));
			}
			pojo.setCategory(3);
			pojo.setStatus(0);
			pojo.setUseCount(0);
			pojo.setAmount(pojo.getUids().size());
			pojo.setCount(pojo.getUids().size());
			pojo.setLimit(1);
			pojo.setCreateTime(new Timestamp(ExDateUtils.getCalendar().getTimeInMillis()));
			int rs = siteCouponsService.addSpecifyPojo(pojo);
			return JSONObject.toJSONString(new RespMsg<>(rs));
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			return JSONObject.toJSONString(new RespMsg<>(Status.error,RespMessage.getRespMsg(Status.error)));
		}
	}
	
	/**
	 * 钓场
	 * category : 1.大众券; 2.定向券
	 * @return
	 */
//	@GET
//	@Path("list/site/{category}/{siteId}/{index}/{size}")
	@RequestMapping(value = "list/site/{category}/{siteId}/{index}/{size}",method = RequestMethod.GET)
	public String getSiteBySiteId(@PathVariable("category") Integer category,@PathVariable("siteId") Integer siteId,
			@PathVariable("index") Long index ,@PathVariable("size") Integer size){
		try {
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("category", category);
			map.put("siteId", siteId);
			map.put("index", index);
			map.put("size", size);
			List<SiteCoupons> list = siteCouponsService.getListBySiteId(map);
			return JSONObject.toJSONString(new RespMsg<>(list));
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			return JSONObject.toJSONString(new RespMsg<>(Status.error,RespMessage.getRespMsg(Status.error)));
		}
	}
	
	/**
	 * 钓场
	 * category : 1.大众券; 2.定向券
	 * @return
	 */
//	@GET
//	@Path("list/new/site/{category}/{siteId}/{index}/{size}")
	@RequestMapping(value = "list/new/site/{category}/{siteId}/{index}/{size}",method = RequestMethod.GET)
	public String getNewSiteBySiteId(@PathVariable("category") Integer category,@PathVariable("siteId") Integer siteId,
			@PathVariable("index") Long index ,@PathVariable("size") Integer size){
		try {
			if(category < 1 || category > 2 || index <= 0 || size <= 0){
				return JSONObject.toJSONString(new RespMsg<>(Status.value_is_null_or_error));
			}
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("category", category);
			map.put("siteId", siteId);
			map.put("index", (index - 1) * size);
			map.put("size", size);
			List<SiteCoupons> list = siteCouponsService.getNewListBySiteId(map);
			return JSONObject.toJSONString(new RespMsg<>(list));
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			return JSONObject.toJSONString(new RespMsg<>(Status.error,RespMessage.getRespMsg(Status.error)));
		}
	}
	
	
	/**
	 * 钓场
	 * 统计
	 * @return
	 */
//	@GET
//	@Path("count/site/{siteId}")
	@RequestMapping(value = "count/site/{siteId}",method = RequestMethod.GET)
	public String getSiteCount(@PathVariable("siteId") Integer siteId){
		try {
			CouponsCount cc = siteCouponsService.countBySiteId(siteId);
			//累计发放次数times，累计发放用户人次users，累计使用张数used，累计优惠金额fees,累计大众券被领取张数counts
			return JSONObject.toJSONString(new RespMsg<>(cc));
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			return JSONObject.toJSONString(new RespMsg<>(Status.error,RespMessage.getRespMsg(Status.error)));
		}
	}
	
	/**
	 * 钓场
	 * flag  1:在商家消费过的用户记录, 2:关注商家的用户记录,3:关注商家个人品牌的用户记录
	 * 获取关联用户
	 * @return
	 */
//	@GET
//	@Path("user/site/{siteId}/{flag}")
	@RequestMapping(value = "user/site/{siteId}/{flag}",method = RequestMethod.GET)
	public String getShopUsers(@PathVariable("siteId") Integer siteId,@PathVariable("flag") Integer flag){
		try {
			//钓场目前只有1和2的内容
			if(flag > 2 || flag < 1){
				return JSONObject.toJSONString(new RespMsg<>(Status.value_is_null_or_error, RespMessage.getRespMsg(Status.value_is_null_or_error)));
			}
			List<SiteCouponsRecord>  sc = siteCouponsService.getSiteUsers(siteId,flag);
			return JSONObject.toJSONString(new RespMsg<>(sc));
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			return JSONObject.toJSONString(new RespMsg<>(Status.error,RespMessage.getRespMsg(Status.error)));
		}
	}
	
	
	
	/**
	 * 店铺
	 * 发放大众券
	 * @param json
	 * @return
	 */
//	@POST
//	@Path("create/shop/general")
	@RequestMapping(value = "create/shop/general",method = RequestMethod.POST,consumes = "application/json")
	public String giveShopGeneralCoupons(@RequestBody JSONObject json){
		try {
			logger.info("json:{}",json);
			ShopCoupons pojo = json == null ? null : JSONObject.toJavaObject(json, ShopCoupons.class);
			if(pojo == null || pojo.getCouponName() == null || pojo.getCouponFee() == null
					|| pojo.getStartTime() == null || pojo.getEndTime() == null || pojo.getLimit() == null
					|| pojo.getShopId() == null || pojo.getAmount() == null || pojo.getUseRule() == null
					
					|| pojo.getAmount() > 1000 || pojo.getAmount() <= 0
					|| pojo.getStartTime().getTime() > pojo.getEndTime().getTime()
					|| pojo.getEndTime().getTime() < ExDateUtils.getCalendar().getTimeInMillis()
					|| pojo.getLimit() > 1000 || pojo.getLimit() <= 0
					|| pojo.getAmount() % pojo.getLimit() != 0
					){
				
				return JSONObject.toJSONString(new RespMsg<>(Status.value_is_null_or_error, RespMessage.getRespMsg(Status.value_is_null_or_error)));
			}
			pojo.setCategory(1);
			pojo.setStatus(0);
			pojo.setUseCount(0);
			pojo.setCount(0);
			pojo.setCreateTime(new Timestamp(ExDateUtils.getCalendar().getTimeInMillis()));
			int rs = shopCouponsService.addPojo(pojo);
			return JSONObject.toJSONString(new RespMsg<>(rs));
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			return JSONObject.toJSONString(new RespMsg<>(Status.error,RespMessage.getRespMsg(Status.error)));
		}
	}
	
	
	/**
	 * {"id":1,"status":1,"uid":1}
	 * 修改优惠券发放状态
	 * @return
	 */
//	@PUT
//	@Path("shop/modification/status")
	@RequestMapping(value = "shop/modification/status",method = RequestMethod.PUT,consumes = "application/json")
	public String modifyShopCouponStatus(@RequestBody JSONObject json){
		try {
			logger.info("json:{}",json);
			Long id = json == null ? null : json.getLong("id");
			Long uid = json == null ? null : json.getLong("uid");
			Integer status = json == null ? null : json.getInteger("status");
			if(id == null || status == null || uid == null
					|| status < 0 || status > 2
					){
				return JSONObject.toJSONString(new RespMsg<>(Status.value_is_null_or_error, RespMessage.getRespMsg(Status.value_is_null_or_error)));
			}
			int rs = shopCouponsService.modifyShopCouponStatus(uid,id,status);
			return JSONObject.toJSONString(new RespMsg<>(rs));
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			return JSONObject.toJSONString(new RespMsg<>(Status.error,RespMessage.getRespMsg(Status.error)));
		}
	}
	
	/**
	 * 店铺
	 * 发放定向券（常规）
	 * @param json
	 * @return
	 */
//	@POST
//	@Path("create/shop/directional/routine")
	@RequestMapping(value = "create/shop/directional/routine",method = RequestMethod.POST,consumes = "application/json")
	public String giveShopRoutineCoupons(@RequestBody JSONObject json){
		try {
			logger.info("json:{}",json);
			ShopCoupons pojo = json == null ? null : JSONObject.toJavaObject(json, ShopCoupons.class);
			if(pojo == null || pojo.getCouponName() == null || pojo.getCouponFee() == null
					|| pojo.getStartTime() == null || pojo.getEndTime() == null
					|| pojo.getShopId() == null || pojo.getUseRule() == null
					|| pojo.getFlag() == null
					
					|| !verifyFlag(pojo.getFlag())
					|| pojo.getStartTime().getTime() > pojo.getEndTime().getTime()
					
					){
				
				return JSONObject.toJSONString(new RespMsg<>(Status.value_is_null_or_error, RespMessage.getRespMsg(Status.value_is_null_or_error)));
			}
			pojo.setCategory(2);
			pojo.setStatus(0);
			pojo.setUseCount(0);
			pojo.setLimit(1);
			pojo.setCreateTime(new Timestamp(ExDateUtils.getCalendar().getTimeInMillis()));
			int rs = shopCouponsService.addRoutinePojo(pojo);
			return JSONObject.toJSONString(new RespMsg<>(rs));
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			return JSONObject.toJSONString(new RespMsg<>(Status.error,RespMessage.getRespMsg(Status.error)));
		}
	}
	
	/**
	 * 店铺
	 * 发放定向券(特定)
	 * @param json
	 * @return
	 */
//	@POST
//	@Path("create/shop/directional/specify")
	@RequestMapping(value = "create/shop/directional/specify",method = RequestMethod.POST,consumes = "application/json")
	public String giveShopSpecifyCoupons(@RequestBody JSONObject json){
		try {
			logger.info("json:{}",json);
			ShopCoupons pojo = json == null ? null : JSONObject.toJavaObject(json, ShopCoupons.class);
			if(pojo == null || pojo.getCouponName() == null || pojo.getCouponFee() == null
					|| pojo.getStartTime() == null || pojo.getEndTime() == null
					|| pojo.getShopId() == null || pojo.getUseRule() == null
					
					|| pojo.getStartTime().getTime() > pojo.getEndTime().getTime()
					|| pojo.getUids().size() <= 0
					){
				
				return JSONObject.toJSONString(new RespMsg<>(Status.value_is_null_or_error, RespMessage.getRespMsg(Status.value_is_null_or_error)));
			}
			pojo.setCategory(3);
			pojo.setStatus(0);
			pojo.setUseCount(0);
			pojo.setLimit(1);
			pojo.setAmount(pojo.getUids().size());
			pojo.setCount(pojo.getUids().size());
			pojo.setCreateTime(new Timestamp(ExDateUtils.getCalendar().getTimeInMillis()));
			int rs = shopCouponsService.addSpecifyPojo(pojo);
			return JSONObject.toJSONString(new RespMsg<>(rs));
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			return JSONObject.toJSONString(new RespMsg<>(Status.error,RespMessage.getRespMsg(Status.error)));
		}
	}
	
	
	/**
	 * 
	 * 店铺
	 * 获取发放券列表
	 * category : 1.大众券; 2.定向券
	 * @return
	 */
//	@GET
//	@Path("list/shop/{category}/{shopId}/{index}/{size}")
	@RequestMapping(value = "list/shop/{category}/{shopId}/{index}/{size}",method = RequestMethod.GET)
	public String getShopByShopId(@PathVariable("category") Integer category,@PathVariable("shopId") Long shopId,
			@PathVariable("index") Long index ,@PathVariable("size") Integer size){
		try {
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("category", category);
			map.put("shopId", shopId);
			map.put("index", index);
			map.put("size", size);
			List<ShopCoupons> list = shopCouponsService.getListByShopId(map);
			return JSONObject.toJSONString(new RespMsg<>(list));
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			return JSONObject.toJSONString(new RespMsg<>(Status.error,RespMessage.getRespMsg(Status.error)));
		}
	}
	
	/**
	 * 
	 * 店铺
	 * 获取发放券列表
	 * category : 1.大众券; 2.定向券
	 * @return
	 */
//	@GET
//	@Path("list/new/shop/{category}/{shopId}/{index}/{size}")
	@RequestMapping(value = "list/new/shop/{category}/{shopId}/{index}/{size}",method = RequestMethod.GET)
	public String getNewShopByShopId(@PathVariable("category") Integer category,@PathVariable("shopId") Long shopId,
			@PathVariable("index") Long index ,@PathVariable("size") Integer size){
		try {
			if(category < 1 || category > 2 || index <= 0 || size <= 0){
				return JSONObject.toJSONString(new RespMsg<>(Status.value_is_null_or_error));
			}
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("category", category);
			map.put("shopId", shopId);
			map.put("index", (index - 1) * size);
			map.put("size", size);
			List<ShopCoupons> list = shopCouponsService.getNewListByShopId(map);
			return JSONObject.toJSONString(new RespMsg<>(list));
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			return JSONObject.toJSONString(new RespMsg<>(Status.error,RespMessage.getRespMsg(Status.error)));
		}
	}
	
	
	/**
	 * 店铺
	 * 统计
	 * @return
	 */
//	@GET
//	@Path("count/shop/{shopId}")
	@RequestMapping(value = "count/shop/{shopId}",method = RequestMethod.GET)
	public String getShopCount(@PathVariable("shopId") Long shopId){
		try {
			CouponsCount cc = shopCouponsService.countByShopId(shopId);
			return JSONObject.toJSONString(new RespMsg<>(cc));
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			return JSONObject.toJSONString(new RespMsg<>(Status.error,RespMessage.getRespMsg(Status.error)));
		}
	}
	
	/**
	 * 店铺
	 * flag  1:在商家消费过的用户, 2:关注商家的用户,3:关注商家个人品牌的用户
	 * 获取关联用户
	 * @return
	 */
//	@GET
//	@Path("user/shop/{shopId}/{flag}")
	@RequestMapping(value = "user/shop/{shopId}/{flag}",method = RequestMethod.GET)
	public String getShopUsers(@PathVariable("shopId") Long shopId,@PathVariable("flag") Integer flag){
		try {
			if(flag > 3 || flag < 1){
				return JSONObject.toJSONString(new RespMsg<>(Status.value_is_null_or_error, RespMessage.getRespMsg(Status.value_is_null_or_error)));
			}
			List<ShopCouponsRecord>  sc = shopCouponsService.getShopUsers(shopId,flag);
			return JSONObject.toJSONString(new RespMsg<>(sc));
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			return JSONObject.toJSONString(new RespMsg<>(Status.error,RespMessage.getRespMsg(Status.error)));
		}
	}
	
	/**
	 * 查询商家是否开通优惠券功能
	 * @return
	 */
//	@GET
//	@Path("isOpen/{category}/{id}")
	@RequestMapping(value = "isOpen/{category}/{id}",method = RequestMethod.GET)
	public String isOpenCoupon(@PathVariable("category")Integer category,@PathVariable("id")Long id){
		
		try {
			Boolean b = null;
			switch (category) {
			case 1:
				b = siteCouponsService.isOpenCoupon(id.intValue());
				break;
			case 2:
				b = shopCouponsService.isOpenCoupon(id);
				break;
			}
			if(b == null){
				return JSONObject.toJSONString(new RespMsg<>(Status.value_is_null_or_error,RespMessage.getRespMsg(Status.value_is_null_or_error)));
			}
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("openCoupon", b);
			return JSONObject.toJSONString(new RespMsg<>(map));
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			return JSONObject.toJSONString(new RespMsg<>(Status.error,RespMessage.getRespMsg(Status.error)));
		}
	}
	
	
	/**
	 * 获取优惠券明细
	 * category 1:钓场，2:店铺
	 * sid  店铺或钓场id
	 * id 发券id
	 * status 2:全部,0:领取记录,1:使用记录
	 * @param category
	 * @param sid
	 * @param id
	 * @return
	 */
//	@GET
//	@Path("list/detail/{category}/{sid}/{id}/{status}/{index}/{size}")
	@RequestMapping(value = "list/detail/{category}/{sid}/{id}/{status}/{index}/{size}",method = RequestMethod.GET)
	public String getDetailList(@PathVariable("category")Integer category,@PathVariable("sid")Long sid,
			@PathVariable("id")Long id,@PathVariable("index")Long index,@PathVariable("size")Integer size,@PathVariable("status")Integer status){
		try {
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("id", id);
			map.put("status", status);
			map.put("index", index > 0 ? new Timestamp(index) : null);
			map.put("size", size);
			List<?> list = null;
			switch (category) {
			case 1:
				list = siteCouponsService.getDetailList(sid.intValue(),map);
				break;
			case 2:
				list = shopCouponsService.getDetailList(sid,map);
				break;
			default:
				return JSONObject.toJSONString(new RespMsg<>(Status.value_is_null_or_error,RespMessage.getRespMsg(Status.value_is_null_or_error)));
			}
			return JSONObject.toJSONString(new RespMsg<>(list));
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			return JSONObject.toJSONString(new RespMsg<>(Status.error,RespMessage.getRespMsg(Status.error)));
		}
	}
	
	/**
	 * 获取领券优惠券列表
	 * @param category 1:钓场,2:店铺
	 * @param sid 
	 * @param uid
	 * @return
	 */
//	@GET
//	@Path("list/{uid}/{category}/{sid}")
	@RequestMapping(value = "list/{uid}/{category}/{sid}",method = RequestMethod.GET)
	public  String  getList(@PathVariable("category")Integer category,@PathVariable("sid")Long sid,@PathVariable("uid")Long uid){
		try {
			List<?> list = null;
			switch (category) {
			case 1:
				list = siteCouponsService.getNotGetList(sid.intValue(),uid);
				break;
			case 2:
				list = shopCouponsService.getNotGetList(sid,uid);
				break;
			default:
				return JSONObject.toJSONString(new RespMsg<>(Status.value_is_null_or_error,RespMessage.getRespMsg(Status.value_is_null_or_error)));
			}
			return JSONObject.toJSONString(new RespMsg<>(list));
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			return JSONObject.toJSONString(new RespMsg<>(Status.error,RespMessage.getRespMsg(Status.error)));
		}
	}
	
	/**
	 * 获取领券分享参数配置
	 * @return
	 */
//	@GET
//	@Path("shareParam")
	@RequestMapping(value = "shareParam",method = RequestMethod.GET)
	public  String  shareParam(@PathVariable("category")Integer category,@PathVariable("sid")Long sid,@PathVariable("uid")Long uid){
		try {
			Map<String, String> params = new HashMap<>();
			params.put("title",  shareTitle);
			params.put("desc",   shareDesc);
			params.put("link",   domain + shareLink);
			params.put("imgUrl", domain + shareImgUrl);
			return JSONObject.toJSONString(new RespMsg<>(params));
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			return JSONObject.toJSONString(new RespMsg<>(Status.error,RespMessage.getRespMsg(Status.error)));
		}
	}
	
	/**
	 * 获取常规用户总数
	 * @param category
	 * @param sid
	 * @param flag
	 * @return
	 */
//	@GET
//	@Path("count/{category}/{sid}/{flag}")
	@RequestMapping(value = "count/{category}/{sid}/{flag}",method = RequestMethod.GET)
	public String getCount(@PathVariable("category") Integer category,@PathVariable("sid") Long sid,@PathVariable("flag") String flag){
		try {
			Map<String,Object> map = new HashMap<String, Object>();
			String[] fs = flag.split("_");
			int count = 0;
			switch (category) {
			case 1:
				count = siteCouponsService.countUids(sid.intValue(),fs);
				break;
			case 2:
				count = shopCouponsService.countUids(sid,fs);
				break;
			default:
				return JSONObject.toJSONString(new RespMsg<>(Status.value_is_null_or_error,RespMessage.getRespMsg(Status.value_is_null_or_error)));
			}
			map.put("count", count);
			return JSONObject.toJSONString(new RespMsg<>(map));
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			return JSONObject.toJSONString(new RespMsg<>(Status.error,RespMessage.getRespMsg(Status.error)));
		}
	}
	
}
