package com.lyle.rabbitmq.public_subscribe;

import java.io.IOException;

import com.lyle.rabbitmq.util.ConnectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

public class Sender {

	public static void main(String[] args) throws IOException {
		Connection connect = ConnectionUtil.getConnection();
		Channel channel = connect.createChannel();
		// fanout 也就是发布订阅模式
		channel.exchangeDeclare(ExchangeConstant.p_s_exchange, "fanout");
		// 发布订阅模式，消息是先发布到交换机中，而交换机没有保存功能，如果没有消费者，则消息就会消失
		channel.basicPublish(ExchangeConstant.p_s_exchange, "", null, "发布订阅模式消息".getBytes());
		channel.close();
		connect.close();
	}
}
