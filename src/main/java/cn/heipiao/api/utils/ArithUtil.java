package cn.heipiao.api.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Arrays;

/**
 * 工具类 - 运算
 */

public class ArithUtil {

	// 默认除法运算精度
	private static final int DEF_DIV_SCALE = 10;

	private ArithUtil() {

	}

	/**
	 * 按照比例分配数值
	 * 
	 * eg:
	 *  5, 10 ,15
	 * 要分配的数值 7
	 * 分配之后的结果
	 * 5分配:1.19
	 * 10分配:2.31
	 * 15分配：3.5
	 * @param val 要分配的值
	 * @param pro 比例的原始值
	 * @return 按顺序返回分配后的结果 ：[1.19, 2.31, 3.5]
	 */
	public static Double[] prorate(Double val,Double ...pro){
		double d = add(pro);
		if(d <=0)
			d = 1;
		Double []rd = new Double [pro.length]; 
		for (int i = 0; i < pro.length; i++) {
			if(i == pro.length - 1){
				rd[i] = sub(val, add(rd));
			}else{
				rd[i] = mul(div(pro[i] == null ? 0D : pro[i], d),val);
			}
		}
		return rd;
	}
	
	/**
	 * 按照比例分配数值
	 * 
	 * @param val 要分配的值
	 * @param pro 比例的原始值
	 * @return 按顺序返回分配后的结果 ：
	 */
	public static Integer[] prorate(Integer val,Double ...pro){
		Integer []rd = new Integer [pro.length]; 
		double d = add(pro);
		if(d <=0)
			d = 1;
		for (int i = 0; i < pro.length; i++) {
			if(i == pro.length - 1){
				rd[i] = val - addInt(rd);
			}else{
				rd[i] = (int)(ArithUtil.div(pro[i] == null ? 0D : pro[i], d, 2) * val);
			}
		}
		return rd;
	}
	
	/**
	 * 平分数值
	 * @param val
	 * @param n 需要平分的数量
	 * @return
	 */
	public static Double[] prorate(double val,int n){
		Double []rd = new Double [n]; 
		for (int i = 0; i < n; i++) {
			if(i == n - 1){
				rd[i] = ArithUtil.sub(val, add(rd));
			}else{
				rd[i] = ArithUtil.div(val, n, 2);
			}
		}
		return rd;
	}
	
	/**
	 * 平分数值
	 * 例如：106平均到5个值中 
	 * [22, 21, 21, 21, 21]
	 * 
	 * @param val
	 * @param n 需要平分的数量
	 * @return
	 */
	public static Integer[] prorate(int val,int n){
		Integer []rd = new Integer [n]; 
		int s = 0;
		for (int i = 0; i < n; i++) {
			if(i == n - 1){
				if(i == 0){
					rd[i] = val;
					break;
				}
				rd[i] = rd[i - 1];
				s += rd[i];
				int m = val - s;
				if(m > 0){
					for (int j = 0; j < rd.length; j++) {
						if(m <= 0)
							break;
						rd[j] += 1;
						m--;
					}
				}
			}else{
				rd[i] = val / n;
				s += rd[i];
			}
		}
		return rd;
	}
	
	/**
	 * 计算int数组的总和
	 * 
	 * @param rd
	 * @return
	 */
	public  static int addInt(Integer[] rd) {
		int s = 0;
		for (Integer i : rd) {
			if(i != null)
				s += i;
		}
		return s;
	}

