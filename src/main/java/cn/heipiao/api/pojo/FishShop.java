package cn.heipiao.api.pojo;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * 
 * @author asdf3070@163.com
 * @date 2016年10月12日
 * @version 2.0
 * @desc 使用以下更新语句，对生产的渔具店数据进行初始化：<br>
 * UPDATE `t_fish_shop` SET <br>
 * `f_fish_shop_status` = 1, <br>
 * `f_fish_shop_type` = 0, <br>
 * `f_fish_shop_auth_status`=0, <br>
 * `f_fish_shop_flag`=NULL, <br>
 * `f_fish_shop_sys_remarks`=NULL, <br>
 * `f_uid`=NULL, <br>
 * `f_fish_shop_top`='0', <br>
 * `f_fish_shop_scale`=NULL, <br>
 * `f_fish_shop_auth_sign_uid`=NULL
 */
public class FishShop implements Serializable {

	private static final long serialVersionUID = 850496916813722913L;
	
	/**
	 * 主键id
	 */
	private Long id;
	
	/**
	 * 所属用户
	 */
	private Integer uid;

	/**
	 * 所属用户昵称（绰号）
	 */
	private String uNickname;

	/**
	 * 所属用户头像
	 */
	private String uPortriat;
	
	/**
	 * 渔具店名称
	 */
	private String name;
	
	/**
	 * 省份id
	 */
	private Integer provinceId;
	
	/**
	 * 城市id
	 */
	private Integer cityId;
	
	/**
	 * 区域id
	 */
	private Integer regionId;
	
	/**
	 * 详细地址
	 */
	private String addr;
	
	/**
	 * 距离（米）
	 */
	private Integer duration;
	
	/**
	 * k码(凯立德汽车导航)
	 */
	private String kNum;	

	/**
	 * 经度
	 */
	private Double longitude;

	/**
	 * 纬度
	 */
	private Double latitude;

	/**
	 * 主图
	 */
	private String mainImg;

	/**
	 * 主图(顶部图片-t_sys_pictures.f_id)
	 */
	private String mainTopImg;

	/**
	 * 主图(主图为空时显示-t_sys_pictures.f_id)
	 */
	private String mainImgNone;

	/**
	 * 资源链接 图片+视频最多9个 
	 */
	private String resources;

	/**
	 * 简介
	 */
	private String summary;

	/**
	 * 联系人
	 */
	private String contact;

	/**
	 * 联系方式
	 */
	private String phone;

	/**
	 * 产品类型，字典shop_p_type
	 */
	private String ptype;

	/**
	 * 产品品牌，字典shop_p_brand
	 */
	private String pbrand;

	/**
	 * 关注数量 默认0
	 */
	private Integer focusCount;

	/**
	 * 店铺规模，字典fs_scale
	 */
	private Integer scale;

	/**
	 * 上线状态 0-待审核 1-正常 2-审核未通过 3-下架 4-黑名单
	 */
	private Integer status;

	/**
	 * 渔具店类型，0-平台添加 1-漂友添加 默认0
	 */
	private Integer type;

	/**
	 * 渔具店实名认证状态，0-漂友认证 1-实名认证 2-签约认证 默认0
	 */
	private Integer authStatus;

	/**
	 * 渔具店实名认证时间
	 */
	private String authTimeName;

	/**
	 * 渔具店签约认证时间
	 */
	private String authTimeSign;

	/**
	 * 签约认证所属用户
	 */
	private Integer signUid;

	/**
	 * 显示置顶 默认0
	 */
	private Integer top;

	/**
	 * (cp)星标
	 */
	private Integer flag;

	/**
	 * (cp)平台备注-运营专用
	 */
	private String sysRemarks;

	/**
	 * 创建时间
	 */
	private String createtime;

	/**
	 * 更新时间
	 */
	private String updatetime;

	/**
	 * 当前用户是否已点赞
	 */
	private Integer isPraise;

	/**
	 * 点赞时间
	 */
	private String praiseTime;

	/**
	 * 当前用户是否已收藏
	 */
	private Integer isCollect;

	/**
	 * 收藏时间
	 */
	private String collectTime;

	/**
	 * 当前点赞总数
	 */
	private Integer praiseNum;

	/**
	 * 当前收藏总数
	 */
	private Integer collectNum;	

	/**
	 * 此店铺签约状态：0可认领，1已认领，2已签约 ,3售票认领,4已售票认领
	 */
	private Integer signStatus;
	/**
	 * 售票认领奖金（分）
	 */
	private Integer tradingSum;
	/**
	 * 此店铺是否被合伙人申请认领
	 */
	private Integer signIsApply;

	/**
	 * 此店铺被合伙人申请认领的店铺唯一标识
	 */
	private Long signShopId;

