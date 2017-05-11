/**
 * 
 */
package cn.heipiao.api.pojo;

/**
 * @author wzw
 * @date 2016年9月22日
 * @version 1.0
 */
public class FishSiteCouponsRecord {

	/**
	 * 优惠券id
	 */
	private Long cid;

	/**
	 * 钓场id
	 */
	private Integer fishSiteId;

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

	/**
	 * @return the fishSiteId
	 */
	public Integer getFishSiteId() {
		return fishSiteId;
	}

	/**
	 * @param fishSiteId
	 *            the fishSiteId to set
	 */
	public void setFishSiteId(Integer fishSiteId) {
		this.fishSiteId = fishSiteId;
	}

}
