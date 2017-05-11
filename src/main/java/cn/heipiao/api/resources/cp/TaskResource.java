/**
 * 
 */
package cn.heipiao.api.resources.cp;

import java.util.concurrent.atomic.AtomicBoolean;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;

import cn.heipiao.api.pojo.RespMsg;
import cn.heipiao.api.resources.Status;
import cn.heipiao.api.thread.DataThread;
import cn.heipiao.api.thread.PushClockThread;
import cn.heipiao.api.thread.SignFishSiteThread;
import cn.heipiao.api.thread.SignShopThread;

/**
 * AtomicBoolean 使用这个原子类是防止并发线程
 * 
 * @author wzw
 * @date 2016年11月29日
 * @version 2.2
 */
@RestController
@RequestMapping(value = "cp/task",produces="application/json")
public class TaskResource {
	
	private static Logger logger = LoggerFactory.getLogger(TaskResource.class);
	
	@Resource
	private DataThread dataThread;
	
	@Resource
	private SignFishSiteThread signFishSiteThread;
	
	@Resource
	private SignShopThread signShopThread;
	
	@Resource
	private PushClockThread pushClockThread;
	
	private static AtomicBoolean refundTicket = new AtomicBoolean(false);
	
	private static AtomicBoolean userWithdraw = new AtomicBoolean(false);
	
	private static AtomicBoolean coupons = new AtomicBoolean(false);
	
	private static AtomicBoolean pay = new AtomicBoolean(false);
	
	private static AtomicBoolean sign = new AtomicBoolean(false);
	
	private static AtomicBoolean jpush = new AtomicBoolean(false);
	
	/**
	 * 用户退票
	 * @return
	 */
//	@GET
//	@Path("refundTicket")
	@RequestMapping(value = "refundTicket",method = RequestMethod.GET)
	public String refundTicket(){
		try {
			if(!refundTicket.getAndSet(true)){
				dataThread.refundTicket();
				refundTicket.getAndSet(false);
			}
			return JSONObject.toJSONString(new RespMsg<>(Status.success));
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			return JSONObject.toJSONString(new RespMsg<>(Status.error));
		} 
	}
	
	/**
	 * 用户提现
	 * @return
	 */
//	@GET
//	@Path("userWithdraw")
	@RequestMapping(value = "userWithdraw",method = RequestMethod.GET)
	public String userWithdraw(){
		try {
			if(!userWithdraw.getAndSet(true)){
				dataThread.userWithdraw();
				userWithdraw.getAndSet(false);
			}
			return JSONObject.toJSONString(new RespMsg<>(Status.success));
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			return JSONObject.toJSONString(new RespMsg<>(Status.error));
		} 
	}
	
	/**
	 * 优惠券
	 * @return
	 */
//	@GET
//	@Path("coupons")
	@RequestMapping(value = "coupons",method = RequestMethod.GET)
	public String coupons(){
		try {
			if(!coupons.getAndSet(true)){
				dataThread.coupons();
				coupons.getAndSet(false);
			}
			return JSONObject.toJSONString(new RespMsg<>(Status.success));
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			return JSONObject.toJSONString(new RespMsg<>(Status.error));
		} 
	}
	
	/**
	 * 第三方支付
	 * @return
	 */
//	@GET
//	@Path("pay")
	@RequestMapping(value = "pay",method = RequestMethod.GET)
	public String pay(){
		try {
			if(!pay.getAndSet(true)){
				dataThread.pay();
				pay.getAndSet(false);
			}
			return JSONObject.toJSONString(new RespMsg<>(Status.success));
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			return JSONObject.toJSONString(new RespMsg<>(Status.error));
		}  
	}
	
	/**
	 * 合伙人签约商家
	 * @return
	 */
//	@GET
//	@Path("sign")
	@RequestMapping(value = "sign",method = RequestMethod.GET)
	public String sign(){
		try {
			if(!sign.getAndSet(true)){
				signFishSiteThread.repealFishSiteClaim();
				signShopThread.repealShopClaim();
				sign.getAndSet(false);
			}
			return JSONObject.toJSONString(new RespMsg<>(Status.success));
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			return JSONObject.toJSONString(new RespMsg<>(Status.error));
		}  
	}
	
	/**
	 * jpush 到钟提醒
	 * @return
	 */
//	@GET
//	@Path("jpush")
	@RequestMapping(value = "jpush",method = RequestMethod.GET)
	public String jpush(){
		try {
			if(!jpush.getAndSet(true)){
				pushClockThread.pushAlert();
				jpush.getAndSet(false);
			}
			return JSONObject.toJSONString(new RespMsg<>(Status.success));
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			return JSONObject.toJSONString(new RespMsg<>(Status.error));
		}  
	}
	
}
