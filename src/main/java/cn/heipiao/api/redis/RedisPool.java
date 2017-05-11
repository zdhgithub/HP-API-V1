package cn.heipiao.api.redis;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
/**
 * redis 连接池类
 * @author wzw
 *
 */
@Component
public class RedisPool extends RedisConfig{

//	String host = "*.aliyuncs.com";
//	String password = "实例id:密码";
//	String host = "192.168.1.220";
//	String password = "";
	private static JedisPoolConfig config = new JedisPoolConfig();
	private static JedisPool pool = null;

	public RedisPool(){
		super();
	}
	
	private void initPool(){
		//最大空闲连接数, 应用自己评估，不要超过ApsaraDB for Redis每个实例最大的连接数
		config.setMaxIdle(maxIdle);
		//最大连接数, 应用自己评估，不要超过ApsaraDB for Redis每个实例最大的连接数
		config.setMaxTotal(maxTotal);
		config.setTestOnBorrow(false);
		config.setTestOnReturn(false);
		if(StringUtils.isBlank(password)){
			pool = new JedisPool(config, host, port, timeout);
		}else{
			pool = new JedisPool(config, host, port, timeout,password);
		}
	}
	
	public Jedis getJedis(int DB){
		if(pool == null){
			synchronized (config) {
				if(pool == null){
					initPool();
				}
			}
		}
		Jedis jedis = pool.getResource();
		jedis.select(DB);
		return jedis;
	}
	
	@SuppressWarnings("deprecation")
	public void releaseJedis(Jedis jedis){
		if(jedis != null){
			pool.returnResource(jedis);
		}
	}
	
}
