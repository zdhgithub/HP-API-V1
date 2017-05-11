package cn.heipiao.api.resources;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;

import cn.heipiao.api.constant.ApiConstant.SysPicturesType;
import cn.heipiao.api.constant.RespMessage;
import cn.heipiao.api.pojo.RespMsg;
import cn.heipiao.api.pojo.SysPictures;
import cn.heipiao.api.service.SysPicturesService;
import cn.heipiao.api.utils.PageSet;
import io.swagger.annotations.Api;

/**
 * @author asdf3070@163.com
 * @date 2016年11月22日
 * @version 2.2
 */
//@Path("/sys/imgs")
//@Produces({ MediaType.APPLICATION_JSON})
//@Consumes({ MediaType.APPLICATION_JSON})
//@Component
@Api(tags = "系统图片模块")
@RestController
@RequestMapping(value = "sys/imgs",produces="application/json")
public class SysPicturesResource {

	private static final Logger logger = LoggerFactory.getLogger(SysPicturesResource.class);
	
	@Resource
	private SysPicturesService sysPicturesService;
		
	public static AtomicBoolean setListAllThread = new AtomicBoolean(false);
	
	/**
	 * 显示图片接口前缀
	 */
	public static String getPicturePrefix = "sys/imgs/r/";
	
	@Autowired
	private HttpServletResponse response;

//	@GET
//	@Path("/r/{imgId}")
//	@Produces({"image/jpeg"})
	@RequestMapping(value = "/r/{imgId}",method = RequestMethod.GET)
	public Object getImg(@PathVariable("imgId") String imgId){
		try {
			logger.debug("imgId:{}", imgId);
			SysPictures sysPictures = sysPicturesService.getById(imgId);
			String picture = sysPictures.getPicture();
			picture = picture.substring(picture.indexOf("base64,") + 7);
			byte[] b = GenerateImage(picture);
//			response.setHeader("Content-Type","image/jpeg");
			response.setHeader("Accept-Ranges", "bytes");
//			response.getOutputStream().write(b);
//			response.getOutputStream().flush();
//			return Base64.encodeBase64String(b);
			return b;
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			return JSONObject.toJSONString(new RespMsg<>(Status.error));
		}
	}
	
	public byte[] GenerateImage(String imgStr){   //对字节数组字符串进行Base64解码并生成图片  
        if (imgStr == null) //图像数据为空  
            return null;  
        Base64 decoder = new Base64();  
        try{
            //Base64解码  
            byte[] b = decoder.decode(imgStr);
            for(int i=0;i<b.length;++i){  
                if(b[i]<0){//调整异常数据  
                    b[i]+=256;
                }  
            }  
            return b;
        }   
        catch (Exception e){  
            return null;  
        }  
    }
	
	/**
	 * 按类型查询系统默认图片库
	 * @param type -查全部
	 * @param pagenum
	 * @param pagesize
	 * @return
	 */
	@SuppressWarnings("rawtypes")
//	@GET
//	@Path("/query/list/{data}/{type}/{pagenum}/{pagesize}")
	@RequestMapping(value = "/query/list/{data}/{type}/{pagenum}/{pagesize}",method = RequestMethod.GET)
	public String queryList(
			@PathVariable("data") String data,
			@PathVariable("type") String type,
			@PathVariable("pagenum") Integer pagenum,
			@PathVariable("pagesize") Integer pagesize){
		try {
			logger.debug("data:{}", data);
			logger.debug("type:{}", type);
			logger.debug("pagenum:{}", pagenum);
			logger.debug("pagesize:{}", pagesize);
			Map<String, Object> map = new HashMap<String, Object>();
			//data
			map.put("data", data);
			//type
			Integer iType = null;
			if(StringUtils.isNoneBlank(type)){
				if(type.indexOf("-") == 0 && StringUtils.isNumeric(type.substring(1))){
					iType = Integer.parseInt(type.substring(1));
				}
			}
			List<Integer> spt = SysPicturesType.getMaps(SysPicturesType.SYSTEM_PICTURE_TYPE);
			if(iType != null && !spt.contains(iType)){
				return JSONObject.toJSONString(new RespMsg(
						Status.SYSTEM_IMG_TYPE_ERROR, RespMessage
								.getRespMsg(Status.SYSTEM_IMG_TYPE_ERROR)));
			}
			map.put("type", iType);
			
			//计算分页
			pagenum = pagenum <= 0 ? 1 : pagenum;
			pagesize = pagesize <= 0 ? 1 : pagesize;
			int startItem = (pagenum - 1) * pagesize;
			logger.debug("executeMap:{}", map);
			int totalItem = sysPicturesService.queryListCount(map); //
			int totalPage = totalItem / pagesize + (totalItem % pagesize == 0 ? 0 : 1);
			if (totalPage > 0 && pagenum > totalPage) { //页数大于总页数
				PageSet<SysPictures> pageSet = new PageSet<>(pagenum, pagesize, totalItem, new ArrayList<SysPictures>());
				return JSONObject.toJSONString(new RespMsg<PageSet<SysPictures>>(pageSet));
			}
			map.put("startItem", startItem);
			map.put("pagesize", pagesize);
			//最终查询
			logger.debug("executeMap:{}", map);
			List<SysPictures> pojos = sysPicturesService.queryList(map);
			PageSet<SysPictures> pageSet = new PageSet<>(pagenum, pagesize, totalItem, pojos);
			pageSet.setObj(map);
			return JSONObject.toJSONString(new RespMsg<PageSet<SysPictures>>(pageSet));
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			return JSONObject.toJSONString(new RespMsg<>(Status.error,
					RespMessage.getRespMsg(Status.error)));
		}
		
	}
	
