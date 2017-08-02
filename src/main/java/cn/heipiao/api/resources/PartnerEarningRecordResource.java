/**
 * 
 */
package cn.heipiao.api.resources;

import java.util.ArrayList;
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
import cn.heipiao.api.pojo.PartnerEarningRecord;
import cn.heipiao.api.pojo.RespMsg;
import cn.heipiao.api.service.PartnerEarningRecordService;

/**
 * @author wzw
 * @date 2016年9月21日
 * @version 1.0
 */
@RestController
@RequestMapping(value = "partner/earningRecord",produces="application/json")
public class PartnerEarningRecordResource {

	private final static Logger logger = LoggerFactory.getLogger(PartnerEarningRecordResource.class);
	
	@Resource
	private PartnerEarningRecordService partnerEarningRecordService;
	
	/**
	 * 查询当前合伙人的所有收益
	 * @param uid
	 * @param index
	 * @param size
	 * @return
	 */
//	@GET
//	@Path("list/{uid}/{index}/{size}")
//	public String getRecordByUid(@PathVariable("uid") Long uid, @PathVariable("index") Long index, @PathVariable("size") Integer size){
//		try {
//			logger.info("uid:{},index:{},size:{}", uid,index,size);
//			Map<String,Object> map = new HashMap<String, Object>();
//			map.put("uid", uid);
//			map.put("index", index);
//			map.put("size", size);
//			List<PartnerEarningRecord> list = partnerEarningRecordService.selectByUid(map);
//			return JSONObject.toJSONString(new RespMsg<>(list));
//		} catch (Exception e) {
//			logger.error(e.getMessage(), e);
//			return JSONObject.toJSONString(new RespMsg<>(Status.error, RespMessage.getRespMsg(Status.error)));
//		}
//	}
	
	/**
	 * 查询收益记录
	 * 后续建议放弃
	 * 
	 * 合伙人收益记录以后查看 {@link AccountBill}
	 *
	 * @param uid
	 * @param fishSiteId
	 * @param index
	 * @param size
	 * @return
	 */
//	@GET
//	@Path("list/{uid}/{category}/{fishSiteId}/{index}/{size}")
	@RequestMapping(value = "list/{uid}/{category}/{fishSiteId}/{index}/{size}",method = RequestMethod.GET)
	public String getRecordByUidAndFishSiteId(@PathVariable("uid") Long uid, @PathVariable("fishSiteId") Integer fishSiteId,@PathVariable("index") Long index, 
			@PathVariable("size") Integer size,@PathVariable("category") Integer category){
		try {
			logger.info("uid:{},fishSiteId:{},index:{},size:{}", uid,fishSiteId,index,size);
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("uid", uid);
			map.put("fishSiteId", fishSiteId);
			map.put("index", index > 0 ? (index - 1) * size : 0);
			map.put("size", size);
			List<PartnerEarningRecord> list = new ArrayList<PartnerEarningRecord>();
			if(category == 2){
				list = partnerEarningRecordService.selectByUidAndFishSiteId(map);
			}else if(category == 1){
				list = partnerEarningRecordService.selectByUid(map);
			} else if(category == 3){
				list = partnerEarningRecordService.selectByFishSiteIdIsNull(map);
			}
			return JSONObject.toJSONString(new RespMsg<>(list));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.toJSONString(new RespMsg<>(Status.error, RespMessage.getRespMsg(Status.error)));
		}
	}
	
}
