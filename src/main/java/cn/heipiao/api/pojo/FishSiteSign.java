package cn.heipiao.api.pojo;

import java.util.Date;
/**
 * 
 * @ClassName: FishShopSign
 * @Description: TODO
 * @author zf
 * @date 2016年10月18日
 */
public class FishSiteSign {
	private Integer siteId;
	private Integer partnerId;
	private Date signTime;
	private Integer signStatus;
	public Integer getSiteId() {
		return siteId;
	}
	public void setSiteId(Integer siteId) {
		this.siteId = siteId;
	}
	public Integer getPartnerId() {
		return partnerId;
	}
	public void setPartnerId(Integer partnerId) {
		this.partnerId = partnerId;
	}
	public Date getSignTime() {
		return signTime;
	}
	public void setSignTime(Date signTime) {
		this.signTime = signTime;
	}
	public Integer getSignStatus() {
		return signStatus;
	}
	public void setSignStatus(Integer signStatus) {
		this.signStatus = signStatus;
	}
	
}
