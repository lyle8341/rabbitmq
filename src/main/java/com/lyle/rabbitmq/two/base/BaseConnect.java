package com.lyle.rabbitmq.two.base;

import com.lyle.rabbitmq.constant.ServerParams;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class BaseConnect {

	protected Channel channel;

	protected Connection connection;

	protected String queuename;

	public BaseConnect(String queuename) throws Exception {
		this.queuename = queuename;
		// 打开连接和创建频道
		ConnectionFactory factory = new ConnectionFactory();
		// 设置MabbitMQ所在主机ip或者主机名
		factory.setHost(ServerParams.host);
		factory.setPort(ServerParams.port);
		factory.setUsername(ServerParams.username);
		factory.setPassword(ServerParams.password);
		connection = factory.newConnection();
		channel = connection.createChannel();
		// 声明创建队列
		channel.queueDeclare(queuename, false, false, false, null);
	}
}
