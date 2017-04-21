package com.lw.rpc.message.handler;

import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Map;

import net.sf.cglib.reflect.FastClass;
import net.sf.cglib.reflect.FastMethod;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lw.rpc.message.Message;
import com.lw.rpc.message.RequestMessage;
import com.lw.rpc.message.ResponseMessage;

/**
 * 请求处理
 * 
 * @author sdc
 *
 */
public class MessageHandler extends SimpleChannelInboundHandler<Message> {

	private static final Logger LOG = LoggerFactory.getLogger(MessageHandler.class);

	private final Map<String, Object> handlerMap;

	public MessageHandler(Map<String, Object> handlerMap) {
		this.handlerMap = handlerMap;
	}

	@Override
	protected void messageReceived(ChannelHandlerContext arg0, Message message) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object object) throws Exception {
		// TODO Auto-generated method stub
		RequestMessage message = (RequestMessage) object;
		ResponseMessage response = new ResponseMessage();
		response.setRequestId(message.getRequestId());

		try {
			Object result = handle(message);
			response.setResult(result);
		} catch (Throwable t) {
			response.setError(t);
		}
		ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);

	}

	private Object handle(RequestMessage request) throws Throwable {
		String className = request.getClassName();
		Object serviceBean = handlerMap.get(className);

		Class<?> serviceClass = serviceBean.getClass();
		String methodName = request.getMethodName();
		Class<?>[] parameterTypes = request.getParameterTypes();
		Object[] parameters = request.getParameters();

		// Method method = serviceClass.getMethod(methodName, parameterTypes);
		// method.setAccessible(true);
		// return method.invoke(serviceBean, parameters);

		FastClass serviceFastClass = FastClass.create(serviceClass);
		FastMethod serviceFastMethod = serviceFastClass.getMethod(methodName, parameterTypes);
		return serviceFastMethod.invoke(serviceBean, parameters);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		LOG.error(" ********服务出现异常 *************", cause);
		ctx.close();
	}
}
