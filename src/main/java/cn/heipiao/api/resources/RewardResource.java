package cn.heipiao.api.resources;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;

import cn.heipiao.api.constant.RespMessage;
import cn.heipiao.api.pojo.RespMsg;
import cn.heipiao.api.pojo.RewardAmount;
import cn.heipiao.api.pojo.RewardDetail;
import cn.heipiao.api.pojo.RewardOfMonth;
import cn.heipiao.api.pojo.RewardPlatformWithdrawOrder;
import cn.heipiao.api.pojo.RewardPolicy;
import cn.heipiao.api.service.RewardAmountService;
import cn.heipiao.api.service.RewardDetailService;
import cn.heipiao.api.service.RewardOfMonthService;
import cn.heipiao.api.service.RewardPlatformService;
import cn.heipiao.api.service.RewardService;
import cn.heipiao.api.service.WxMiniPayOnceRewardService;
import cn.heipiao.api.utils.ResultUtils;
import io.swagger.annotations.Api;

/**
 * @author duzh
 * @date 2017年1月14日
 */
//@Path("reward")
//@Consumes({ MediaType.APPLICATION_JSON + ";charset=utf-8"})
//@Produces({ MediaType.APPLICATION_JSON + ";charset=utf-8"})
//@Component
@Api(tags = "商家奖励模块")
@RestController
@RequestMapping(value = "reward",produces="application/json")
public class RewardResource {
	private static final Logger logger = LoggerFactory
			.getLogger(RegionResource.class);
	@Resource
	private RewardService rewardService;
	@Resource
	private RewardPlatformService rewardPlatformService;
	@Resource
	private RewardDetailService rewrdDetailService;
	@Resource
	private RewardOfMonthService rewardOfMonthService;
	@Resource
	private RewardAmountService rewardAmountService;
	
	@Resource
	private WxMiniPayOnceRewardService wxMiniPayOnceRewardService;
	
