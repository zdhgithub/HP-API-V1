/**
 * 
 */
package cn.heipiao.api.resources;

import java.util.ArrayList;
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

import cn.heipiao.api.constant.FishSiteConstant;
import cn.heipiao.api.constant.RespMessage;
import cn.heipiao.api.pojo.FoundMenu;
import cn.heipiao.api.pojo.Partner;
import cn.heipiao.api.pojo.RespMsg;
import cn.heipiao.api.service.FoundMenuService;
import cn.heipiao.api.service.PartnerService;

/**
 * @author wzw
 * @date 2016年6月15日
 * @version 1.0
 */
@RestController
@RequestMapping(value = "dicts/foundMenu",produces="application/json")
public class FoundMenuResource {

	private static final Logger logger = LoggerFactory
			.getLogger(FoundMenuResource.class);

	@Resource
	private FoundMenuService foundMenuService;

	@Resource
	private PartnerService partnerService;

	/**
	 * api 查询启用的菜单列表
	 * 
	 * app发现模块
	 * 
	 * @return
	 */
//	@GET
//	@Path("list/{uid}")
	@RequestMapping(value = "list/{uid}",method = RequestMethod.GET)
	public String queryByStatus(@PathVariable("uid") Long uid) {
		try {
			logger.debug("uid:{}", uid);
			List<FoundMenu> pojos = selectAll(uid);
			return JSONObject.toJSONString(new RespMsg<List<FoundMenu>>(pojos));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.toJSONString(new RespMsg<>(Status.error,
					RespMessage.getRespMsg(Status.error)));
		}
	}

	private List<FoundMenu> selectAll(Long uid) {
		List<FoundMenu> pojos = foundMenuService
				.selectAllByStatus(FishSiteConstant.ONE);
		List<FoundMenu> res = new ArrayList<FoundMenu>();
		if (pojos != null && pojos.size() > 0) {
			Partner p = null;
			if (uid > 0) {
				p = partnerService.selectByUid(uid);
			}
			Iterator<FoundMenu> it = pojos.iterator();
			while (it.hasNext()) {
				FoundMenu fm = it.next();
				if (p == null && fm.getGroup().equals("2"))
					continue;
				res.add(fm);
			}
		}
		return res;
	}

	/**
	 * 查询文章态度列表
	 * 
	 * @return
	 */
	@Deprecated
//	@GET
//	@Path("article/{start}/{size}")
	@RequestMapping(value = "article/{start}/{size}",method = RequestMethod.GET)
	public String getArticles(@PathVariable("start") final Integer start,
			@PathVariable("size") final Integer size) {
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("start", (start - 1) * size);
			params.put("size", size);
			List<FoundMenu> list = foundMenuService.selectArticles(params);
			return JSONObject.toJSONString(new RespMsg<List<FoundMenu>>(list));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.toJSONString(new RespMsg<Integer>(Status.error));
		}
	}

	/**
	 * 置顶
	 * 
	 * @param id
	 * @return
	 */
//	@PUT
//	@Path("article/{id}")
	@RequestMapping(value = "article/{id}",method = RequestMethod.PUT,consumes = "application/json")
	public String TopArticle(@PathVariable("id") final Integer id) {
		try {
			foundMenuService.topArticle(id);
			return JSONObject
					.toJSONString(new RespMsg<Integer>(Status.success));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.toJSONString(new RespMsg<Integer>(Status.error));
		}
	}

	/**
	 * cp 查询一级菜单列表
	 * 
	 * @return
	 */
//	@GET
//	@Path("list/first")
	@RequestMapping(value = "list/first",method = RequestMethod.GET)
	public String cpQueryFirsts() {
		try {
			List<FoundMenu> pojos = foundMenuService.selectAll();
			return JSONObject.toJSONString(new RespMsg<List<FoundMenu>>(pojos));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.toJSONString(new RespMsg<>(Status.error,
					RespMessage.getRespMsg(Status.error)));
		}
	}

	/**
	 * cp 查询二级级菜单列表
	 * 
	 * @return
	 */
//	@GET
//	@Path("list/second/{pid}")
	@RequestMapping(value = "list/second/{pid}",method = RequestMethod.GET)
	public String cpQuerySeconds(@PathVariable("pid") final Integer pid) {
		try {
			logger.debug("pid:{}", pid);
			List<FoundMenu> pojos = foundMenuService.selectAll();
			return JSONObject.toJSONString(new RespMsg<List<FoundMenu>>(pojos));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.toJSONString(new RespMsg<>(Status.error,
					RespMessage.getRespMsg(Status.error)));
		}
	}

	/**
	 * 添加菜单
	 * 
	 * @param json
	 * @return
	 */
//	@POST
//	@Path("create")
	@RequestMapping(value = "create",method = RequestMethod.POST,consumes = "application/json")
	public String addPojo(@RequestBody JSONObject json) {
		try {
			logger.debug("json:{}", json);
			FoundMenu pojo = JSONObject.toJavaObject(json, FoundMenu.class);
			if (pojo == null || StringUtils.isBlank(pojo.getTitle())
					|| StringUtils.isBlank(pojo.getImg())
					|| StringUtils.isBlank(pojo.getUrl())
					|| StringUtils.isBlank(pojo.getGroup())
					|| pojo.getStatus() == null) {
				return JSONObject.toJSONString(new RespMsg<Integer>(
						Status.value_is_null_or_error));
			}
			foundMenuService.insertPojo(pojo);
			return JSONObject
					.toJSONString(new RespMsg<Integer>(Status.success));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.toJSONString(new RespMsg<Integer>(Status.error));
		}
	}

	/**
	 * 修改菜单
	 * 
	 * @param json
	 * @return
	 */
//	@PUT
//	@Path("modification")
	@RequestMapping(value = "modification",method = RequestMethod.PUT,consumes = "application/json")
	public String modifyPojo(@RequestBody JSONObject json) {
		try {
			FoundMenu pojo = JSONObject.toJavaObject(json, FoundMenu.class);
			List<Integer> zo = FishSiteConstant
					.getMaps(FishSiteConstant.zero_one);
			if (pojo == null || pojo.getFid() == null
					|| StringUtils.isBlank(pojo.getTitle())
					|| StringUtils.isBlank(pojo.getImg())
					|| StringUtils.isBlank(pojo.getUrl())
					|| StringUtils.isBlank(pojo.getGroup())
					|| !zo.contains(pojo.getStatus())) {
				return JSONObject.toJSONString(new RespMsg<Integer>(
						Status.value_is_null_or_error));
			}
			foundMenuService.updatePojo(pojo);
			return JSONObject.toJSONString(new RespMsg<Integer>());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.toJSONString(new RespMsg<Integer>(Status.error));
		}
	}

	/**
	 * 删除菜单
	 * 
	 * @param fid
	 * @return
	 */
//	@DELETE
//	@Path("remove/{fid}")
	@RequestMapping(value = "remove/{fid}",method = RequestMethod.DELETE)
	public String removePojo(@PathVariable("fid") Integer fid) {
		try {
			logger.debug("fid:{}", fid);
			foundMenuService.deletePojo(fid);
			return JSONObject.toJSONString(new RespMsg<>());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.toJSONString(new RespMsg<>(Status.error,
					RespMessage.getRespMsg(Status.error)));
		}
	}

}
