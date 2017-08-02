package cn.heipiao.api.pojo;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author zf
 * @version 1.0
 * @description 合伙人
 * @date 2016年6月1日
 */
@XmlRootElement
public class Partner extends User implements Serializable {
	private static final long serialVersionUID = -1125848497124471689L;
	/** 合伙人id */
	private Long pId;
	/** 星标状态，1是有星号，0没有星号 */
	private String pFlag;
	/** 平台给合伙人的的描述 */
	private String pRemark;
	/** 管辖区域外键id */
	private Integer pRegionId;
	// private Long pUserId;// 用户uid
	/** 创建时间 */
	private Date pCreateTime;
	
	/**
	 * 已发放优惠券数量
	 */
	private Integer givingOutCouponCount;
	
	/**
	 * 发放优惠券总数量 , 可在后台配置
	 */
	private Integer givingOutCouponSum; 

	/**
	 * 发券金额:元 ,可在后台配置
	 */
	private Integer givingOutCouponFee;
	
	/**
	 * 合伙人状态 true：存在，false:解除
	 */
	private Boolean isExists;
	

	public Long getpId() {
		return pId;
	}

	public String getpFlag() {
		return pFlag;
	}

	public String getpRemark() {
		return pRemark;
	}

	public Integer getpRegionId() {
		return pRegionId;
	}

	public Date getpCreateTime() {
		return pCreateTime;
	}

	public void setpId(Long pId) {
		this.pId = pId;
	}

	public void setpFlag(String pFlag) {
		this.pFlag = pFlag;
	}

	public void setpRemark(String pRemark) {
		this.pRemark = pRemark;
	}

	public void setpRegionId(Integer pRegionId) {
		this.pRegionId = pRegionId;
	}

	public void setpCreateTime(Date pCreateTime) {
		this.pCreateTime = pCreateTime;
	}

	/**
	 * @return the givingOutCouponCount
	 */
	public Integer getGivingOutCouponCount() {
		return givingOutCouponCount;
	}

	/**
	 * @param givingOutCouponCount the givingOutCouponCount to set
	 */
	public void setGivingOutCouponCount(Integer givingOutCouponCount) {
		this.givingOutCouponCount = givingOutCouponCount;
	}

	/**
	 * @return the givingOutCouponSum
	 */
	public Integer getGivingOutCouponSum() {
		return givingOutCouponSum;
	}

	/**
	 * @param givingOutCouponSum the givingOutCouponSum to set
	 */
	public void setGivingOutCouponSum(Integer givingOutCouponSum) {
		this.givingOutCouponSum = givingOutCouponSum;
	}

	/**
	 * @return the givingOutCouponFee
	 */
	public Integer getGivingOutCouponFee() {
		return givingOutCouponFee;
	}

	/**
	 * @param givingOutCouponFee the givingOutCouponFee to set
	 */
	public void setGivingOutCouponFee(Integer givingOutCouponFee) {
		this.givingOutCouponFee = givingOutCouponFee;
	}

	public Boolean getIsExists() {
		return isExists;
	}

	public void setIsExists(Boolean isExists) {
		this.isExists = isExists;
	}

}
