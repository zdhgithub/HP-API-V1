package cn.heipiao.api.start;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.async.DeferredResult;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author wzw
 * @date 2017年3月2日
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

	
	@Bean
	public Docket demoCp() {
		return new Docket(DocumentationType.SWAGGER_2).groupName("cp").genericModelSubstitutes(DeferredResult.class)
				// .genericModelSubstitutes(ResponseEntity.class)
				.useDefaultResponseMessages(false).forCodeGeneration(false).pathMapping("/").select()
				.paths(PathSelectors.regex("/cp/.*"))// 过滤的接口
//				.paths(PathSelectors.any())// 所有接口
				.build()
		 .apiInfo(demoApiInfo());
	}
	
	@Bean
	public Docket demoApi() {
		return new Docket(DocumentationType.SWAGGER_2).groupName("api").genericModelSubstitutes(DeferredResult.class)
				// .genericModelSubstitutes(ResponseEntity.class)
				.useDefaultResponseMessages(false).forCodeGeneration(false).pathMapping("/").select()
				.paths(PathSelectors.regex("(?!/cp/).*"))// 过滤的接口
//				.paths(PathSelectors.any())// 所有接口
				.build()
				.apiInfo(demoApiInfo());
	}

	private ApiInfo demoApiInfo() {
		Contact contact = new Contact("", "", "");
		ApiInfo apiInfo = new ApiInfo("黑漂API接口文档", // 大标题
				"REST风格API", // 小标题
				"0.1", // 版本
				"www.heipiaola.com", contact, // 作者
				"主页", // 链接显示文字
				""// 网站链接
		);
		return apiInfo;
	}
}
