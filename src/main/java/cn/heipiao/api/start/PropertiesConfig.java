package cn.heipiao.api.start;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.UrlResource;

/**
 * @author wzw
 * @date 2017年2月25日
 */
@Configuration
public class PropertiesConfig {

	@Bean
	public PropertySourcesPlaceholderConfigurer getPropertySourcesPlaceholderConfigurer() throws Exception{
		PropertySourcesPlaceholderConfigurer pspc = new PropertySourcesPlaceholderConfigurer();
		String path = System.getProperty("hp.config");
		System.out.println(path);
		pspc.setLocations(new UrlResource("file:" + path + "/heipiaoConfig.properties"));
		pspc.setFileEncoding("UTF-8");
		return pspc;
	}
	
}
