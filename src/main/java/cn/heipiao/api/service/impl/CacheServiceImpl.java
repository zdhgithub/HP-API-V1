/**
 * 
 */
package cn.heipiao.api.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.heipiao.api.redis.RedisPool;
import cn.heipiao.api.service.CacheService;
import redis.clients.jedis.Jedis;

/**
 * @author wzw
 * @date 2016年7月11日
 * @version 1.0
 */
@Service
public class CacheServiceImpl implements CacheService {

	@Resource
	private RedisPool redisPool;
	
	/* (non-Javadoc)
	 * @see cn.heipiao.api.service.CacheService#put(java.lang.String, java.lang.Object)
	 */
	@Override
//	@CachePut(value = { DEFAULT_CACHE },key="#p0")
	public void put(String key, String value,int DB) {
		Jedis jedis = redisPool.getJedis(DB);
		jedis.set(key, value);
		redisPool.releaseJedis(jedis);
	}
	
	/* (non-Javadoc)
	 * @see cn.heipiao.api.service.CacheService#get(java.lang.String)
	 */
	@Override
//	@Cacheable(value = { DEFAULT_CACHE },key="#p0")
	public String get(String key,int DB) {
		Jedis jedis = redisPool.getJedis(DB);
		String value = jedis.get(key);
		redisPool.releaseJedis(jedis);
		return value;
	}

	/* (non-Javadoc)
	 * @see cn.heipiao.api.service.CacheService#remove(java.lang.String)
	 */
	@Override
//	@CacheEvict(value = { DEFAULT_CACHE },key="#p0")
	public void remove(String key,int DB) {
		Jedis jedis = redisPool.getJedis(DB);
		jedis.del(key);
		redisPool.releaseJedis(jedis);
	}

}
