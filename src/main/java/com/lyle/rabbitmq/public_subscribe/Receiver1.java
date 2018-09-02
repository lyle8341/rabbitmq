package com.lyle.rabbitmq.public_subscribe;

import java.io.IOException;

import com.lyle.rabbitmq.util.ConnectionUtil;
import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

/**
 * @ClassName: Receiver1
 * @Description: 同一个会话， consumerTag 是固定的 可以做此会话的名字，<br>
 *               deliveryTag 每次接收消息+1，可以做此消息处理通道的名字。 因此 deliveryTag 可以用来回传告诉 rabbitmq 这个消息处理成功
 *               清除此消息（basicAck方法）。
 * @author: Lyle
 * @date: 2018年8月30日 上午10:13:20
 */
public class Receiver1 {

	public static final String queuename = "psQueue1";

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
				System.out.println("消费者1: " + new String(body));
				System.out.println("consumerTag: " + consumerTag);
				System.out.println("deliveryTag: " + envelope.getDeliveryTag());
				channel.basicAck(envelope.getDeliveryTag(), false);
			}
		};
		channel.basicConsume(queuename, false, consumer);
	}
}
