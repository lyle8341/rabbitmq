package com.lyle.rabbitmq.confirm;

import java.io.IOException;

import com.lyle.rabbitmq.simple.QueueConstant;
import com.lyle.rabbitmq.util.ConnectionUtil;
import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

public class ConfirmReceiver1 {

	public static void main(String[] args) throws IOException {
		Connection connect = ConnectionUtil.getConnection();
		Channel channel = connect.createChannel();
		channel.queueDeclare(QueueConstant.cfQueuename1, false, false, false, null);
		DefaultConsumer consumer = new DefaultConsumer(channel) {

			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, BasicProperties properties, byte[] body)
					throws IOException {
				System.out.println(new String(body));
			}
		};
		channel.basicConsume(QueueConstant.cfQueuename1, true, consumer);
	}
}
