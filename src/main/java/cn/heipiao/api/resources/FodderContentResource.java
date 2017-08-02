package cn.heipiao.api.resources;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;

import cn.heipiao.api.constant.RespMessage;
import cn.heipiao.api.pojo.FodderContent;
import cn.heipiao.api.pojo.RespMsg;
import cn.heipiao.api.resources.cp.CpAllResource;
import cn.heipiao.api.service.FodderContentService;
import cn.heipiao.api.utils.PageSet;

/**
 * 提供内容文章的所有功能
 * Provide all functions of content and article
 * 
 * @author GengJinpeng[asdf3070@163.com]
 * @version 2.3 2017-01-04
 */
@RestController
@RequestMapping(value = "fodder/content",produces="application/json")
public class FodderContentResource {

	private static final Logger logger = LoggerFactory.getLogger(FodderContentResource.class);

	@Resource
	private FodderContentService fodderContentService;
	
	//访问页面domain
	@Value("${article.url}")
	private String domain;
	
	//访问页面路径
	@Value("${fodder.content.path}")
	private String path;
	//访问分享图标路径
	@Value("${coverimg.url}")
	private String imgpath;


	/**
	 * 查询黑漂有态度列表-调用及返回完全与接口<code>/dicts/foundMenu/article</code>类似 <br>
	 * 使app最少的修改代码进行接入
	 * @return 
	 */
//	@GET
//	@Path("article/{start}/{size}")
	@RequestMapping(value = "article/{start}/{size}",method = RequestMethod.GET)
	public String getArticles(@PathVariable("start") final Integer start,
			@PathVariable("size") final Integer size) {
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("start", (start - 1) * size);
			params.put("size", size);
			params.put("domain", StringUtils.join(domain, path));
			params.put("imgpath", imgpath);
			List<Map<String, Object>> list = fodderContentService.selectArticles(params);
			return JSONObject.toJSONString(new RespMsg<>(list));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return getErrorRet(Status.error);
		}
	}
	
	/**
	 * 以文章类型查询内容文章列表
	 * @param parentId
	 * @param start
	 * @param size
	 * @return
	 */
//	@GET
//	@Path("query/{parentId}/{keyword}/{pagenum}/{pagesize}")
//	@RequestMapping(value = "query/{parentId}/{keyword}/{pagenum}/{pagesize}",method = RequestMethod.GET)
	public String queryArticles(
			Integer parentId,
			String keyword,
			Integer pagenum,
			 Integer pagesize) {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("parentId", parentId == 0 ? null:parentId);
			//处理关键字
			if(keyword.indexOf("-") == 0){
				keyword = keyword.substring(1);
			}
			if(keyword.length() == 0){
				keyword = null;
			}else{
				keyword = StringUtils.join("%", keyword, "%");
			}
			map.put("keyword", keyword);
			//计算分页
			pagenum = pagenum <= 0 ? 1 : pagenum;
			pagesize = pagesize <= 0 ? 1 : pagesize;
			int startItem = (pagenum - 1) * pagesize;
			logger.debug("executeMap:{}", map);
			int totalItem = fodderContentService.queryListCount(map); 
			int totalPage = totalItem / pagesize + (totalItem % pagesize == 0 ? 0 : 1);
			if (totalPage > 0 && pagenum > totalPage) { //页数大于总页数
				PageSet<FodderContent> pageSet = new PageSet<>(pagenum, pagesize, totalItem, new ArrayList<FodderContent>());
				return JSONObject.toJSONString(new RespMsg<PageSet<FodderContent>>(pageSet));
			}
			map.put("startItem", startItem);
			map.put("pagesize", pagesize);

			logger.debug("executeMap:{}", map);
			List<FodderContent> pojos = fodderContentService.queryList(map);
			PageSet<FodderContent> pageSet = new PageSet<>(pagenum, pagesize, totalItem, pojos);
			return JSONObject.toJSONString(new RespMsg<PageSet<FodderContent>>(pageSet));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return getErrorRet(Status.error);
		}
	}
	
	/**
	 * 查询所有文章分类
	 * @param parentId
	 * @param start
	 * @param size
	 * @return
	 */
