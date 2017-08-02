/**
 * 
 */
package cn.heipiao.api.mapper;

import cn.heipiao.api.pojo.PlatformAccountConstraint;

/**
 * @author wzw
 * @date 2016年8月9日
 * @version 1.0
 */
public interface PlatformAccountConstraintMapper {

	
	PlatformAccountConstraint selectUniqueAccount(Integer platform);
	
}
