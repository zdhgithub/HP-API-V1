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

import cn.heipiao.api.pojo.FishSite;
import cn.heipiao.api.pojo.Goods;
import cn.heipiao.api.pojo.RespMsg;
import cn.heipiao.api.service.FishSiteService;
import cn.heipiao.api.service.GoodsService;
import cn.heipiao.api.utils.ResultUtils;
import io.swagger.annotations.Api;

/**
 * @author zf
 * @version 1.0
 * @description 商品resource
 * @date 2016年6月28日
 */
@Api(tags = "商品模块")
@RestController
@RequestMapping(value = "goods",produces="application/json")
public class GoodsResource {
	private static final Logger logger = LoggerFactory
			.getLogger(GoodsResource.class);

	@Resource
	private GoodsService goodsService;
	
	@Resource
	private FishSiteService fishSiteService;

	/**
	 * 查询不同状态的商品
	 * 
	 * @param status
	 * @return
	 * @since 1.0
	 */
//	@Path("{siteId}/{status}/{start}/{size}")
//	@GET
	@RequestMapping(value = "{siteId}/{status}/{start}/{size}",method = RequestMethod.GET)
	public String queryGoodsByType(@PathVariable("status") final Integer status,
			@PathVariable("start") final Integer start,
			@PathVariable("size") final Integer size,
			@PathVariable("siteId") final Integer siteId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("status", status);
		params.put("siteId", siteId);
		params.put("start", start);
		params.put("size", size);
		List<Goods> list = null;
		try {
			list = goodsService.queryGoods(params);
		} catch (Exception e) {
			logger.error("ExceptionMessage:{},printStackTrace:",
					e.getMessage(), e);
			return JSONObject.toJSONString(new RespMsg<Goods>(Status.error));
		}
		return JSONObject.toJSONString(new RespMsg<List<Goods>>(list));
	}
	
	/**
	 * 微信小程序查询不同状态的商品
	 * 
	 * @param status
	 * @return
	 * @since 1.0
	 */
//	@Path("wxMini/{siteId}/{status}/{start}/{size}")
//	@GET
	@RequestMapping(value = "wxMini/{siteId}/{status}/{start}/{size}",method = RequestMethod.GET)
	public String queryWxMiniGoodsByType(@PathVariable("status") final Integer status,
			@PathVariable("start") final Integer start,
			@PathVariable("size") final Integer size,
			@PathVariable("siteId") final Integer siteId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("status", status);
		params.put("siteId", siteId);
		params.put("start", start);
		params.put("size", size);
		List<Goods> list = null;
		try {
			list = goodsService.queryGoods(params);
			FishSite fs = fishSiteService.selectById(siteId);
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("list", list);
			map.put("fishSiteName", fs.getFishSiteName());
			return JSONObject.toJSONString(new RespMsg<>(map));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.toJSONString(new RespMsg<Goods>(Status.error));
		}
	}

	/**
	 * 上架/下架
	 * 
	 * @param json
	 * @return
	 * @since 1.0
	 */
//	@Path("good/status")
//	@PUT
	@RequestMapping(value = "good/status",method = RequestMethod.PUT,consumes = "application/json")
	public String saleGoods(@RequestBody JSONObject json) {
		Goods good = new Goods();
		good.setGoodId(json.getInteger("id"));
		good.setStatus(json.getInteger("status"));
		int result;
		try {
			result = goodsService.modifyGoods(good);
		} catch (Exception e) {
			logger.error("ExceptionMessage:{},printStackTrace:",
					e.getMessage(), e);
			return JSONObject.toJSONString(new RespMsg<Goods>(Status.error));
		}
		return JSONObject.toJSONString(new RespMsg<Goods>(result));
	}

	/**
	 * 修改库存
	 * 
	 * @param json
	 * @return
	 * @since 1.0
	 */
//	@Path("good/amount")
//	@PUT
	@RequestMapping(value = "good/amount",method = RequestMethod.PUT,consumes = "application/json")
	public String modifyAmount(@RequestBody JSONObject json) {
		Goods good = new Goods();
		good.setGoodId(json.getInteger("id"));
		good.setAmount(json.getInteger("amount"));
		int result;
		try {
			result = goodsService.modifyGoods(good);
		} catch (Exception e) {
			logger.error("ExceptionMessage:{},printStackTrace:",
					e.getMessage(), e);
			return JSONObject.toJSONString(new RespMsg<Goods>(Status.error));
		}
		return JSONObject.toJSONString(new RespMsg<Goods>(result));
	}

	/**
	 * 编辑商品信息
	 * 
	 * @param json
	 * @return
	 * @since 1.0
	 */
//	@Path("good")
//	@PUT
	@RequestMapping(value = "good",method = RequestMethod.PUT,consumes = "application/json")
	public String editGoods(@RequestBody JSONObject json) {
		logger.debug("json:{}",json);
		if(json==null) {
			return ResultUtils.out(Status.value_is_null_or_error);
		}
		Goods good = JSONObject.toJavaObject(json, Goods.class);
		int result = 0;
		try {
			result = goodsService.modifyGoodsEx(good);
		} catch (Exception e) {
			logger.error("ExceptionMessage:{},printStackTrace:",
					e.getMessage(), e);
			return JSONObject.toJSONString(new RespMsg<Goods>(Status.error));
		}
		return JSONObject.toJSONString(new RespMsg<Goods>(result));
	}

	/**
	 * 新增商品
	 * 
	 * @param json
	 * @return
	 * @since 1.0
	 */
//	@Path("good")
//	@POST
	@RequestMapping(value = "good",method = RequestMethod.POST,consumes = "application/json")
	public String addGoods(@RequestBody JSONObject json) {
		logger.debug("json:{}",json);
		if(json==null) {
			return ResultUtils.out(Status.value_is_null_or_error);
		}
		Goods good = JSONObject.toJavaObject(json, Goods.class);
		int result;
		try {
			result = goodsService.save(good);
		} catch (Exception e) {
			logger.error("ExceptionMessage:{},printStackTrace:",
					e.getMessage(), e);
			return JSONObject.toJSONString(new RespMsg<Goods>(Status.error));
		}
		return JSONObject.toJSONString(new RespMsg<Goods>(result));
	}

	/**
	 * 
	 * @说明 商品销量统计 - 增量统计
	 * @return
	 * @since 1.2
	 */
//	@Path("volume")
//	@POST
	@RequestMapping(value = "volume",method = RequestMethod.POST,consumes = "application/json")
	public String salesVolume() {
		goodsService.salesVolume();
		return JSONObject.toJSONString(new RespMsg<>());
	}

}
