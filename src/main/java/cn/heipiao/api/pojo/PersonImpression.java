package cn.heipiao.api.pojo;

import java.io.Serializable;

/**
 * 
 * @ClassName: PersonImpression
 * @Description: 自我印象
 * @author zf
 * @date 2016年10月11日
 */
public class PersonImpression implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2416707781418918621L;
	/** 用户id */
	private Integer uid;
	/** shopId */
	private Long shopId;
	/** 评价数量 */
	private Integer num;
	/** 启用状态0弃用，1启用 */
	private Integer status;
	/** 印象标签名称 */
	private String label;

	public PersonImpression() {
	}

	public PersonImpression(Integer uid,Long shopId, String label, Integer status) {
		this.uid = uid;
		this.shopId = shopId;
		this.label = label;
		this.status = status;
		this.num = 0;
	}

	public Integer getUid() {
		return uid;
	}

	public Long getShopId() {
		return shopId;
	}

	public Integer getNum() {
		return num;
	}

	public Integer getStatus() {
		return status;
	}

	public String getLabel() {
		return label;
	}

	public void setUid(Integer uid) {
		this.uid = uid;
	}

	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public void setLabel(String label) {
		this.label = label;
	}

}
