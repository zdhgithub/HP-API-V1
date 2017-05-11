package cn.heipiao.api.pojo;

import java.util.Date;
/**
 * 
 * @ClassName: FishShopSign
 * @Description: TODO
 * @author zf
 * @date 2016年10月18日
 */
public class FishShopSign {
	private Long shopId;
	private Integer partnerId;
	private Date signTime;
	private Integer signStatus;
	public Long getShopId() {
		return shopId;
	}

	public Integer getPartnerId() {
		return partnerId;
	}

	public Date getSignTime() {
		return signTime;
	}

	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}

	public void setPartnerId(Integer partnerId) {
		this.partnerId = partnerId;
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
