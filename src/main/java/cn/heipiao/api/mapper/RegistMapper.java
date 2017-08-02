package cn.heipiao.api.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.heipiao.api.pojo.PushUser;

public interface RegistMapper {
	String selectOne(@Param("mobile") String mobile, @Param("type") String type);

	List<String> selectList();

	PushUser selectPojo(@Param("mobile") String mobile,
			@Param("type") String type);

	int insert(PushUser p);

	int update(@Param("mobile") final String mobile,
			@Param("alias") final String alias,
			@Param("tags") final String tags,
			@Param("registration_id") final String registration_id,
			@Param("type") String type,
			@Param("os") String os);

	int delete(@Param("mobile") String mobile, @Param("type") String type);
}
