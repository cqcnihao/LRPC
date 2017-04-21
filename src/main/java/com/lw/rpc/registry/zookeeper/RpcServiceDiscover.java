package com.lw.rpc.registry.zookeeper;

import java.util.ArrayList;
import java.util.List;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.api.BackgroundPathable;
import org.apache.curator.framework.api.CuratorWatcher;
import org.apache.curator.x.discovery.ServiceDiscovery;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import com.lw.rpc.constant.RPCConstant;
import com.lw.rpc.util.CuratorClientUtil;

/**
 * 服务发现
 * @author sdc
 *
 */
public class RpcServiceDiscover {

	private static final Logger LOG = LoggerFactory.getLogger(ServiceDiscovery.class);
	
	private volatile List<String> list = new ArrayList<String>();
	
	private String registryAddress; //注册地址

	public RpcServiceDiscover(String registryAddress) {
		this.registryAddress = registryAddress;
		
		CuratorFramework client = CuratorClientUtil.getInstance(registryAddress);
		if(!StringUtils.isEmpty(client)) {
		}
	}
	
	/**
	 * 发现服务
	 * @return
	 */
	public String discoverServiceUrl() {
		String dataUrl = null;
		int dataListSize = list.size();
		if(dataListSize > 0) {
			if(dataListSize == 1) { 
				dataUrl = list.get(0);
			}else { //说明有好几个服务，随机选择，以后会弄一个算法，
				int randomUrl = (int) Math.random();
				dataUrl = list.get(randomUrl * dataListSize);
			}
		}
		return dataUrl;
	}
	
	/**
	 * 获取数据
	 * @param client
	 */
	public void watchNode(CuratorFramework client) {
		try {
			BackgroundPathable<List<String>> bp = client.getChildren().usingWatcher(new CuratorWatcher() {
				
				@Override
				public void process(WatchedEvent event) throws Exception {
					// TODO Auto-generated method stub
					if (event.getType() == EventType.NodeChildrenChanged) {
						System.out.println("监视节点");
						watchNode(client);
					}
				}
			});
			
			List<String> tempDataList = new ArrayList<String>();
			
			//获取数据
			List<String> pathList = bp.inBackground().forPath(RPCConstant.ZK_REGISTRY_PATH);
			pathList.forEach(item -> {
				byte[] dataByte = null;
				try {
					dataByte = client.getData().forPath(RPCConstant.ZK_REGISTRY_PATH + "/" + item);
					tempDataList.add(new String(dataByte));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			});
			
			this.list = tempDataList;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
