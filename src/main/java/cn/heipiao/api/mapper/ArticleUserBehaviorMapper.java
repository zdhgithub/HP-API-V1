package cn.heipiao.api.mapper;

import cn.heipiao.api.pojo.ArticleUserBehavior;

/**
 * @author asdf3070@163.com
 * @date 2016年11月7日
 * @version 2.1
 */
public interface ArticleUserBehaviorMapper {

	ArticleUserBehavior selectBehavior(ArticleUserBehavior pojo);

	int add(ArticleUserBehavior pojo);

	int updateCount(ArticleUserBehavior pojo);

}
