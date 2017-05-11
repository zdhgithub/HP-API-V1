//package cn.heipiao.api.mapper;
//
//import java.util.Map;
//
//import org.apache.ibatis.annotations.Param;
//
//import cn.heipiao.api.pojo.CouponUser;
//
///**
// * @author zf
// * @version 1.0
// * @description
// * @date 2016年7月19日
// */
//public interface CouponUserMapper extends BaseMapper<CouponUser> {
//	/**
//	 * 统计钓场主共发券数量
//	 * 
//	 * @return
//	 */
//	public int countSendedCoupons();
//
//	/**
//	 * 统计钓场主发券的总金额
//	 * 
//	 * @return
//	 */
//	public double countSendedCouponMoney();
//
//	/**
//	 * 统计用户可以使用的券的数量
//	 * 
//	 * @return
//	 */
//	public int countUnusedCoupons(@Param("userId") Integer userId,
//			@Param("status") Integer status);
//	
//	/**
//	 * @说明 批量插入
//	 * @param pojo
//	 * @return
//	 */
//	<U> int inserts(U pojo);
//
//	/**
//	 * @param map
//	 */
//	public void updateCouponByUid(Map<String, Object> map);
//	
//}
