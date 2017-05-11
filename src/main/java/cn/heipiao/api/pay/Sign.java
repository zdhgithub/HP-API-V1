/**
 * 
 */
package cn.heipiao.api.pay;

import org.apache.commons.codec.digest.DigestUtils;

import cn.heipiao.api.utils.RSA;


/**
 * 签名
 * 
 * @author wzw
 * @date 2016年7月13日
 * @version 1.0
 */
public class Sign {

	/**
	 * 签名
	 * @param content 带签名的内容
	 * @param concat 添加额外参数参与签名 可以为null
	 * @return
	 */
	public static String md5Sign(String content,String concat){
		return DigestUtils.md5Hex(content + (concat == null ? "" : concat));
	}
	
	
	public static String rsaSign(String content,String privateKey,String charset){
		return RSA.sign(content, privateKey, charset);
	}
	
}
