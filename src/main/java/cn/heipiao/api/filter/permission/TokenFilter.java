//package cn.heipiao.api.filter.permission;
//
//import java.io.IOException;
//
//import javax.annotation.Priority;
//import javax.servlet.http.HttpServletRequest;
//import javax.ws.rs.Priorities;
//import javax.ws.rs.container.ContainerRequestContext;
//import javax.ws.rs.container.ContainerRequestFilter;
//import javax.ws.rs.core.Context;
//import javax.ws.rs.ext.Provider;
//
//
//@Priority(Priorities.USER + 2)
//@Provider
//public class TokenFilter implements ContainerRequestFilter {
//
//	@Context
//	private HttpServletRequest request;
//	
//	@Override
//	public void filter(ContainerRequestContext requestContext) throws IOException {
//		
//		System.out.println(request.getRequestURI());
//	}
//
//}