	/**
	 * 提供精确的加法运算。如果参数为null，则视为0处理。
	 * 
	 * @param v1
	 *            被加数
	 * @param v2
	 *            加数
	 * @return 两个参数的和
	 */
	public static double add(Double v1, Double v2) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1 == null ? 0D : v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2 == null ? 0D : v2));
		return b1.add(b2).doubleValue();
	}

	/**
	 * 提供精确的加法运算。
	 * 
	 * @param vs
	 *            加数
	 * @return 和
	 */
	public static double add(Double... vs) {
		BigDecimal b1 = new BigDecimal(0);
		for (Double d : vs) {
			BigDecimal b2 = new BigDecimal(Double.toString(d == null ? 0D : d));
			b1 = b1.add(b2);
		}
		return b1.doubleValue();
	}

	/**
	 * 提供精确的减法运算。
	 * 
	 * @param v1
	 *            被减数
	 * @param v2
	 *            减数
	 * @return 两个参数的差
	 */
	public static double sub(double v1, double v2) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.subtract(b2).doubleValue();
	}

	/**
	 * 提供精确的乘法运算。
	 * 
	 * @param v1
	 *            被乘数
	 * @param v2
	 *            乘数
	 * @return 两个参数的积
	 */
	public static double mul(double v1, double v2) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.multiply(b2).doubleValue();
	}

	/**
	 * 提供精确的乘法运算。
	 * 
	 * @param v1
	 *            被乘数
	 * @param v2
	 *            乘数
	 * @param scale
	 *            保留小数位数
	 * @return 两个参数的积
	 */
	public static double mul(double v1, double v2, int scale) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return ArithUtil.round(b1.multiply(b2).doubleValue(), scale);
	}

	/**
	 * 提供（相对）精确的除法运算，当发生除不尽的情况时，精确到 小数点以后10位，以后的数字四舍五入。
	 * 
	 * @param v1
	 *            被除数
	 * @param v2
	 *            除数
	 * @return 两个参数的商
	 */
	public static double div(double v1, double v2) {
		return div(v1, v2, DEF_DIV_SCALE);
	}

	/**
	 * 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指 定精度，以后的数字四舍五入。
	 * 
	 * @param v1
	 *            被除数
	 * @param v2
	 *            除数
	 * @param scale
	 *            表示表示需要精确到小数点以后几位。
	 * @return 两个参数的商
	 */
	public static double div(double v1, double v2, int scale) {
		if (scale < 0) {
			throw new IllegalArgumentException("参数scale必须为整数为零!");
		}
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	/**
	 * 提供精确的小数位四舍五入处理。
	 * 
	 * @param v
	 *            需要四舍五入的数字
	 * @param scale
	 *            小数点后保留几位
	 * @return 四舍五入后的结果
	 */
	public static double round(double v, int scale) {
		if (scale < 0) {
			throw new IllegalArgumentException("参数scale必须为整数或零!");
		}
		BigDecimal b = new BigDecimal(Double.toString(v));
		BigDecimal one = new BigDecimal("1");
		return b.divide(one, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	/**
	 * 提供精确的类型转换(Float)
	 * 
	 * @param v
	 *            需要被转换的数字
	 * @return 返回转换结果
	 */
	public static float convertsToFloat(double v) {
		BigDecimal b = new BigDecimal(v);
		return b.floatValue();
	}

	/**
	 * 提供精确的类型转换(Int)不进行四舍五入
	 * 
	 * @param v
	 *            需要被转换的数字
	 * @return 返回转换结果
	 */
	public static int convertsToInt(double v) {
		BigDecimal b = new BigDecimal(v);
		return b.intValue();
	}

	/**
	 * 提供精确的类型转换(Long)
	 * 
	 * @param v
	 *            需要被转换的数字
	 * @return 返回转换结果
	 */
	public static long convertsToLong(double v) {
		BigDecimal b = new BigDecimal(v);
		return b.longValue();
	}

	/**
	 * 返回两个数中大的一个值
	 * 
	 * @param v1
	 *            需要被对比的第一个数
	 * @param v2
	 *            需要被对比的第二个数
	 * @return 返回两个数中大的一个值
	 */
	public static double returnMax(double v1, double v2) {
		BigDecimal b1 = new BigDecimal(v1);
		BigDecimal b2 = new BigDecimal(v2);
		return b1.max(b2).doubleValue();
	}

	/**
	 * 返回两个数中小的一个值
	 * 
	 * @param v1
	 *            需要被对比的第一个数
	 * @param v2
	 *            需要被对比的第二个数
	 * @return 返回两个数中小的一个值
	 */
	public static double returnMin(double v1, double v2) {
		BigDecimal b1 = new BigDecimal(v1);
		BigDecimal b2 = new BigDecimal(v2);
		return b1.min(b2).doubleValue();
	}

	/**
	 * 精确比较两个数字
	 * 
	 * @param v1
	 *            需要被对比的第一个数
	 * @param v2
	 *            需要被对比的第二个数
	 * @return 如果两个数一样则返回0，如果第一个数比第二个数大则返回1，反之返回-1
	 */
	public static int compareTo(double v1, double v2) {
		BigDecimal b1 = new BigDecimal(v1);
		BigDecimal b2 = new BigDecimal(v2);
		return b1.compareTo(b2);
	}

	/**
	 * 获取数字小数位数
	 * 
	 * @param number
	 *            数字.
	 * 
	 * @return 小数位数
	 */
	public static int getDecimals(double number) {
		DecimalFormat decimalFormat = new DecimalFormat("#.####");
		String numberString = decimalFormat.format(number);
		if (numberString.indexOf(".") > 0) {
			return numberString.length() - String.valueOf(number).indexOf(".")
					- 1;
		} else {
			return 0;
		}
	}

	/**
	 * 获取数字小数位数
	 * 
	 * @param number
	 *            数字.
	 * 
	 * @return 小数位数
	 */
	public static int getDecimals(float number) {
		DecimalFormat decimalFormat = new DecimalFormat("#.####");
		String numberString = decimalFormat.format(number);
		if (numberString.indexOf(".") > 0) {
			return numberString.length() - String.valueOf(number).indexOf(".")
					- 1;
		} else {
			return 0;
		}
	}

	/**
	 * 对double数据进行取精度.
	 * 
	 * @param value
	 *            double数据.
	 * @param scale
	 *            精度位数(保留的小数位数).
	 * @param roundingMode
	 *            精度取值方式.
	 * @return 精度计算后的数据.
	 */
	public static double round(double value, int scale, RoundingMode roundingMode) {
		BigDecimal bd = new BigDecimal(value);
		bd = bd.setScale(scale, roundingMode);
		double d = bd.doubleValue();
		bd = null;
		return d;
	}
	
	
	public static void main(String[] args) {
		System.out.println(Arrays.toString(prorate(105, 5)));
		
		Double d[] = new Double[]{12.2};
		System.out.println(Arrays.toString(prorate(105,d)));
		
//		System.out.println(Arrays.toString(prorate(12.79, 2)));
		
	}
	
}
