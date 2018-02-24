package com.cobee.activemqtoss.test;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.joda.time.DateTime;

public class ActiveMQReplySenderTester {

	private static ReplyMessageListener replyMessageListener = new ReplyMessageListener();
	
	public static void main(String[] args) throws JMSException {
		
		ConnectionFactory connFactory = new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_USER,
				ActiveMQConnection.DEFAULT_PASSWORD, ActiveMQConnection.DEFAULT_BROKER_URL);
		Connection conn = connFactory.createConnection();
		conn.start();
		Session session = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);
		Destination destination = session.createQueue("firstQueue");
		MessageProducer messageProducer = session.createProducer(destination);
		sendMessage(session, messageProducer);
//		sendMessage(session, messageProducer);
	}
	
	private static void sendMessage(Session session, MessageProducer messageProducer) throws JMSException
	{
		TextMessage textMessage = session.createTextMessage("Hi I am cobee 1");
		System.out.println("发送消息: " + textMessage.getText() + "  ------" + new DateTime().toString("yyyy-MM-dd HH:mm:ss"));
		
		// 创建临时回应队列
		Destination tmpQueue = session.createTemporaryQueue();
		MessageConsumer consumer = session.createConsumer(tmpQueue);
		consumer.setMessageListener(replyMessageListener);
//		System.out.println("tmpQueue: " + tmpQueue.toString());
		textMessage.setJMSReplyTo(tmpQueue);
		messageProducer.send(textMessage);
	}

	private static class ReplyMessageListener implements MessageListener
	{

		@Override
		public void onMessage(Message message) {
			if (message instanceof TextMessage)
			{
				TextMessage textMessage = (TextMessage) message;
				try {
					String content = textMessage.getText();
					System.out.println("回复消息: " + content + "  ------" + new DateTime().toString("yyyy-MM-dd HH:mm:ss"));
				} catch (JMSException e) {
					e.printStackTrace();
				}
			}
		}
		
	}
	
}
