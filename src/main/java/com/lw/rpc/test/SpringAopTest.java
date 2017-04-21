package com.lw.rpc.test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.lw.rpc.model.SpiderOrder;
import com.lw.rpc.service.SpiderOrderService;

public class SpringAopTest {

	public static void main(String[] args) {
		ApplicationContext cxt = new ClassPathXmlApplicationContext("/spring/spring-applicationContext.xml");  
		SpiderOrderService spiderOrderService = (SpiderOrderService) cxt.getBean("spiderOrderService");  
		spiderOrderService.saveSpiderOrder(new SpiderOrder());
	}
	
}
