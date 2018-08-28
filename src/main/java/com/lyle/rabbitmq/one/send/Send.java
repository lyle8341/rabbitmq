package com.lyle.rabbitmq.one.send;

import java.io.IOException;

import com.lyle.rabbitmq.constant.RabbitmqConfig;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class Send {

	private static final String QUEUE_NAME = "海洋之心";

	public static void main(String[] args) throws IOException {
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
		// 指定一个队列
		/**
		 * queueDeclare(String queue,//队列的名字<br>
		 * boolean durable, //该队列是否持久化（即是否保存到磁盘中）<br>
		 * boolean exclusive,//该队列是否为该通道独占的，即其他通道是否可以消费该队列<br>
		 * boolean autoDelete,//该队列不再使用的时候，是否让RabbitMQ服务器自动删除掉<br>
		 * Map<String, Object> arguments)//其他参数
		 */
		channel.queueDeclare(QUEUE_NAME, false, false, false, null);
		String message = "消息队列测试";
		// 往队列中发出一条消息
		channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
		System.out.println("sent '" + message + "'");
		// 关闭频道和连接
		channel.close();
		connection.close();
	}
}
