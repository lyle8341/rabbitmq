package com.lyle.rabbitmq.two.send;

import java.io.Serializable;

public class MsgInfo implements Serializable {

	private static final long serialVersionUID = 3600801907619157152L;

	private String channel;

	private String content;

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}
