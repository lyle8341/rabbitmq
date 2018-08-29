package com.lyle.rabbitmq.util;

import java.io.IOException;

import com.lyle.rabbitmq.constant.RabbitmqConfig;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class ConnectionUtil {

	public static Connection getConnection() throws IOException {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost(RabbitmqConfig.host);
		factory.setPort(RabbitmqConfig.port);
		factory.setUsername(RabbitmqConfig.username);
		factory.setPassword(RabbitmqConfig.password);
		factory.setVirtualHost("/lyle");// 相当于mysql中的数据库
		return factory.newConnection();
	}
}
