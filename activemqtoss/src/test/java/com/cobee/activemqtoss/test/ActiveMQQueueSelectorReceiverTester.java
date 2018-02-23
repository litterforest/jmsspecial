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

import org.apache.activemq.ActiveMQConnectionFactory;
import org.joda.time.DateTime;

public class ActiveMQQueueSelectorReceiverTester {

	public static void main(String[] args) throws JMSException {
		ConnectionFactory connFactory = new ActiveMQConnectionFactory();
		Connection conn = connFactory.createConnection();
		conn.start();
		Session session = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);
		Destination destination = session.createQueue("selectorQueue");
		
		MessageConsumer messageConsumerA = session.createConsumer(destination, "receiver='ForConsumerA'");
		MessageConsumer messageConsumerB = session.createConsumer(destination, "receiver='ForConsumerB'");
		MessageConsumer messageConsumerC = session.createConsumer(destination, "receiver='ForConsumerC'");
		
		messageConsumerA.setMessageListener(new SelectorMessageListener("A"));
		messageConsumerB.setMessageListener(new SelectorMessageListener("B"));
		messageConsumerC.setMessageListener(new SelectorMessageListener("C"));
	}
	
	private static class SelectorMessageListener implements MessageListener
	{

		private String selectorName;
		
		public SelectorMessageListener() {
		}
		
		public SelectorMessageListener(String selectorName) {
			this.selectorName = selectorName;
		}
		
		@Override
		public void onMessage(Message message) {
			
			if (message instanceof TextMessage)
			{
				TextMessage textMessage = (TextMessage) message;
				try {
					String content = textMessage.getText();
					System.out.println(selectorName + "接收消息：" + content + " ------ " + new DateTime().toString("yyyy-MM-dd HH:mm:ss"));
				} catch (JMSException e) {
					e.printStackTrace();
				}
			}
			
		}
		
	}
	

}
