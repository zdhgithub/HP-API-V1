//package cn.heipiao.api.temp;
//
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.HashMap;
//import java.util.Iterator;
//import java.util.List;
//import java.util.Map;
//import java.util.Map.Entry;
//
//import org.apache.commons.lang3.StringUtils;
//
///**
// * 
// * @author Chris
// * 应当是无用类
// *
// */
//@Deprecated
//public class RequestUtil {
//
//	@SuppressWarnings({ "unchecked", "rawtypes" })
//	public static String buildParamUrl(String url, Map params) {
//		StringBuilder s = new StringBuilder();
//		Iterator<Entry> it = params.entrySet().iterator();
//		while (it.hasNext()) {
//			Entry entry = it.next();
//			s.append("&").append(entry.getKey()).append("=").append(entry.getValue());
//		}
//		if (url.indexOf("?") < 0) {
//			s.replace(0, 1, "?");
//		}
//		s.insert(0, url);
//		return s.toString();
//	}
//
//	/**
//	 * Map 连接成Url链接格式字符串
//	 * 
//	 * @param params
//	 * @return
//	 */
//	public static String buildParamStr(Map<String, String> params) {
//		StringBuilder s = new StringBuilder();
//		List<String> ks = new ArrayList<String>(params.keySet());
//		Collections.sort(ks);
//		Iterator<String> it = ks.iterator();
//		while (it.hasNext()) {
//			String key = it.next();
//			String value = params.get(key);
//			if (StringUtils.isNotBlank(value)) {
//				s.append(key).append("=").append(value).append("&");
//			}
//		}
//		if (s.length() > 0) {
//			s.deleteCharAt(s.length() - 1);
//		}
//		return s.toString();
//	}
//
//	/**
//	 * Map 连接成Url链接格式字符串
//	 * 
//	 * @param params
//	 * @return
//	 */
//	public static String buildParamStrWithEmpty(Map<String, String> params) {
//		StringBuilder s = new StringBuilder();
//		Iterator<Entry<String, String>> it = params.entrySet().iterator();
//		while (it.hasNext()) {
//			Entry<String, String> entry = it.next();
//			String key = entry.getKey();
//			String value = entry.getValue();
//			s.append(key).append("=").append(value).append("&");
//		}
//		if (s.length() > 0) {
//			s.deleteCharAt(s.length() - 1);
//		}
//		return s.toString();
//	}
//
//	/**
//	 * 带默认请求参数partner 的map
//	 * 
//	 * @return
//	 */
//	public static Map<String, String> defaultParamMap(String partner) {
//		Map<String, String> params = new HashMap<String, String>();
//		params.put("partner", partner);
//		return params;
//	}
//
//}
