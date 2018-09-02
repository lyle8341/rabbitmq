package com.lyle.rabbitmq.public_subscribe;

import java.io.IOException;

import com.lyle.rabbitmq.util.ConnectionUtil;
import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

public class Receiver2 {

	/**
	 * 两个消费者使用同一个队列的时候,每个消息只能被一个消费者收到，两一个无法收到
	 */
	public static final String queuename = "psQueue2";

	public static void main(String[] args) throws IOException {
		Connection connect = ConnectionUtil.getConnection();
		Channel channel = connect.createChannel();
		channel.queueDeclare(queuename, false, false, false, null);
		// 绑定队列到交换机
		channel.queueBind(queuename, ExchangeConstant.p_s_exchange, "");
		channel.basicQos(1);
		DefaultConsumer consumer = new DefaultConsumer(channel) {

			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, BasicProperties properties, byte[] body)
					throws IOException {
				System.out.println("消费者2: " + new String(body));
				System.out.println("consumerTag: " + consumerTag);
				System.out.println("deliveryTag: " + envelope.getDeliveryTag());
				channel.basicAck(envelope.getDeliveryTag(), false);
			}
		};
		channel.basicConsume(queuename, false, consumer);
	}
}
