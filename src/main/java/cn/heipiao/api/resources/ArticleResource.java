package cn.heipiao.api.resources;

import java.util.HashMap;
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

import cn.heipiao.api.pojo.FishShop;
import cn.heipiao.api.service.ArticleService;
import cn.heipiao.api.service.FishShopService;
import cn.heipiao.api.utils.ResultUtils;
import io.swagger.annotations.ApiOperation;
/**
 * 文章管理
 * @ClassName: ArticleResource
 * @Description: TODO
 * @author zf
 * @date 2016年12月16日
 * @version 2.0
 */
@RestController
@RequestMapping(value = "article",produces="application/json")
public class ArticleResource {
	private Logger log = LoggerFactory.getLogger(ArticleResource.class);

	@Resource
	private ArticleService articleService;

	@Resource
	private FishShopService fishShopService;

	/**
	 * 获取文章分享的URL
	 * 
	 * @return
	 * @since 2.0
	 */
//	@Path("share/{id}")
//	@GET
	@Deprecated
	@RequestMapping(value = "share/{id}",method = RequestMethod.GET)
	public String getArticleShareUri(@PathVariable("id") Integer id) {
		try {
			log.debug("id:{}", id);
			return ResultUtils.out(articleService.getShareUri(id));
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return ResultUtils.out(Status.error);
		}
	}

	/**
	 * 查询列表(所有文章/经验)
	 * 
	 * @return
	 * @since 2.2
	 * regionId参数可有可无 兼容旧版本
	 */
//	@Path("clist/{uid}/{start}/{size}")
//	@GET
	@RequestMapping(value = "clist/{uid}/{start}/{size}",method = RequestMethod.GET)
	public String getArticles(@PathVariable("uid") Integer uid,@PathVariable("start") Integer start,
			@PathVariable("size") Integer size) {
		try {
			log.debug("uid:{},start:{},size:{}", uid, start, size);
			return ResultUtils.out(articleService.getList(uid,start, size,null));
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return ResultUtils.out(Status.error);
		}
	}
	/**
	 * 查询列表(所有文章/经验)
	 * (根据区域查询)
	 * @return
	 * @since 2.3
	 * 
	 */
//	@Path("clist/{uid}/{start}/{size}/{regionId}")
//	@GET
	@RequestMapping(value = "clist/{uid}/{start}/{size}/{regionId}",method = RequestMethod.GET)
	public String getArticles(@PathVariable("uid") Integer uid,@PathVariable("start") Integer start,
			@PathVariable("size") Integer size,@PathVariable("regionId") Integer regionId) {
		try {
			log.debug("uid:{},start:{},size:{},regionId", uid, start, size,regionId);
			return ResultUtils.out(articleService.getList(uid,start, size,regionId));
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return ResultUtils.out(Status.error);
		}
	}
	
