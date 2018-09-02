package com.lyle.rabbitmq.confirm;

import java.io.IOException;

import com.lyle.rabbitmq.simple.QueueConstant;
import com.lyle.rabbitmq.util.ConnectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

public class ConfirmSend1 {

	public static void main(String[] args) throws IOException, InterruptedException {
		Connection connect = ConnectionUtil.getConnection();
		Channel channel = connect.createChannel();
		channel.queueDeclare(QueueConstant.cfQueuename1, false, false, false, null);
		channel.confirmSelect();// 开启事务模式
		channel.basicPublish("", QueueConstant.cfQueuename1, null, "confirm模式消息".getBytes());
		if (!channel.waitForConfirms()) {
			System.out.println("发送失败");
		} else {
			System.out.println("发送成功");
		}
		channel.close();
		connect.close();
	}
}
