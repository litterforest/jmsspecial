package com.cobee.activemqtoss.service;

import javax.annotation.Resource;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

@Service
public class JmsSenderService {

	@Autowired
	private JmsTemplate jmsTemplate;
	@Resource(name = "firstQueue")
	private Destination queue;

	public void sendMessage(final String message) {
		System.out.println("---------------生产者-----------------");
		System.out.println("发送消息：" + message + "   ------" + new DateTime().toString("yyyy-MM-dd HH:mm:ss"));
		jmsTemplate.send(queue, new MessageCreator() {
			public Message createMessage(Session session) throws JMSException {
				return session.createTextMessage(message);
			}
		});

	}

}
