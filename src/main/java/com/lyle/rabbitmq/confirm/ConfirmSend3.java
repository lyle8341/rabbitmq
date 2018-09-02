package com.lyle.rabbitmq.confirm;

import java.io.IOException;
import java.util.Collections;
import java.util.SortedSet;
import java.util.TreeSet;

import com.lyle.rabbitmq.simple.QueueConstant;
import com.lyle.rabbitmq.util.ConnectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmListener;
import com.rabbitmq.client.Connection;

public class ConfirmSend3 {

	public static void main(String[] args) throws IOException, InterruptedException {
		Connection connect = ConnectionUtil.getConnection();
		Channel channel = connect.createChannel();
		channel.queueDeclare(QueueConstant.cfQueuename3, false, false, false, null);
		channel.confirmSelect();// 开启事务模式
		final SortedSet<Long> confirmSet = Collections.synchronizedSortedSet(new TreeSet<>());
		// 通道添加监听器
		channel.addConfirmListener(new ConfirmListener() {

			// 否定应答
			@Override
			public void handleNack(long deliveryTag, boolean multiple) throws IOException {
			}

			// 应答
			@Override
			public void handleAck(long deliveryTag, boolean multiple) throws IOException {
				if (multiple) {
					System.out.println("---handleAck---multiple");
					confirmSet.headSet(deliveryTag + 1).clear();
					;
				} else {
					System.out.println("---handleAck---single");
					confirmSet.remove(deliveryTag);
				}
			}
		});
		while (true) {
			long seqNo = channel.getNextPublishSeqNo();
			channel.basicPublish("", QueueConstant.cfQueuename3, null, "confirm模式消息".getBytes());
			confirmSet.add(seqNo);
		}
	}
}
