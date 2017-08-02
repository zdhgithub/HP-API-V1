package cn.heipiao.api.constant;

/**
 * 
 * 说明 : MyBatis操作使用的常量类 功能 : a. 封装map通用参数 - start,size 使用规范 : a.
 * 对mybatis操作需要常量时建议使用此类常量
 * 
 * @author chenwenye
 * @since 2016-6-3 heipiao1.0
 */
public final class MyBatisConstant {

	/** map参数中start **/
	public final static String PARAM_START = "start";

	/** map参数中size **/
	public final static String PARAM_SIZE = "size";

	/** 默认重第几条开始 MYSQL - 0 , ORACLE - 1 **/
	public final static int DEFAULT_START = 0;

	/** 默认查找条数 **/
	public final static int DEFALUT_SIZE = 30;
	
	/** 升序 **/
	public final static String ORDER_ASC = "asc";
	
	/** 降序 **/
	public final static String ORDER_DESC = "desc";
	
	/** 排序字段 **/
	public final static String ORDER = "order";
	
}
