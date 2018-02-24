package com.cobee.activemqtoss.test;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
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
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ActivemqTester {
	
	private ConnectionFactory connFactory = null;
	private Connection connection = null;
	private Session session = null;
	private Destination dest = null;

	@Before
	public void setUpBefore() throws Exception {
		connFactory = new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_USER, ActiveMQConnection.DEFAULT_PASSWORD, ActiveMQConnection.DEFAULT_BROKER_URL);
		connection = connFactory.createConnection();
	}

//	@After
//	public void tearDownAfter() throws Exception {
//		if (session != null)
//		{
//			session.close();
//		}
//		if (connection != null)
//		{
//			connection.close();
//		}
//	}

	/**
	 * ����Ϣ�����з�����Ϣ����
	 * @throws JMSException
	 */
	@Test
	public void sendMessage() throws JMSException {
		connection.start();
		session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		dest = session.createQueue("firstQueue");
		MessageProducer producer = session.createProducer(dest);
		producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
		for (int i = 1; i <= 10; i++)
		{
			TextMessage textMessage = session.createTextMessage("��"+ i +"����Ϣ");
			producer.send(textMessage);
		}
		System.out.println("���ݷ������");
	}

	/**
	 * �ͻ��˴�������ȥ������Ϣ������Ϣ
	 * @throws JMSException
	 */
	@Test
	public void receiveActive() throws JMSException
	{
		connection.start();
		session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		dest = session.createQueue("firstQueue");
		MessageConsumer consumer = session.createConsumer(dest);
		while(true)
		{
			Message message = consumer.receive();
			if (message != null)
			{
				TextMessage textMessage = (TextMessage) message;
				System.out.println(textMessage.getText());
			}
			else
			{
				break;
			}
		}
	}
	
	/**
	 * ʹ�ü�����ģʽ��������Ϣ
	 * @throws JMSException
	 */
	@Test
	public void receiveListener() throws JMSException
	{
		connection.start();
		session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
		dest = session.createQueue("firstQueue");
		MessageConsumer consumer = session.createConsumer(dest);
		consumer.setMessageListener(new MessageListener() {

			@Override
			public void onMessage(Message message) {
				if (message instanceof TextMessage)
				{
					try {
						String text = ((TextMessage) message).getText();
						System.out.println("���յ�����Ϣ��" + text + "------" + new DateTime().toString("yyyy-MM-dd HH:mm:ss"));
						Thread.sleep(2000);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}});
		System.out.println("������ʼ����");
	}
	
}
