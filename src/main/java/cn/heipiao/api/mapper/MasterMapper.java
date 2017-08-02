package cn.heipiao.api.mapper;


import cn.heipiao.api.pojo.Master;

public interface MasterMapper {

	Master selectByUid(Long uid);

	int updateById(Master master);

	int insertMaster(Master master);

}
