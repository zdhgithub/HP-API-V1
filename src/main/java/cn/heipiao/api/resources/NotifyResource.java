/**
 * 
 */
package cn.heipiao.api.resources;

import java.net.URLDecoder;
import java.util.Map;
import java.util.TreeMap;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;

import cn.heipiao.api.pay.PayConfig;
import cn.heipiao.api.pay.PayParams;
import cn.heipiao.api.pay.PayService;
import cn.heipiao.api.pay.Sign;
import cn.heipiao.api.pay.XMLParser;
import cn.heipiao.api.pojo.AliPayNotify;
import cn.heipiao.api.pojo.WxPayNotify;
import cn.heipiao.api.service.NotifyService;

/**
 * 第三方支付回调目前是微信、支付宝
 * 
 * 建议：将其改为查询是否支付成功，将回调放到其它地方用作日志记录
 * 
 * @author wzw
 * @date 2016年7月19日
 * @version 1.0
 */
//@Path("payNotify/")
//@Component
@RestController
@RequestMapping("payNotify/")
public class NotifyResource {

	private static final Logger logger  = org.slf4j.LoggerFactory.getLogger(NotifyResource.class);
	
	@Resource
	private NotifyService notifyService;
	
	@Resource(name="PayService")
	private PayService payService;
	
	@Resource
	private PayParams payParams;
	
	@Resource
	private PayConfig payConfig;
	
	/**
	 * 微信支付回调
	 * 返回的content-type为xml类型
	 * @param req
	 * @return
	 */
//	@Path("wxPayNotify")
//	@POST
//	@Consumes("text/xml")
	@RequestMapping(value = "wxPayNotify",method = RequestMethod.POST,consumes = {"text/xml"})
	public String wxPayNotify(@RequestBody String req){
		try {
			logger.debug("req:{}",req);
			if(req == null){
				return PayParams.responseXml(PayParams.fail);
			}
			Map<String, String> map = XMLParser.getMapFromXML(req);		
			//判断通讯状态
			if(!map.get("return_code").equalsIgnoreCase(PayParams.success)){
				return PayParams.responseXml(PayParams.fail);
			}
			//验证签名
			if(!Sign.md5Sign(PayParams.signStr(map, PayParams.sign), PayParams.keyStr + payConfig.pay_wx_key_c).toUpperCase().equals(map.get(PayParams.sign).toString()))
				return PayParams.responseXml(PayParams.fail);
			
			WxPayNotify wxPayNotify = JSONObject.toJavaObject(JSONObject.parseObject(JSONObject.toJSONString(map)), WxPayNotify.class);
			
			if(!wxPayNotify.getReturn_code().equalsIgnoreCase(PayParams.success)){
				return PayParams.responseXml(PayParams.fail);
			}

//			WxPayNotify wx = notifyService.getWxPayNotify(wxPayNotify.getOut_trade_no());
			//判断参数是否处理
			if(!wxPayNotify.getResult_code().equalsIgnoreCase(PayParams.success)){
				return PayParams.responseXml(PayParams.fail);
			}
			
			//业务处理
			return notifyService.wxNotify(wxPayNotify);
//			if(wx.getAttach().equals(cn.heipiao.api.service.PayService.buyGoodOrders)){
//			}else if(wx.getAttach().equals(cn.heipiao.api.service.PayService.payGoldCoin)){
//				//微信充值
//				notifyService.wxPayGoldCoinNofify(wxPayNotify);
//			}
//			return PayParams.responseXml(PayParams.success);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			return PayParams.responseXml(PayParams.fail);
		}
	}
	