	/**
	 * 此店铺被申请认领的合伙人用户唯一标识
	 */
	private Integer signUserid;

	/**
	 * 此店铺被申请认领的合伙人用户昵称
	 */
	private String signNickname;

	/**
	 * 此店铺被申请认领的合伙人用户头像
	 */
	private String signPortriat;

	/**
	 * 是否开通发放优惠券功能
	 */
	private Boolean openCoupon;
	
	/**
	 * 是否有可领取的券
	 */
	private Boolean isCoupon;
	
	/**
	 * 剩余优惠券数量
	 */
	private Integer couponCount;
	
	/**
	 * 是否授权 cp控制
	 */
	private Boolean isAuthCoupon;
	/**
	 * 
	 * 是否支持配送
	 */
	private int distributionStatus;
	
	
	
	private Integer partnerId;
	
	private String partnerNickName;
	
	private String partnerPhoneNum;
	
	private String partnerRealName;
	
	private String shopNickName;
	
	private String shopPhoneNum;
	
	private String shopRealName;
	
	
	
	
	public int getDistributionStatus() {
		return distributionStatus;
	}



	public void setDistributionStatus(int distributionStatus) {
		this.distributionStatus = distributionStatus;
	}



	public Boolean getIsAuthCoupon() {
		return isAuthCoupon;
	}



	public void setIsAuthCoupon(Boolean isAuthCoupon) {
		this.isAuthCoupon = isAuthCoupon;
	}



	public Long getId() {
		return id;
	}



	public void setId(Long id) {
		this.id = id;
	}



	public Integer getUid() {
		return uid;
	}



	public void setUid(Integer uid) {
		this.uid = uid;
	}



	public String getuNickname() {
		return uNickname;
	}



	public void setuNickname(String uNickname) {
		this.uNickname = uNickname;
	}



	public String getuPortriat() {
		return uPortriat;
	}



	public void setuPortriat(String uPortriat) {
		this.uPortriat = uPortriat;
	}



	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public Integer getProvinceId() {
		return provinceId;
	}



	public void setProvinceId(Integer provinceId) {
		this.provinceId = provinceId;
	}



