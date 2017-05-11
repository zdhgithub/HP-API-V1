/**
 * 
 */
package cn.heipiao.api.resources;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;

import cn.heipiao.api.constant.RespMessage;
import cn.heipiao.api.pojo.AccountBill;
import cn.heipiao.api.pojo.RespMsg;
import cn.heipiao.api.service.AccountBillService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * @author wzw
 * @date 2016年10月19日
 * @version 1.0
 */

@Api(tags = "用户总账单模块")
@RestController
@RequestMapping(value = "account/bill",produces="application/json")
public class AccountBillResource {

	private static Logger logger = LoggerFactory.getLogger(AccountBillResource.class);
	
	@Resource
	private AccountBillService accountBillService;
	
	/**
	 * 获取账单列表
	 * @param uid
	 * @param index
	 * @param size
	 * @return
	 */
//	@GET
//	@Path("list/{uid}/{index}/{size}")
	@RequestMapping(value = "list/{uid}/{index}/{size}", method = RequestMethod.GET)
	@ApiOperation(value = "获取账单列表", notes = "参数描述", code = 200, produces = "application/json")
	public String getListByUid(@ApiParam(name = "uid", value = "用户uid", required = true)@PathVariable("uid")Long uid,
			@ApiParam(name = "index", value = "第一次传0,之后传每页的最后一条记录时间", required = true)@PathVariable("index")Long index,
			@ApiParam(name = "size", value = "记录数", required = true)@PathVariable("size") Integer size){
		try {
			logger.info("uid:{},index:{},size:{}", uid,index,size);
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("uid", uid);
			map.put("index", index == 0 ? null : new Timestamp(index));
			map.put("size", size);
			List<AccountBill> list = accountBillService.getByUid(map);
			return JSONObject.toJSONString(new RespMsg<>(list));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.toJSONString(new RespMsg<>(Status.error, RespMessage.getRespMsg(Status.error)));
		}
	}
	
}
