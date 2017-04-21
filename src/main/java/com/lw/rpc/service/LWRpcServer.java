package com.lw.rpc.service;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections4.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.util.CollectionUtils;

import com.lw.rpc.message.Message;
import com.lw.rpc.message.RequestMessage;
import com.lw.rpc.message.ResponseMessage;
import com.lw.rpc.message.handler.MessageDecoder;
import com.lw.rpc.message.handler.MessageEncoder;
import com.lw.rpc.message.handler.MessageHandler;
import com.lw.rpc.registry.zookeeper.RpcRegisterZookeeper;
import com.lw.rpc.util.StringUtil;

/**
 * 轻量级注册服务，启动的时候注册地址
 * 
 * @author sdc
 *
 */
public class LWRpcServer implements ApplicationContextAware, InitializingBean {

	private static final Logger LOG =  LoggerFactory.getLogger(LWRpcServer.class);
	
	private String serverAddress;
	
	private RpcRegisterZookeeper rpcRegisterZookeeper; //注册
	
	private Map<String, Object> serviceHandlderMap = new HashMap<String, Object>(); //service处理map
	
	@Override
	public void afterPropertiesSet() throws Exception {
		// TODO Auto-generated method stub
		EventLoopGroup boossGroup = new NioEventLoopGroup();
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		
		try {
			ServerBootstrap serverBootstrap = new ServerBootstrap();
			serverBootstrap.group(boossGroup, workerGroup).channel(NioServerSocketChannel.class)
							.childHandler(new ChannelInitializer<SocketChannel>() {

								@Override
								protected void initChannel(SocketChannel sc) throws Exception {
									// TODO Auto-generated method stub
									sc.pipeline()
										.addLast(new MessageDecoder(RequestMessage.class)) //解码
										.addLast(new MessageEncoder(ResponseMessage.class)) //处理rpc响应，编码
										.addLast(new MessageHandler(serviceHandlderMap)); //处理请求
								}
							})
							.option(ChannelOption.SO_BACKLOG, 128)
							.childOption(ChannelOption.SO_KEEPALIVE, true);
			if(!StringUtil.isEmpty(serverAddress)) {
				String[] address = serverAddress.split(":");  //地址解析
				String ip = address[0]; 
				int port = Integer.parseInt(address[1]);
				
				ChannelFuture future = serverBootstrap.bind(ip, port).sync(); //启动
				LOG.info(" 服务启动 " + serverAddress);
				
				if(!StringUtil.isEmpty(rpcRegisterZookeeper)) { //注册服务地址
					rpcRegisterZookeeper.register(serverAddress, "1.0", serverAddress);
					LOG.info(" 服务注册 ");
				}
				
				future.channel().closeFuture().sync();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			workerGroup.shutdownGracefully();
			boossGroup.shutdownGracefully();
		}
		
	}

	@Override
	public void setApplicationContext(ApplicationContext ctx) throws BeansException {
		// TODO Auto-generated method stub
		Map<String, Object> nettyServiceBeanMap = ctx.getBeansWithAnnotation(NettyRpcService.class);
		if(MapUtils.isNotEmpty(nettyServiceBeanMap)) {
			for(Object rpcServiceBean : nettyServiceBeanMap.values()) {
				String rpcName = rpcServiceBean.getClass().getAnnotation(NettyRpcService.class).value().getName();
				serviceHandlderMap.put(rpcName, rpcServiceBean);
			}
		}
	}

}
