package com.lw.rpc.registry.zookeeper;

import java.lang.reflect.Method;
import java.util.UUID;

import net.sf.cglib.proxy.InvocationHandler;
import net.sf.cglib.proxy.Proxy;

import com.lw.rpc.client.LwRpcClient;
import com.lw.rpc.message.RequestMessage;
import com.lw.rpc.message.ResponseMessage;

/**
 * 调用代理
 * 
 * @author sdc
 *
 */
public class LwRpcProxy {

	private String serverAddress; //服务地址

	private RpcServiceDiscover rpcServiceDiscover; //发现服务

	public LwRpcProxy(String serverAddress) {
		this.serverAddress = serverAddress;
	}

	public LwRpcProxy(RpcServiceDiscover rpcServiceDiscover) {
		this.rpcServiceDiscover = rpcServiceDiscover;
	}

	@SuppressWarnings("unchecked")
	public <T> T create(Class<?> interfaceClass) {
		return (T) Proxy.newProxyInstance(interfaceClass.getClassLoader(), new Class<?>[] { interfaceClass }, new InvocationHandler() {
			@Override
			public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
				RequestMessage request = new RequestMessage(); // 创建并初始化 RPC 请求
				request.setRequestId(UUID.randomUUID().toString());
				request.setClassName(method.getDeclaringClass().getName());
				request.setMethodName(method.getName());
				request.setParameterTypes(method.getParameterTypes());
				request.setParameters(args);

				if (rpcServiceDiscover != null) {
					serverAddress = rpcServiceDiscover.discoverServiceUrl(); // 发现服务
				}

				String[] array = serverAddress.split(":");
				String host = array[0];
				int port = Integer.parseInt(array[1]);

				// 初始化 RPC 客户端
				LwRpcClient client = new LwRpcClient(host, port);
				// 通过 RPC 客户端发送 RPC
				ResponseMessage response = client.sendMessage(request);
				// 请求并获取 RPC 响应
				if (response.getError() != null) {
					throw response.getError();
				} else {
					return response.getResult();
				}
			}
		});
	}
}
