package com.lyle.rabbitmq.workqueue;

import java.io.IOException;

import com.lyle.rabbitmq.simple.QueueConstant;
import com.lyle.rabbitmq.util.ConnectionUtil;
import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConsumerCancelledException;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.ShutdownSignalException;

public class Receiver1 {

	public static void main(String[] args)
			throws IOException, ShutdownSignalException, ConsumerCancelledException, InterruptedException {
		Connection connect = ConnectionUtil.getConnection();
		Channel channel = connect.createChannel();
		channel.queueDeclare(QueueConstant.workqueuename, false, false, false, null);
		// 每个消费者发送确认消息之前，消息队列不发送下一个消息到消费者，一次只处理一个消息
		channel.basicQos(1);
		DefaultConsumer consumer = new DefaultConsumer(channel) {

			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, BasicProperties properties, byte[] body)
					throws IOException {
				// 当收到消息时候
				System.out.println("消费者1收到的消息：" + new String(body));
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				channel.basicAck(envelope.getDeliveryTag(), false);
			}
		};
		channel.basicConsume(QueueConstant.workqueuename, false, consumer);
	}
}
