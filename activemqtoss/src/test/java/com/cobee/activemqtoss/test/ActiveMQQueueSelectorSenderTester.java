package com.cobee.activemqtoss.test;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.joda.time.DateTime;

/**
 * 往消息队列里面发送消息数据，使用消息选择器功能
 * @author Administrator
 *
 */
public class ActiveMQQueueSelectorSenderTester {

	public static void main(String[] args) throws JMSException {
		ConnectionFactory connFactory = new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_USER,
				ActiveMQConnection.DEFAULT_PASSWORD, ActiveMQConnection.DEFAULT_BROKER_URL);
		Connection conn = connFactory.createConnection();
		conn.start();
		Session session = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);
		Destination destination = session.createQueue("selectorQueue");
		
		MessageProducer messageProducer = session.createProducer(destination);
		messageProducer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
		
		sendMessage(session, messageProducer, "ForConsumerA");
		sendMessage(session, messageProducer, "ForConsumerB");
		sendMessage(session, messageProducer, "ForConsumerC");
		sendMessage(session, messageProducer, "ForConsumerD");
		
		if (session != null)
		{
			session.close();
		}
		if (conn != null)
		{
			conn.close();
		}
	}

	private static void sendMessage(Session session, MessageProducer messageProducer, String selector) throws JMSException
	{
		TextMessage textMessage = session.createTextMessage("消息选择器是" + selector);
		textMessage.setStringProperty("receiver", selector);
		messageProducer.send(textMessage);
		System.out.println("发送消息，消息选择器是" + selector + " ------ " + new DateTime().toString("yyyy-MM-dd HH:mm:ss"));
	}
	
}
