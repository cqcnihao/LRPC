package com.lw.rpc.registry.zookeeper;

import java.util.Collection;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.api.transaction.CuratorTransactionResult;

public class TransactionExample {

	public static void main(String[] args) {
		
	}
	
	public static Collection<CuratorTransactionResult> transaction(CuratorFramework cf) throws Exception {
		Collection<CuratorTransactionResult> results = cf.inTransaction().create()
				.forPath("/a/path", "test".getBytes())
				.and().setData().forPath("/another/path", "other data".getBytes())
				.and().delete().forPath("/yet/another/path")
				.and().commit();
		
		for(CuratorTransactionResult ctr : results) {
			System.out.println(ctr.getForPath() + "," + ctr.getType());

		}
		
		return results;
	}
	
}
