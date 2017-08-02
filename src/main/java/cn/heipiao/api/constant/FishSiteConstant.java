package cn.heipiao.api.constant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * 说明 :
 * 		钓场常量类
 * 功能 : 
 *      a.	
 * 使用规范 :
 * 		a.
 * @author chenwenye
 * @since 2016-6-2 heipiao1.0
 */
public final class FishSiteConstant {

	/**
	 * 常量集合
	 */
	private static Map<String,List<Integer>> maps = new HashMap<String, List<Integer>>();

//	=======================fish_status====================
	/**
	 * fish_status 钓场状态 常量
	 */
	public static final String FISH_STATUS_STR = "fish_status";
	
	
	/** 上线状态 - 黑名单 **/
	public static final int ONLINE_BLACKLIST = 4;
	
	/** 上线状态 - 审核未通过**/
	public static final int ONLINE_NO_PASS = 2;
	
	/** 上线状态 - 下架 **/
	public static final int OFF_LINE = 3;
	
	/** 上线状态 - 待审核 **/
	public static final int ONLINE_CHECK = 0;
	
	/** 上线状态 - 正常 **/
	public static final int ONLINE_NORMAL = 1;
	
//	==========================0,1====================
	public static final String zero_one = "zero_one";
	
	public static final int ZERO = 0;
	
	public static final int ONE = 1;
	
//	============================fish_auth_type===================
	/**
	 * 钓场认证类型常量
	 */
	public static final String FISH_AUTH_TYPE_STR = "fish_auth_type";
	
	/**
	 * 未认证 
	 */
	public static final Integer  UNAUTHERIZED = 0;
	
	/**
	 * 平台认证
	 */
	public static final Integer PLATFORM_AUTHERIZED = 1;
	
	/**
	 * 钓场主认证
	 */
	public static final Integer FISH_SITE = 2;
	
	/**
	 * 钓友认证
	 */
	public static final Integer FISHER_FRIEND = 3;


	
	static {
		List<Integer> fsl = new ArrayList<Integer>();
		fsl.add(ONLINE_BLACKLIST);
		fsl.add(ONLINE_NO_PASS);
		fsl.add(OFF_LINE);
		fsl.add(ONLINE_CHECK);
		fsl.add(ONLINE_NORMAL);
		maps.put(FISH_STATUS_STR, fsl);
		
		List<Integer> fats = new ArrayList<Integer>();
		fats.add(UNAUTHERIZED);
		fats.add(PLATFORM_AUTHERIZED);
		fats.add(FISH_SITE);
		fats.add(FISHER_FRIEND);
		maps.put(FISH_AUTH_TYPE_STR, fats);
		
		List<Integer> zo = new ArrayList<Integer>();
		zo.add(ZERO);
		zo.add(ONE);
		maps.put(zero_one, zo);
		
	}
	
	public static  List<Integer> getMaps(String key){
		return maps.get(key);
	}
	
}
