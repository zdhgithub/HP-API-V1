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
public class MenuRegion implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String province;

	private Integer rid;

	private int count;

	private List<City> citys;

	/**
	 * @return the rid
	 */
	public Integer getRid() {
		return rid;
	}

	/**
	 * @param rid
	 *            the rid to set
	 */
	public void setRid(Integer rid) {
		this.rid = rid;
	}

	/**
	 * @return the province
	 */
	public String getProvince() {
		return province;
	}

	/**
	 * @param province
	 *            the province to set
	 */
	public void setProvince(String province) {
		this.province = province;
	}

	/**
	 * @return the count
	 */
	public int getCount() {
		return count;
	}

	/**
	 * @param count
	 *            the count to set
	 */
	public void setCount(int count) {
		this.count = count;
	}

	/**
	 * @return the citys
	 */
	public List<City> getCitys() {
		return citys;
	}

	/**
	 * @param citys
	 *            the citys to set
	 */
	public void setCitys(List<City> citys) {
		this.citys = citys;
	}

	public static class City implements Serializable{

		/**
		 * 
		 */
		private static final long serialVersionUID = -694412138594186497L;

		private String city;

		private int count;

		private Integer rid;

		/**
		 * @return the rid
		 */
		public Integer getRid() {
			return rid;
		}

		/**
		 * @param rid
		 *            the rid to set
		 */
		public void setRid(Integer rid) {
			this.rid = rid;
		}

		/**
		 * @return the city
		 */
		public String getCity() {
			return city;
		}

		/**
		 * @param city
		 *            the city to set
		 */
		public void setCity(String city) {
			this.city = city;
		}

		/**
		 * @return the count
		 */
		public int getCount() {
			return count;
		}

		/**
		 * @param count
		 *            the count to set
		 */
		public void setCount(int count) {
			this.count = count;
		}

	}

}
