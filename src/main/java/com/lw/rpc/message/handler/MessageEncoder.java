package com.lw.rpc.message.handler;

import com.lw.rpc.message.Message;
import com.lw.rpc.message.MessageHeader;
import com.lw.rpc.serialize.ProtostuffSerializeUtil;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * 编码，消息转换成byte进行传输
 * 
 * @author sdc
 *
 */
public class MessageEncoder extends MessageToByteEncoder<Message> {

	private Class<?> genericClass;

	public MessageEncoder(Class<?> genericClass) {
		this.genericClass = genericClass;
	}

	@Override
	protected void encode(ChannelHandlerContext ctx, Message message, ByteBuf out) throws Exception {
		// TODO Auto-generated method stub
		// 头部数据
		// MessageHeader header = message.getHeader();
		//
		// //注意写入顺序
		// out.writeInt(header.getVersion());
		// out.writeInt(message.getBody().length());
		// out.writeBytes(header.getSessionId().getBytes());
		//
		// out.writeBytes(message.getBody().getBytes());

		if (genericClass.isInstance(message)) {
			byte[] data = ProtostuffSerializeUtil.serialize(message);
			out.writeInt(data.length);
			out.writeBytes(data);
		}
	}

}
