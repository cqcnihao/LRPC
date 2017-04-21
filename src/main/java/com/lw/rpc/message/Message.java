package com.lw.rpc.message;

/**
 * 消息主体
 * 
 * @author sdc
 *
 */
public class Message {

	/**
	 * 消息的头部
	 */
	private MessageHeader header;
	
	/**
	 * 消息体
	 */
	private String body;

	public Message(MessageHeader header, String body) {
		super();
		this.header = header;
		this.body = body;
	}

	public MessageHeader getHeader() {
		return header;
	}

	public void setHeader(MessageHeader header) {
		this.header = header;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}
	
}
