package com.zhw.test.lang;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import static java.lang.System.out;

/**
 * @author yxy 执行的参数是：类名 方法名 java MethodSpy java.lang.Class getConstructor java
 *         MethodSpy java.io.PrintStream format java MethodSpy java.lang.Class
 *         cast
 */
public class MethodSpy {
	// 定义输出的格式。
	private static final String fmt = "%24s: %s%n";

	// for the morbidly curious
	<E extends RuntimeException> void genericThrow() throws E {
	}

	public static void main(String... args) {

		//Class<?> c = null;
		// 根据传入的参数加载相应的类
		//c = Class.forName(args[0]);
		Method[] allMethods = GenericParamType.class.getDeclaredMethods();
		// 遍历出加载的类的所有方法。
		for (Method m : allMethods) {
			// 如果方法名与输入的参数（方法名）不一致，就跳过当前的循环执行下一次循环。
//			if (!m.getName().equals(args[1])) {
//				continue;
//			}
			// 方法名与输入的参数一致，输出方法的相关信息。
			out.format("%s%n", m.toGenericString());// 格式化成字符串输出方法相关信息
			out.format(fmt, "ReturnType", m.getReturnType());// 返回的类型
			out.format(fmt, "GenericReturnType", m.getGenericReturnType());// 原始的返回类型。

			// 获取方法的所有的参数的类型。
			Class<?>[] pType = m.getParameterTypes();
			// 获取方法的所有的参数的原始类型。
			Type[] gpType = m.getGenericParameterTypes();
			for (int i = 0; i < pType.length; i++) {
				out.format(fmt, "ParameterType", pType[i]);
				out.format(fmt, "GenericParameterType", gpType[i]);
			}
			// 获取方法的异常类型。
			Class<?>[] xType = m.getExceptionTypes();
			// 获取方法原始的异常类型。
			Type[] gxType = m.getGenericExceptionTypes();
			// 遍历出所有的异常类型
			for (int i = 0; i < xType.length; i++) {
				out.format(fmt, "ExceptionType", xType[i]);
				out.format(fmt, "GenericExceptionType", gxType[i]);
			}
		}
	}
}
