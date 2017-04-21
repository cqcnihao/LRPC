package com.lw.rpc.message.handler;

import java.util.List;

import com.lw.rpc.message.Message;
import com.lw.rpc.message.MessageHeader;
import com.lw.rpc.serialize.ProtostuffSerializeUtil;
import com.lw.rpc.util.StringUtil;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

/**
 * 消息解码，转换成我们定义好的消息体
 * 
 * @author sdc
 *
 */
public class MessageDecoder extends ByteToMessageDecoder {

	private Class<?> genericClass;

	public MessageDecoder(Class<?> genericClass) {
		this.genericClass = genericClass;
	}

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf bb, List<Object> list) throws Exception {
		// 协议的版本
		// int version = bb.readInt();
		// // 消息的长度
		// int messageLegth = bb.readInt();
		// // 获取sessionId
		// byte[] sessionIdByte = new byte[36];
		//
		// bb.readBytes(sessionIdByte);
		// String sessionId = new String(sessionIdByte);
		//
		// // 消息头
		// MessageHeader header = new MessageHeader(version, messageLegth,
		// sessionId);
		//
		// // 消息体
		// byte[] body = bb.readBytes(bb.readableBytes()).array();
		//
		// // 生成最终的消息
		// Message message = new Message(header, new String(body));
		//
		// list.add(message);

		if (bb.readableBytes() < 4) {
			return;
		}
		bb.markReaderIndex();
		int dataLength = bb.readInt();
		if (dataLength < 0) {
			ctx.close();
		}
		if (bb.readableBytes() < dataLength) {
			bb.resetReaderIndex();
			return;
		}
		byte[] data = new byte[dataLength];
		bb.readBytes(data);

		Object obj = ProtostuffSerializeUtil.deserialize(data, genericClass);
		list.add(obj);
	}

}
