package com.lw.rpc.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoop;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import com.lw.rpc.message.RequestMessage;
import com.lw.rpc.message.ResponseMessage;
import com.lw.rpc.message.handler.MessageDecoder;
import com.lw.rpc.message.handler.MessageEncoder;
import com.lw.rpc.message.handler.MessageHandler;

/**
 * 客户端调用
 * 
 * @author sdc
 *
 */
public class LwRpcClient extends SimpleChannelInboundHandler<ResponseMessage> {

	private static final Logger LOG = LoggerFactory.getLogger(LwRpcClient.class);

	private String host;

	private int port;

	private ResponseMessage message;

	private final Object obj = new Object();

	public LwRpcClient(String host, int port) {
		super();
		this.host = host;
		this.port = port;
	}

	@Override
	protected void messageReceived(ChannelHandlerContext arg0, ResponseMessage message) throws Exception {
		// TODO Auto-generated method stub
		this.message = message;
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		// TODO Auto-generated method stub
		LOG.error("客户端接受消息异常", cause);
		ctx.close();
	}

	/**
	 * 发送消息
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public ResponseMessage sendMessage(RequestMessage request) throws Exception {
		EventLoopGroup group = new NioEventLoopGroup();
		try {
			Bootstrap bootstrap = new Bootstrap();
			bootstrap.group(group).channel(NioSocketChannel.class).handler(new ChannelInitializer<Channel>() {

				@Override
				protected void initChannel(Channel channel) throws Exception {
					// TODO Auto-generated method stub
					channel.pipeline().addLast(new MessageEncoder(RequestMessage.class)) // 处理rpc响应，编码
							.addLast(new MessageDecoder(ResponseMessage.class)) // 解码
							.addLast(LwRpcClient.this); // 使用LwRpcClient发送请求
				}

			}).option(ChannelOption.SO_KEEPALIVE, true);

			ChannelFuture future = bootstrap.connect(host, port).sync();
			future.channel().writeAndFlush(request).sync();

			synchronized (obj) {
				obj.wait();// 未收到响应，使用线程等待
			}

			if (!StringUtils.isEmpty(message)) {
				future.channel().closeFuture().sync();
			}

			return message;
		} finally {
			group.shutdownGracefully();
		}
	}

}
