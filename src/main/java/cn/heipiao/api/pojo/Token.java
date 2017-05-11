/**
 * 
 */
package cn.heipiao.api.pojo;

import java.io.Serializable;

/**
 * @author wzw
 * @date 2016年7月11日
 * @version 1.0
 */
public class Token implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String name;
	
	private Long expiration;
	
	private String accessKeyId;
	
	private String accessKeySecret;
	
	private String securityToken;

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the expiration
	 */
	public Long getExpiration() {
		return expiration;
	}

	/**
	 * @param expiration the expiration to set
	 */
	public void setExpiration(Long expiration) {
		this.expiration = expiration;
	}

	/**
	 * @return the accessKeyId
	 */
	public String getAccessKeyId() {
		return accessKeyId;
	}

	/**
	 * @param accessKeyId the accessKeyId to set
	 */
	public void setAccessKeyId(String accessKeyId) {
		this.accessKeyId = accessKeyId;
	}

	/**
	 * @return the accessKeySecret
	 */
	public String getAccessKeySecret() {
		return accessKeySecret;
	}

	/**
	 * @param accessKeySecret the accessKeySecret to set
	 */
	public void setAccessKeySecret(String accessKeySecret) {
		this.accessKeySecret = accessKeySecret;
	}

	/**
	 * @return the securityToken
	 */
	public String getSecurityToken() {
		return securityToken;
	}

	/**
	 * @param securityToken the securityToken to set
	 */
	public void setSecurityToken(String securityToken) {
		this.securityToken = securityToken;
	}
	
	
}
