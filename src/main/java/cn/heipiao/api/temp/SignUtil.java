//package cn.heipiao.api.temp;
//
//import java.io.UnsupportedEncodingException;
//import java.net.URLEncoder;
//import java.util.Map;
//
///**
// * 
// * @author Chris
// * 应当是无用类
// *
// */
//@Deprecated
//public class SignUtil {
//
//	public static final String CHARSET = "UTF-8";
//
//	/**
//	 * 暂时没用
//	 * 
//	 * @return
//	 */
//	public static boolean checkSign() {
//		return false;
//	}
//
//	/**
//	 * 生成签名, 并且 使用URLEncoder 编码
//	 * 
//	 * @param params
//	 * @return
//	 */
//	public static String signWithEncode(Map<String, String> params, String pubkey) {
//		String sign = sign(params, pubkey);
//		try {
//			String rs = URLEncoder.encode(sign, CHARSET);
//			return rs;
//		} catch (UnsupportedEncodingException e) {
//			e.printStackTrace();
//		}
//		return sign;
//	}
//
//	/**
//	 * 生成签名
//	 * 
//	 * @param params
//	 * @param pubkey
//	 * @return
//	 */
//	@SuppressWarnings({ "rawtypes", "unchecked" })
//	public static String sign(Map params, String pubkey) {
//		String pm = RequestUtil.buildParamStr(params);
//		String rs = RSAUtil.encrypt(pubkey, pm);
//		return rs;
//	}
//
//}
