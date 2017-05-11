package cn.heipiao.api.redis;

import org.springframework.beans.factory.annotation.Value;
/**
 * redis 配置类
 * @author wzw
 *
 */
public class RedisConfig {
	
	// 
	public static final int smsDB = 0;

	public static final int regoinDB = 1;
	
	// 全局配置
	public static final int GLOBAL_CONFIG = 2;
	
	
	//------------------------key
	// 合伙人招募信息
	public static final String PARTNER_RECRUIT_INFO = "recruit_info";
	// 合伙人招募开放城市
	public static final String PARTNER_CITY = "partnerCity";
	
	
	@Value("${redis.host}")
	protected String host ;
	
	@Value("${redis.port}")
	protected Integer port;
	
	@Value("${redis.password}")
	protected String password;
	//最大空闲连接数
	@Value("${redis.maxIdle}")
	protected Integer maxIdle;
	//最大连接数
	@Value("${redis.maxTotal}")
	protected Integer maxTotal;
	
	//超时时间
	@Value("${redis.timeout}")
	protected Integer timeout;
	
	
}
