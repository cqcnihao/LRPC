package com.lw.rpc.service.impl;

import org.springframework.stereotype.Service;

import com.lw.rpc.model.SpiderOrder;
import com.lw.rpc.service.SpiderOrderService;

/**
 * 订单实现类
 * 
 * @author shangdc
 *
 */
@Service("spiderOrderService")
public class SpiderOrderServiceImpl implements SpiderOrderService{

	/**
	 * 生成订单 
	 */
	public int saveSpiderOrder(SpiderOrder spiderOrder) {
		//具体的业务逻辑
		System.out.println("保存方法");
		return 0;
	}

	@Override
	public void updateSpiderOrder(SpiderOrder spiderOrder) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteSpiderOrderById(Long spiderId) {
		// TODO Auto-generated method stub
		
	}

}
