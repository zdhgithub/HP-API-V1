/**
 * 
 */
package cn.heipiao.api.oss;

import org.springframework.beans.factory.annotation.Value;

/**
 * oss配置类
 * @author wzw
 * @date 2016年7月26日
 * @version 1.0
 */
public class OssConfig {

	@Value("${oss.accessKeyId}")
	protected String accessKeyId;

	@Value("${oss.accessKeySecret}")
	protected String accessKeySecret;

	// RoleArn 需要在 RAM 控制台上获取
	@Value("${oss.roleArn}")
	protected String roleArn;
	
	@Value("${oss.endpoint}")
	protected String endpoint;

}
