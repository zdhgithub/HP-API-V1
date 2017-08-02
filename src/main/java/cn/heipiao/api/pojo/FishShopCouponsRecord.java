/**
 * 
 */
package cn.heipiao.api.pojo;

/**
 * @author wzw
 * @date 2016年9月22日
 * @version 1.0
 */
public class FishShopCouponsRecord {

	/**
	 * 优惠券id
	 */
	private Long cid;

	/**
	 * 店铺id
	 */
	private Long shopId;

	/**
	 * @return the cid
	 */
	public Long getCid() {
		return cid;
	}

	/**
	 * @param cid
	 *            the cid to set
	 */
	public void setCid(Long cid) {
		this.cid = cid;
	}

	public Long getShopId() {
		return shopId;
	}

	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}

}
