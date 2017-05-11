package cn.heipiao.api.resources;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

import cn.heipiao.api.constant.ApiConstant;
import cn.heipiao.api.constant.FishShopConstant;
import cn.heipiao.api.constant.RespMessage;
import cn.heipiao.api.pojo.DictConfig;
import cn.heipiao.api.pojo.FishShop;
import cn.heipiao.api.pojo.FishShopUserStatus;
import cn.heipiao.api.pojo.FishSite;
import cn.heipiao.api.pojo.RespMsg;
import cn.heipiao.api.pojo.User;
import cn.heipiao.api.service.ConfigService;
import cn.heipiao.api.service.FishShopService;
import cn.heipiao.api.service.FishSiteService;
import cn.heipiao.api.service.ModuleService;
import cn.heipiao.api.service.PartnerService;
import cn.heipiao.api.service.RewardPlatformService;
import cn.heipiao.api.service.UserOpService;
import cn.heipiao.api.utils.ExMapUtil;
import cn.heipiao.api.utils.PageSet;
import io.swagger.annotations.Api;

/**
 * @author asdf3070@163.com
 * @date 2016年10月11日
 * @version 2.0
 */
@Api(tags = "店铺模块")
@RestController
@RequestMapping(value = "fshop",produces="application/json")
public class FishShopResource {

	private static final Logger logger = LoggerFactory.getLogger(FishShopResource.class);

	@Resource
	private FishShopService fishShopService;

	@Resource
	private UserOpService userService;
	
	@Resource
	private ConfigService configService;
	
	@Resource
	private ModuleService moduleService;
	@Resource
	private RewardPlatformService rewardPlatformService;
	@Resource
	private PartnerService partnerService;
	@Resource
	private FishSiteService fishSiteService;
	
	
	
	
	
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
	 * {"shopId":123,"uid":1}
	 * 开通优惠券发放功能
	 * @param json
	 * @return
	 */
//	@PUT
//	@Path("open/coupon")
	@RequestMapping(value = "open/coupon",method = RequestMethod.PUT,consumes = "application/json")
	public String openCoupon(@RequestBody JSONObject json){
		try {
			logger.info("json:{}",json);
			Long shopId = json == null ? null : json.getLong("shopId");
			Long  uid = json == null ? null : json.getLong("uid");
			if(shopId == null || uid == null){
				return JSONObject.toJSONString(new RespMsg<>(Status.value_is_null_or_error, RespMessage.getRespMsg(Status.value_is_null_or_error)));
			}
			int rs = fishShopService.modifyOpenCoupon(uid,shopId);
			return JSONObject.toJSONString(new RespMsg<>(rs));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.toJSONString(new RespMsg<>(Status.error,RespMessage.getRespMsg(Status.error)));
		}
	}

	/**
	 * 查询并返回所有渔具店信息
	 * 并与在更新的有时间的脚本，使每个渔具店的各时间查差1秒
	 */
	public String settime() {
		String fPath = FishShopResource.class.getResource("").getPath() + "updateTime.sql";
		FileOutputStream fos = null;
		PrintStream ps = null;
		try {
			logger.debug("uid:{} settime");
			List<FishShop> fishShop = fishShopService.getAllFishShop();
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Long cts = sdf.parse("2016-10-01 00:00:00").getTime();
			fos = new FileOutputStream(fPath);
			ps = new PrintStream(fos);
			for(int i=0; i<fishShop.size(); i++){
				FishShop fs = fishShop.get(i);
				String currCreateTime = sdf.format(new Date(cts));
				String updateString = "UPDATE `t_fish_shop` SET `f_fish_shop_auth_time_name`='" + currCreateTime + "', "
						+ "`f_fish_shop_auth_time_sign`='" + currCreateTime + "', `f_fish_shop_create_time`='" + currCreateTime + "', "
						+ "`f_fish_shop_update_time`='" + currCreateTime + "' WHERE `f_fish_shop_id`=" + fs.getId() + ";";
				ps.println(updateString);
				cts += 1000;
			}
			ps.flush();
			if(ps != null){
				ps.close();
			}
			if(fos != null){
				fos.close();
			}
			
			return JSONObject.toJSONString(new RespMsg<List<FishShop>>(fishShop));
		} catch (Exception e) {
			if(ps != null){
				ps.close();
			}
			if(fos != null){
				try {
					fos.close();
				} catch (IOException e1) {
					logger.error(e1.getMessage(), e1);
				}
			}
			logger.error(e.getMessage(), e);
			return JSONObject.toJSONString(new RespMsg<>(Status.error,
					RespMessage.getRespMsg(Status.error)));
		}
	}
	
	

