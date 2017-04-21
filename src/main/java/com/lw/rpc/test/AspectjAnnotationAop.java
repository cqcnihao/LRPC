package com.lw.rpc.test;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component
@Aspect
// 定义切面
public class AspectjAnnotationAop {

	/**
     * 指定切入点匹配表达式，注意它是以方法的形式进行声明的。 
     * 分析： 
     *  execution 是方法的织入语言 
     *  第一个 * ：返回任意类型 
     *  com.lw.rpc： 包。 
     *  ..：rpc包以及其子包。 
     *  第二个 * ：rpc包以及其子包下的任意类。 
     *  第三个 * ：rpc包以及其子包下的任意类的任意方法。 
     *  (..) ：方法的参数为任意。 
     *  总结：对com.lw.rpc包以及其子包下的任意类的任意方法作切入 
     */  
	@Before("execution(* com.lw.rpc.service.impl.SpiderOrderServiceImpl.saveSpiderOrder(..))")  
	public void beforeAdvice(JoinPoint jp) {
		System.out.println(" 进入连接点前 +++++++++++++++ ");

		System.out.println(jp.getTarget().getClass() + "对象正在用这个");

		System.out.println(jp.getArgs()[0] + "test");

		System.out.println(" 进入到连接点点方法 ");
	}

	/**
	 * 后置通知
	 * 
	 * @param jp
	 */
	public void afterAdvice(JoinPoint jp, String result) {
		System.out.println(" 连接点方法执行完成后 +++++++++++++++ ");

		System.out.println(jp.getTarget().getClass() + "对象正在用这个");

		System.out.println(jp.getSignature().getName() + "方法");

		System.out.println("结果：" + result);
	}

	/**
	 * 环绕通知
	 * 
	 * @param jp
	 * @param result
	 * @throws Throwable
	 */
	public void aroundAdvice(ProceedingJoinPoint pjp) throws Throwable {
		System.out.println(" 进入到环绕通知  +++++++++++++++ ");

		// 调用方法的参数
		Object[] args = pjp.getArgs();
		// 调用的方法名
		String method = pjp.getSignature().getName();
		// 获取目标对象
		Object target = pjp.getTarget();

		// 执行完方法的返回值：调用proceed()方法，就会触发切入点方法执行
		Object result = pjp.proceed();

		System.out.println("调用方法结束：之后执行！\n");

		System.out.println("输出：" + args[0] + ";" + method + ";" + target + ";" + result + "\n");
	}

	/**
	 * 异常通知
	 * 
	 * @param jp
	 * @param e
	 */
	public void afterThrow(JoinPoint jp, Throwable e) {
		System.out.println(" 异常通知 ");
	}

}
