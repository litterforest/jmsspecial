package com.cobee.activemqtoss.test;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

public class ActiveMQTopicSendTester {

	public static void main(String[] args) throws Exception {
		ConnectionFactory connFactory = new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_USER, ActiveMQConnection.DEFAULT_PASSWORD, ActiveMQConnection.DEFAULT_BROKER_URL);
		Connection conn = connFactory.createConnection();
		conn.start();
		Session session = conn.createSession(false, Session.AUTO_ACKNOWLEDGE); // 自动消费消息数据
		Destination destination = session.createTopic("firstTopic");
		MessageProducer messageProducer = session.createProducer(destination);
		messageProducer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
		for (int i = 1; i <= 10; i++)
		{
			TextMessage textMessage = session.createTextMessage("第"+ i +"条消息数据");
			messageProducer.send(textMessage);
		}
		
		System.out.println("数据发送完毕");
		
		if (session != null)
		{
			session.close();
		}
		if (conn != null)
		{
			conn.close();
		}
		
	}

}
