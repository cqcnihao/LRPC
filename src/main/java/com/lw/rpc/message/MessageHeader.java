package com.lw.rpc.message;

/**
 * 消息头部
 * 
 * @author sdc
 *
 */
public class MessageHeader {

	/**
	 * 协议版本
	 */
	private int version;

	/**
	 * 消息内容长度
	 */
	private int contentLength;

	/**
	 * 服务名称
	 */
	private String sessionId;

	public MessageHeader(int version, int contentLength, String sessionId) {
		super();
		this.version = version;
		this.contentLength = contentLength;
		this.sessionId = sessionId;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public int getContentLength() {
		return contentLength;
	}

	public void setContentLength(int contentLength) {
		this.contentLength = contentLength;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

}
