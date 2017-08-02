package cn.heipiao.api.utils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;

/**
 * 说明 : bean属性操作工具类
 * 功能 : a.动态setter属性
 * @author chenwenye
 * @since 2016-6-29  heipiao1.0
 * 
 * TODO 考虑本类是否有必要使用此实现办法
 */
public class ExPropertyUtils {
	
	/**
	 * 作用: 利用java内省对bean动态setter属性值
	 * 注意: a.bean的属性的类型与参数类型保持一致
	 * 		 b.bean必须遵循sun规定的javaBean规则,有getter和setter函数,并且名称正确
	 * @param bean
	 * @param fieldName
	 * @param fieldValue
	 */
	public static <T , U> void setProperty(T bean , String fieldName , U fieldValue) throws Exception{
		PropertyDescriptor pd = new PropertyDescriptor(fieldName, bean.getClass());
		Method method = pd.getWriteMethod();
		method.invoke(bean, fieldValue);
	}
	
}
