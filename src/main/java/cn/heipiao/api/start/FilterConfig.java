package cn.heipiao.api.start;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import cn.heipiao.api.filter.TokenFilter;
import cn.heipiao.api.filter.URLFilter;

/**
 * @author wzw
 * @date 2017年4月19日
 */
@Configuration
public class FilterConfig {

	@Bean
	public FilterRegistrationBean uRLFilter() {
	    FilterRegistrationBean registration = new FilterRegistrationBean();
	    registration.setFilter(new URLFilter());
	    registration.addUrlPatterns("/*");
	    registration.setName("URLFilter");
	    registration.setOrder(1);
	    return registration;
	}
	
	@Bean
	public FilterRegistrationBean testFilter() {
		FilterRegistrationBean registration = new FilterRegistrationBean();
		registration.setFilter(new TokenFilter());
		registration.addUrlPatterns("/*");
		registration.setName("TokenFilter");
		registration.setOrder(2);
		return registration;
	}
	
}
