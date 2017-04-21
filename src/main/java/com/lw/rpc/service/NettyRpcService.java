package com.lw.rpc.service;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.stereotype.Component;

/**
 * 采用注解方式，声明为服务
 * 
 * @Component 注解是spring的注解
 * 
 * @author shangdc
 *
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Component 
public @interface NettyRpcService {
	
	Class<?> value();

}
