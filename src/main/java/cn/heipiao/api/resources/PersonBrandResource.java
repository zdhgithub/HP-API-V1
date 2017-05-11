package cn.heipiao.api.resources;

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

import cn.heipiao.api.pojo.FishShop;
import cn.heipiao.api.service.BrandService;
import cn.heipiao.api.service.FishShopService;
import cn.heipiao.api.utils.ResultUtils;

/**
 * 
 * @ClassName: PersonBrandResource
 * @Description: 个人品牌
 * @author zf
 * @date 2016年10月11日
 * @version 2.0
 */
//@Path("brand")
//@Consumes({ MediaType.APPLICATION_JSON })
//@Produces({ MediaType.APPLICATION_JSON })
//@Controller
@RestController
@RequestMapping(value = "brand",produces="application/json")
public class PersonBrandResource {
	private Logger logger = LoggerFactory.getLogger(PersonBrandResource.class);

	@Resource
	private BrandService brandService;

	@Resource
	private FishShopService fishShopService;

	/**
	 * 查询用户的自我印象
	 * 
	 * @param uid
	 * @return
	 * @since 2.0
	 */
//	@Path("impression/{shopId}")
//	@GET
	@RequestMapping(value = "impression/{shopId}",method = RequestMethod.GET)
	public String getPersonImp(@PathVariable("shopId") Long shopId) {
		try {
			logger.debug("shopId:{}", shopId);
			if (shopId == null) {
				return ResultUtils.out(Status.value_is_null_or_error);
			}
			FishShop fs = this.fishShopService.getFishShopById(shopId);
			if (fs == null) {
				return ResultUtils.out(Status.FISH_SHOP_NOT_EXISTS);
			}
			if (fs.getUid() == null) {
				return ResultUtils.out(Status.FISH_SHOP_NOT_NAME_PASS);
			}
			return ResultUtils
					.out(brandService.getPersonImpression(fs.getUid(),shopId));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return ResultUtils.out(Status.error);
		}
	}

	/**
	 * 添加印象或是取消印象
	 * 
	 * @param json
	 * @return
	 * @since 2.0
	 */
//	@Path("impression/refresh")
//	@POST
	@RequestMapping(value = "impression/refresh",method = RequestMethod.POST,consumes = "application/json")
	public String startOrForbid(@RequestBody JSONObject json) {
		try {
			logger.debug("印象：json:{}", json);
			if (json == null || json.getLong("shopId") == null
					|| StringUtils.isEmpty(json.getString("label"))
					|| json.getInteger("type") == null) {
				return ResultUtils.out(Status.value_is_null_or_error);
			}
			return ResultUtils.out(this.brandService.startOrForbid(json));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return ResultUtils.out(Status.error);
		}
	}

	/**
	 * C端用户评价印象
	 * 
	 * @param json
	 *            uid登陆用户
	 * @return
	 * @since 2.0
	 */
//	@Path("impression/plus")
//	@PUT
	@RequestMapping(value = "impression/plus",method = RequestMethod.PUT,consumes = "application/json")
	public String plusOne(@RequestBody JSONObject json) {
		try {
			logger.debug("josn:{}", json);
			if (json == null || json.getLong("shopId") == null
					|| json.getInteger("uid") == null
					|| StringUtils.isEmpty(json.getString("label"))) {
				return ResultUtils.out(Status.value_is_null_or_error);
			}
			return ResultUtils.out(this.brandService.plus(
					json.getInteger("uid"), json.getLong("shopId"),
					json.getString("label")));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return ResultUtils.out(Status.error);
		}
	}

	/**
	 * 获取用户领域列表
	 * 
	 * @param uid
	 * @return
	 * @since 2.0
	 */
//	@Path("field/{shopId}")
//	@GET
	@RequestMapping(value = "field/{shopId}",method = RequestMethod.GET)
	public String getFields(@PathVariable("shopId") final Long shopId) {
		try {
			logger.debug("shopId:{}", shopId);
			if (shopId == null) {
				return ResultUtils.out(Status.value_is_null_or_error);
			}
			FishShop fs = this.fishShopService.getFishShopById(shopId);
			if (fs == null) {
				return ResultUtils.out(Status.FISH_SHOP_NOT_EXISTS);
			}
			if (fs.getUid() == null) {
				return ResultUtils.out(Status.FISH_SHOP_NOT_NAME_PASS);
			}
			return ResultUtils.out(this.brandService.getFields(shopId));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return ResultUtils.out(Status.error);
		}
	}

	/**
	 * 用户添加领域
	 * 
	 * @param json
	 * @return
	 * @since 2.0
	 */
//	@Path("field/add")
//	@POST
	@RequestMapping(value = "field/add",method = RequestMethod.POST,consumes = "application/json")
	public String addField(@RequestBody JSONObject json) {
		try {
			logger.debug("json:{}", json);
			if (json.getLong("shopId") == null
					|| StringUtils.isEmpty(json.getString("label"))) {
				return ResultUtils.out(Status.value_is_null_or_error);
			}
			return ResultUtils.out(this.brandService.addField(json.getLong("shopId"),
					json.getString("label")));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return ResultUtils.out(Status.error);
		}
	}

	/**
	 * 用户删除领域
	 * 
	 * @param uid
	 * @param label
	 * @return
	 * @since 2.0
	 */
//	@Path("field/{shopId}/{label}")
//	@DELETE
	@RequestMapping(value = "field/{shopId}/{label}",method = RequestMethod.DELETE)
	public String rmField(@PathVariable("shopId") final Long shopId,
			@PathVariable("label") final String label) {
		try {
			logger.debug("shopId:{},label:{}", shopId, label);
			if (shopId == null || StringUtils.isEmpty(label)) {
				return ResultUtils.out(Status.value_is_null_or_error);
			}
			return ResultUtils.out(this.brandService.rmField(shopId, label));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return ResultUtils.out(Status.error);
		}
	}
}
