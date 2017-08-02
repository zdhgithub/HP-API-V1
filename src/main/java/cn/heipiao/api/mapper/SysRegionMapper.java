package cn.heipiao.api.mapper;

import java.util.List;

import cn.heipiao.api.pojo.Region;
/**
 * 
 * @ClassName: SysRegionMapper
 * @Description: TODO
 * @author zf
 */
public interface SysRegionMapper {
	/**
	 * 查询开放的城市列表
	 * @return
	 */
	public List<Region> selectOpenList();
	/**
	 * 查询对渔具店开放的城市列表
	 * @return
	 */
	public List<Region> selectOpenListForShop();
	/**
	 * 指定开放城市
	 * @param id
	 * @return
	 */
	public Integer appointOpenCity(Integer id);
	/**
	 * 
	 * @param pid
	 * @return
	 */
	public List<Region> selectListByPid(Integer pid);
}
