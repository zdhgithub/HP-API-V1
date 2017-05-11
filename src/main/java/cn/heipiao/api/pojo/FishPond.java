package cn.heipiao.api.pojo;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 
 * @author wzw
 * @date 2016年6月1日
 * @version 1.0
 */
public class FishPond implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3355639691469940014L;

	/**
	 * 鱼塘id
	 */
	private Integer fishPondId;

	/**
	 * 钓场id
	 */
	private Integer fishSiteId;

	/**
	 * 鱼塘名称
	 */
	private String fishPondName;

	/**
	 * 水域面积
	 */
	private Float area;

	/**
	 * 水域面积的单位（平米，亩）
	 */
	private Integer areaUnit;

	/**
	 * 水深（是区间数比如3-4米）
	 */
	private String depth;

	/**
	 * 钓位数 鱼塘的最大钓位
	 */
	private Integer capacity;

	/**
	 * 主鱼种
	 */
	private String mainFishType;

	/**
	 * 次鱼种
	 */
	private String viceFishType;

	/**
	 * 每个钓位的最大鱼竿数
	 */
	private Integer fishRodLimit;

	/**
	 * 鱼塘备注
	 */
	private String pondRemarks;

	/**
	 * 收费类型 0，钟塘,1斤塘
	 */
	private Integer payType;

	/**
	 * 入场券金额
	 */
	private Integer admissionTicket;

	/**
	 * 单位价格 按近收费和按小时收费
	 * 
	 */
	private Float unitPrice;

	/**
	 * 限带鱼量
	 */
	private Float getFishLimit;

	/**
	 * 鱼塘创建时间
	 */
	private Timestamp createTime;
	
	/**
	 * 鱼塘状态  0:删除,1:正常
	 */
	private Integer status;
	

	/**
	 * @return the fishPondId
	 */
	public Integer getFishPondId() {
		return fishPondId;
	}

	/**
	 * @param fishPondId
	 *            the fishPondId to set
	 */
	public void setFishPondId(Integer fishPondId) {
		this.fishPondId = fishPondId;
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
	 * @return the fishPondName
	 */
	public String getFishPondName() {
		return fishPondName;
	}

	/**
	 * @param fishPondName
	 *            the fishPondName to set
	 */
	public void setFishPondName(String fishPondName) {
		this.fishPondName = fishPondName;
	}

	/**
	 * @return the area
	 */
	public Float getArea() {
		return area;
	}

	/**
	 * @param area
	 *            the area to set
	 */
	public void setArea(Float area) {
		this.area = area;
	}

	/**
	 * @return the areaUnit
	 */
	public Integer getAreaUnit() {
		return areaUnit;
	}

	/**
	 * @param areaUnit
	 *            the areaUnit to set
	 */
	public void setAreaUnit(Integer areaUnit) {
		this.areaUnit = areaUnit;
	}

	/**
	 * @return the depth
	 */
	public String getDepth() {
		return depth;
	}

	/**
	 * @param depth
	 *            the depth to set
	 */
	public void setDepth(String depth) {
		this.depth = depth;
	}

	/**
	 * @return the capacity
	 */
	public Integer getCapacity() {
		return capacity;
	}

	/**
	 * @param capacity
	 *            the capacity to set
	 */
	public void setCapacity(Integer capacity) {
		this.capacity = capacity;
	}

	/**
	 * @return the mainFishType
	 */
	public String getMainFishType() {
		return mainFishType;
	}

	/**
	 * @param mainFishType
	 *            the mainFishType to set
	 */
	public void setMainFishType(String mainFishType) {
		this.mainFishType = mainFishType;
	}

	/**
	 * @return the viceFishType
	 */
	public String getViceFishType() {
		return viceFishType;
	}

	/**
	 * @param viceFishType
	 *            the viceFishType to set
	 */
	public void setViceFishType(String viceFishType) {
		this.viceFishType = viceFishType;
	}

	/**
	 * @return the fishRodLimit
	 */
	public Integer getFishRodLimit() {
		return fishRodLimit;
	}

	/**
	 * @param fishRodLimit
	 *            the fishRodLimit to set
	 */
	public void setFishRodLimit(Integer fishRodLimit) {
		this.fishRodLimit = fishRodLimit;
	}

	/**
	 * @return the pondRemarks
	 */
	public String getPondRemarks() {
		return pondRemarks;
	}

	/**
	 * @param pondRemarks
	 *            the pondRemarks to set
	 */
	public void setPondRemarks(String pondRemarks) {
		this.pondRemarks = pondRemarks;
	}

	/**
	 * @return the payType
	 */
	public Integer getPayType() {
		return payType;
	}

	/**
	 * @param payType
	 *            the payType to set
	 */
	public void setPayType(Integer payType) {
		this.payType = payType;
	}

	/**
	 * @return the admissionTicket
	 */
	public Integer getAdmissionTicket() {
		return admissionTicket;
	}

	/**
	 * @param admissionTicket
	 *            the admissionTicket to set
	 */
	public void setAdmissionTicket(Integer admissionTicket) {
		this.admissionTicket = admissionTicket;
	}

	/**
	 * @return the unitPrice
	 */
	public Float getUnitPrice() {
		return unitPrice;
	}

	/**
	 * @param unitPrice
	 *            the unitPrice to set
	 */
	public void setUnitPrice(Float unitPrice) {
		this.unitPrice = unitPrice;
	}

	/**
	 * @return the getFishLimit
	 */
	public Float getGetFishLimit() {
		return getFishLimit;
	}

	/**
	 * @param getFishLimit
	 *            the getFishLimit to set
	 */
	public void setGetFishLimit(Float getFishLimit) {
		this.getFishLimit = getFishLimit;
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
	 * @return the status
	 */
	public Integer getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}
	
}