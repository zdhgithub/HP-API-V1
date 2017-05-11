//package cn.heipiao.api.filter.permission;
//
//import java.io.IOException;
//
//import javax.annotation.Priority;
//import javax.servlet.http.HttpServletRequest;
//import javax.ws.rs.Priorities;
//import javax.ws.rs.container.ContainerRequestContext;
//import javax.ws.rs.container.ContainerRequestFilter;
//import javax.ws.rs.container.PreMatching;
//import javax.ws.rs.core.Context;
//import javax.ws.rs.ext.Provider;
//
//import org.apache.commons.lang3.StringUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.mobile.device.Device;
//import org.springframework.mobile.device.LiteDeviceResolver;
//
//import cn.heipiao.api.utils.ExRequestUtils;
//
///**
// * @author z
// * @version 1.0
// * @description
// * @date 2016年6月21日
// */
//// 当前类应当是删除了
//@Deprecated
//@PreMatching
//@Priority(Priorities.USER + 5)
//@Provider
//public class PermissionFilter extends LiteDeviceResolver implements
//		ContainerRequestFilter {
//	private static final Logger log = LoggerFactory
//			.getLogger(PermissionFilter.class);
//	private final String SEPARATOR = ",";
//	private final String WHITE_LIST = "white_list";
//	@Context
//	private HttpServletRequest request;
//
////	@SuppressWarnings("rawtypes")
//	@Override
//	public void filter(ContainerRequestContext requestContext)
//			throws IOException {
//		boolean source = isFromMobile();
//		log.debug("urI:{}",request.getRequestURI());
//		if (source) {
//			log.info("From APP");
//			/**
//			 * APP这端，对于需要登录的接口来说，需要userId和IMEI，如果和服务器存在，则通过，
//			 * 如果不存在，则返回登录提醒；对于不要登录的接口来说，传IMEI没多大意义;
//			 * 后面考虑把部分接口区分开，APP使用APP的，PC使用PC的;
//			 * */
//		} else {
//			/**
//			 * 白名单过滤
//			 */
//			String ip = ExRequestUtils.getIpAddr(request);
//			log.debug("From PC,request IP is:{}", ip);
//			String whites = request.getServletContext().getInitParameter(
//					WHITE_LIST);
//			if (StringUtils.isNotBlank(whites)) {
//				String[] ip_list = whites.split(SEPARATOR);
//				for (@SuppressWarnings("unused") String p : ip_list) {
////					log.info("init param IP:{}" , p);
//					//先注掉，后期使用的时候放开
////					if (p.contains(ip)) {
////					} else {
////						Response response = Response
////								.status(Status.FORBIDDEN)
////								.type(MediaType.APPLICATION_JSON).build();
////						throw new WebApplicationException(response);
////					}
//				}
//			}
//		}
//
//	}
//
//	/**
//	 * 来源判断
//	 * 
//	 * @param requestContext
//	 * @return
//	 */
//	public boolean isFromMobile() {
//		Device device = this.resolveDevice(request);
//		if (device.isMobile() || device.isTablet()) {
//			return true;
//		}
//		return false;
//	}
//
//}
