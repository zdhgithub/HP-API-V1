package cn.heipiao.api.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Update;

import cn.heipiao.api.pojo.SysPictures;

public interface SysPicturesMapper {

	int queryListCount(Map<String, Object> map);

	List<SysPictures> queryList(Map<String, Object> map);

	int insertNew(SysPictures pojo);

	SysPictures selectById(String id);

	int updateById(SysPictures pojo);

	int deleteById(SysPictures pojo);

	List<SysPictures> selectMainIsNoneAll(Map<String, Object> map);

	@Update("UPDATE `t_fish_shop` SET f_fish_shop_main_img_none=NULL")
	public int setShopListEmpty();

	@Update("UPDATE `t_fish_site` SET f_main_img_none=NULL")
	public int setSiteListEmpty();

}
