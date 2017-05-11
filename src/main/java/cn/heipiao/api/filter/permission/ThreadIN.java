//package cn.heipiao.api.filter.permission;
//
//import java.util.Iterator;
//import java.util.Map;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import cn.heipiao.api.utils.ExDateUtils;
//
//@Deprecated
///**
// * @author z
// * @version 1.0
// * @description 查频次，如果超过频次，将用户加到黑名单
// * @date 2016年6月22日
// */
//public class ThreadIN implements Runnable {
//	private static final Logger log = LoggerFactory.getLogger(ThreadIN.class);
//
//	@Override
//	public void run() {
//		Iterator<Map.Entry<String, Long>> entries = FrequencyFilter.caseMap
//				.entrySet().iterator();
//		while (entries.hasNext()) {
//			Map.Entry<String, Long> entry = (Map.Entry<String, Long>) entries
//					.next();
//			String key = (String) entry.getKey();
//			Long value = (Long) entry.getValue();
//			int ownNum = FrequencyFilter.NumberMap.get(key);
//			// 如果在60s内，请求次数超过指定值，就移到黑名单
//			if ((ExDateUtils.getDate().getTime() - value) / 1000 <= 60
//					&& ownNum >= FrequencyFilter.highestNum) {
//				// 移到黑名单，删除本地记录
//				FrequencyFilter.blackMap.put(key, ExDateUtils.getDate()
//						.getTime());
//				entries.remove();
//				FrequencyFilter.NumberMap.remove(key);
//			}
//			// 如果请求间隔大于60s，并且次数不超过指定值，重置请求时间
//			if ((ExDateUtils.getDate().getTime() - value) / 1000 > 60
//					&& ownNum < FrequencyFilter.highestNum) {
//				FrequencyFilter.caseMap.put(key, ExDateUtils.getDate()
//						.getTime());
//				log.info("重置时间请求：{}",key);
//
//			}
//		}
//	}
//
//	public ThreadIN() {
//
//	}
//
//}
