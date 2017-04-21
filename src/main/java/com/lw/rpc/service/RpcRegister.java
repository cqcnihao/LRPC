package com.lw.rpc.service;

/**
 * 发布服务的地址及端口到服务注册中心
 * 
 * @author sdc
 *
 */
public interface RpcRegister {

	/**
	 * 发布服务接口
	 * 
	 * @param service
	 *            服务接口名称，一个产品中不能重复
	 * @param version
	 *            服务接口的版本号，默认1.0.0
	 * @param address
	 *            服务发布的地址和端口
	 */
	public void register(String service, String version, String address) throws Exception;

}
