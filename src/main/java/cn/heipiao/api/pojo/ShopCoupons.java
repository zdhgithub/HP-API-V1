/**
 * 
 */
package cn.heipiao.api.pojo;

/**
 * @author wzw
 * @date 2016年11月2日
 * @version 1.0
 */
public class ShopCoupons extends GiveCoupons{

	/**
	 * 店鋪id
	 */
	private Long shopId;

	public Long getShopId() {
		return shopId;
	}

	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}

}