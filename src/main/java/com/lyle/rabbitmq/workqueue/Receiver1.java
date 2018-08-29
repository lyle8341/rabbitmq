package com.lyle.rabbitmq.workqueue;

import java.io.IOException;

import com.lyle.rabbitmq.helloworld.QueueConstant;
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
		channel.queueDeclare(QueueConstant.queuename, false, false, false, null);
		// 告诉服务器，在我没有确认当前消息完成之前不要再给我分配新的消息
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
		channel.basicConsume(QueueConstant.queuename, false, consumer);
	}
}