	/*@Path("wxRefundNotify")
	@POST
	@Consumes("text/xml")
	public String wxRefundNotify(String req){
		logger.debug("req:{}" + req);
		return null;
	}*/
	
	
	/**
	 * 支付宝支付通知
	 * 返回的content-type:表单提交类型
	 * @param req
	 * @return
	 */
//	@Path("aliPayNotify")
//	@POST
//	@Consumes("application/x-www-form-urlencoded")
	@RequestMapping(value = "aliPayNotify",method = RequestMethod.POST,consumes = "application/x-www-form-urlencoded")
	public String aliPayNotify(@RequestBody String req){
		try {
			logger.debug("req:{}" , req);
			if(req == null){
				return PayParams.fail;
			}
			Map<String,String> map = toTreeMap(req);
			if(!verify(map)){
				return PayParams.fail;
			}
			AliPayNotify aliPayNotify = JSONObject.toJavaObject(JSONObject.parseObject(JSONObject.toJSONString(map)), AliPayNotify.class);
			
			if(aliPayNotify.getTrade_status() != null){
				AliPayNotify ali = notifyService.getAliPayNotify(aliPayNotify.getOut_trade_no());
				if(ali == null)
					return PayParams.success;
				//判断参数是否处理
				if (aliPayNotify.getTrade_status().equalsIgnoreCase("TRADE_SUCCESS") || aliPayNotify.getTrade_status().equalsIgnoreCase("TRADE_FINISHED")){
					//状态为空处理业务
					if(StringUtils.isBlank(ali.getTrade_status())){
						aliPayNotify.setSign(null);
						//调用业务

						return notifyService.aliNotify(aliPayNotify);
//						//购买商品通知
//						if(ali.getAttach().equals(cn.heipiao.api.service.PayService.buyGoodOrders)){
//							return notifyService.aliPayNotify(aliPayNotify);
//						}else if(ali.getAttach().equals(cn.heipiao.api.service.PayService.payGoldCoin)){
//							//暂时注释
//							return notifyService.aliPayGoldCoinNofify(aliPayNotify);
////							return PayParams.fail;
//						}
					}
				//如果为订单关闭通知只需要修改关闭状态
				} else if(aliPayNotify.getTrade_status().equalsIgnoreCase("TRADE_CLOSED")){
					if(ali.getAttach().equals(cn.heipiao.api.service.PayService.buyGoodOrders)){
						//购票订单关闭
						notifyService.updateAliPayNotify(aliPayNotify);
					}else if(ali.getAttach().equals(cn.heipiao.api.service.PayService.payGoldCoin)){
						//充值订单关闭
						notifyService.updateAliPayGoldCoin(aliPayNotify);
					}else if(ali.getAttach().equals(cn.heipiao.api.service.PayService.payShop)){
						//票支付订单关闭
						notifyService.updateAliPayShop(aliPayNotify);
					}
				}
				return PayParams.success;
			}
		} catch (Exception e) {
			logger.error(req);
			logger.error(e.getMessage(),e);
			return PayParams.fail;
		}
		return PayParams.fail;
	}
	
	
	/**
	 * 表单提交的参数转为map
	 * @param req
	 * @return
	 * @throws Exception 
	 */
	private Map<String, String> toTreeMap(String req) throws Exception {
		Map<String,String> map = new TreeMap<String, String>();
		String params[] = req.split("&");
		for (String p : params) {
			String []kv = p.split("=");
			map.put(URLDecoder.decode(kv[0],"UTF-8"), kv[1] == null ? "" : URLDecoder.decode(kv[1],"UTF-8"));
		}
		return map;
	}

	/**
	 * 验证参数
	 * @param map
	 * @param req
	 * @return
	 * @throws Exception 
	 */
	private boolean verify(Map<String, String> map) throws Exception {
		
		//验证签名和notify_id
		return map.get("notify_id") == null || !payService.notifyVerify(map.get("notify_id").toString()).equalsIgnoreCase("true") 
				|| !Sign.rsaSign(PayParams.signStr(map, PayParams.sign,PayParams.sign_type), payConfig.pay_ali_privateKey, "UTF-8")
				.equals(map.get(PayParams.sign).toString()) ;
	}

//	/**
//	 * 支付宝退款通知
//	 * @param req
//	 * @return
//	 */
//	@Path("aliRefundNotify")
//	@POST
//	@Consumes("application/x-www-form-urlencoded")
//	public String aliRefundNotify(String req){
//		try {
//			logger.debug("req:{}" + req);
//			if(req == null){
//				return PayParams.fail;
//			}
//			Map<String,String> map = toTreeMap(req);
//			if(!verify(map)){
//				return PayParams.fail;
//			}
//			if(Integer.valueOf(map.get("success_num")).intValue() == 1){
//				//调用业务处理
//				//TODO
//				return notifyService.aliRefundNotify(map);
//			}
////			return PayParams.success;
//		} catch (Exception e) {
//			logger.error(req);
//			logger.error(e.getMessage(),e);
//		}
//		return PayParams.fail;
//	}
	
	
	
	
}
