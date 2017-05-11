package cn.heipiao.api.filter;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;

import cn.heipiao.api.pojo.RespMsg;
import cn.heipiao.api.resources.Status;

/**
 * @author wzw
 * @date 2017年3月2日
 */
public class URLFilter implements Filter {
	
	private static final Logger logger =  LoggerFactory.getLogger(URLFilter.class);
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		//代理服务器传过来的参数
		String host = req.getHeader("X-Real-IP");
		if(host == null){
			host = req.getRemoteHost();
		}
		String uri = req.getRequestURI();
		int index = uri.indexOf("/", 1) + 1;
		logger.info("uri:{}",uri);
//		logger.info(uri.substring(index, uri.indexOf("/",index) < 0 ? uri.length() : uri.indexOf("/",index)) + "\r\n" + host);
		/**
		 * 凡是以cp调用的api接口的url，必须以cp开始，api禁止使用这种规范
		 */
		if(uri.substring(index, uri.indexOf("/",index) < 0 ? uri.length() : uri.indexOf("/",index)).equals("cp") && !host.equals("127.0.0.1")){
//					/**
//					 * FIXME 这种写法可能就是导致以下错误的原因
//					 * http://www.th7.cn/Program/java/201608/935153.shtml 
//					 */
//					Response response = Response
//							.status(Status.FORBIDDEN)
//							.type(MediaType.APPLICATION_JSON).build();
//					throw new WebApplicationException(response);
			HttpServletResponse resp = (HttpServletResponse) response; 
			PrintWriter out = resp.getWriter();
			out.println(JSONObject.toJSON(new RespMsg<>(Status.FORBIDDEN)));
			out.flush();
			out.close();
			return;
		}
		
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {

	}

}