	/**
	 * C端查询放鱼列表
	 * 
	 * @return
	 * @since 2.2
	 */
//	@Path("list/putFish/{uid}/{lng}/{lat}/{regionId}/{start}/{size}")
//	@GET
	@RequestMapping(value = "list/putFish/{uid}/{lng}/{lat}/{regionId}/{start}/{size}",method = RequestMethod.GET)
	public String getPutFishList(@PathVariable("uid") Long uid,@PathVariable("regionId") Integer regionId,
			@PathVariable("lng") Double lng,@PathVariable("lat") Double lat,
			@PathVariable("start") Integer start,@PathVariable("size") Integer size) {
		try {
			log.debug("start:{},size:{},lng:{},lat:{},uid:{}",start,size,lng,lat,uid);
			if(Math.abs(lng) > 180 || Math.abs(lat) > 90){
				return ResultUtils.out(Status.value_is_null_or_error);
			}
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("lng", lng);
			map.put("lat", lat);
			map.put("regionId", regionId);
			map.put("userId", uid);
			map.put("start", start);
			map.put("size", size);
			return ResultUtils.out(articleService.getPutFishList(map));
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return ResultUtils.out(Status.error);
		}
	}
	/**
	 * 标红(文章/经验)
	 * 
	 * @return
	 * @since 2.2
	 */
//	@Path("art/red/{uid}")
//	@GET
	@RequestMapping(value = "art/red/{uid}",method = RequestMethod.GET)
	public String getArticleRed(@PathVariable("uid")final Integer uid) {
		try {
			log.debug("uid{}", uid);
			return ResultUtils.out(articleService.getArticleRed(uid));
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return ResultUtils.out(Status.error);
		}
	}
	/**
	 * 标红(放鱼)
	 * 
	 * @return
	 * @since 2.2
	 */
//	@Path("putfish/red/{uid}/{lng}/{lat}/{regionId}")
//	@GET
	@RequestMapping(value = "putfish/red/{uid}/{lng}/{lat}/{regionId}",method = RequestMethod.GET)
	public String getPutFishRed(@PathVariable("uid")final Integer uid,
			@PathVariable("lng")final Double lng,
			@PathVariable("lat")final Double lat,
			@PathVariable("regionId")final Integer regionId) {
		try {
			if(Math.abs(lng) > 180 || Math.abs(lat) > 90){
				return ResultUtils.out(Status.value_is_null_or_error);
			}
			log.debug("uid{},lng:{},lat:{},regionId", uid,lng,lat,regionId);
			return ResultUtils.out(articleService.getPutFishRed(uid,lng,lat,regionId));
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return ResultUtils.out(Status.error);
		}
	}

	
	/**
	 * 查询用户文章列表（C）
	 * 
	 * @return
	 * @since 2.0
	 */
//	@Path("list/{shopId}/{visitor}/{start}/{size}")
//	@GET
	@RequestMapping(value = "list/{shopId}/{visitor}/{start}/{size}",method = RequestMethod.GET)
	public String getArticlesForC(@PathVariable("start") Integer start,
			@PathVariable("size") Integer size, @PathVariable("shopId") Long shopId,
			@PathVariable("visitor") Integer visitor) {
		try {
			log.debug("start:{},size:{},shopId:{},visitor:{}", start, size,
					shopId, visitor);
			FishShop fs = this.fishShopService.getFishShopById(shopId);
			if (fs == null) {
				return ResultUtils.out(Status.FISH_SHOP_NOT_EXISTS);
			}
			if (fs.getUid() == null) {
				return ResultUtils.out(Status.FISH_SHOP_NOT_NAME_PASS);
			}
			return ResultUtils.out(articleService.getListForC(start, size,
					shopId, visitor));
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return ResultUtils.out(Status.error);
		}
	}

	/**
	 * 查询用户文章列表（C）(带类型)
	 * 
	 * @return
	 * @since 2.2
	 */
//	@Path("list/{shopId}/{type}/{visitor}/{start}/{size}")
//	@GET
	@ApiOperation("钓场中钓友渔获列表和钓场动态列表")
	@RequestMapping(value = "list/{shopId}/{type}/{visitor}/{start}/{size}",method = RequestMethod.GET)
	public String getArticlesForCByType(@PathVariable("start") Integer start,
			@PathVariable("size") Integer size, @PathVariable("shopId") Long shopId,
			@PathVariable("type") Integer type,
			@PathVariable("visitor") Integer visitor) {
		try {
			log.debug("start:{},size:{},shopId:{},type:{},visitor:{}", start,
					size, shopId, type, visitor);
			return ResultUtils.out(articleService.getListForCByType(start,
					size, shopId, type, visitor));
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return ResultUtils.out(Status.error);
		}
	}

	/**
	 * 查询用户的文章列表(发布)
	 * 
	 * @param uid
	 * @param start
	 * @param size
	 * @return
	 * @since 2.0
	 */
//	@Path("list/{shopId}/{start}/{size}")
//	@GET
	@RequestMapping(value = "list/{shopId}/{start}/{size}",method = RequestMethod.GET)
	public String getArticleByUser(@PathVariable("shopId") final Long shopId,
			@PathVariable("start") final Integer start,
			@PathVariable("size") final Integer size) {
		try {
			log.debug("shopId:{},start:{},size:{}", shopId, start, size);
			if (shopId == null) {
				return ResultUtils.out(Status.value_is_null_or_error);
			}
			return ResultUtils.out(this.articleService.getListByShop(shopId,
					start, size));
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return ResultUtils.out(Status.error);
		}
	}

