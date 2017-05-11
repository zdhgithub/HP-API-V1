package cn.heipiao.api.service;

import cn.heipiao.api.pojo.ArticleUserBehavior;

/**
 * @author asdf3070@163.com
 * @date 2016年11月7日
 * @version 2.1
 */
public interface ArticleUserBehaviorService {

	/**
	 * 查询相应行为
	 * @param pojo
	 * @return
	 */
	ArticleUserBehavior getBehavior(ArticleUserBehavior pojo);

	/**
	 * 增加用户行为
	 * @param pojo
	 * @return
	 */
	int add(ArticleUserBehavior pojo);

	/**
	 * 更新行为数量
	 * @param pojo
	 * @return
	 */
	int updateCount(ArticleUserBehavior pojo);

}