	/**
	 * 查询指定用户的渔具店列表
	 * @param uid 所属用户唯一标识
	 * @param type 0：列表显示数据 1-全数据
	 * @return
	 */
//	@GET
//	@Path("/query/user/{uid}/{type}")
	@RequestMapping(value = "query/user/{uid}/{type}",method = RequestMethod.GET)
	public String queryUser(@PathVariable("uid") final Integer uid, 
			@PathVariable("type") final Integer type) {
		try {
			logger.debug("uid:{} type:{}", uid, type);
			List<FishShop> fishShop = null;
			if(type == 0){
				fishShop = fishShopService.getFishShopByUid(uid);
			}else if(type == 1){
				fishShop = fishShopService.getFishShopAllByUid(uid);
			}
			return JSONObject.toJSONString(new RespMsg<List<FishShop>>(fishShop));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.toJSONString(new RespMsg<>(Status.error,
					RespMessage.getRespMsg(Status.error)));
		}
	}
	
	/**
	 * 查询指定用户关注的渔具店
	 * @param uid
	 * @param pagenum
	 * @param pagesize
	 * @return
	 */
//	@GET
//	@Path("/query/collect/{uid}/{lng}/{lat}/{pagenum}/{pagesize}")
	@RequestMapping(value = "query/collect/{uid}/{lng}/{lat}/{pagenum}/{pagesize}",method = RequestMethod.GET)
	public String queryCollect(
			@PathVariable("uid") final String uid,
			@PathVariable("lng") Double lng,
			@PathVariable("lat") Double lat, 
			@PathVariable("pagenum") Integer pagenum,
			@PathVariable("pagesize") Integer pagesize) {
		try {
			logger.debug("uid:{} lng:{} lat:{} pagenum:{} pagesize:{}", uid, lng, lat, pagenum, pagesize);
			if (uid == null) {
				return JSONObject.toJSONString(new RespMsg<>(
						Status.value_is_null_or_error, RespMessage
								.getRespMsg(Status.value_is_null_or_error)));
			}
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("uid", uid);
			
			//处理经纬度
			if(lng > 0 && lng < 180 && lat > 0 &&  lat < 90){
				map.put("lng", lng);
				map.put("lat", lat);
			}
			
			//计算分页
			pagenum = pagenum <= 0 ? 1 : pagenum;
			pagesize = pagesize <= 0 ? 1 : pagesize;
			int startItem = (pagenum - 1) * pagesize;
			logger.debug("executeMap:{}", map);
			int totalItem = fishShopService.queryCollectListCount(map); //
			int totalPage = totalItem / pagesize + (totalItem % pagesize == 0 ? 0 : 1);
			if (totalPage > 0 && pagenum > totalPage) { //页数大于总页数
				//pagenum = totalPage;
				//startItem = (pagenum - 1) * pagesize;
				PageSet<FishShop> pageSet = new PageSet<>(pagenum, pagesize, totalItem, new ArrayList<FishShop>());
				return JSONObject.toJSONString(new RespMsg<PageSet<FishShop>>(pageSet));
			}
			map.put("startItem", startItem);
			map.put("pagesize", pagesize);
			//最终查询
			logger.debug("executeMap:{}", map);
			List<FishShop> pojos = fishShopService.queryCollectList(map);
			PageSet<FishShop> pageSet = new PageSet<>(pagenum, pagesize, totalItem, pojos);
			return JSONObject.toJSONString(new RespMsg<PageSet<FishShop>>(pageSet));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.toJSONString(new RespMsg<>(Status.error,
					RespMessage.getRespMsg(Status.error)));
		}
	}
	
