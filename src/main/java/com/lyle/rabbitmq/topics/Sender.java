package com.lyle.rabbitmq.topics;

import java.io.IOException;

import com.lyle.rabbitmq.public_subscribe.ExchangeConstant;
import com.lyle.rabbitmq.util.ConnectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

public class Sender {

	public static void main(String[] args) throws IOException {
		Connection connect = ConnectionUtil.getConnection();
		Channel channel = connect.createChannel();
		channel.exchangeDeclare(ExchangeConstant.t_exchange, "topic");
		channel.basicPublish(ExchangeConstant.t_exchange, "key.topic", null, "topic 消息模式".getBytes());
		channel.close();
		connect.close();
	}
}
