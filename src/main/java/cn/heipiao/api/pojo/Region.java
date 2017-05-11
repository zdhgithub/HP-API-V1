/**
 * 
 */
package cn.heipiao.api.pojo;

import java.io.Serializable;
import java.util.List;

/**
 * @author wzw
 * @date 2016年6月22日
 * @version 1.0
 */
public class Region implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer id;

	private Integer regionNum;

	private String regionName;

	private Integer pid;

	private String Initial;
	/**
	 * 开放标识
	 */
	private Integer openFlag;
	
	/**
	 * 招募合伙人标识
	 */
	private Integer regionRecruit;
	
	/**
	 * 钓场统计
	 */
	private Integer count;
	
	private List<Region> subcollection;

	public List<Region> getSubcollection() {
		return subcollection;
	}

	public void setSubcollection(List<Region> subcollection) {
		this.subcollection = subcollection;
	}

	public Integer getOpenFlag() {
		return openFlag;
	}

	public void setOpenFlag(Integer openFlag) {
		this.openFlag = openFlag;
	}

	public Integer getRegionRecruit() {
		return regionRecruit;
	}

	public void setRegionRecruit(Integer regionRecruit) {
		this.regionRecruit = regionRecruit;
	}

	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * @return the regionNum
	 */
	public Integer getRegionNum() {
		return regionNum;
	}

	/**
	 * @param regionNum
	 *            the regionNum to set
	 */
	public void setRegionNum(Integer regionNum) {
		this.regionNum = regionNum;
	}

	/**
	 * @return the regionName
	 */
	public String getRegionName() {
		return regionName;
	}

	/**
	 * @param regionName
	 *            the regionName to set
	 */
	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}

	/**
	 * @return the pid
	 */
	public Integer getPid() {
		return pid;
	}

	/**
	 * @param pid
	 *            the pid to set
	 */
	public void setPid(Integer pid) {
		this.pid = pid;
	}

	/**
	 * @return the count
	 */
	public Integer getCount() {
		return count;
	}

	/**
	 * @param count
	 *            the count to set
	 */
	public void setCount(Integer count) {
		this.count = count;
	}

	/**
	 * @return the initial
	 */
	public String getInitial() {
		return Initial;
	}

	/**
	 * @param initial
	 *            the initial to set
	 */
	public void setInitial(String initial) {
		Initial = initial;
	}

}
