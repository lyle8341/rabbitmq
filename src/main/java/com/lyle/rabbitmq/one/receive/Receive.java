package com.lyle.rabbitmq.one.receive;

import com.lyle.rabbitmq.constant.RabbitmqConfig;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;

public class Receive {

	private static final String QUEUE_NAME = "海洋之心";

	public static void main(String[] args) throws Exception {
		/**
		 * 创建连接连接到MabbitMQ
		 */
		ConnectionFactory factory = new ConnectionFactory();
		// 设置MabbitMQ所在主机ip或者主机名
		factory.setHost(RabbitmqConfig.host);
		factory.setPort(RabbitmqConfig.port);
		factory.setUsername(RabbitmqConfig.username);
		factory.setPassword(RabbitmqConfig.password);
		// 创建一个连接
		Connection connection = factory.newConnection();
		// 创建一个频道
		Channel channel = connection.createChannel();
		// 声明队列，主要为了防止消息接收者先运行此程序，队列还不存在时创建队列。
		channel.queueDeclare(QUEUE_NAME, false, false, false, null);
		System.out.println("Waiting for messages. To exit press CTRL+C");
		// 创建队列消费者
		QueueingConsumer consumer = new QueueingConsumer(channel);
		// 指定消费队列
		channel.basicConsume(QUEUE_NAME, true, consumer);
		while (true) {
			// nextDelivery是一个阻塞方法（内部实现其实是阻塞队列的take方法）
			QueueingConsumer.Delivery delivery = consumer.nextDelivery();
			String message = new String(delivery.getBody());
			System.out.println("Received '" + message + "'");
		}
	}
}
