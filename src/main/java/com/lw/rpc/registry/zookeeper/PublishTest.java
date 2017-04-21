package com.lw.rpc.registry.zookeeper;

import java.util.concurrent.CountDownLatch;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.CuratorWatcher;
import org.apache.curator.framework.state.ConnectionState;
import org.apache.curator.framework.state.ConnectionStateListener;
import org.apache.curator.retry.RetryNTimes;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//发布与订阅
public class PublishTest {

	private static Logger LOG = LoggerFactory.getLogger(PublishTest.class);  
	
	public static CuratorFramework client = null;
	
	//节点路径
	public static String PATH = "/path/data_config";
	
	//注册地址
	public static final String zkAddressPort = "localhost:2189";
	
	//超时时间
	public static final int timeout = 10000;
	
	// 并发
	public static CountDownLatch countDownLath = new CountDownLatch(1);
	
	/**
	 * 监听器
	 */
	static ConnectionStateListener clientListener = new ConnectionStateListener() {
		
		@Override
		public void stateChanged(CuratorFramework client, ConnectionState newState) {
			// TODO Auto-generated method stub
			if(newState == ConnectionState.CONNECTED) {
				LOG.info("连接已经建立");
				countDownLath.countDown();
			}else if(newState == ConnectionState.LOST) {
				LOG.info(" 断开连接 ");
				try {
					LOG.info("重新连接——————————————————————————");
					reinit();
					LOG.info(" 初始化___________________________ ");
				}catch(Exception e) {
					e.printStackTrace();
				}
			}
		}
	};
	
	/**
	 * 初始化连接
	 * @throws Exception
	 */
	public static void init() throws Exception {
		client = CuratorFrameworkFactory.builder().connectString(zkAddressPort)
			.sessionTimeoutMs(timeout).retryPolicy(new RetryNTimes(5, 5000)).build();
		client.getConnectionStateListenable().addListener(clientListener);
		client.start();
		//连接成功后进行下一步
		countDownLath.await();
	}
	
	/**
	 * 重新连接
	 */
	public static void reinit() {
		try {
			unregister();
			init();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 注销
	 */
	public static void unregister() {
		try {
			if(client != null) {
				client.close();
				client = null;
			}
		} catch (Exception e) {
			LOG.info(" 关闭zookeeper异常 ");
			e.printStackTrace();
		}
	}
	
	/**
	 * 读取节点数据
	 * @param path
	 * @return
	 * @throws Exception
	 */
	public static String readPath(String path) throws Exception {
		byte[] data =  client.getData().forPath(path);
		return new String(data);
	}
	
	/**
	 * 监控路径
	 * @param path
	 * @param watcher
	 * @return
	 * @throws Exception
	 */
	public static String watcherPath(String path, CuratorWatcher watcher) throws Exception {
		byte[] data = client.getData().usingWatcher(watcher).forPath(path);
		LOG.info(" 监控的数据： " + data);
		return new String(data);
	}
	
	/**
	 * 节点监控
	 */
	static CuratorWatcher pathWatcher = new CuratorWatcher() {
		
		@Override
		public void process(WatchedEvent event) throws Exception {
			// TODO Auto-generated method stub
			if(event.getType() == EventType.NodeDataChanged) {
				String watchValue = readPath(event.getPath());
				System.out.println("变化后的数据为：" + watchValue);
			}
		}
	};
	
	public static void main(String[] args) throws Exception {  
	        init();  
	        watcherPath(PATH, pathWatcher);  
	        Thread.sleep(Integer.MAX_VALUE);  
    }  
}
