package com.cobee.activemqtoss.test;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.joda.time.DateTime;

public class ActiveMQReceiveListener {

	public static void main(String[] args) throws Exception {
		ConnectionFactory connFactory = null;
		Connection conn = null;
		Session session = null;
		try {
			connFactory = new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_USER,
					ActiveMQConnection.DEFAULT_PASSWORD, ActiveMQConnection.DEFAULT_BROKER_URL);
			conn = connFactory.createConnection();
			conn.start();
			session = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);
			Destination dest = session.createQueue("firstQueue");
			MessageConsumer consumer = session.createConsumer(dest);
			consumer.setMessageListener(new MessageListener() {

				@Override
				public void onMessage(Message message) {
					if (message instanceof TextMessage) {
						try {
							String text = ((TextMessage) message).getText();
							System.out.println(
									"接收到的消息：" + text + "------" + new DateTime().toString("yyyy-MM-dd HH:mm:ss"));
							Thread.sleep(2000);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}

			});
			System.out.println("监听开始……");
		} catch (Exception e) {
			e.printStackTrace();
		}
		// finally
		// {
		// if (session != null)
		// {
		// session.close();
		// }
		// if (conn != null)
		// {
		// conn.close();
		// }
		// }

	}

}

class MyMessageListener implements MessageListener {

	@Override
	public void onMessage(Message message) {
		if (message instanceof TextMessage) {
			try {
				String text = ((TextMessage) message).getText();
				System.out.println("接收到的消息：" + text + "------" + new DateTime().toString("yyyy-MM-dd HH:mm:ss"));
				Thread.sleep(2000);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
