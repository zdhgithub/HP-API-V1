package cn.heipiao.api.resources;

import java.util.List;

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

import cn.heipiao.api.pojo.ArticleDict;
import cn.heipiao.api.pojo.DepositFish;
import cn.heipiao.api.pojo.DictConfig;
import cn.heipiao.api.pojo.RespMsg;
import cn.heipiao.api.service.ConfigService;
import cn.heipiao.api.utils.ResultUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * @author zf
 * @version 1.0
 * @description
 * @date 2016年6月7日
 */
@Api(tags = "数据字典模块")
@RestController
@RequestMapping(value = "dicts",produces="application/json")
public class DictConfigResource {
	private static final Logger logger = LoggerFactory
			.getLogger(DictConfigResource.class);
	@Resource
	private ConfigService configService;

	
	

	@ApiOperation("文章分类列表获取")
	@RequestMapping(value = "article/{category}",method = RequestMethod.GET)
	public String getListArticleCategory(
			@ApiParam(value = "1:身边长文发布类型列表",required = true)@PathVariable("category") Integer category) {
		try {
			List<ArticleDict> list = configService.getListArticleCategory(category);
			return JSONObject.toJSONString(new RespMsg<>(list));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.toJSONString(new RespMsg<>(Status.error));
		}
	}
	
	/**
	 * 通过type查询某一类的字典
	 * 
	 * @param type
	 * @return
	 * @since 1.0
	 */
	@RequestMapping(value = "{type}",method = RequestMethod.GET)
	public String queryConfigByType(@PathVariable("type") final String type) {
		List<DictConfig> list = null;
		try {
			list = configService.queryConfigByType(type);
		} catch (Exception e) {
			logger.error("ExceptionMessage:{},printStackTrace:",
					e.getMessage(), e);
			return JSONObject.toJSONString(new RespMsg<DepositFish>(
					Status.error));
		}
		return JSONObject.toJSONString(new RespMsg<List<DictConfig>>(list));
	}

	/**
	 * 增加字典
	 * 
	 * @param json
	 * @return
	 * @since 1.0
	 */
	@RequestMapping(value = "ad",method = RequestMethod.POST,consumes = "application/json")
	public String addDict(@RequestBody JSONObject json) {
		try {
			logger.debug("json:{}", json);
			if (json == null || StringUtils.isEmpty(json.getString("type"))
					|| StringUtils.isEmpty(json.getString("value"))
					|| StringUtils.isEmpty(json.getString("remarks"))) {
				return ResultUtils.out(Status.value_is_null_or_error);
			}
			DictConfig dict = JSONObject.toJavaObject(json, DictConfig.class);
			return ResultUtils.out(this.configService.addDict(dict)+"");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return ResultUtils.out(Status.error);
		}
	}

	/**
	 * 删除字典
	 * 
	 * @param id
	 * @return
	 * @since 1.0
	 */
	@RequestMapping(value = "del/{id}",method = RequestMethod.DELETE)
	public String deleteDict(@PathVariable("id") final Integer id) {
		try {
			logger.debug("id:{}", id);
			return ResultUtils.out(this.configService.deleteDict(id));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return ResultUtils.out(Status.error);
		}
	}

	/**
	 * 更新字典
	 * 
	 * @param json
	 * @return
	 * @since 1.0
	 */
	@RequestMapping(value = "upd",method = RequestMethod.PUT,consumes = "application/json")
	public String updateDict(@RequestBody JSONObject json) {
		try {
			logger.debug("json:{}", json);
			if (json == null || json.getInteger("id") == null) {
				return ResultUtils.out(Status.value_is_null_or_error);
			}
			DictConfig dict = JSONObject.toJavaObject(json, DictConfig.class);
			return ResultUtils.out(this.configService.updateDict(dict));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return ResultUtils.out(Status.error);
		}
	}
}
