package com.lyle.rabbitmq.helloworld;

import java.io.IOException;

import com.lyle.rabbitmq.util.ConnectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

public class Sender {

	public static void main(String[] args) throws IOException {
		// 获取链接
		Connection connect = ConnectionUtil.getConnection();
		// 创建通道
		Channel channel = connect.createChannel();
		// 声明队列
		channel.queueDeclare(QueueConstant.queuename, false, false, false, null);
		// 发送内容
		channel.basicPublish("", QueueConstant.queuename, null, "余额不足".getBytes());
		// 关闭连接
		channel.close();
		connect.close();
	}
}
