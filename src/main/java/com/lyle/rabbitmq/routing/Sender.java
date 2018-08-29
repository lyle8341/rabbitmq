package com.lyle.rabbitmq.routing;

import java.io.IOException;

import com.lyle.rabbitmq.public_subscribe.ExchangeConstant;
import com.lyle.rabbitmq.util.ConnectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

public class Sender {

	public static void main(String[] args) throws IOException {
		Connection connect = ConnectionUtil.getConnection();
		Channel channel = connect.createChannel();
		// 路由模式
		channel.exchangeDeclare(ExchangeConstant.r_exchange, "direct");
		channel.basicPublish(ExchangeConstant.r_exchange, "key3", null, "路由模式消息".getBytes());
		channel.close();
		connect.close();
	}
}
