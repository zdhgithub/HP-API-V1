package cn.heipiao.api.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author wzw
 * @date 2017年3月2日
 */
public class TokenFilter implements Filter {

	private static final Logger logger =  LoggerFactory.getLogger(TokenFilter.class);
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
			logger.info("tokenFilter");
		
			chain.doFilter(request, response);
			
		
	}

	@Override
	public void destroy() {

	}

}