	/**
	 * 查询指定标识的渔具店
	 * @param id 渔具店唯一标识
	 * @return
	 */
//	@GET
//	@Path("/query/shop/{id}/{uid}")
	@RequestMapping(value = "query/shop/{id}/{uid}",method = RequestMethod.GET)
	public String queryUser(@PathVariable("id") final Long id, 
			@PathVariable("uid") final String uid) {
		try {
			logger.debug("id:{} uid:{}", id, uid);
			String realUid = uid;
			Integer intUid = null;
			if(uid.indexOf("-") == 0){
				realUid = uid.substring(1);
			}
			intUid = parseInteger(realUid);
			
			FishShop fishShop = fishShopService.getFishShopById(id, intUid);
			if (fishShop == null)
				return JSONObject.toJSONString(new RespMsg<>(
						Status.FISH_SHOP_NOT_EXISTS, RespMessage
								.getRespMsg(Status.FISH_SHOP_NOT_EXISTS)));
			
			return JSONObject.toJSONString(new RespMsg<FishShop>(fishShop));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.toJSONString(new RespMsg<>(Status.error,
					RespMessage.getRespMsg(Status.error)));
		}
	}
	
	public static Integer parseInteger(String sInt){
		if(sInt != null){
			try{
				return Integer.parseInt(sInt);
			}catch(Exception e){
				return null;
			}
		}else{
			return null;
		}
	}
	
	
	/**
	 * 查询渔具店(分类检索)
	 * 
	 * APP获取渔具店列表接口，高频接口
	 * 
	 * XXX 目前发现拉列表的时候有部分无图渔具店无法获取默认图片，2016.12.30
	 * 
	 * @param id 渔具店唯一标识
	 * @return
	 */
//	@GET
//	@Path("/query/list/{status}/{rtype}/{regionId}/{keyword}/{uid}/{lng}/{lat}/{radius}/{order}/{pagenum}/{pagesize}")
	@RequestMapping(value = "query/list/{status}/{rtype}/{regionId}/{keyword}/{uid}/{lng}/{lat}/{radius}/{order}/{pagenum}/{pagesize}",method = RequestMethod.GET)
	public String queryList(
			@PathVariable("status") String status,
			@PathVariable("rtype") Integer rtype,
			@PathVariable("regionId") Integer regionId,
			@PathVariable("keyword") String keyword,
			@PathVariable("uid") Integer uid,
			@PathVariable("lng") Double lng,
			@PathVariable("lat") Double lat, 
			@PathVariable("radius") Integer radius,
			@PathVariable("order") String order,
			@PathVariable("pagenum") Integer pagenum,
			@PathVariable("pagesize") Integer pagesize) {
		try {
			logger.debug("status:{}", status);
			logger.debug("rtype:{}", rtype);
			logger.debug("regionId:{}", regionId);
			logger.debug("keyword:{}", keyword);
			logger.debug("uid:{}", uid);
			logger.debug("lng:{}", lng);
			logger.debug("lat:{}", lat);
			logger.debug("radius:{}", radius);
			logger.debug("order:{}", order);
			logger.debug("pagenum:{}", pagenum);
			logger.debug("pagesize:{}", pagesize);		
			
			Map<String, Object> map = new HashMap<String, Object>();

			//处理状态筛选 status
			due2ListStatus(status, map);
			if(map.get("pagesize") != null){
				pagesize = (Integer)map.get("pagesize");
			}
			
			//处理按地区筛选
			if(rtype > 0 && regionId > 0){
				map.put("rtype", rtype);
				map.put("regionId", regionId);
			}
			
			//处理模糊查询关键字
			if(!StringUtils.isBlank(keyword) && keyword.indexOf('-') == 0){
				keyword = keyword.trim().substring(1);
				if(keyword.length() > 0){
					map.put("keyword", keyword);
				}
			}
			
			//处理用户对本渔具店的点赞、收藏情况
			if(uid > 0){
				map.put("uid", uid);
			}
			
			//处理经纬度
			if(lng > 0 && lng < 180 && lat > 0 &&  lat < 90){
				map.put("lng", lng);
				map.put("lat", lat);
				//处理范围：单位km
				if(radius > 0){
					int currRadius = 5;//默认范围 5
					if(radius == 1){//范围=调用系统配置
						List<DictConfig> radiusList = configService.queryConfigByType("fs_radius");
						if(radiusList != null && radiusList.size() > 0){
							DictConfig radiusConfig = radiusList.get(0);
							try{
								currRadius = parseInteger(radiusConfig.getValue());
							}catch(Exception e){}
						}
					}else{//范围=参数
						currRadius = radius;
					}
					double[] lls = ExMapUtil.getAround(lat, lng, currRadius * 1000);
					map.put("minLng", lls[0]);
					map.put("minLat", lls[1]);
					map.put("maxLng", lls[2]);
					map.put("maxLat", lls[3]);
					map.put("radius", currRadius);
				}
			}
			
			//处理显示排序分类
			if(!StringUtils.isBlank(order) && order.indexOf('-') == 0){
				order = order.trim().substring(1);
				if(order.length() > 0){
					String[] orderArray = order.split(",");
					for(int i=0; i<orderArray.length; i++){
						String[] oArray = orderArray[i].split(":");
						if(oArray.length == 2){
							map.put(oArray[0].trim(), oArray[1].trim().toUpperCase());
						}
					}
				}
			}

			//计算分页
			pagenum = pagenum <= 0 ? 1 : pagenum;
			pagesize = pagesize <= 0 ? 1 : pagesize;
			int startItem = (pagenum - 1) * pagesize;
			logger.debug("executeMap:{}", map);
			int totalItem = fishShopService.queryListCount(map); //
			int totalPage = totalItem / pagesize + (totalItem % pagesize == 0 ? 0 : 1);
			if (totalPage > 0 && pagenum > totalPage) { //页数大于总页数
				//pagenum = totalPage;
				//startItem = (pagenum - 1) * pagesize;
				PageSet<FishShop> pageSet = new PageSet<>(pagenum, pagesize, totalItem, new ArrayList<FishShop>());
				return JSONObject.toJSONString(new RespMsg<PageSet<FishShop>>(pageSet));
			}
			map.put("startItem", startItem);
			map.put("pagesize", pagesize);
			//最终查询
			logger.debug("executeMap:{}", map);
			List<FishShop> pojos = fishShopService.queryList(map);
			PageSet<FishShop> pageSet = new PageSet<>(pagenum, pagesize, totalItem, pojos);
			pageSet.setObj(map);
			return JSONObject.toJSONString(new RespMsg<PageSet<FishShop>>(pageSet));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.toJSONString(new RespMsg<>(Status.error,
					RespMessage.getRespMsg(Status.error)));
		}
	}
	
