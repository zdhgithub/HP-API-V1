package cn.heipiao.api.resources;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;

import cn.heipiao.api.constant.RespMessage;
import cn.heipiao.api.pojo.Article;
import cn.heipiao.api.pojo.ArticleUserBehavior;
import cn.heipiao.api.pojo.RespMsg;
import cn.heipiao.api.service.ArticleService;
import cn.heipiao.api.service.ArticleUserBehaviorService;
import cn.heipiao.api.utils.ResultUtils;

/**
 * @author asdf3070@163.com
 * @date 2016年11月7日
 * @version 2.1
 */
//@Path("acticle/ub")
//@Consumes({ MediaType.APPLICATION_JSON })
//@Produces({ MediaType.APPLICATION_JSON })
//@Controller
@RestController
@RequestMapping(value = "acticle/ub",produces="application/json")
public class ArticleUserBehaviorResource {
	private static final Logger logger = LoggerFactory.getLogger(ArticleUserBehaviorResource.class);

	@Resource
	private ArticleService articleService;
	
	@Resource
	private ArticleUserBehaviorService articleUserBehaviorService;
	

	/**
	 * 增加用户行为
	 * @param json
	 * @return
	 */
//	@POST
//	@Path("add")
	@RequestMapping(value = "add",method = RequestMethod.POST,consumes = "application/json")
	public String add(@RequestBody JSONObject json){
		try {
			logger.info("json:{}",json);
			ArticleUserBehavior pojo = json == null ? null : JSONObject.toJavaObject(json, ArticleUserBehavior.class);
			Long aid = pojo.getArticleId();
			if (aid == null) {
				return ResultUtils.out(Status.value_is_null_or_error);
			}
			Article article = articleService.getOneContent(aid);
			if(article != null){
				ArticleUserBehavior articleUserBehavior = articleUserBehaviorService.getBehavior(pojo);
				if(articleUserBehavior == null){
					articleUserBehaviorService.add(pojo);
				}else{
					articleUserBehaviorService.updateCount(pojo);
				}
			}
			return JSONObject.toJSONString(new RespMsg<>(""));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.toJSONString(new RespMsg<>(Status.error,RespMessage.getRespMsg(Status.error)));
		}
	}
}