	/**
	 * 查询用户的文章列表(发布)(带类型)
	 * 
	 * @param uid
	 * @param start
	 * @param size
	 * @return
	 * @since 2.0
	 */
//	@Path("all/{shopId}/{type}/{start}/{size}")
//	@GET
	@RequestMapping(value = "all/{shopId}/{type}/{start}/{size}",method = RequestMethod.GET)
	public String getArticleByType(@PathVariable("shopId") final Long shopId,
			@PathVariable("type") final Integer type,
			@PathVariable("start") final Integer start,
			@PathVariable("size") final Integer size) {
		try {
			log.debug("shopId:{},type:{},start:{},size:{}", shopId, type,
					start, size);
			if (shopId == null) {
				return ResultUtils.out(Status.value_is_null_or_error);
			}
			return ResultUtils.out(this.articleService.getList2ByType(shopId,
					type, start, size));
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return ResultUtils.out(Status.error);
		}
	}

	/**
	 * 查询用户的文章列表(草稿)
	 * 
	 * @param uid
	 * @param start
	 * @param size
	 * @return
	 * @since 2.0
	 */
//	@Path("list/cg/{shopId}/{start}/{size}")
//	@GET
	@RequestMapping(value = "list/cg/{shopId}/{start}/{size}",method = RequestMethod.GET)
	public String getDraftByUser(@PathVariable("shopId") final Long shopId,
			@PathVariable("start") final Integer start,
			@PathVariable("size") final Integer size) {
		try {
			log.debug("shopId:{},start:{},size:{}", shopId, start, size);
			if (shopId == null) {
				return ResultUtils.out(Status.value_is_null_or_error);
			}
			return ResultUtils.out(this.articleService.getListByShop2(shopId,
					start, size));
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return ResultUtils.out(Status.error);
		}
	}

	/**
	 * 查询用户的文章列表(草稿)(带类型)
	 * 
	 * @param shopId
	 * @param start
	 * @param size
	 * @param type
	 * @return
	 * @since 2.2
	 */
//	@Path("list/cg/{shopId}/{type}/{start}/{size}")
//	@GET
	@RequestMapping(value = "list/cg/{shopId}/{type}/{start}/{size}",method = RequestMethod.GET)
	public String getDraftByType(@PathVariable("shopId") final Long shopId,
			@PathVariable("type") final Integer type,
			@PathVariable("start") final Integer start,
			@PathVariable("size") final Integer size) {
		try {
			log.debug("shopId:{},type:{},start:{},size:{}", shopId, type,
					start, size);
			if (shopId == null || type == null || start == null || size == null) {
				return ResultUtils.out(Status.value_is_null_or_error);
			}
			return ResultUtils.out(this.articleService.getListByType(shopId,
					type, start, size));
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return ResultUtils.out(Status.error);
		}
	}

