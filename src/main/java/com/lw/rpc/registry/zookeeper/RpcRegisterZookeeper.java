package com.lw.rpc.registry.zookeeper;

import org.apache.curator.framework.CuratorFramework;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lw.rpc.service.RpcRegister;
import com.lw.rpc.util.CuratorClientUtil;

/**
 * zookeeper注册器
 * 
 * @author sdc
 *
 */
public class RpcRegisterZookeeper implements RpcRegister {

	private static final Logger LOG = LoggerFactory.getLogger(RpcRegisterZookeeper.class);

	private String registryAddress;

	public RpcRegisterZookeeper(String registryAddress) {
		super();
		this.registryAddress = registryAddress;
	}

	@Override
	public void register(String service, String version, String zkAddress) throws Exception {
		zkAddress = registryAddress;
		CuratorFramework client = CuratorClientUtil.getInstance(zkAddress);
		boolean createFlag = CuratorClientUtil.createNode(client, service, null);
		LOG.info(" 注册结果，createFlag： " + createFlag);
	}

}
