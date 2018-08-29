package com.lyle.rabbitmq.topics;

import java.io.IOException;

import com.lyle.rabbitmq.public_subscribe.ExchangeConstant;
import com.lyle.rabbitmq.util.ConnectionUtil;
import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

public class Receiver1 {

	public static final String queuename = "topicQueue1";

	public static void main(String[] args) throws IOException {
		Connection connect = ConnectionUtil.getConnection();
		Channel channel = connect.createChannel();
		channel.queueDeclare(queuename, false, false, false, null);
		// 绑定队列到交换机
		// 绑定到交换机指定一个标记，
		// *只能匹配一个词
		// #可以匹配一个或多个词
		channel.queueBind(queuename, ExchangeConstant.t_exchange, "key.*");
		channel.queueBind(queuename, ExchangeConstant.t_exchange, "abc.#");
		channel.basicQos(1);
		DefaultConsumer consumer = new DefaultConsumer(channel) {

			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, BasicProperties properties, byte[] body)
					throws IOException {
				System.out.println("消费者1: " + new String(body));
				channel.basicAck(envelope.getDeliveryTag(), false);
			}
		};
		channel.basicConsume(queuename, false, consumer);
	}
}
