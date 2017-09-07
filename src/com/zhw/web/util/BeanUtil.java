package com.zhw.web.util;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;


public final class BeanUtil {
	private BeanUtil() {
		
	}  
	
	private static ApplicationContext	applicationContext;
	
	
	/**
	 * 获取对象
	 * 
	 * @param name
	 * @return Object 一个以所给名字注册的bean的实例
	 * @throws BeansException
	 */
	@SuppressWarnings("unchecked")
	public static <T extends Object> T getBean(String name) throws BeansException {
		return (T) applicationContext.getBean(name);
	}
	
	/**
	 * 获取类型为requiredType的对象
	 * 如果bean不能被类型转换，相应的异常将会被抛出（BeanNotOfRequiredTypeException）
	 * 
	 * @param name
	 *            bean注册名
	 * @param requiredType
	 *            返回对象类型
	 * @return Object 返回requiredType类型对象
	 * @throws BeansException
	 */
	@SuppressWarnings("unchecked")
	public static <T extends Object> T getBean(String name, Class<?> requiredType)
																					throws BeansException {
		return (T) applicationContext.getBean(name, requiredType);
	}
	
	@SuppressWarnings("unchecked")
	public static <T extends Object> T getBean(Class<?> requiredType) throws BeansException {
		return (T) applicationContext.getBean(requiredType);
	}
	
	/**
	 * 判断以给定名字注册的bean定义是一个singleton还是一个prototype。
	 * 如果与给定名字相应的bean定义没有被找到，将会抛出一个异常（NoSuchBeanDefinitionException）
	 * 
	 * @param name
	 * @return boolean
	 * @throws NoSuchBeanDefinitionException
	 */
	public static boolean isSingleton(String name) throws NoSuchBeanDefinitionException {
		return applicationContext.isSingleton(name);
	}
	
	/**
	 * 如果给定的bean名字在bean定义中有别名，则返回这些别名
	 * 
	 * @param name
	 * @return
	 * @throws NoSuchBeanDefinitionException
	 */
	public static String[] getAliases(String name) throws NoSuchBeanDefinitionException {
		return applicationContext.getAliases(name);
	}
}