	public Integer getCityId() {
		return cityId;
	}



	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}



	public Integer getRegionId() {
		return regionId;
	}



	public void setRegionId(Integer regionId) {
		this.regionId = regionId;
	}



	public String getAddr() {
		return addr;
	}



	public void setAddr(String addr) {
		this.addr = addr;
	}



	public String getkNum() {
		return kNum;
	}



	public void setkNum(String kNum) {
		this.kNum = kNum;
	}



	public Double getLongitude() {
		return longitude;
	}



	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}



	public Double getLatitude() {
		return latitude;
	}



	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}



	public String getMainImg() {
		return mainImg;
	}



	public void setMainImg(String mainImg) {
		this.mainImg = mainImg;
	}
	


	public String getMainTopImg() {
		return mainTopImg;
	}



	public void setMainTopImg(String mainTopImg) {
		this.mainTopImg = mainTopImg;
	}



	public String getMainImgNone() {
		return mainImgNone;
	}



	public void setMainImgNone(String mainImgNone) {
		this.mainImgNone = mainImgNone;
	}



	public String getResources() {
		return resources;
	}



	public void setResources(String resources) {
		this.resources = resources;
	}



	public String getSummary() {
		return summary;
	}



	public void setSummary(String summary) {
		this.summary = summary;
	}



	public String getContact() {
		return contact;
	}



	public void setContact(String contact) {
		this.contact = contact;
	}



	public String getPhone() {
		return phone;
	}



	public void setPhone(String phone) {
		this.phone = phone;
	}



	public String getPtype() {
		return ptype;
	}



	public void setPtype(String ptype) {
		this.ptype = ptype;
	}



	public String getPbrand() {
		return pbrand;
	}



	public void setPbrand(String pbrand) {
		this.pbrand = pbrand;
	}



	public Integer getFocusCount() {
		return focusCount;
	}



	public void setFocusCount(Integer focusCount) {
		this.focusCount = focusCount;
	}



	public Integer getScale() {
		return scale;
	}



	public void setScale(Integer scale) {
		this.scale = scale;
	}



	public Integer getStatus() {
		return status;
	}



	public void setStatus(Integer status) {
		this.status = status;
	}



	public Integer getAuthStatus() {
		return authStatus;
	}



	public void setAuthStatus(Integer authStatus) {
		this.authStatus = authStatus;
	}



	public Integer getType() {
		return type;
	}



	public void setType(Integer type) {
		this.type = type;
	}



	public String getAuthTimeName() {
		return authTimeName;
	}



	public String getAuthTimeSign() {
		return authTimeSign;
	}



	public void setAuthTimeSign(String authTimeSign) {
		this.authTimeSign = authTimeSign;
	}



	public Integer getSignUid() {
		return signUid;
	}



	public void setSignUid(Integer signUid) {
		this.signUid = signUid;
	}



	public void setAuthTimeName(String authTimeName) {
		this.authTimeName = authTimeName;
	}



	public Integer getTop() {
		return top;
	}



	public void setTop(Integer top) {
		this.top = top;
	}



	public Integer getFlag() {
		return flag;
	}



	public void setFlag(Integer flag) {
		this.flag = flag;
	}



	public String getSysRemarks() {
		return sysRemarks;
	}



	public void setSysRemarks(String sysRemarks) {
		this.sysRemarks = sysRemarks;
	}



	public String getCreatetime() {
		return createtime;
	}



	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}



	public String getUpdatetime() {
		return updatetime;
	}



	public void setUpdatetime(String updatetime) {
		this.updatetime = updatetime;
	}



	public Integer getDuration() {
		return duration;
	}



	public void setDuration(Integer duration) {
		this.duration = duration;
	}



	public Integer getIsPraise() {
		return isPraise;
	}



	public void setIsPraise(Integer isPraise) {
		this.isPraise = isPraise;
	}



	public Integer getIsCollect() {
		return isCollect;
	}



	public void setIsCollect(Integer isCollect) {
		this.isCollect = isCollect;
	}



	public Integer getPraiseNum() {
		return praiseNum;
	}



	public void setPraiseNum(Integer praiseNum) {
		this.praiseNum = praiseNum;
	}



	public Integer getCollectNum() {
		return collectNum;
	}



	public void setCollectNum(Integer collectNum) {
		this.collectNum = collectNum;
	}



	public Integer getSignStatus() {
		return signStatus;
	}



	public void setSignStatus(Integer signStatus) {
		this.signStatus = signStatus;
	}



	public Integer getSignIsApply() {
		return signIsApply;
	}



	public void setSignIsApply(Integer signIsApply) {
		this.signIsApply = signIsApply;
	}



	public Long getSignShopId() {
		return signShopId;
	}



	public void setSignShopId(Long signShopId) {
		this.signShopId = signShopId;
	}



	public Integer getSignUserid() {
		return signUserid;
	}



	public void setSignUserid(Integer signUserid) {
		this.signUserid = signUserid;
	}



	public String getSignNickname() {
		return signNickname;
	}



	public void setSignNickname(String signNickname) {
		this.signNickname = signNickname;
	}



	public String getSignPortriat() {
		return signPortriat;
	}



	public void setSignPortriat(String signPortriat) {
		this.signPortriat = signPortriat;
	}



	public Boolean getOpenCoupon() {
		return openCoupon;
	}



	public void setOpenCoupon(Boolean openCoupon) {
		this.openCoupon = openCoupon;
	}



	public Boolean getIsCoupon() {
		return isCoupon;
	}



	public void setIsCoupon(Boolean isCoupon) {
		this.isCoupon = isCoupon;
	}



	public Integer getCouponCount() {
		return couponCount;
	}



	public void setCouponCount(Integer couponCount) {
		this.couponCount = couponCount;
	}



	public String getPraiseTime() {
		return praiseTime;
	}



	public void setPraiseTime(String craiseTime) {
		this.praiseTime = craiseTime;
	}



	public String getCollectTime() {
		return collectTime;
	}



	public void setCollectTime(String collectTime) {
		this.collectTime = collectTime;
	}



	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}



	public Integer getTradingSum() {
		return tradingSum;
	}



	public void setTradingSum(Integer tradingSum) {
		this.tradingSum = tradingSum;
	}



	public Integer getPartnerId() {
		return partnerId;
	}



	public void setPartnerId(Integer partnerId) {
		this.partnerId = partnerId;
	}



	public String getPartnerNickName() {
		return partnerNickName;
	}



	public void setPartnerNickName(String partnerNickName) {
		this.partnerNickName = partnerNickName;
	}



	public String getPartnerPhoneNum() {
		return partnerPhoneNum;
	}



	public void setPartnerPhoneNum(String partnerPhoneNum) {
		this.partnerPhoneNum = partnerPhoneNum;
	}



	public String getPartnerRealName() {
		return partnerRealName;
	}



	public void setPartnerRealName(String partnerRealName) {
		this.partnerRealName = partnerRealName;
	}



	public String getShopNickName() {
		return shopNickName;
	}



	public void setShopNickName(String shopNickName) {
		this.shopNickName = shopNickName;
	}



	public String getShopPhoneNum() {
		return shopPhoneNum;
	}



	public void setShopPhoneNum(String shopPhoneNum) {
		this.shopPhoneNum = shopPhoneNum;
	}



	public String getShopRealName() {
		return shopRealName;
	}



	public void setShopRealName(String shopRealName) {
		this.shopRealName = shopRealName;
	}
	
}
