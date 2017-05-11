/**
 * 
 */
package cn.heipiao.api.service;

/**
 * @author wzw
 * @date 2016年7月11日
 * @version 1.0
 */
public interface CacheService {

	
public static final String DEFAULT_CACHE = "heipiaoDefaultCache";
	
	/**
	 * 作用: 存
	 * 说明:使用默认的缓存(数据库)
	 * @param key
	 * @param value
	 */
	void put(String key, String value, int DB);
	
	/**
	 * 作用: 取
	 * 说明:使用默认的数据库
	 * @param key
	 * @return
	 */
	String get(String key,int DB);

	/**
	 * 作用: 删除缓存
	 * 说明:使用默认的数据库
	 * @param key
	 */
	void remove(String key,int DB);
	
//	/**
//	 * 作用: 存
//	 * @param key
//	 * @param value
//	 * @param cacheName 如果是ehcache就是缓存的名称,如果是redis那么就是数据库的名称
//	 */
//	T put(String key, T value, String cacheName);
//	
//	/**
//	 * 作用: 取
//	 * @param key
//	 * @param value
//	 * @param cacheName 如果是ehcache就是缓存的名称,如果是redis那么就是数据库的名称
//	 */
//	T get(String key, String cacheName);
//	
//	/**
//	 * 作用: 删除
//	 * @param key
//	 * @param value
//	 * @param cacheName 如果是ehcache就是缓存的名称,如果是redis那么就是数据库的名称
//	 */
//	void remove(String key, String cacheName);
	
}