	/**
	 * 处理查询渔具店(分类检索)的status
	 * @param status
	 * @param map
	 */
	public static void due2ListStatus(String status, Map<String, Object> map){
		String authStatusString = status.toString();
		Integer p0 = null;
		Integer p1 = null;       
		Integer p2 = null;   
		String[] authStatusArray = authStatusString.split(ApiConstant.spaceCharacter.PARAMS_SPACE_EACH_OTHER);
		if(authStatusArray.length == 3){
			p0 = parseInteger(authStatusArray[0]);
			p1 = parseInteger(authStatusArray[1]);
			p2 = parseInteger(authStatusArray[2]);
		}else if(authStatusArray.length == 2){
			p0 = parseInteger(authStatusArray[0]);
			p1 = parseInteger(authStatusArray[1]);
		}else {
			p0 = parseInteger(authStatusArray[0]);
		}
		if(p0 != null){
			switch(p0){
			case 51: // 51-附近店铺(查询附近的渔具店)
				//pagesize = 4;
				map.put("pagesize", 5);
				break;
			case 61: // 61-认证列表(所有等待审核的店铺)
				if(p1 != null){
					map.put("signUserid", p1);
				}else{
					map.put("authList", 61);
				}
				break;
			case 888:// 手机app调用时，设置定值
				map.put("status", FishShopConstant.STATUS_1);
				break;
			case 999:// 不做此项限制(处理状态筛选 status)
				break;
			default: // 设置认证状态：0-漂友认证 1-实名认证 2-签约认证
				List<Integer> fsas = FishShopConstant.getMaps(FishShopConstant.FISH_SHOP_AUTH_STATUS);
				if(fsas.contains(p0)){
					map.put("authStatus", p0);
					map.put("status", FishShopConstant.STATUS_1);
				}
			}
		}
		if(p1 != null){ // 设置上线状态：0-待审核      1-正常           2-审核未通过   3-下架    4-黑名单
			List<Integer> fss = FishShopConstant.getMaps(FishShopConstant.FISH_SHOP_STATUS);
			if(fss.contains(p1)){
				map.put("status", p1);
			}
		}
		if(p2 != null){ // 设置店类型：     0-平台添加 1-漂友添加
			List<Integer> fst = FishShopConstant.getMaps(FishShopConstant.FISH_SHOP_TYPE);
			if(fst.contains(p2)){
				map.put("type", p2);
			}
		}
	}