	/**
	 * 添加系统默认图片库（cp）
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
			SysPictures pojo = json == null ? null : JSONObject.toJavaObject(json, SysPictures.class);
			
			if (pojo == null 
					|| StringUtils.isBlank(pojo.getPicture())
					|| pojo.getType() == null) {
				return JSONObject.toJSONString(new RespMsg<String>(Status.value_is_null_or_error));
			}
			
			List<Integer> spt = SysPicturesType.getMaps(SysPicturesType.SYSTEM_PICTURE_TYPE);
			if(!spt.contains(pojo.getType())){
				return JSONObject.toJSONString(new RespMsg(
						Status.SYSTEM_IMG_TYPE_ERROR, RespMessage
								.getRespMsg(Status.SYSTEM_IMG_TYPE_ERROR)));
			}
			pojo.setId(UUID.randomUUID().toString().replaceAll("-", "").toUpperCase());
			sysPicturesService.add(pojo);
			return JSONObject.toJSONString(new RespMsg<SysPictures>(pojo));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.toJSONString(new RespMsg(Status.error));
		}
	}

	/**
	 * 修改系统默认图片库<br>（cp）
	 * 修改指定唯一标识的系统默认图片库
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
			SysPictures pojo = json == null ? null : JSONObject.toJavaObject(json, SysPictures.class);
			if (pojo == null || (pojo != null && pojo.getId() == null)) {
				return JSONObject.toJSONString(new RespMsg(
						Status.value_is_null_or_error, RespMessage
								.getRespMsg(Status.value_is_null_or_error)));
			}

			if(pojo.getType() != null){
				List<Integer> spt = SysPicturesType.getMaps(SysPicturesType.SYSTEM_PICTURE_TYPE);
				if(!spt.contains(pojo.getType())){
					return JSONObject.toJSONString(new RespMsg(
							Status.SYSTEM_IMG_TYPE_ERROR, RespMessage
									.getRespMsg(Status.SYSTEM_IMG_TYPE_ERROR)));
				}
			}
			
			SysPictures tmp = sysPicturesService.getById(pojo.getId());
			if (tmp == null)
				return JSONObject.toJSONString(new RespMsg<>(
						Status.SYSTEM_IMG_NOT_EXIST, RespMessage
								.getRespMsg(Status.SYSTEM_IMG_NOT_EXIST)));
			
			sysPicturesService.updateById(pojo);
			return JSONObject.toJSONString(new RespMsg());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.toJSONString(new RespMsg(Status.error,
					RespMessage.getRespMsg(Status.error)));
		}
	}

	/**
	 * 删除指定唯一标识的系统默认图片库（cp）
	 * @param json
	 * @return
	 */
	@SuppressWarnings("rawtypes")
//	@Path("/remove/{id}")
//	@DELETE
	@RequestMapping(value = "remove/{id}",method = RequestMethod.DELETE)
	public String delete(
			@PathVariable("id") String id) {
		try {
			logger.debug("id:{}", id);
			if (id == null) {
				return JSONObject.toJSONString(new RespMsg(
						Status.value_is_null_or_error, RespMessage
								.getRespMsg(Status.value_is_null_or_error)));
			}
			
			SysPictures pojo = sysPicturesService.getById(id);
			if (pojo == null)
				return JSONObject.toJSONString(new RespMsg<>(
						Status.SYSTEM_IMG_NOT_EXIST, RespMessage
								.getRespMsg(Status.SYSTEM_IMG_NOT_EXIST)));
			
			sysPicturesService.deleteById(pojo);
			return JSONObject.toJSONString(new RespMsg());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.toJSONString(new RespMsg(Status.error,
					RespMessage.getRespMsg(Status.error)));
		}
	}
	
	
	/**
	 * 设置指定钓场或店铺的列表图为空的默认图片
	 * @param type
	 * @param id
	 * @return
	 */
	@SuppressWarnings("rawtypes")
//	@Path("/setlist")
//	@PUT
	@RequestMapping(value = "setlist",method = RequestMethod.PUT,consumes = "application/json")
	public String setList(@RequestBody JSONObject json){
		try{
			logger.debug("json:{}", json);
			Integer type = json.getInteger("type");
			Long id = json.getLong("id");
			logger.debug("type:{} id:{}", type, id);
			if (id == null || type == null) {
				return JSONObject.toJSONString(new RespMsg(
						Status.value_is_null_or_error, RespMessage
								.getRespMsg(Status.value_is_null_or_error)));
			}
			Map<String, Object> map = new HashMap<>();
			if(type == SysPicturesType.SHOP_LIST){
				String imgId = sysPicturesService.updateShopListImg(id);
				map.put("mainImgNone", imgId);
			}else if(type == SysPicturesType.SITE_LIST){
				String imgId = sysPicturesService.updateSiteListImg(id);
				map.put("mainImgNone", imgId);
			}else{
				return JSONObject.toJSONString(new RespMsg(
						Status.SYSTEM_IMG_TYPE_ERROR, RespMessage
								.getRespMsg(Status.SYSTEM_IMG_TYPE_ERROR)));
			}
			return JSONObject.toJSONString(new RespMsg<Map<String, Object>>(map));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.toJSONString(new RespMsg(Status.error,
					RespMessage.getRespMsg(Status.error)));
		}
		
	}
	
	
	/**
	 * 设置指定钓场、店铺、大师的顶部图片
	 * @param type
	 * @param id
	 * @param imgId
	 * @return
	 */
	@SuppressWarnings("rawtypes")
//	@Path("/setop")
//	@PUT
	@RequestMapping(value = "setop",method = RequestMethod.PUT,consumes = "application/json")
	public String setop(@RequestBody JSONObject json){
		try{
			logger.debug("json:{}", json);
			Integer type = json.getInteger("type");
			Long id = json.getLong("id");
			String imgId = getPicturePrefix + json.getString("imgId");
			logger.debug("type:{} id:{}", type, id);
			if (id == null || type == null) {
				return JSONObject.toJSONString(new RespMsg(
						Status.value_is_null_or_error, RespMessage
								.getRespMsg(Status.value_is_null_or_error)));
			}
			if(type == SysPicturesType.SHOP_TOP || 
					type == SysPicturesType.SITE_TOP ||
					type == SysPicturesType.MASTER_TOP){
				sysPicturesService.updateTopImg(type, id, imgId);
			}else{
				return JSONObject.toJSONString(new RespMsg(
						Status.SYSTEM_IMG_TYPE_ERROR, RespMessage
								.getRespMsg(Status.SYSTEM_IMG_TYPE_ERROR)));
			}
			return JSONObject.toJSONString(new RespMsg());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.toJSONString(new RespMsg(Status.error,
					RespMessage.getRespMsg(Status.error)));
		}
	}
	

	/**
	 * 设置钓场或店铺所有列表图为空的默认图片
	 * @param type
	 * @param id
	 * @return
	 */
	//@Path("/setlistAll")
	//@PUT
	public String setListAll(@RequestBody JSONObject json){
		// 已由FisheryParse的独立一次性程序取代
		return null;
	}
	
}

