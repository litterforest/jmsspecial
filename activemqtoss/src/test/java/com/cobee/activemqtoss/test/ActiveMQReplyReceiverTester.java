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

public class ActiveMQReplyReceiverTester {

	public static void main(String[] args) throws JMSException {
		ConnectionFactory connFactory = new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_USER,
				ActiveMQConnection.DEFAULT_PASSWORD, ActiveMQConnection.DEFAULT_BROKER_URL);
		Connection conn = connFactory.createConnection();
		conn.start();
		Session session = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);
		Destination destination = session.createQueue("firstQueue");
		MessageConsumer messageConsumer = session.createConsumer(destination);
		messageConsumer.setMessageListener(new MessageListener() {

			@Override
			public void onMessage(Message message) {
				if (message instanceof TextMessage)
				{
					TextMessage textMessage = (TextMessage) message;
					try {
						String content = textMessage.getText();
						System.out.println("接收到消息：" + content + "   ------" + new DateTime().toString("yyyy-MM-dd HH:mm:ss"));
						
						Thread.sleep(5000);
						
						// 回复发送者消息
						Destination replyQueue = message.getJMSReplyTo();
						MessageProducer messageProducer = session.createProducer(replyQueue);
						TextMessage replyMessage = session.createTextMessage("Hi, I am jone 2");
						messageProducer.send(replyMessage);
						System.out.println("发送回复消息：" + replyMessage.getText() + "   ------" + new DateTime().toString("yyyy-MM-dd HH:mm:ss"));
						
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				
			}});
		
	}

}
