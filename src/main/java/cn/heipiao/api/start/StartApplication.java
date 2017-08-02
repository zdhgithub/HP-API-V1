package cn.heipiao.api.start;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author wzw
 * @date 2017年3月1日
 */
@SpringBootApplication
@ComponentScan(basePackages="cn.heipiao.api")
public class StartApplication extends SpringBootServletInitializer{

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(StartApplication.class);
	}

	
	
	/**
	 * 开发者通过main方法启动
	 * @param args
	 */
	public static void main(String[] args) {
		SpringApplication.run(StartApplication.class);
	}
	
	@Bean
	public EmbeddedServletContainerFactory servletContainer() {
	    TomcatEmbeddedServletContainerFactory tomcat = new TomcatEmbeddedServletContainerFactory();
	    tomcat.setPort(9999);
	    tomcat.setContextPath("/v1");
	    return tomcat;
	}

}
