package cn.heipiao.api.mapper;

import java.util.List;
import java.util.Map;
/**
 * 
 * @ClassName: FishShopSignMapper
 * @Description: TODO
 * @author z
 * @date 2016年10月18日
 */
public interface FishShopSignMapper<T> {
	public List<T> selectList(Map<String, Object> params);

	public T selectOne(Long shopId);

	public void insert(T pojo);

	public void delete(Long shopId);
	
	public int selectMax(Integer partnerId);
	public void update(Map<String ,Object> map);
	public void updatePartner(Map<String ,Object> map);
	/**
	 * @param partnerId
	 */
	public void deletePartnerId(Integer partnerId);
}
