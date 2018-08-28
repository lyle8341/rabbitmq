package com.lyle.rabbitmq.two.receive;

import java.io.IOException;

import org.apache.commons.lang.SerializationUtils;

import com.lyle.rabbitmq.two.base.BaseConnect;
import com.lyle.rabbitmq.two.send.MsgInfo;
import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.ShutdownSignalException;

public class Receiver extends BaseConnect implements Runnable, Consumer {

	public Receiver(String queuename) throws Exception {
		super(queuename);
	}

	// 实现Runnable的run方法
	@Override
	public void run() {
		try {
			channel.basicConsume(queuename, true, this);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 下面这些方法都是实现Consumer接口的
	 **/
	// 当消费者注册完成自动调用
	@Override
	public void handleConsumeOk(String consumerTag) {
		System.out.println("Consumer " + consumerTag + " registered");
	}

	// 当消费者接收到消息会自动调用
	@Override
	public void handleDelivery(String consumerTag, Envelope env, BasicProperties props, byte[] body)
			throws IOException {
		MsgInfo messageInfo = (MsgInfo) SerializationUtils.deserialize(body);
		System.out.println("Message ( " + "channel : " + messageInfo.getChannel() + " , content : "
				+ messageInfo.getContent() + " ) received.");
	}

	// 下面这些方法可以暂时不用理会
	@Override
	public void handleCancelOk(String consumerTag) {
	}

	@Override
	public void handleCancel(String consumerTag) throws IOException {
	}

	@Override
	public void handleShutdownSignal(String consumerTag, ShutdownSignalException sig) {
	}

	@Override
	public void handleRecoverOk(String consumerTag) {
	}
}
