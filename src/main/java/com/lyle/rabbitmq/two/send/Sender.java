package com.lyle.rabbitmq.two.send;

import java.io.IOException;
import java.io.Serializable;

import org.apache.commons.lang.SerializationUtils;

import com.lyle.rabbitmq.two.base.BaseConnect;

public class Sender extends BaseConnect {

	public Sender(String queuename) throws Exception {
		super(queuename);
	}

	public void sendMessage(Serializable object) throws IOException {
		channel.basicPublish("", queuename, null, SerializationUtils.serialize(object));
	}
}
