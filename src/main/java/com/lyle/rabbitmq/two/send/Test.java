package com.lyle.rabbitmq.two.send;

import com.lyle.rabbitmq.two.receive.Receiver;

public class Test {

	public static void main(String[] args) throws Exception {
		Receiver receiver = new Receiver("testQueue");
		Thread receiverThread = new Thread(receiver);
		receiverThread.start();
		Sender sender = new Sender("testQueue");
		for (int i = 0; i < 5; i++) {
			MsgInfo messageInfo = new MsgInfo();
			messageInfo.setChannel("test");
			messageInfo.setContent("msg" + i);
			sender.sendMessage(messageInfo);
		}
	}
}