	/**
	 * 查询城市合伙人签约店铺列表
	 */
//	@GET
//	@Path("/list/{cityId}/{lat}/{lng}/{pagenum}/{pagesize}")
	@RequestMapping(value = "list/{cityId}/{lat}/{lng}/{pagenum}/{pagesize}",method = RequestMethod.GET)
	public String getListByCity(
			@PathVariable("cityId") Integer cityId,
			@PathVariable("lat") Double lat,
			@PathVariable("lng") Double lng,
			@PathVariable("pagenum") Integer pagenum, 
			@PathVariable("pagesize") Integer pagesize) {
		try {
			logger.debug("cityId:{},lat:{},lng:{},pagenum:{},pagesize:{},",
					cityId, lat, lng, pagenum, pagesize);
			Map<String, Object> map = new HashMap<String, Object>();

			map.put("cityId", cityId);
			
			//处理经纬度
			if(lng > 0 && lng < 180 && lat > 0 &&  lat < 90){
				map.put("lng", lng);
				map.put("lat", lat);
			}
			//计算分页
			pagenum = pagenum <= 0 ? 1 : pagenum;
			pagesize = pagesize <= 0 ? 1 : pagesize;
			int startItem = (pagenum - 1) * pagesize;
			logger.debug("executeMap:{}", map);
			int totalItem = fishShopService.queryPartnerListCount(map); //
			int totalPage = totalItem / pagesize + (totalItem % pagesize == 0 ? 0 : 1);
			if (totalPage > 0 && pagenum > totalPage) { //页数大于总页数
				PageSet<FishShop> pageSet = new PageSet<>(pagenum, pagesize, totalItem, new ArrayList<FishShop>());
				return JSONObject.toJSONString(new RespMsg<PageSet<FishShop>>(pageSet));
			}
			map.put("startItem", startItem);
			map.put("pagesize", pagesize);
			//最终查询
			logger.debug("executeMap:{}", map);
			List<FishShop> pojos = fishShopService.queryPartnerList(map);
			for(int i=0; i<pojos.size(); i++){	
				Integer signIsApply = pojos.get(i).getSignIsApply();
				if(signIsApply != null && (signIsApply == 1||signIsApply == 4)){
					pojos.get(i).setSignStatus(FishShopConstant.SIGN_ALLREADY);
					pojos.get(i).setTradingSum(null);
					pojos.get(i).setSignIsApply(null);
				} 
				if(signIsApply != null && signIsApply == 3){
					pojos.get(i).setSignStatus(FishShopConstant.TICKET_ALLREADY);
					pojos.get(i).setSignIsApply(null);
				}
				if(signIsApply != null && signIsApply == 2){
					pojos.get(i).setSignStatus(FishShopConstant.AUTH_ALLREADY);
				}
			}
			
			PageSet<FishShop> pageSet = new PageSet<>(pagenum, pagesize, totalItem, pojos);
			return JSONObject.toJSONString(new RespMsg<PageSet<FishShop>>(pageSet));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.toJSONString(new RespMsg<>(Status.error,
					RespMessage.getRespMsg(Status.error)));
		}

	}
	/**
	 * 查询城市合伙人签约店铺列表(new 参数不一样)
	 */
//	@GET
//	@Path("/list/{cityId}/{lat}/{lng}/{pagenum}/{pagesize}/{partnerId}")
	@RequestMapping(value = "list/{cityId}/{lat}/{lng}/{pagenum}/{pagesize}/{partnerId}",method = RequestMethod.GET)
	public String getListByCity(
			@PathVariable("cityId") Integer cityId,
			@PathVariable("lat") Double lat,
			@PathVariable("lng") Double lng,
			@PathVariable("pagenum") Integer pagenum, 
			@PathVariable("pagesize") Integer pagesize,
			@PathVariable("partnerId")Integer partnerId) {
		try {
			logger.debug("cityId:{},lat:{},lng:{},pagenum:{},pagesize:{},partnerId:{}",
					cityId, lat, lng, pagenum, pagesize,partnerId);
			Map<String, Object> map = new HashMap<String, Object>();

			map.put("cityId", cityId);
			
			//处理经纬度
			if(lng > 0 && lng < 180 && lat > 0 &&  lat < 90){
				map.put("lng", lng);
				map.put("lat", lat);
			}
			//计算分页
			pagenum = pagenum <= 0 ? 1 : pagenum;
			pagesize = pagesize <= 0 ? 1 : pagesize;
			int startItem = (pagenum - 1) * pagesize;
			logger.debug("executeMap:{}", map);
			int totalItem = fishShopService.queryPartnerListCount(map); //
			int totalPage = totalItem / pagesize + (totalItem % pagesize == 0 ? 0 : 1);
			if (totalPage > 0 && pagenum > totalPage) { //页数大于总页数
				PageSet<FishShop> pageSet = new PageSet<>(pagenum, pagesize, totalItem, new ArrayList<FishShop>());
				return JSONObject.toJSONString(new RespMsg<PageSet<FishShop>>(pageSet));
			}
			map.put("startItem", startItem);
			map.put("pagesize", pagesize);
			map.put("partnerId", partnerId);
			//最终查询
			logger.debug("executeMap:{}", map);
			List<FishShop> pojos = fishShopService.queryPartnerList(map);
			for(int i=0; i<pojos.size(); i++){
				Integer signIsApply = pojos.get(i).getSignIsApply();
				if(signIsApply != null && (signIsApply == 1 || signIsApply == 4)){
					pojos.get(i).setSignStatus(FishShopConstant.SIGN_ALLREADY);
					pojos.get(i).setSignIsApply(null);
				}else if(signIsApply != null && signIsApply == 3){
					pojos.get(i).setSignStatus(FishShopConstant.TICKET_ALLREADY);
					pojos.get(i).setSignIsApply(null);
				}else if(signIsApply != null && signIsApply == 2){
					pojos.get(i).setSignStatus(FishShopConstant.AUTH_ALLREADY);
				}
			}
			PageSet<FishShop> pageSet = new PageSet<>(pagenum, pagesize, totalItem, pojos);
			return JSONObject.toJSONString(new RespMsg<PageSet<FishShop>>(pageSet));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.toJSONString(new RespMsg<>(Status.error,
					RespMessage.getRespMsg(Status.error)));
		}

	}
	/**
	 * 修改渔具店<br>
	 * 修改指定唯一标识的渔具店
	 * @param json
	 * @return
	 */
	@SuppressWarnings("rawtypes")
//	@Path("/modification")
//	@PUT
	@RequestMapping(value = "modification",method = RequestMethod.PUT,consumes = "application/json")
	public String modification(@RequestBody JSONObject json) {
		try {
			logger.debug("json:{}", json);
			FishShop pojo = json == null ? null : JSONObject.toJavaObject(json, FishShop.class);
			if (pojo == null || pojo.getId() == null) {
				return JSONObject.toJSONString(new RespMsg(
						Status.value_is_null_or_error, RespMessage
								.getRespMsg(Status.value_is_null_or_error)));
			}
			FishShop tmp = fishShopService.getFishShopById(pojo.getId(), null);
			if (tmp == null)
				return JSONObject.toJSONString(new RespMsg<>(
						Status.FISH_SHOP_NOT_EXISTS, RespMessage
								.getRespMsg(Status.FISH_SHOP_NOT_EXISTS)));
			//处理取九图之首作为MainImg存储
			if(StringUtils.isBlank(pojo.getMainImg())){
				String[] resources = pojo.getResources().trim().split(",");
				for(int i=0; i<resources.length; i++){
					if(!StringUtils.isBlank(resources[i])){
						pojo.setMainImg(resources[i]);
						break;
					}
				}
			}
			fishShopService.setFishShop(pojo);
			return JSONObject.toJSONString(new RespMsg());
		} catch (Exception e) {
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
			pojo.setFlag(FishShopConstant.FLAG_OF);
			
			pojo.setId(fishShopService.addFishShop(pojo));
			return JSONObject.toJSONString(new RespMsg<FishShop>(pojo));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.toJSONString(new RespMsg(Status.error));
		}
	}
	
	
	/**
	 * 指定用户对指定渔具店点赞、收藏/取消<br>
	 * 再次执行则取消这个用户的点赞、收藏
	 * @param json
	 * @return
	 */
	@SuppressWarnings("rawtypes")
//	@Path("/shop/ustatus")
//	@PUT
	@RequestMapping(value = "shop/ustatus",method = RequestMethod.PUT,consumes = "application/json")
	public String ustatus(@RequestBody JSONObject json){
		try{
			logger.debug("json:{}", json);
			Long id = json == null ? null : json.getLong("id");
			Long uid = json == null ? null : json.getLong("uid");
			Integer type = json == null ? null : json.getInteger("type");
			if (id == null || uid == null || type == null) {
				return JSONObject.toJSONString(new RespMsg<>(Status.value_is_null_or_error, 
						RespMessage.getRespMsg(Status.value_is_null_or_error)));
			}
			FishShop pojo = fishShopService.getFishShopById(id, null);
			if (pojo == null){
				return JSONObject.toJSONString(new RespMsg<>(Status.FISH_SHOP_NOT_EXISTS, 
						RespMessage.getRespMsg(Status.FISH_SHOP_NOT_EXISTS)));
			}
			User u = userService.queryUserById(uid);
			if (u == null) {
				return JSONObject.toJSONString(new RespMsg<>(Status.no_such_user, 
						RespMessage.getRespMsg(Status.no_such_user)));
			}
			List<Integer> fsust = FishShopConstant.getMaps(FishShopConstant.FISH_SHOP_USER_STATUS_TYPE);
			if(!fsust.contains(type)){
				return JSONObject.toJSONString(new RespMsg<>(Status.FISH_SHOP_USER_STATUS_NOT_EXISTS, 
						RespMessage.getRespMsg(Status.FISH_SHOP_USER_STATUS_NOT_EXISTS)));
			}
			FishShopUserStatus userStatus = new FishShopUserStatus();
			userStatus.setFsId(id);
			userStatus.setFusType(type);
			userStatus.setUid(uid);
			FishShopUserStatus fsUserStatus = fishShopService.getFishShopUserStatus(userStatus);
			if(fsUserStatus != null){
				//通过状态设置取消关注、点赞
				//fishShopService.delFishShopUserStatus(userStatus);
				//通过硬盘删除记录设置取消关注、点赞
				fishShopService.delFishShopUserStatus4HD(userStatus);
			}else{
				fishShopService.addFishShopUserStatus(userStatus);
			}
			if(type == FishShopConstant.USER_STATUS_COLLECT){
				fishShopService.updateFishShopFocusCount(id);
			}
			return JSONObject.toJSONString(new RespMsg<>());
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			return JSONObject.toJSONString(new RespMsg(Status.error, 
					RespMessage.getRespMsg(Status.error)));
		}
	}
	