//	@GET
//	@Path("classify")
	@RequestMapping(value = "classify",method = RequestMethod.GET)
	public String queryAllClassify() {
		try {
			List<FodderContent> pojos = fodderContentService.queryAllClassify();
			return JSONObject.toJSONString(new RespMsg<List<FodderContent>>(pojos));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return getErrorRet(Status.error);
		}
	}
	
	/**
	 * 通过唯一标识查询指定的内容文章
	 * @return 
	 */
//	@GET
//	@Path("get/{id}")
	@RequestMapping(value = "get/{id}",method = RequestMethod.GET)
	public String get(@PathVariable("id") final String sId) {
		try {
			Integer id = convert2IntId(sId);
			FodderContent content = fodderContentService.getById(id);
			return JSONObject.toJSONString(new RespMsg<>(content));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return getErrorRet(Status.error);
		}
	}
	
	private Integer convert2IntId(String sId){
		Integer id = 0;
		try{
			id = Integer.parseInt(sId);
		}catch(Exception e){
			id = Integer.parseInt(new String(Base64.decodeBase64(sId.getBytes())));
		}
		return id;
	}
	
	/**
	 * 用户阅读文章，并记录阅读数
	 * @return 
	 */
//	@GET
//	@Path("read/{id}")
	@RequestMapping(value = "read/{id}",method = RequestMethod.GET)
	public String read(@PathVariable("id") final String sId) {
		try {
			String ret = get(sId);
			Integer id = convert2IntId(sId);
			fodderContentService.plusReadCount(id);
			return ret;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return getErrorRet(Status.error);
		}
	}
	
	/**
	 * 创建内容文章
	 * @param json
	 * @return 
	 * @see CpAllResource
	 */
	//@POST @Path("/create")
	public String create(JSONObject json){
		try{
			logger.debug("json:", json);
			FodderContent pojo = json == null ? null : JSONObject.toJavaObject(json, FodderContent.class);
			if(checkCreateParams(pojo)){
				return getErrorRet(Status.value_is_null_or_error);
			}
			fodderContentService.create(pojo);
			Map<String, Integer> ret = new HashMap<>();
			ret.put("id", pojo.getId());
			return JSONObject.toJSONString(new RespMsg<>(ret));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return getErrorRet(Status.error);
		}
	}
	/**
	 * 修改内容文章
	 * @param json
	 * @return
	 */
	//@PUT @Path("/modification")
	public String modification(JSONObject json){
		try{
			logger.debug("json:", json);
			FodderContent pojo = json == null ? null : JSONObject.toJavaObject(json, FodderContent.class);
			if(checkMdificationParams(pojo)){
				return getErrorRet(Status.value_is_null_or_error);
			}
			if(checkFodderContentIdExists(pojo.getId())){
				return getErrorRet(Status.FODDER_CONTENT_NOT_EXISTS);
			}
			int count = fodderContentService.modification(pojo);
			return getRightRet(count);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return getErrorRet(Status.error);
		}
	}
	
	public String delectArticle(Integer id){
		try {
			logger.debug("id:{}",id);
			if(id==null){
				return JSONObject.toJSONString(new RespMsg<>(Status.value_is_null_or_error));
			}
			fodderContentService.delectArticle(id);
			return JSONObject.toJSONString(new RespMsg<>());
		} catch (Exception e) {
			return JSONObject.toJSONString(new RespMsg<>(Status.error));

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
	
	// 判断主表中此唯一标识是否存在
	private boolean checkFodderContentIdExists(Integer id){
		FodderContent fc = fodderContentService.getById(id);
		return fc == null;
	}

	// 修改内容文章输入参数判断
	private boolean checkMdificationParams(FodderContent pojo) {
		return pojo == null
				|| pojo.getId() == null;
	}

	// 创建内容文章输入参数判断
	private boolean checkCreateParams(FodderContent pojo) {
		return pojo == null
				|| StringUtils.isBlank(pojo.getTitle())
				|| StringUtils.isBlank(pojo.getContent())
				|| StringUtils.isBlank(pojo.getCoverImg());
	}
	
}
