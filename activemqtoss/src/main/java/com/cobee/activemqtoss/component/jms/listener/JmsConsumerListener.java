package com.cobee.activemqtoss.component.jms.listener;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

@Service
public class JmsConsumerListener {

	@JmsListener(destination = "firstQueue")
	public void receiveMessage(String message) {
		System.out.println("---------------消费者监听-----------------");
		System.out.println("接收纯文本消息：" + message);
	}

}
