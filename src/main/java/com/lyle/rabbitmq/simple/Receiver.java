package com.lyle.rabbitmq.simple;

import java.io.IOException;

import com.lyle.rabbitmq.util.ConnectionUtil;
import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConsumerCancelledException;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.QueueingConsumer;
import com.rabbitmq.client.QueueingConsumer.Delivery;
import com.rabbitmq.client.ShutdownSignalException;

public class Receiver {

	public static void main(String[] args)
			throws IOException, ShutdownSignalException, ConsumerCancelledException, InterruptedException {
		Connection connect = ConnectionUtil.getConnection();
		Channel channel = connect.createChannel();
		// Channel c2 = connect.createChannel();
		// System.out.println(channel.hashCode());
		// System.out.println(c2.hashCode());
		channel.queueDeclare(QueueConstant.simplequeuename, false, false, false, null);
		// oldApi(channel);
		newApi(channel);
	}

	private static void newApi(Channel channel) throws IOException {
		DefaultConsumer consumer = new DefaultConsumer(channel) {

			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, BasicProperties properties, byte[] body)
					throws IOException {
				System.out.println(new String(body));
			}
		};
		// 监听队列
		channel.basicConsume(QueueConstant.simplequeuename, true, consumer);
	}

	@SuppressWarnings("unused")
	private static void oldApi(Channel channel) throws IOException, InterruptedException {
		QueueingConsumer consumer = new QueueingConsumer(channel);
		// 接收消息
		channel.basicConsume(QueueConstant.simplequeuename, true, consumer);
		while (true) {
			// 获取消息
			Delivery delivery = consumer.nextDelivery();// 下一个到达的
			String message = new String(delivery.getBody());
			System.out.println(message);
		}
	}
}
