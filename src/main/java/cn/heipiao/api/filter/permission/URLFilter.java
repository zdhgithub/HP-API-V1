///**
// * 
// */
//package cn.heipiao.api.filter.permission;
//
//import java.io.IOException;
//
//import javax.annotation.Priority;
//import javax.servlet.http.HttpServletRequest;
//import javax.ws.rs.Priorities;
//import javax.ws.rs.WebApplicationException;
//import javax.ws.rs.container.ContainerRequestContext;
//import javax.ws.rs.container.ContainerRequestFilter;
//import javax.ws.rs.core.Context;
//import javax.ws.rs.core.MediaType;
//import javax.ws.rs.core.Response;
//import javax.ws.rs.ext.Provider;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import cn.heipiao.api.resources.Status;
//
///**
// * url请求限制过滤器
// * 
// * 目前是硬编码ip控制，
// * 以后可以加根据redis缓存用户控制
// * 
// * @author wzw
// * @date 2016年11月29日
// * @version 2.2
// */
//@Priority(Priorities.USER + 1)
//@Provider
//public class URLFilter implements ContainerRequestFilter {
//
//	private static final Logger logger =  LoggerFactory.getLogger(URLFilter.class);
//	
//	
//	@Context
//	private HttpServletRequest request;
//	
//	/* (non-Javadoc)
//	 * @see javax.ws.rs.container.ContainerRequestFilter#filter(javax.ws.rs.container.ContainerRequestContext)
//	 */
//	@Override
//	public void filter(ContainerRequestContext requestContext) throws IOException {
////		System.out.println(requestContext.getUriInfo().getAbsolutePath());
////		System.out.println(requestContext.getUriInfo());
////		String host = request.getRemoteHost();
//		//代理服务器传过来的参数
//		String host = request.getHeader("X-Real-IP");
//		if(host == null){
//			host = request.getRemoteHost();
//		}
//		String uri = request.getRequestURI();
//		int index = uri.indexOf("/", 1) + 1;
//		logger.info(uri.substring(index, uri.indexOf("/",index) < 0 ? uri.length() : uri.indexOf("/",index)) + "\r\n" + host);
//		/**
//		 * 凡是以cp调用的api接口的url，必须以cp开始，api禁止使用这种规范
//		 */
//		if(uri.substring(index, uri.indexOf("/",index) < 0 ? uri.length() : uri.indexOf("/",index)).equals("cp") && !host.equals("127.0.0.1")){
//			/**
//			 * FIXME 这种写法可能就是导致以下错误的原因
//			 * http://www.th7.cn/Program/java/201608/935153.shtml 
//			 */
//			Response response = Response
//					.status(Status.FORBIDDEN)
//					.type(MediaType.APPLICATION_JSON).build();
//			throw new WebApplicationException(response);
//		}
//	}
//
//}
