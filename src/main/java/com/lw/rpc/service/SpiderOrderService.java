package com.lw.rpc.service;

import com.lw.rpc.model.SpiderOrder;

/**
 * 提供数据service
 * 
 * @author 
 *
 */
public interface SpiderOrderService {

	/**
	 * 生成订单信息
	 * @param spiderOrder
	 * @return
	 */
	public int saveSpiderOrder(SpiderOrder spiderOrder);
	
	/**
	 * 修改订单信息
	 * @param spiderOrder
	 */
	public void updateSpiderOrder(SpiderOrder spiderOrder);
	
	/**
	 * 删除订单信息
	 * @param spiderId
	 */
	public void deleteSpiderOrderById(Long spiderId);
	
}
