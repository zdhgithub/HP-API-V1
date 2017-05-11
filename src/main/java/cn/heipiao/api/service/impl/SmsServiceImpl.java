//package cn.heipiao.api.service.impl;
//
//import java.util.Map;
//
//import org.apache.commons.lang3.StringUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Value;
//
//import cn.heipiao.api.service.SmsService;
//
//import com.alibaba.fastjson.JSONObject;
//import com.taobao.api.DefaultTaobaoClient;
//import com.taobao.api.TaobaoClient;
//import com.taobao.api.request.AlibabaAliqinFcSmsNumSendRequest;
//import com.taobao.api.response.AlibabaAliqinFcSmsNumSendResponse;
//
///**
// * @author zf
// * @version 1.0
// * @description
// * @date 2016年6月25日
// */
////@Service
//public class SmsServiceImpl implements SmsService {
//	private static final Logger log = LoggerFactory.getLogger(SmsServiceImpl.class);
//	
//	String url = "http://gw.api.taobao.com/router/rest";
//	
//	@Value("${sms.key}")
//	private String smsKey;
//	
//	@Value("${sms.secret}")
//	private String smsSecret;
//	
//	@Value("${sms.name}")
//	private String smsName;
//	
//	@Value("${sms.template}")
//	private String smsTemplate;
//	
//	@Value("${sms.type}")
//	private String smsType;
//	
//	private static final String smsReturnKey = "alibaba_aliqin_fc_sms_num_send_response";
//
//	/*
//	 * 正确返回信息
//	 * {
//	    "alibaba_aliqin_fc_sms_num_send_response": {
//	        "result": {
//	            "err_code": "0",
//	            "model": "102160727109^1102801434922",
//	            "success": true
//	        },
//	        "request_id": "12lrwo62x58py"
//	    }
//	}
//	
//	*异常返回信息
//	*
//	*{"error_response":{"code":50,"msg":"Remote service error","sub_code":"isv.invalid-parameter","sub_msg":"非法参数"}}
//	*
//	*/
//	
//	@Override
//	public boolean send(Map<String, String> params) throws Exception {
//		log.info("短信参数：{}",params);
//		String resp = StringUtils.EMPTY;
//		TaobaoClient client = new DefaultTaobaoClient(url,smsKey,smsSecret);
//		AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();
//		req.setSmsType(smsType);
//		req.setSmsFreeSignName(smsName);
//		req.setSmsParamString(params.get("content"));
//		req.setRecNum(params.get("mobile"));
//		req.setSmsTemplateCode(smsTemplate);
//
//		AlibabaAliqinFcSmsNumSendResponse rsp = null;
////		try {
//			rsp = client.execute(req);
//			resp = rsp.getBody();
//			log.debug(resp);
//			JSONObject json = resp == null ? null : JSONObject.parseObject(resp);
//			if(json != null && json.containsKey(smsReturnKey)){
//				json = json.getJSONObject(smsReturnKey).getJSONObject("result");
//				if(json.getString("err_code").equals("0")){
//					return json.getBooleanValue("success");
//				}
//			}else{
//				log.error("resp:{}",resp);
//			}
////		} catch (Exception e) {
////			log.error(e.getMessage());
////			log.error("阿里短信服务推送失败",e);
////			if( req != null ){
////			log.error(rsp.getMsg());
////			log.error(rsp.getSubMsg());
////			}
////		}
//		return false;
//	}
//
//}
