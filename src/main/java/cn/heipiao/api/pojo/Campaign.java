package cn.heipiao.api.pojo;

import java.util.Date;

/**
 * 活动
 * @author Chris
 * @version 3.0
 * @date 2017-03-03
 *
 */
public class Campaign {
	
	/**
	 * 自增长主键
	 */
	private Integer id;
	
	/**
	 * 活动名称
	 */
	private String name;
	
	/**
	 * 活动分类
	 */
	private Integer type;
	
	/**
	 * 活动分类名称
	 */
	private String typeName;
	
	/**
	 * 限额
	 */
	private Integer quota;
	
	/**
	 * 已报名人数
	 */
	private Integer count;
	
	/**
	 * 开始时间
	 */
	private Date beginTime;
	
	/**
	 * 结束时间
	 */
	private Date endTime;
	
	/**
	 * 报名截止时间
	 */
	private Date entryTerminalTime;
	
	/**
	 * 海报
	 */
	private String bill;
	
	/**
	 * 背景音乐
	 */
	private String background;
	
	/**
	 * 省（id）
	 */
	private Integer province;
	
	/**
	 * 省（名称）
	 */
	private String provinceName;
	
	/**
	 * 市（id）
	 */
	private Integer city;
	
	/**
	 * 市（名称）
	 */
	private String cityName;
	
	/**
	 * 地址
	 */
	private String addr;
	
	/**
	 * 活动须知
	 */
	private String note;
	
	/**
	 * 活动详情
	 */
	private String detail;
	
	/**
	 * 服务电话
	 */
	private String call;
	
	/**
	 * 活动费用
	 */
	private Float cost;
	
	/**
	 * 活动创建时间
	 */
	private Date createTime;
	
	/**
	 * 活动费用说明
	 */
	private String costExplain;

	/**
	 * 活动状态：0未发布，1已发布（报名中），2待抽签（报名结束），3已抽签（比赛中），4已结束，5暂停，6已取消
	 */
	private Integer status;
	
	/**
	 * 视频
	 */
	private String video;
	/**
	 * 活动类型  1竞技活动 ，2营销活动
	 */
	private Integer style;
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public Integer getQuota() {
		return quota;
	}

	public void setQuota(Integer quota) {
		this.quota = quota;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public Date getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Date getEntryTerminalTime() {
		return entryTerminalTime;
	}

	public void setEntryTerminalTime(Date entryTerminalTime) {
		this.entryTerminalTime = entryTerminalTime;
	}

	public String getBill() {
		return bill;
	}

	public void setBill(String bill) {
		this.bill = bill;
	}

	public String getBackground() {
		return background;
	}

	public void setBackground(String background) {
		this.background = background;
	}

	public Integer getProvince() {
		return province;
	}

	public void setProvince(Integer province) {
		this.province = province;
	}

	public String getProvinceName() {
		return provinceName;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}

	public Integer getCity() {
		return city;
	}

	public void setCity(Integer city) {
		this.city = city;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public String getCall() {
		return call;
	}

	public void setCall(String call) {
		this.call = call;
	}

	public Float getCost() {
		return cost;
	}

	public void setCost(Float cost) {
		this.cost = cost;
	}

	public String getCostExplain() {
		return costExplain;
	}

	public void setCostExplain(String costExplain) {
		this.costExplain = costExplain;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getVideo() {
		return video;
	}

	public void setVideo(String video) {
		this.video = video;
	}

	public Integer getStyle() {
		return style;
	}

	public void setStyle(Integer style) {
		this.style = style;
	}

	
}
