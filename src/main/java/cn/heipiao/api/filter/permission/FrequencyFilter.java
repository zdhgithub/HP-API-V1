package cn.heipiao.api.filter.permission;

/**
 * @author z
 * @version 1.0
 * @description 控制用户的请求频次 n次/s
 * @date 2016年6月22日
 */
// FIXME 当删除，该功能不应当由应用来做，nginx可以，返回统一的状态码，APP收到状态码做统一提示：类似操作太频繁
//@Deprecated
//@PreMatching
//@Priority(Priorities.USER + 2)
//@Provider
//public class FrequencyFilter implements ContainerRequestFilter {
//	private static final Logger log = LoggerFactory
//			.getLogger(FrequencyFilter.class);
//	public static Map<String, Long> blackMap = new ConcurrentHashMap<String, Long>();
//	public static Map<String, Long> caseMap = new ConcurrentHashMap<String, Long>();
//	public static Map<String, Integer> NumberMap = new ConcurrentHashMap<String, Integer>();
//	public static final int highestNum = 80;
//	private final int initNum = 1;
//	public static final int interval_seconds = 600;
//	@SuppressWarnings("unused")
//	private final String userInfo = "user-info";
//
//	@Context
//	private HttpServletRequest request;
//
//	@Override
//	public void filter(ContainerRequestContext requestContext)
//			throws IOException {
//		/**String user_info = requestContext.getHeaderString(userInfo);
//		// log.info("user-info Head INFO:{}",user_info);
//		if (StringUtils.isNotEmpty(user_info)) {
//			// 从黑名单移除
//			deleteBlack(user_info);
//			// 如果请求在黑名单，不予响应
//			if (blackMap.containsKey(user_info)) {
//				Response response = Response
//						.status(Status.FORBIDDEN)
//						.type(MediaType.APPLICATION_JSON).build();
//				throw new WebApplicationException(response);
//			}
//			// 初始化
//			init(user_info);
//
//			Thread t1 = new Thread(new ThreadIN());
//			t1.start();
//		} else {
//			Response response = Response
//					.status(Status.FORBIDDEN).type(MediaType.APPLICATION_JSON)
//					.build();
//			throw new WebApplicationException(response);
//		}
//*/
//	}
//
//	/**
//	 * 初始化每次请求
//	 * 
//	 * @param user_info
//	 */
//	public void init(String user_info) {
//		if (!caseMap.containsKey(user_info)) {
//			caseMap.put(user_info, ExDateUtils.getDate().getTime());
//			NumberMap.put(user_info, initNum);
//		} else {
//			NumberMap.put(user_info, NumberMap.get(user_info) + initNum);
//			log.info("{}数量：{}", user_info, NumberMap.get(user_info));
//		}
//	}
//
//	/**
//	 * 从黑名单移除
//	 * 
//	 * @param user_info
//	 */
//	public void deleteBlack(String user_info) {
//		Long value = blackMap.get(user_info);
//		if (value == null) {
//			return;
//		}
//		if ((ExDateUtils.getDate().getTime() - value) / 1000 >= interval_seconds) {
//			// 移除黑名单
//			blackMap.remove(user_info);
//		}
//	}
//
//}
