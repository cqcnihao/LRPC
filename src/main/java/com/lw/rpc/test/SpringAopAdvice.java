package com.lw.rpc.test;

import java.lang.reflect.Method;

import net.sf.cglib.reflect.FastClass;
import net.sf.cglib.reflect.FastMethod;

import org.aopalliance.aop.Advice;
import org.springframework.aop.AfterAdvice;
import org.springframework.aop.MethodBeforeAdvice;
import org.springframework.aop.ThrowsAdvice;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

/**
 * AfterAdvice
 * 
 * @author
 *
 */
public class SpringAopAdvice implements MethodInterceptor {
	/**
	 * 对未知异常的处理.
	 */
	// public void afterThrowing(Method method, Object[] args, Object target,
	// NumberFormatException ex) throws Throwable {
	// System.out.println("出现异常的类: " + target.getClass().getName());
	// System.out.println("出现异常的方法: " + method.getName());
	//
	// System.out.println(" 具体什么异常 : " + ex.getClass().getName());
	// }

	public static void main(String[] args) {
		// MethodBeforeAdvice
		// AfterAdvice
		// MethodInterceptor

		SpringAopBean bean = new SpringAopBean();

		ProxyFactory pf = new ProxyFactory();
		pf.setTarget(bean);
		pf.addAdvice((Advice) new SpringAopAdvice());

		SpringAopBean proxy = (SpringAopBean) pf.getProxy();

		try {
			// 测试
			proxy.aopMethod1("spring");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public Object intercept(Object target, Method arg1, Object[] obj, MethodProxy arg3) throws Throwable {
		System.out.println("前置通知 -> ");

		SpringAopBean sab = (SpringAopBean) target;
		sab.aopMethod();
		
		System.out.println("<- 后置通知");

		return "";
	}
}
