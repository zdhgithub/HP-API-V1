package cn.heipiao.api.mapper;

import java.util.List;

import cn.heipiao.api.pojo.ArticleDict;

/**
 * @author wzw
 * @date 2017年3月31日
 */
public interface ArticleDictMapper {

	public List<ArticleDict> selectListByCategory(Integer category);
	
}
