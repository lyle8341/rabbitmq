package com.lyle.rabbitmq.tx;

import java.io.IOException;

import com.lyle.rabbitmq.simple.QueueConstant;
import com.lyle.rabbitmq.util.ConnectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

public class TxSend {

	public static void main(String[] args) throws IOException {
		Connection connect = ConnectionUtil.getConnection();
		Channel channel = connect.createChannel();
		channel.queueDeclare(QueueConstant.txQueuename, false, false, false, null);
		try {
			channel.txSelect();// 开启事务模式
			channel.basicPublish("", QueueConstant.txQueuename, null, "事务模式消息".getBytes());
			channel.txCommit();
		} catch (Exception e) {
			channel.txRollback();
		}
		channel.close();
		connect.close();
	}
}
