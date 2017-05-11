package cn.heipiao.api.pojo;

import java.io.Serializable;
import java.util.Date;

/**
 * @author zf
 * @version 1.0
 * @description 券
 * @date 2016年7月19日
 */
public class Coupon implements Serializable {

	private static final long serialVersionUID = -7152344409778485122L;
	/** 券ID */
	private Integer id;
	/** 券类型0表示现金券，1表示折扣券，2表示组合券 */
	private Integer type;
	/** 券标题(券名称) */
	private String name;
	/** 使用规则对应的id或是编号1表示满100可用，2表示满200可用等 */
	private Integer useRule;
	/** 券描述（规则描述等） */
	private String description;
	/** 面值 */
	private Double money;
	/** 有效期方式一 */
	private Integer indate;
	/** 有效期方式二 */
	private Date indateTime;
	/** 发放数量 */
	private Integer sendNum;
	/** 发放日期时间 */
	private Date sendTime;
	/**
	 * 领取方式,0表示全体用户 1表示新注册用户， 2表示30天内有消费的会员， 3表示未消费过的会员， 4表示30天内未消费的会员
	 */
	private Integer receiveWay;
	/** 使用规则，订单满多少可以使用 */
	@Deprecated
//	private Double useCondition;
	/** 发放状态，0表示待发放，1表示发放中，2表示发放完成 */
	private Integer status;
	/** 是否可以发放标识，1表示可以发放，0表示不能发放 */
	private Integer flag;
	/** 钓场id */
	private Integer fishSiteId;
	/** 生成时间 */
	private Date createTime;
	/** 钓场名称 */
	private String fishSiteName;
	/** 联系电话 */
	private String phoneNum;

	public Integer getUseRule() {
		return useRule;
	}

	public Integer getId() {
		return id;
	}

	public Integer getType() {
		return type;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public Double getMoney() {
		return money;
	}

	public Integer getIndate() {
		return indate;
	}

	public Date getIndateTime() {
		return indateTime;
	}

	public Integer getSendNum() {
		return sendNum;
	}

	public Date getSendTime() {
		return sendTime;
	}

	public Integer getReceiveWay() {
		return receiveWay;
	}

//	public Double getUseCondition() {
//		return useCondition;
//	}

	public Integer getStatus() {
		return status;
	}

	public Integer getFlag() {
		return flag;
	}

	public Integer getFishSiteId() {
		return fishSiteId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public String getFishSiteName() {
		return fishSiteName;
	}

	public String getPhoneNum() {
		return phoneNum;
	}

	public void setUseRule(Integer useRule) {
		this.useRule = useRule;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setMoney(Double money) {
		this.money = money;
	}

	public void setIndate(Integer indate) {
		this.indate = indate;
	}

	public void setIndateTime(Date indateTime) {
		this.indateTime = indateTime;
	}

	public void setSendNum(Integer sendNum) {
		this.sendNum = sendNum;
	}

	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}

	public void setReceiveWay(Integer receiveWay) {
		this.receiveWay = receiveWay;
	}

//	public void setUseCondition(Double useCondition) {
//		this.useCondition = useCondition;
//	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public void setFlag(Integer flag) {
		this.flag = flag;
	}

	public void setFishSiteId(Integer fishSiteId) {
		this.fishSiteId = fishSiteId;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public void setFishSiteName(String fishSiteName) {
		this.fishSiteName = fishSiteName;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}

	@Override
	public String toString() {
		String ts = "id:" + this.id + " name:" + this.name + " type:"
				+ this.type + " description:" + this.description + " money:"
				+ this.money + " indate:" + this.indate + " indateTime:"
				+ this.indateTime + " sendNum:" + this.sendNum + " sendTime:"
				+ this.sendTime + " receiveWay:" + this.receiveWay
				+ " useCondition:" + " status:"
				+ this.status + " flag:" + this.flag + " fishSiteId:"
				+ this.fishSiteId + " createTime:" + this.createTime + " "
				+ super.toString();
		return ts;
	}
}
