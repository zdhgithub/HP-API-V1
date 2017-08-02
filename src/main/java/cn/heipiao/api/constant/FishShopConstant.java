package cn.heipiao.api.constant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * 说明 :
 * 		渔具店常量类
 * 功能 : 
 *      a.	
 * 使用规范 :
 * 		a.
 * @author asdf3070@163.com
 * @since 2016-10-14
 */
public final class FishShopConstant {

	/**
	 * 常量集合
	 */
	private static Map<String,List<Integer>> maps = new HashMap<String, List<Integer>>();

	
	/**
	 * f_fish_shop_auth_status 渔具店实名认证状态，0-漂友认证 1-实名认证 2-签约认证
	 */
	public static final String FISH_SHOP_AUTH_STATUS = "f_fish_shop_auth_status";
	
	
	/** 0-漂友认证 **/
	public static final int AUTH_USER_PASS = 0;
	
	/** 1-实名认证 **/
	public static final int AUTH_NAME_PASS = 1;
	
	/** 2-签约认证 **/
	public static final int AUTH_PACT_PASS = 2;
	
	/**
	 * f_fish_shop_type 渔具店类型，0-平台添加 1-漂友添加
	 */
	public static final String FISH_SHOP_TYPE = "f_fish_shop_type";

	/** 0-平台添加**/
	public static final int ADD_PLAT = 0;
	
	/** 1-漂友添加 **/
	public static final int ADD_USER = 1;
	
	/**
	 * t_fish_shop_user_status.f_type 处理类型 0-点赞 1-收藏
	 */
	public static final String FISH_SHOP_USER_STATUS_TYPE = "t_fish_shop_user_status.f_type";

	/** 0-点赞**/
	public static final int USER_STATUS_PRAISE = 0;
	
	/** 1-收藏 **/
	public static final int USER_STATUS_COLLECT = 1;
	
	/**
	 * f_fish_shop_flag (cp)星标 0-未标 1-已标
	 */
	public static final String FISH_SHOP_FLAG = "f_fish_shop_flag";

	/** 0-未标**/
	public static final int FLAG_OF = 0;
	
	/** 1-已标**/
	public static final int FLAG_ON = 1;
	
	/**
	 * f_fish_shop_status 上线状态 0-待审核 1-正常 2-审核未通过 3-下架 4-黑名单
	 */
	public static final String FISH_SHOP_STATUS = "f_fish_shop_status";

	/** 0-待审核**/
	public static final int STATUS_0 = 0;
	
	/** 1-正常**/
	public static final int STATUS_1 = 1;
	
	/** 1-审核未通过**/
	public static final int STATUS_2 = 2;
	
	/** 1-下架**/
	public static final int STATUS_3 = 3;
	
	/** 1-黑名单**/
	public static final int STATUS_4 = 4;
	
	/**
	 * 此店铺签约状态(0可认领，1已认领，2已签约，3售票认领,4已售票认领)<br/>
	 * 0可认领
	 */
	public static final int SIGN_CAN = 0;
	/**
	 * 此店铺签约状态(0可认领，1已认领，2已签约，3售票认领,4已售票认领)<br/>
	 * 1已认领
	 */
	public static final int SIGN_ALLREADY = 1;
	/**
	 * 此店铺签约状态(0可认领，1已认领，2已签约，3售票认领，4已售票认领)<br/>
	 * 2已签约
	 */
	public static final int AUTH_ALLREADY = 2;
	/**
	 * 此店铺签约状态(0可认领，1已认领，2已签约，3售票认领,4已售票认领)<br/>
	 * 3售票认领
	 */
	public static final int TICKET_ALLREADY = 3;
	/**
	 * 此店铺签约状态(0可认领，1已认领，2已签约，3售票认领,4已售票认领)<br/>
	 * 4已售票认领
	 */
	public static final int TICKET_ALLREADYED = 4;
	
	static {
		List<Integer> ffsas = new ArrayList<Integer>();
		ffsas.add(AUTH_USER_PASS);
		ffsas.add(AUTH_NAME_PASS);
		ffsas.add(AUTH_PACT_PASS);
		maps.put(FISH_SHOP_AUTH_STATUS, ffsas);

		List<Integer> ffst = new ArrayList<Integer>();
		ffst.add(ADD_PLAT);
		ffst.add(ADD_USER);
		maps.put(FISH_SHOP_TYPE, ffst);

		List<Integer> tfsusf = new ArrayList<Integer>();
		tfsusf.add(USER_STATUS_PRAISE);
		tfsusf.add(USER_STATUS_COLLECT);
		maps.put(FISH_SHOP_USER_STATUS_TYPE, tfsusf);

		List<Integer> ffsf = new ArrayList<Integer>();
		ffsf.add(FLAG_ON);
		ffsf.add(FLAG_OF);
		maps.put(FISH_SHOP_FLAG, ffsf);

		List<Integer> ffss = new ArrayList<Integer>();
		ffss.add(STATUS_0);
		ffss.add(STATUS_1);
		ffss.add(STATUS_2);
		ffss.add(STATUS_3);
		ffss.add(STATUS_4);
		maps.put(FISH_SHOP_STATUS, ffss);
	}
	
	public static  List<Integer> getMaps(String key){
		return maps.get(key);
	}
	
}
