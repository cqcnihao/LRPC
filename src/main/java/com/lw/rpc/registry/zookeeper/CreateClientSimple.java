package com.lw.rpc.registry.zookeeper;

import java.util.Collection;
import java.util.List;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.CuratorEvent;
import org.apache.curator.framework.api.CuratorListener;
import org.apache.curator.framework.api.transaction.CuratorTransaction;
import org.apache.curator.framework.api.transaction.CuratorTransactionFinal;
import org.apache.curator.framework.api.transaction.CuratorTransactionResult;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper.States;

public class CreateClientSimple {

	private static final String connect_addr = "localhost:2189";

	private static final int session_outtime = 5000; // ms

	public static void main(String[] args) throws Exception {
		// 重试策略
		RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 10);
		CuratorFramework cf = null;
		try {
			// 通过工厂创建连接
			cf = CuratorFrameworkFactory.builder().connectString(connect_addr).sessionTimeoutMs(session_outtime).retryPolicy(retryPolicy).build();

			cf.start();

			System.out.println(States.CONNECTED);
			System.out.println(cf.getState());

			// 建立节点， 指定节点类型（不加withnode默认为持久型节点），路径，内容
			// cf.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT).forPath("/super/c1",
			// "c1内容".getBytes());

			// 删除节点
			// cf.delete().guaranteed().deletingChildrenIfNeeded().forPath("/super");

			cf.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT).forPath("/super/c1", "c1内容".getBytes());
			cf.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT).forPath("/super/c2", "c2内容".getBytes());

			// 读取节点
			String ret1 = new String(cf.getData().forPath("/super/c2"));
			System.out.println("修改前c2节点=====" + ret1);
			// 修改节点
			cf.setData().forPath("/super/c2", "修改c2内容".getBytes());
			String ret2 = new String(cf.getData().forPath("/super/c2"));
			System.out.println("修改后c2节点=====" + ret2);

		} catch (Exception e) {

		} finally {
			if (cf != null) {
				cf.close();
			}
		}
	}
	
	//下面是两种方式创建
	public static CuratorFramework createSimple(String connectString) {
		ExponentialBackoffRetry retryPolicy = new ExponentialBackoffRetry(1000, 3);
		return CuratorFrameworkFactory.newClient(connectString, retryPolicy);
	}
	
	public static CuratorFramework craeteWithOptions(String connectString, RetryPolicy retryPolicy,
			int connectionTimeoutMs, int sessionTimeoutMs) {
		return CuratorFrameworkFactory.builder().connectString(connectString)
				.retryPolicy(retryPolicy)
				.connectionTimeoutMs(connectionTimeoutMs)
				.sessionTimeoutMs(sessionTimeoutMs)
				.build();
	}
	
	//创建节点
	public static void create(CuratorFramework cf, String path, byte[] data) throws Exception{
		cf.create().forPath(path, data);
	}

	//创建临时节点
	public static void createEphemeral(CuratorFramework cf, String path, byte[] data) throws Exception {
		cf.create().withMode(CreateMode.EPHEMERAL).forPath(path, data);
	}
	
	//数据
	public static void setData(CuratorFramework cf, String path, byte[] data) throws Exception {
		cf.setData().forPath(path, data);
	}
 	
	//同步设置数据
	public static void setDataAsync(CuratorFramework cf, String path, byte[] data) throws Exception{
		CuratorListener listener = new CuratorListener() {
			
			@Override
			public void eventReceived(CuratorFramework arg0, CuratorEvent arg1) throws Exception {
				// TODO Auto-generated method stub
				
			}
		};
		cf.getCuratorListenable().addListener(listener);
		cf.setData().inBackground().forPath(path, data);
	}
	
	//删除节点
	public static void deleteData(CuratorFramework cf, String path) throws Exception {
		cf.delete().guaranteed().forPath(path);
	}
	
	//监视节点
	public static List<String> watchGetChilden(CuratorFramework cf, String path) throws Exception {
		return cf.getChildren().watched().forPath(path);
	}
	
	//使用自定义的监视自己的节点
	public static List<String> watchGetChilDenByWatcher(CuratorFramework cf, String path, Watcher watcher) throws Exception {
		return cf.getChildren().usingWatcher(watcher).forPath(path);
	}
	
	//事务
	public static Collection<CuratorTransactionResult> transation(CuratorFramework curator) throws Exception {
		Collection<CuratorTransactionResult> results = curator.inTransaction().create().forPath("/a/path").and().setData().forPath("/another/path", "/another data".getBytes())
		.and().delete().forPath("/yet/another/path")
		.and().commit();
		for(CuratorTransactionResult result: results) {
			System.out.println(result.getForPath() + "-" + result.getType());
		}
		return results;
	}
	
	public static CuratorTransactionFinal addCreateToTransactionFinal(CuratorTransaction curatorTransaction) throws Exception {
		return curatorTransaction.create().forPath("/a/path", "some data".getBytes()).and();
	}
	
	public static CuratorTransactionFinal deleteCreateToTransactionFinal(CuratorTransaction curatorTransaction) throws Exception {
		return curatorTransaction.delete().forPath("/another/path").and();
	}
	
	public static void commitTransation(CuratorTransactionFinal transation) throws Exception {
		transation.commit();
	}
	
	public static void addListener(CuratorFramework client, String path, boolean cacheData) throws Exception {
//		PathChildrenCache cache = new PathChildrenCache(client, path, cacheData);
//		cache.start();
//		cache.getListenable().addListener(new MyListener());
	}
	
	class MyListener implements PathChildrenCacheListener {

		@Override
		public void childEvent(CuratorFramework curator, PathChildrenCacheEvent event) throws Exception {
			// TODO Auto-generated method stub
			switch (event.getType()) {
			case CHILD_ADDED:
				//新的节点建立了
				System.out.println("新的节点建立了");
				break;
			case CHILD_UPDATED:
				//新的节点建立了
				System.out.println("节点更新");
				break;
			case CHILD_REMOVED:
				//新的节点建立了
				System.out.println("节点移除");
				break;

			default:
				break;
			}
		}
		
	}
}
