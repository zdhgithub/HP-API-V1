package cn.heipiao.api.utils;

import java.util.Random;

import org.apache.commons.lang3.StringUtils;

/**
 * 功能 : 生成随机数
 * @author chenwenye
 * @since 2016-6-25  heipiao1.0
 * TODO 使用commons第3方包实现
 */
public final class RandomNumberUtils {
	
	/** 验证码随机数  **/
	private static final byte [] verifyNum = { 1 , 2 , 3 , 5 , 6 , 7 , 8 , 9 , 0 };
	
	/**
	 * 随机数实例
	 */
	private static final Random r = new Random();
	/**
	 * @param length 随机数字码长度
	 * @return 重数字随机数
	 */
	public static String getVerifyNum(int length){
		
		if( length == 0 || length <0 ){
			throw new RuntimeException("请不要输入少于0的数字");
		}
		
		Byte [] bytes = new Byte [length];
		for (int i = 0; i < bytes.length; i++) {
			bytes[i] = verifyNum[r.nextInt(verifyNum.length)];
		}
		return StringUtils.join( bytes );
	}
	
	
	/**
     * 获取一定长度的随机字符串
     * @param length 指定字符串长度
     * @return 一定长度的数字和字符的混合串
     */
    public static String getRandomStringByLength(int length) {
        String base = "abcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }

    /**
     * 获取固定长度的值
     * 
     * eg：例如   6,12 返回 :000012
     * 
     * @param len
     * @param val
     * @return
     */
    public static String getFixedLengthNum(int len,String val){
    	int l = val.length();
    	if(len == l)
    		return val.toString();
    	if(len < l)
    		return null;
    	StringBuffer sb = new StringBuffer();
    	for (int i = 0; i < len - l; i++) {
    		sb.append("0");
		}
    	sb.append(val);
    	return sb.toString();
    }
    
    /**
     * 获取min-max范围内的一个随即数
     * @param min
     * @param max
     * @return
     */
    public static Integer getRangeRandom(int min, int max){
    	Random random = new Random();
    	return random.nextInt(max)%(max-min+1) + min;
    }
    
}
