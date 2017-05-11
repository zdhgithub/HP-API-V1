/**
 * 
 */
package cn.heipiao.api.service;

import java.util.Map;

import cn.heipiao.api.pojo.Token;

/**
 * @author wzw
 * @date 2016年7月11日
 * @version 1.0
 */
public interface TokenService {

	Token selectByName(String name, String roleSessionName) throws Exception;
	
//	Token insert(String name,String roleSessionName) throws Exception;
	
	Token update(String name,String roleSessionName) throws Exception;

	/**
	 * @param roleSessionName 
	 * @param bucket 
	 * @return
	 * @throws Exception 
	 */
	Map<String, String> getTokenBySign(String bucket, String roleSessionName) throws Exception;

	String getWxaQrcode(String path) throws Exception;
	
}
