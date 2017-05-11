package cn.heipiao.api.pojo;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * @author zf
 * @version 1.0
 * @description 数据字典
 * @date 2016年6月7日
 */
@XmlRootElement
public class DictConfig implements Serializable{
	
	/** 有效状态 **/
	public static final String VALID = "1";
	
	/** 无效状态**/
	public static final String INVALID = "0";
	
	private static final long serialVersionUID = 1203070061610563969L;
	private Integer id;// ID
	private String type;// 数据类别
	private String code;// 数据编码
	private String value;// 数据名称/值
	private Integer sortNo;// 顺序
	private String remarks;// 数据类别说明
	private String valid;// 字典数据有效状态:0无效;1:有效
	private String logo;// 图片存储-base64格式

	@XmlElement
	public Integer getId() {
		return id;
	}

	@XmlElement
	public String getType() {
		return type;
	}

	@XmlElement
	public String getCode() {
		return code;
	}

	@XmlElement
	public String getValue() {
		return value;
	}

	@XmlElement
	public Integer getSortNo() {
		return sortNo;
	}

	@XmlElement
	public String getRemarks() {
		return remarks;
	}

	@XmlElement
	public String getValid() {
		return valid;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public void setSortNo(Integer sortNo) {
		this.sortNo = sortNo;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public void setValid(String valid) {
		this.valid = valid;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}
	

	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
