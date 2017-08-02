package cn.heipiao.api.resources.cp;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;

import cn.heipiao.api.pojo.FodderContent;
import cn.heipiao.api.resources.FodderContentResource;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * @author GengJinpeng[asdf3070@163.com]
 * @version 2.3 2017-01-04
 */
@Api(tags = "黑漂有态度")
@RestController
@RequestMapping(value = "cp/fodder/content",produces="application/json")
public class CpAllResource {

	@Resource
	private FodderContentResource fodderContentResource;
	
	/**
	 * 创建内容文章
	 * @param json
	 * @return 
	 */
//	@POST @Path("/fodder/content/create")
	@ApiOperation("添加-黑漂有态度")
	@RequestMapping(value = "create",method = RequestMethod.POST,consumes = "application/json")
	public String fodderContentCreate(@RequestBody JSONObject json){
		return fodderContentResource.create(json);
	}
	
	/**
	 * 修改内容文章
	 * @param json
	 * @return 
	 */
//	@PUT @Path("/fodder/content/modification")
	@ApiOperation("修改-黑漂有态度")
	@RequestMapping(value = "modification",method = RequestMethod.PUT,consumes = "application/json")
	public String fodderContentModification(@RequestBody JSONObject json){
		return fodderContentResource.modification(json);
	}
	
	@ApiOperation("删除-黑漂有态度")
	@RequestMapping(value = "article/{id}",method = RequestMethod.DELETE)
	public String delectArticle(@PathVariable Integer id){
		return fodderContentResource.delectArticle(id);
	}
	
	@ApiOperation(value = "查询文章列表",response = FodderContent.class)
	@RequestMapping(value = "query/{parentId}/{keyword}/{pagenum}/{pagesize}",method = RequestMethod.GET)
	public String queryArticles(@ApiParam(value ="合伙人id,为空时传0")@PathVariable("parentId") final Integer parentId,
			@ApiParam(value ="模糊搜索:-关键词（标题，备注，内容），为空时传-")@PathVariable("keyword") String keyword,
			@ApiParam(value = "页数 ，首次传1")@PathVariable("pagenum") Integer pagenum,
			@ApiParam(value = "页码容量")@PathVariable("pagesize") Integer pagesize){
		return fodderContentResource.queryArticles(parentId,keyword,pagenum,pagesize);
	}
	
}
