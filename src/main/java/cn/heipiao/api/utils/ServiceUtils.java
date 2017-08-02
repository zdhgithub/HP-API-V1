package cn.heipiao.api.utils;

public class ServiceUtils {

	private static final String regex_phone_simple = "^1[3|4|5|7|8]\\d{9}$";
	
	
	/**
	 * 验证手机号
	 * 
	 * @param phone
	 * @return
	 */
	public static boolean verifyPhone(String phone) {
		return phone.matches(regex_phone_simple);
	}


	/**
	 * 判断商家ID是钓场还是店铺
	 * @param business
	 * @return true-钓场ID false-店铺ID 
	 */
	public static boolean isFishSiteId(Long business) {
		if(business <= 999999999){
			return true;
		}
		return false;
	}
	
}
