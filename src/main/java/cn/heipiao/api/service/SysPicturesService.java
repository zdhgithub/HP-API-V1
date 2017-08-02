package cn.heipiao.api.service;

import java.util.List;
import java.util.Map;

import cn.heipiao.api.pojo.SysPictures;

/**
 * @author asdf3070@163.com
 * @date 2016年11月22日
 * @version 2.2
 */
public interface SysPicturesService {

	List<SysPictures> queryList(Map<String, Object> map);

	int queryListCount(Map<String, Object> map);

	int add(SysPictures pojo);

	SysPictures getById(String id);

	int updateById(SysPictures pojo);

	int deleteById(SysPictures pojo);

	String updateShopListImg(Long id);

	String updateSiteListImg(Long id);

	void updateTopImg(Integer type, Long id, String imgId);

	List<SysPictures> updateListImgAll(Integer type);

	void setListEmpty();

}
