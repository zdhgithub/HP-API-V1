package cn.heipiao.api.service;

import java.util.Map;

/**
 * @author zf
 * @version 1.0
 * @description 发送短信
 * @date 2016年6月25日
 */
public interface SmsService {
	/**
	 * 发送短信
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public boolean send(Map<String, String> params) throws Exception;
}
