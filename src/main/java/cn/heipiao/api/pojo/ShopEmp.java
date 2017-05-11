package cn.heipiao.api.pojo;

import java.io.Serializable;

/**
 * 
 * @ClassName: ShopEmp
 * @Description:
 * @author zf
 * @date 2016年10月9日
 */
public class ShopEmp implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -154593982731617872L;
	/** 用户ID */
	private Integer uid;
	/** 渔具店ID */
	private Long shopId;
	/** 店主对员工的备注 */
	private String remark;
	/** 渔具店员工状态 ： 1-正常 0-冻结 2-店主 */
	private Integer status;
	/** 昵称 */
	private String nickname;
	/** 手机号 */
	private String phone;
	/** 头像url **/
	private String portriat;
	
	/**
	 * 店铺名称
	 */
	private String name;
	/**
	 * 店铺主图
	 */
	private String mainImg;

	public ShopEmp() {

	}

	public ShopEmp(Integer uid, Long shopId) {
		this.uid = uid;
		this.shopId = shopId;
	}

	public Integer getUid() {
		return uid;
	}

	public Long getShopId() {
		return shopId;
	}

	public String getRemark() {
		return remark;
	}

	public Integer getStatus() {
		return status;
	}

	public String getNickname() {
		return nickname;
	}

	public String getPhone() {
		return phone;
	}

	public String getPortriat() {
		return portriat;
	}

	public void setUid(Integer uid) {
		this.uid = uid;
	}

	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public void setPortriat(String portriat) {
		this.portriat = portriat;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMainImg() {
		return mainImg;
	}

	public void setMainImg(String mainImg) {
		this.mainImg = mainImg;
	}

}
