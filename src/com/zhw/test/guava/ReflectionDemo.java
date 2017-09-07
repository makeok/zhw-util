package com.zhw.test.guava;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Proxy;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import javax.lang.model.type.TypeVariable;

import org.junit.Test;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.common.reflect.Invokable;
import com.google.common.reflect.Reflection;
import com.google.common.reflect.TypeParameter;
import com.google.common.reflect.TypeToken;

public class ReflectionDemo {
	public static void TypeTokentest(String arg0) throws NoSuchMethodException, SecurityException {

		// jdk
		ArrayList<String> stringList = Lists.newArrayList();
		ArrayList<Integer> intList = Lists.newArrayList();
		// Class.isAssignableFrom()是用来判断一个类Class1和另一个类Class2是否相同或是另一个类的超类或接口。
		System.out.println(stringList.getClass().isAssignableFrom(intList.getClass()));
		// Java 语言修饰符,public、protected、private、final、static、abstract
		int i = stringList.getClass().getModifiers();
		String retval = Modifier.toString(i);
		Modifier.isPublic(i);
		System.out.println("Class Modifier = " + retval);
		// 方法是否是 package private?
		Method method = ReflectionDemo.class.getMethod("TypeTokentest",String.class);
		int mi = method.getModifiers();
		System.out.println("getGenericParameterTypes"+method.getGenericParameterTypes()[0].toString());
		java.lang.reflect.TypeVariable<?>[] tv = stringList.getClass().getTypeParameters();
		System.out.println("getTypeParameters="+tv[0].toString());
		System.out.println("package private=" + !(Modifier.isPrivate(mi) || Modifier.isPublic(mi)));
		// 方法是否能够被子类重写？
		System.out.println(!(Modifier.isFinal(mi) || Modifier.isPrivate(mi) || Modifier.isStatic(mi)
				|| Modifier.isFinal(ReflectionDemo.class.getDeclaringClass().getModifiers())));
		// 方法的第一个参数是否被定义了注解@Nullable？
		boolean isnullable = false;
		for (Annotation annotation : method.getParameterAnnotations()[0]) {
			if (annotation instanceof Transactional) {
				isnullable = true;
			}
		}

		// guava
		TypeToken<String> stringTok = TypeToken.of(String.class);
		TypeToken<Integer> intTok = TypeToken.of(Integer.class);
		TypeToken<List<String>> stringListTok = new TypeToken<List<String>>() {
		};
		TypeToken<Map<?, ?>> wildMapTok = new TypeToken<Map<?, ?>>() {
		};
		TypeToken<Map<String, BigInteger>> mapToken = mapToken(TypeToken.of(String.class),
				TypeToken.of(BigInteger.class));
		TypeToken<Map<Integer, Queue<String>>> complexToken = mapToken(TypeToken.of(Integer.class),
				new TypeToken<Queue<String>>() {
				});
		System.out.println(incorrectMapToken());
		// 获得包装的 java.lang.reflect.Type.
		System.out.println("getType=" + stringTok.getType());
		// 返回大家熟知的运行时类
		System.out.println("getRawType=" + stringTok.getRawType());
		// 返回那些有特定原始类的子类型。举个例子，如果这有一个 Iterable 并且参数是 List.class，那么返回将是 List。
		// System.out.println("getSubtype="+stringTok.getSubtype(Object.class));
		// 产生这个类型的超类，这个超类是指定的原始类型。举个例子，如果这是一个 Set 并且参数是Iterable.class，结果将会是
		// Iterable。
		System.out.println("getSupertype=" + stringTok.getSupertype(Object.class));
		// 返回一个 Set，包含了这个所有接口，子类和类是这个类型的类。返回的 Set 同样提供了 classes()和
		// interfaces()方法允许你只浏览超类和接口类。
		System.out.println("getTypes=" + stringTok.getTypes());
		// 检查某个类型是不是数组，甚至是<? extends A[]>。
		System.out.println("isArray=" + stringTok.isArray());
		// 返回组件类型数组。
		System.out.println("getComponentType=" + stringTok.getComponentType());

		TypeToken<Function<Integer, String>> funToken = new TypeToken<Function<Integer, String>>() {
		};
		// resolveType 是一个可以用来“替代”
		TypeToken<?> funResultToken = funToken.resolveType(Function.class.getTypeParameters()[1]);

		Invokable<?, Object> invokable = Invokable.from(method);
		// Invokable<ReflectionDemo, ReflectionDemo> invokable =
		// Invokable.from(ReflectionDemo.class.getConstructor(null));
//		Invokable<List<String>, ?> invokable = new TypeToken<List<String>>() {
//		}.method(method);
		System.out.println("isPublic=" + invokable.isPublic());
		// 方法是否是 package private?
		System.out.println("isPackagePrivate=" + invokable.isPackagePrivate());
		// 方法是否能够被子类重写？
		System.out.println("isPackagePrivate=" + invokable.isOverridable());
		// 方法的第一个参数是否被定义了注解@Nullable？
		invokable.getParameters().get(0).isAnnotationPresent(Transactional.class);
		System.out.println("getParameters=" + invokable.getParameters());
		System.out.println("getReturnType=" + invokable.getReturnType());
	}

	static <K, V> TypeToken<Map<K, V>> incorrectMapToken() {
		return new TypeToken<Map<K, V>>() {
		};
	}

	static <K, V> TypeToken<Map<K, V>> mapToken(TypeToken<K> keyToken, TypeToken<V> valueToken) {
		return new TypeToken<Map<K, V>>() {
		}.where(new TypeParameter<K>() {
		}, keyToken).where(new TypeParameter<V>() {
		}, valueToken);
	}

	
	/**
	 * 动态代理
	 */
	public void testProxy() {
		InvocationHandler invocationHandler = new MyInvocationHandler(new MyFoo());

        // Guava Dynamic Proxy implement
        IFoo foo = Reflection.newProxy(IFoo.class, invocationHandler);
        foo.doSomething();
        //jdk Dynamic proxy implement
        IFoo jdkFoo = (IFoo) Proxy.newProxyInstance(
                IFoo.class.getClassLoader(),
                new Class<?>[]{IFoo.class},
                invocationHandler);
        jdkFoo.doSomething();

	    
	}
	
	public class MyInvocationHandler implements InvocationHandler {
	    
		private Object concreteClass;  
	      
	    public MyInvocationHandler(Object concreteClass){  
	        this.concreteClass=concreteClass;  
	    }
		
		public Object invoke(Object proxy, Method method, Object[] args)
	                throws Throwable {
	            System.out.println("proxy println something");
	            Object object = method.invoke(concreteClass, args);//普通的Java反射代码,通过反射执行某个类的某方法  
	            System.out.println("After invoke method...");  
	            return object;
	        }
	}

	public interface IFoo {
		void doSomething();
	}
	
	public class MyFoo implements IFoo {

		@Override
		public void doSomething() {
			System.out.println("MyFoo doSomething...");
			
		}
		
	}

	@Test
	public void test() {
		try {
			TypeTokentest("123");
			testProxy();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
