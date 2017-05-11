package cn.heipiao.api.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.heipiao.api.pojo.Emp;

/**
 * 说明 : Emp模板crud
 * @author chenwenye
 * @since 2016-6-27  heipiao1.0
 */
public interface EmpMapper extends BaseMapper<Emp>{
	
	/**
	 * 根据用户id查询员工
	 * @param userId
	 * @return
	 */
	Emp selectById(Long userId);
	
	/**
	 * 作用: 根据电话号码查询员工所在的钓场的id
	 * @param phone
	 * @return
	 */
	Integer getFishSiteIdByPhone(String phone);
	
	/**
	 * 作用: 根据参数查询Emp;
	 * @param param - 参数:电话号码or用户名or邮箱or userId
	 * @return 返回userId + fishSiteId
	 */
	Emp findEmpByParams(String params);
	
	/**
	 * 作用: 根据uid查Emp;
	 * @param uid用户id
	 * @return 返回userId + fishSiteId
	 */
	Emp selectEmpByuid(int uid);
	/**
	 * 作用:根据参数查询用户包括用户的密码
	 * @param params
	 * @return
	 */
	Emp selectEmpAndPasswordByParams(@Param("param") String params);

	/**
	 * @param uid
	 * @return
	 */
	List<Emp> selectByUid(Long userId);

	/**
	 * @param fishSiteId
	 */
	void deleteBySiteId(Integer fishSiteId);

	/**
	 * @param siteId
	 * @return
	 */
	List<Long> selectUidsBySiteId(Integer siteId);
	
}