//package cn.heipiao.api.filter.log;
//
//import java.io.IOException;
//
//import javax.servlet.Filter;
//import javax.servlet.FilterChain;
//import javax.servlet.FilterConfig;
//import javax.servlet.ServletException;
//import javax.servlet.ServletRequest;
//import javax.servlet.ServletResponse;
//
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.stereotype.Component;
//
///**
// * 
// * 说明 :
// * 		访问日志记录过滤器
// * 功能 : 
// *      a.	记录访问
// * @author chenwenye
// * @since 2016-6-13 heipiao1.0
// */
//@Deprecated
//@Component("logFilter")
//@SuppressWarnings("all")
//public final class LogFilter implements Filter {
//	
//	//private static final Logger log = LoggerFactory.getLogger(LogFilter.class);
//	//@Resource
//	private JdbcTemplate jdbcTemplate;
//	
//	@Override
//	public void doFilter(ServletRequest req, ServletResponse response, FilterChain chain)
//			throws IOException, ServletException {
//		
//		/*if( !(req instanceof HttpServletRequest) ){
//			chain.doFilter(req, response);
//		}*/
//		//TODO 待数据源整理进JdbcTemlate和App端获取User对象值解决
//		/*HttpServletRequest request = (HttpServletRequest)req;
//		
//		String ip = this.getIpAddr(request);
//		Date currTime = new Date();
//		String userId = "";
//		String nickname = "";
//		String content = "";
//		String interfaceName = request.getRequestURI();
//		
//		StringBuffer sql = new StringBuffer("insert into t_user_operate_log ( f_operate_uid , f_operate_nickname , f_operate_time , f_operate_content , f_operate_interface , f_operate_ip ) value ( ");
//		sql.append(userId).append(" , ");
//		sql.append(nickname).append(" , ");
//		sql.append(currTime).append(" , ");
//		sql.append(content).append(" , ");
//		sql.append(interfaceName).append(" , ");
//		sql.append(ip);
//		sql.append(")");
//		jdbcTemplate.execute(sql.toString());*/
//		chain.doFilter(req, response);
//		
//	}
//
//	@Override
//	public void destroy() {
//	}
//	@Override
//	public void init(FilterConfig filterConfig) throws ServletException {
//	}
//}