	/**
	 * 修复接口：统计表“渔具店用户保存状态流水表”的收藏数量对渔具店主表进行更新
	 * @return
	 */
	@SuppressWarnings("rawtypes")
//	@Path("/fix/focus/count")
//	@PUT
	@RequestMapping(value = "fix/focus/count",method = RequestMethod.PUT,consumes = "application/json")
	public String fixFSFocusCount(){
		try{
			List<FishShopUserStatus> list = fishShopService.getAllFSUSShopId();
			for(int i=0; i<list.size(); i++){
				FishShopUserStatus fsus = list.get(i);
				if(fsus.getFsId() != null){
					fishShopService.updateFishShopFocusCount(fsus.getFsId());
				}
			}
			return JSONObject.toJSONString(new RespMsg(Status.success, 
					RespMessage.getRespMsg(Status.success)));
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			return JSONObject.toJSONString(new RespMsg(Status.error, 
					RespMessage.getRespMsg(Status.error)));
		}
	}
	
	/**
	 * 指定店铺设置图标logo
	 * @param json
	 * @return
	 */
	@SuppressWarnings("rawtypes")
//	@Path("/logo")
//	@PUT
	@RequestMapping(value = "logo",method = RequestMethod.PUT,consumes = "application/json")
	public String logo(@RequestBody JSONObject json){
		try{
			logger.debug("json:{}", json);
			Long fid = json == null ? null : json.getLong("fid");
			if (fid == null) {
				return JSONObject.toJSONString(new RespMsg<>(Status.value_is_null_or_error, 
						RespMessage.getRespMsg(Status.value_is_null_or_error)));
			}
			FishShop pojo = fishShopService.getFishShopById(fid);
			if (pojo == null){
				return JSONObject.toJSONString(new RespMsg<>(Status.fish_site_not_exists, 
						RespMessage.getRespMsg(Status.fish_site_not_exists)));
			}
			
			fishShopService.setLogo(pojo);
			return JSONObject.toJSONString(new RespMsg<>());
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			return JSONObject.toJSONString(new RespMsg(Status.error, RespMessage.getRespMsg(Status.error)));
		}
	}
	/**
	 * 查询钓场附近的渔具店（10公里以内）
	 * 2.3
	 */
//	@GET
//	@Path("shop/{siteId}/{lat}/{lng}/{start}/{size}")
	@RequestMapping(value = "shop/{siteId}/{lat}/{lng}/{start}/{size}",method = RequestMethod.GET)
	public String selectShop(@PathVariable("siteId")Integer siteId,
							@PathVariable("lat") Double lat,@PathVariable("lng")Double lng,
							@PathVariable("start") Integer start,@PathVariable("size")Integer size){
		try {
			logger.debug("siteId:{},lat:{},lng:{},start:{},size:{}",siteId,lat,lng,start,size);
			
			if (siteId == null) {
				return JSONObject.toJSONString(new RespMsg<>(Status.value_is_null_or_error, 
						RespMessage.getRespMsg(Status.value_is_null_or_error)));
			}
			FishSite pojo = fishSiteService.selectById(siteId);
			if (pojo == null){
				return JSONObject.toJSONString(new RespMsg<>(Status.fish_site_not_exists, 
						RespMessage.getRespMsg(Status.fish_site_not_exists)));
			}
			Map<String,Object> map = new HashMap<>();
			map.put("siteId", siteId);
			map.put("lat", lat);
			map.put("lng", lng);
			List<FishShop> total = fishShopService.queryDistributionList(map);
			map.put("start", start);
			map.put("size", size);
			List<FishShop> list = fishShopService.queryDistributionList(map);
			start = start <= 0 ? 1 : start;
			size = size <= 0 ? 1 : size;
			int startItem = (start - 1) * size;
			int totalItem = total.size();
			int totalPage = totalItem / size + (totalItem % size == 0 ? 0 : 1);
			if (totalPage > 0 && start > totalPage) { //页数大于总页数

				PageSet<FishShop> pageSet = new PageSet<>(start, size, totalItem, new ArrayList<FishShop>());
				return JSONObject.toJSONString(new RespMsg<PageSet<FishShop>>(pageSet));
			}
			map.put("startItem", startItem);
			PageSet<FishShop> pageSet = new PageSet<>(start, size, totalItem, list);
			return JSONObject.toJSONString(new RespMsg<PageSet<FishShop>>(pageSet));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.toJSONString(new RespMsg<>(Status.error,RespMessage.getRespMsg(Status.error)));
		}
	}
}