	/**
	 * 商家获取奖励政策信息
	 * @param bid
	 */
//	@Path("policy/{bid}/{type}")
//	@GET
	@RequestMapping(value = "policy/{bid}/{type}",method = RequestMethod.GET)
	public String findRewardPolicyByBid(@PathVariable("bid") Long bid,@PathVariable("type") int type){
		try {
			logger.debug("bid:{},type:{}", bid, type);
			RewardPolicy rewardPolicy = rewardService.selectRewardPloicyByBid(bid,type);
			return JSONObject.toJSONString(new RespMsg<RewardPolicy>(rewardPolicy));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return ResultUtils.out(Status.error);
		}
	}
	
	
	/**
	 * 商家获取平台奖励明细
	 */
//	@Path("detail/{bid}/{type}/{start}/{size}")
//	@GET
	@RequestMapping(value = "detail/{bid}/{type}/{start}/{size}",method = RequestMethod.GET)
	public String findRewardPlatformDetail(@PathVariable("bid")Long bid,
			@PathVariable("type")int type,@PathVariable("start")int start,
			@PathVariable("size")int size){
		try {
			logger.debug("bid:{},type:{}",bid,type,size);
			List<RewardDetail> list=rewrdDetailService.selectByBid(bid, type,start,size);
			return JSONObject.toJSONString(new RespMsg<>(list));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.toJSONString(new RespMsg<>(Status.error, RespMessage.getRespMsg(Status.error)));
		}
	}
	/**
	 * 商家获取平台奖励月账单
	 */
//	@Path("month/{bid}/{type}/{start}/{size}")
//	@GET
	@RequestMapping(value = "month/{bid}/{type}/{start}/{size}",method = RequestMethod.GET)
	public String findRewardOfMonth(@PathVariable("bid")Long bid,@PathVariable("type")int type,@PathVariable("start")int start,@PathVariable("size")int size){
		List<RewardOfMonth> list=null;
		try {
			logger.debug("bid:{},type:{}",bid,type,start,size);
			list= rewardOfMonthService.selectRewardOfMonth(bid, type,start,size);
			return JSONObject.toJSONString(new RespMsg<>(list));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.toJSONString(new RespMsg<>(Status.error, RespMessage.getRespMsg(Status.error)));
		}
	}
	/**
	 * 商家获取平台奖励账户信息
	 */
//	@Path("amount/{bid}/{type}")
//	@GET
	@RequestMapping(value = "amount/{bid}/{type}",method = RequestMethod.GET)
	public String findRewardAmount(@PathVariable("bid")Long bid,@PathVariable("type")int type){
		RewardAmount rewardAmount =null; 
		try{
			logger.debug("bid:{},type:{}",bid,type);
			rewardAmount = rewardAmountService.findRewardAmount(bid, type);
			return JSONObject.toJSONString(new RespMsg<RewardAmount>(rewardAmount));
		} catch(Exception e){
			logger.error(e.getMessage(), e);
			return JSONObject.toJSONString(new RespMsg<>(Status.error, RespMessage.getRespMsg(Status.error)));
		}
	}
	/**
	 * 商家提现
	 *  
	 * withdrawFee: 提现金额(单位：分)
	 * 
	 * @param json
	 * @return
	 */
//	@Path("withdraws")
//	@POST
	@RequestMapping(value = "withdraws",method = RequestMethod.POST,consumes = "application/json")
	public String accountStoreWithdraw(@RequestBody JSONObject json) {
		try {
			logger.debug("json:{}", json);
//			Long uid = json == null ? null : json.getLong("uid");
//			Integer withdrawFee = json == null ? null : json.getInteger("withdrawFee");
//			int platform = json.getInteger("platform");
//			String bindAccountNum = json == null ? null : json.getString("bindAccountNum");
//			Long bid = json == null ? null : json.getLong("bid");
//			int type = json.getInteger("type");
//			
//			if (uid == null || withdrawFee == null || bid == null
//					|| bindAccountNum == null) {
//				return JSONObject.toJSONString(new RespMsg<>(
//						Status.value_is_null_or_error, RespMessage
//								.getRespMsg(Status.value_is_null_or_error)));
//			}
//			// 创建申请订单业务
//			RewardPlatformWithdrawOrder wo = new RewardPlatformWithdrawOrder();
//			wo.setUid(uid);
//			wo.setBid(bid);
//			wo.setType(type);
//			wo.setTradeFee(withdrawFee);
//			wo.setPlatform(platform);
//			wo.setTradeAccount(bindAccountNum);
//			int rs;
//			if(type==0){
//				//渔具店提现申请
//				rs = rewardAmountService.bussinessAccountWithdrawApply(wo);
//				if (rs == 0) {
//					rewardAmountService.bussinessAccountWithdraw(wo);
//				}
//			}else{
//				//钓场提现申请
//				rs = rewardAmountService.fishSiteBussinessAccountWithdrawApply(wo);
//				if (rs == 0) {
//					rewardAmountService.bussinessAccountWithdraw(wo);
//				}
//			}
//			return JSONObject.toJSONString(new RespMsg<>(rs, RespMessage.getRespMsg(rs)));
			return JSONObject.toJSONString(new RespMsg<>(Status.value_is_null_or_error,"此功能已关闭"));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.toJSONString(new RespMsg<>(Status.error,RespMessage.getRespMsg(Status.error)));
		}
	}
	/**
	 * 商家提现进度（订单）
	 * 
	 * @param uid
	 * @param index
	 * @param size
	 * @param type
	 * @return
	 */
//	@GET
//	@Path("list/withdrawOrder/{bid}/{start}/{size}/{type}")
	@RequestMapping(value = "list/withdrawOrder/{bid}/{start}/{size}/{type}",method = RequestMethod.GET)
	public String rewardWihtDradOrder(@PathVariable("bid") Long bid,
			@PathVariable("start") Integer start, @PathVariable("size") Integer size,@PathVariable("type")Integer type) {
		try {
			logger.debug("bid:{},start:{},size:{},type:{}", bid, start, size,type);
			if (bid == null || size == null || type== null) {
				return JSONObject.toJSONString(new RespMsg<>(Status.value_is_null_or_error, RespMessage.getRespMsg(Status.value_is_null_or_error)));
			}
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("bid", bid);
			map.put("start", start);
			map.put("size", size);
			map.put("type", type);
			List<RewardPlatformWithdrawOrder> list = rewardAmountService.selectListByLimit(map);
			return JSONObject.toJSONString(new RespMsg<>(list));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.toJSONString(new RespMsg<>(Status.error,
					RespMessage.getRespMsg(Status.error)));
		}
	}
	
	/**
	 * {"orderId":"124314123","uid":123,"category":1}
	 * category 1:购票,2:充值,3:票支付,
	 * 微信小程序首次支付奖励
	 * @param json
	 * @return
	 */
//	@Path("wxMini/payOnce")
//	@POST
	@RequestMapping(value = "wxMini/payOnce",method = RequestMethod.POST,consumes = "application/json")
	public String wxMiniPayOnceReward(@RequestBody JSONObject json){
		try {
			logger.info("json:{}", json);
			Long uid = json == null ? null : json.getLong("uid");
			Integer category = json == null ? null : json.getInteger("category");
			String orderId = json == null ? null : json.getString("orderId");
			if(uid == null || orderId == null || category == null 
					|| category < 1 || category > 3){
				return JSONObject.toJSONString(new RespMsg<>(Status.value_is_null_or_error,RespMessage.getRespMsg(Status.value_is_null_or_error)));
			}
			int b = wxMiniPayOnceRewardService.reward(uid, orderId,category);
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("reward", b);
			return JSONObject.toJSONString(new RespMsg<>(map));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.toJSONString(new RespMsg<>(Status.error,
					RespMessage.getRespMsg(Status.error)));
		}
	}
	
}
