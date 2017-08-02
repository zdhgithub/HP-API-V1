package cn.heipiao.api.pojo;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * 
 * @author wzw
 * @date 2016年6月1日
 * @version 1.0
 */
public class FishSite implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4129574360406243202L;

	/**
	 * 钓场id
	 * 
	 * 
	 */
	private Integer fishSiteId;

	/**
	 * 合伙人id
	 */
	private Integer partnerId;

	/**
	 * 钓场所属用户的uid
	 */
	private Long uid;

	/**
	 * 实名时间-对应f_uid
	 */
	private Date namedTime;
	
	
	/**
	 * 钓场名称
	 */
	private String fishSiteName;

	/**
	 * 是否收费
	 */
	private Integer isFree;

	/**
	 * 钓场类型
	 */
	private String fishSiteType;

	/**
	 * 钓场信息
	 */
	private String fishSiteInfo;

	/**
	 * 资源链接 图片+视频最多9个
	 * 
	 */
	private String resources;

	/**
	 * 区域id
	 */
	private Integer regionId;

	/**
	 * 市id
	 */
	private Integer cityId;

	/**
	 * 省id
	 */
	private Integer provinceId;

	/**
	 * 详细地址
	 */
	private String addr;

	/**
	 * 联系人
	 */
	private String contactMan;

	/**
	 * 联系电话
	 */
	private String phoneNum;

	/**
	 * K码(凯立德汽车导航)
	 */
	private String kNum;

	/**
	 * 是否可购票
	 */
	private Integer isBuyTicket;

	/**
	 * 认证类型
	 */
	private Integer authType;

	/**
	 * 平台备注-运营专用
	 */
	private String sysRemarks;

	/**
	 * 钓场等级
	 */
	private Float grade;

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
	 * 钓场状态 4 黑名单 , 2 审核未通过 , 3 下架 , 0 待审核 , 1 正常
	 */
	private Integer status;

	/**
	 * 星标-运维专用
	 */
	private Integer flag;

	/**
	 * 钓场创建时间
	 */
	private Timestamp createTime;

	/**
	 * 钓场信息更新时间
	 */
	private Timestamp updateTime;

	/**
	 * 经度
	 */
	private Double longitude;

	/**
	 * 纬度
	 */
	private Double latitude;

	/**
	 * 钓场放鱼更新时间
	 */
	private Timestamp updatePutFishTime;

	/**
	 * 钟塘，斤塘标签
	 */
	private String label;

	/**
	 * 钓场设施
	 */
	private String facility;

	/**
	 * 关注数量
	 */
	private Integer focusCount;

	/**
	 * 费用描述 10 或者 10-90
	 */
	private String chargeDesc;

	/**
	 * 回鱼描述
	 */
	private String backFishDesc;

	/**
	 * 提现费率
	 */
	private Double withdrawRate;

	/** 签约状态，0表示可认领，1表示已认领，2表示已签约 ,3售票认领 ,4 表示已售票认领*/
	private Integer signStatus;
	/** 售票认领奖金*/
	private Integer transactionSum;
	/** 认领的时间 或签约的时间，可认领置空 */
	private Date signTime;
	
	/**
	 * 钓场分类A-大,B-中,C-小三类
	 */
	private String category;

	// 用户关注钓场标识
	private int focus;

	//
	private Integer duration;

	// 添加池塘的实体类引用
	private List<FishPond> fishPonds;

	/**
	 * 显示置顶 默认0
	 */
	private Integer top;

	/**
	 * 是否设置了钓场LOGO 0-未设置 1-已设置
	 */
	private Integer logo;

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
	 * 配送状态 0表示不支持配送 1表示支持配送
	 */
	private int distributionStatus;
	/**钓场用户名*/
	private String siteUserName;
	/**钓场用户昵称*/
	private String siteNickName;
	/**钓场用户电话号码*/
	private String sitePhonenum;
	/**钓场用户真实姓名*/
	private String siteRealName;
	/** 钓场合伙人真实姓名*/
	private String partnerRealName;
	/**钓场合伙人昵称*/
	private String partnerNickName;
	/**钓场合伙人电话*/
	private String partnerPhone;
	/**钓场合伙人用户名*/
	private String partnerUserName;
	/**钓场认领状态*/
	private Integer isApply;
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

	/**
	 * @return the partnerId
	 */
	public Integer getPartnerId() {
		return partnerId;
	}

	/**
	 * @param partnerId
	 *            the partnerId to set
	 */
	public void setPartnerId(Integer partnerId) {
		this.partnerId = partnerId;
	}

	/**
	 * @return the uid
	 */
	public Long getUid() {
		return uid;
	}

	/**
	 * @param uid
	 *            the uid to set
	 */
	public void setUid(Long uid) {
		this.uid = uid;
	}

	public Date getNamedTime() {
		return namedTime;
	}

	public void setNamedTime(Date namedTime) {
		this.namedTime = namedTime;
	}

	/**
	 * @return the label
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * @param label
	 *            the label to set
	 */
	public void setLabel(String label) {
		this.label = label;
	}

	/**
	 * @return the fishSiteName
	 */
	public String getFishSiteName() {
		return fishSiteName;
	}

	/**
	 * @param fishSiteName
	 *            the fishSiteName to set
	 */
	public void setFishSiteName(String fishSiteName) {
		this.fishSiteName = fishSiteName;
	}

	/**
	 * @return the isFree
	 */
	public Integer getIsFree() {
		return isFree;
	}

	/**
	 * @param isFree
	 *            the isFree to set
	 */
	public void setIsFree(Integer isFree) {
		this.isFree = isFree;
	}

	/**
	 * @return the fishSiteType
	 */
	public String getFishSiteType() {
		return fishSiteType;
	}

	/**
	 * @param fishSiteType
	 *            the fishSiteType to set
	 */
	public void setFishSiteType(String fishSiteType) {
		this.fishSiteType = fishSiteType;
	}

	/**
	 * @return the fishSiteInfo
	 */
	public String getFishSiteInfo() {
		return fishSiteInfo;
	}

	/**
	 * @param fishSiteInfo
	 *            the fishSiteInfo to set
	 */
	public void setFishSiteInfo(String fishSiteInfo) {
		this.fishSiteInfo = fishSiteInfo;
	}

	/**
	 * @return the resources
	 */
	public String getResources() {
		return resources;
	}

	/**
	 * @param resources
	 *            the resources to set
	 */
	public void setResources(String resources) {
		this.resources = resources;
	}

	/**
	 * @return the regionId
	 */
	public Integer getRegionId() {
		return regionId;
	}

	/**
	 * @param regionId
	 *            the regionId to set
	 */
	public void setRegionId(Integer regionId) {
		this.regionId = regionId;
	}

	/**
	 * @return the cityId
	 */
	public Integer getCityId() {
		return cityId;
	}

	/**
	 * @param cityId
	 *            the cityId to set
	 */
	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}

	/**
	 * @return the provinceId
	 */
	public Integer getProvinceId() {
		return provinceId;
	}

	/**
	 * @param provinceId
	 *            the provinceId to set
	 */
	public void setProvinceId(Integer provinceId) {
		this.provinceId = provinceId;
	}

	/**
	 * @return the addr
	 */
	public String getAddr() {
		return addr;
	}

	/**
	 * @param addr
	 *            the addr to set
	 */
	public void setAddr(String addr) {
		this.addr = addr;
	}

	/**
	 * @return the contactMan
	 */
	public String getContactMan() {
		return contactMan;
	}

	/**
	 * @param contactMan
	 *            the contactMan to set
	 */
	public void setContactMan(String contactMan) {
		this.contactMan = contactMan;
	}

	/**
	 * @return the phoneNum
	 */
	public String getPhoneNum() {
		return phoneNum;
	}

	/**
	 * @param phoneNum
	 *            the phoneNum to set
	 */
	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}

	/**
	 * @return the kNum
	 */
	public String getkNum() {
		return kNum;
	}

	/**
	 * @param kNum
	 *            the kNum to set
	 */
	public void setkNum(String kNum) {
		this.kNum = kNum;
	}

	/**
	 * @return the isBuyTicket
	 */
	public Integer getIsBuyTicket() {
		return isBuyTicket;
	}

	/**
	 * @param isBuyTicket
	 *            the isBuyTicket to set
	 */
	public void setIsBuyTicket(Integer isBuyTicket) {
		this.isBuyTicket = isBuyTicket;
	}

	/**
	 * @return the authType
	 */
	public Integer getAuthType() {
		return authType;
	}

	/**
	 * @param authType
	 *            the authType to set
	 */
	public void setAuthType(Integer authType) {
		this.authType = authType;
	}

	/**
	 * @return the sysRemarks
	 */
	public String getSysRemarks() {
		return sysRemarks;
	}

	/**
	 * @param sysRemarks
	 *            the sysRemarks to set
	 */
	public void setSysRemarks(String sysRemarks) {
		this.sysRemarks = sysRemarks;
	}

	/**
	 * @return the grade
	 */
	public Float getGrade() {
		return grade;
	}

	/**
	 * @param grade
	 *            the grade to set
	 */
	public void setGrade(Float grade) {
		this.grade = grade;
	}

	/**
	 * @return the mainImg
	 */
	public String getMainImg() {
		return mainImg;
	}

	/**
	 * @param mainImg
	 *            the mainImg to set
	 */
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

	/**
	 * @return the status
	 */
	public Integer getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}

	/**
	 * @return the flag
	 */
	public Integer getFlag() {
		return flag;
	}

	/**
	 * @param flag
	 *            the flag to set
	 */
	public void setFlag(Integer flag) {
		this.flag = flag;
	}

	/**
	 * @return the createTime
	 */
	public Timestamp getCreateTime() {
		return createTime;
	}

	/**
	 * @param createTime
	 *            the createTime to set
	 */
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	/**
	 * @return the updateTime
	 */
	public Timestamp getUpdateTime() {
		return updateTime;
	}

	/**
	 * @param updateTime
	 *            the updateTime to set
	 */
	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

	/**
	 * @return the longitude
	 */
	public Double getLongitude() {
		return longitude;
	}

	/**
	 * @param longitude
	 *            the longitude to set
	 */
	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	/**
	 * @return the latitude
	 */
	public Double getLatitude() {
		return latitude;
	}

	/**
	 * @param latitude
	 *            the latitude to set
	 */
	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	/**
	 * @return the updatePutFishTime
	 */
	public Timestamp getUpdatePutFishTime() {
		return updatePutFishTime;
	}

	/**
	 * @param updatePutFishTime
	 *            the updatePutFishTime to set
	 */
	public void setUpdatePutFishTime(Timestamp updatePutFishTime) {
		this.updatePutFishTime = updatePutFishTime;
	}

	/**
	 * @return the fishPonds
	 */
	public List<FishPond> getFishPonds() {
		return fishPonds;
	}

	/**
	 * @param fishPonds
	 *            the fishPonds to set
	 */
	public void setFishPonds(List<FishPond> fishPonds) {
		this.fishPonds = fishPonds;
	}

	/**
	 * @return the focus
	 */
	public int getFocus() {
		return focus;
	}

	/**
	 * @param focus
	 *            the focus to set
	 */
	public void setFocus(int focus) {
		this.focus = focus;
	}

	/**
	 * @return the facility
	 */
	public String getFacility() {
		return facility;
	}

	/**
	 * @param facility
	 *            the facility to set
	 */
	public void setFacility(String facility) {
		this.facility = facility;
	}

	/**
	 * @return the focusCount
	 */
	public Integer getFocusCount() {
		return focusCount;
	}

	/**
	 * @param focusCount
	 *            the focusCount to set
	 */
	public void setFocusCount(Integer focusCount) {
		this.focusCount = focusCount;
	}

	/**
	 * @return the chargeDesc
	 */
	public String getChargeDesc() {
		return chargeDesc;
	}

	/**
	 * @param chargeDesc
	 *            the chargeDesc to set
	 */
	public void setChargeDesc(String chargeDesc) {
		this.chargeDesc = chargeDesc;
	}

	/**
	 * @return the withdrawRate
	 */
	public Double getWithdrawRate() {
		return withdrawRate;
	}

	/**
	 * @param withdrawRate
	 *            the withdrawRate to set
	 */
	public void setWithdrawRate(Double withdrawRate) {
		this.withdrawRate = withdrawRate;
	}

	/**
	 * @return the duration
	 */
	public Integer getDuration() {
		return duration;
	}

	/**
	 * @param duration
	 *            the duration to set
	 */
	public void setDuration(Integer duration) {
		this.duration = duration;
	}

	/**
	 * @return the backFishDesc
	 */
	public String getBackFishDesc() {
		return backFishDesc;
	}

	/**
	 * @param backFishDesc
	 *            the backFishDesc to set
	 */
	public void setBackFishDesc(String backFishDesc) {
		this.backFishDesc = backFishDesc;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public Integer getTop() {
		return top;
	}

	public void setTop(Integer top) {
		this.top = top;
	}

	public Integer getLogo() {
		return logo;
	}

	public void setLogo(Integer logo) {
		this.logo = logo;
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

	public Integer getTransactionSum() {
		return transactionSum;
	}

	public void setTransactionSum(Integer transactionSum) {
		this.transactionSum = transactionSum;
	}

	public String getSiteUserName() {
		return siteUserName;
	}

	public void setSiteUserName(String siteUserName) {
		this.siteUserName = siteUserName;
	}

	public String getSiteNickName() {
		return siteNickName;
	}

	public void setSiteNickName(String siteNickName) {
		this.siteNickName = siteNickName;
	}

	public String getSitePhonenum() {
		return sitePhonenum;
	}

	public void setSitePhonenum(String sitePhonenum) {
		this.sitePhonenum = sitePhonenum;
	}

	public String getSiteRealName() {
		return siteRealName;
	}

	public void setSiteRealName(String siteRealName) {
		this.siteRealName = siteRealName;
	}

	public String getPartnerRealName() {
		return partnerRealName;
	}

	public void setPartnerRealName(String partnerRealName) {
		this.partnerRealName = partnerRealName;
	}

	public String getPartnerNickName() {
		return partnerNickName;
	}

	public void setPartnerNickName(String partnerNickName) {
		this.partnerNickName = partnerNickName;
	}

	public String getPartnerPhone() {
		return partnerPhone;
	}

	public void setPartnerPhone(String partnerPhone) {
		this.partnerPhone = partnerPhone;
	}

	public String getPartnerUserName() {
		return partnerUserName;
	}

	public void setPartnerUserName(String partnerUserName) {
		this.partnerUserName = partnerUserName;
	}

	public Integer getIsApply() {
		return isApply;
	}

	public void setIsApply(Integer isApply) {
		this.isApply = isApply;
	}
	
	
}