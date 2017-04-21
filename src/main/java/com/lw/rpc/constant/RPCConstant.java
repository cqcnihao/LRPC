package com.lw.rpc.constant;

/**
 * RPC常量类
 * 
 * @author sdc
 *
 */
public class RPCConstant {

	//namesapce
	public static final String ZK_REGISTRY_PATH = "/lightweight"; 

	//
	public static final String ZK_DATA_PATH = ZK_REGISTRY_PATH + "/rpc";

	// session超时
	public static final int sessionTimeout = 30000;
	
	//连接超时
	public static final int connectionTimeout = 30000;
	
	//重试时间间隔
	public static final int regryTime = 1000;
	
	//重试次数
	public static final int regryTimes = 3;
	
}
