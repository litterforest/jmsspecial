package com.cobee.activemqtoss.test;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

public class ActiveMQTopicReceiverTester {

	public static void main(String[] args) throws JMSException {
//		System.out.println("DEFAULT_USER=" + ActiveMQConnection.DEFAULT_USER);
//		System.out.println("DEFAULT_PASSWORD=" + ActiveMQConnection.DEFAULT_PASSWORD);
//		System.out.println("DEFAULT_BROKER_URL=" + ActiveMQConnection.DEFAULT_BROKER_URL);
		ConnectionFactory connFactory = new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_USER, ActiveMQConnection.DEFAULT_PASSWORD, ActiveMQConnection.DEFAULT_BROKER_URL);
		Connection conn = connFactory.createConnection();
		conn.start();
		Session session = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);
		Destination destination = session.createTopic("firstTopic");
		MessageConsumer messageConsumer = session.createConsumer(destination);
		messageConsumer.setMessageListener(new MessageListener() {

			@Override
			public void onMessage(Message message) {
				
				if (message instanceof TextMessage)
				{
					try {
						String text = ((TextMessage) message).getText();
						System.out.println("接收到的数据:" + text);
					} catch (JMSException e) {
						e.printStackTrace();
					}
				}
				
			}});
		
		
	}

}
