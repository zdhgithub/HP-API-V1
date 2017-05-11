package cn.heipiao.api.pojo;

import java.sql.Timestamp;

/**
 * 
 */

/**
 * @author wzw
 * @date 2016年11月26日
 * @version 1.0
 */
public class UserShareOrders {

	/**
	 * 订单号 yyMMddHHmmss + uid
	 */
	private String orderId;
	
	/**
	 * 用户id
	 */
	private Long uid;
	
	/**
	 * 获得漂币
	 */
	private Integer amount;
	
	/**
	 * 分享内容的id
	 */
	private Long shareId;
	
	/**
	 *  创建时间
	 */
	private Timestamp createTime;

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public Long getUid() {
		return uid;
	}

	public void setUid(Long uid) {
		this.uid = uid;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public Long getShareId() {
		return shareId;
	}

	public void setShareId(Long shareId) {
		this.shareId = shareId;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
	
	
	
}