	/**
	 * 编辑文章
	 * 
	 * @param json
	 * @return
	 * @since 2.0
	 */
//	@Path("edit")
//	@POST
	@ApiOperation(value="个人品牌")
	@RequestMapping(value = "edit",method = RequestMethod.POST,consumes = "application/json")
	public String editArticle(@RequestBody JSONObject json) {
		try {
			log.debug("json:{}", json);
			if (json == null || StringUtils.isEmpty(json.getString("title"))
					|| json.getInteger("status") == null
					|| json.getInteger("uid") == null
					|| json.getLong("shopId") == null
					|| StringUtils.isEmpty(json.getString("content"))
					|| StringUtils.isEmpty(json.getString("subTitle"))) {
				return ResultUtils.out(Status.value_is_null_or_error);
			}
			Integer result = this.articleService.editArticle(json);
			if (result == 0) {
				return ResultUtils.out(Status.success);
			} else {
				return ResultUtils.out(result + "");
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return ResultUtils.out(Status.error);
		}
	}

	/**
	 * 编辑动态，鱼获，放鱼信息
	 * 
	 * @param json
	 * @return
	 * @since 2.2
	 */
//	@Path("edits")
//	@POST
	@ApiOperation(value = "钓场c端渔获和b端放鱼 ")
	@RequestMapping(value = "edits",method = RequestMethod.POST,consumes = "application/json")
	public String edits(@RequestBody JSONObject json) {
		try {
			log.debug("json:{}", json);
			if (json == null || StringUtils.isEmpty(json.getString("title"))
					|| json.getInteger("status") == null
					|| json.getInteger("uid") == null
					//shopId :钓场id
					|| json.getLong("shopId") == null
					|| StringUtils.isEmpty(json.getString("content"))
//					|| StringUtils.isEmpty(json.getString("subTitle"))
//					|| json.getInteger("type") == null
					) {
				return ResultUtils.out(Status.value_is_null_or_error);
			}
			
			if(json.getInteger("type") != null && json.getIntValue("type") != 5 ){
				if(StringUtils.isEmpty(json.getString("subTitle"))){
					return ResultUtils.out(Status.value_is_null_or_error);
				}
			}else {
				json.put("type", 5);
				json.put("subTitle", "鱼获");
			}
			
			Integer result = this.articleService.edits(json);
			if (result == 0) {
				return ResultUtils.out(Status.success);
			} else {
				return ResultUtils.out(result + "");
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return ResultUtils.out(Status.error);
		}
	}

	/**
	 * 删除文章
	 * 
	 * @param id
	 * @return
	 * @since 2.0
	 */
//	@Path("rm/{id}")
//	@DELETE
	@RequestMapping(value = "rm/{id}",method = RequestMethod.DELETE)
	public String rmArticle(@PathVariable("id") final Long id) {
		try {
			log.debug("id:{}", id);
			if (id == null) {
				return ResultUtils.out(Status.value_is_null_or_error);
			}
			this.articleService.rmArticle(id);
			return ResultUtils.out(Status.success);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return ResultUtils.out(Status.error);
		}
	}

	/**
	 * 查文章内容(旧)
	 * 
	 * @param id
	 * @return
	 * @since 2.0
	 */
//	@Path("content/{id}")
//	@GET
	@RequestMapping(value = "content/{id}",method = RequestMethod.GET)
	public String getContent(@PathVariable("id") final Long id) {
		try {
			log.debug("id:{}", id);
			if (id == null) {
				return ResultUtils.out(Status.value_is_null_or_error);
			}
			return ResultUtils.out(this.articleService.getOneContent(id));
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return ResultUtils.out(Status.error);
		}
	}
	/**
	 * 查文章内容（新）
	 * 
	 * @param id
	 * @return
	 * @since 2.2
	 */
//	@Path("content/new/{id}")
//	@GET
	@ApiOperation("通过id获取 大师经验，放鱼信息，钓友渔获，钓场动态")
	@RequestMapping(value = "content/new/{id}",method = RequestMethod.GET)
	public String getContentForNew(@PathVariable("id") final Long id) {
		try {
			log.debug("id:{}", id);
			if (id == null) {
				return ResultUtils.out(Status.value_is_null_or_error);
			}
			return ResultUtils.out(this.articleService.getOneContentForNew(id));
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return ResultUtils.out(Status.error);
		}
	}

	/**
	 * 查某个文章的评论列表
	 * 
	 * @param id
	 * @return
	 * @since 2.2
	 */
//	@Path("comment/{id}/{start}/{size}")
//	@GET
	@RequestMapping(value = "comment/{id}/{start}/{size}",method = RequestMethod.GET)
	public String getComments(@PathVariable("id") final Long id,
			@PathVariable("start") final Integer start,
			@PathVariable("size") final Integer size) {
		try {
			log.debug("id:{},start:{},size:{}", id, start, size);
			if (id == null) {
				return ResultUtils.out(Status.value_is_null_or_error);
			}
			return ResultUtils.out(this.articleService.getComments(id, start,
					size));
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return ResultUtils.out(Status.error);
		}

	}

	/**
	 * 评论
	 * 
	 * @param json
	 * @return
	 * @since 2.2
	 */
//	@Path("comment/ad")
//	@POST
	@RequestMapping(value = "comment/ad",method = RequestMethod.POST,consumes = "application/json")
	public String addComment(@RequestBody JSONObject json) {
		try {
			log.debug("json:{}", json);
			if (json == null || StringUtils.isEmpty(json.getString("content"))
					|| json.getInteger("userId") == null
					|| json.getLong("sid") == null) {
				return ResultUtils.out(Status.value_is_null_or_error);
			}
			return ResultUtils.out(this.articleService.adComment(json));
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return ResultUtils.out(Status.error);
		}
	}

	/**
	 * 删除评论
	 * 
	 * @param id
	 * @return
	 * @since 2.2
	 */
//	@Path("comment/{id}")
//	@DELETE
	@RequestMapping(value = "comment/{id}",method = RequestMethod.DELETE)
	public String rmComment(@PathVariable("id") final Long id) {
		try {
			log.debug("id:{}", id);
			if (id == null) {
				return ResultUtils.out(Status.value_is_null_or_error);
			}
			return ResultUtils.out(this.articleService.rmComment(id));
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return ResultUtils.out(Status.error);
		}
	}

}
