package cn.heipiao.api.mapper;

import java.util.List;
import java.util.Map;

import cn.heipiao.api.pojo.FishSiteSign;
/**
 * 
 * @ClassName: FishSiteSignMapper
 * @Description: TODO
 * @author z
 */
public interface FishSiteSignMapper {
	public List<FishSiteSign> selectList(Map<String, Object> params);

	public FishSiteSign selectOne(Integer siteId);

	public void insert(FishSiteSign pojo);

	public void delete(Integer siteId);
	
	public int selectMax(Integer partnerId);
	public void update(Map<String ,Object> map);
	/**
	 * @param partnerId
	 */
	public void deletePartnerId(Integer partnerId);
}
