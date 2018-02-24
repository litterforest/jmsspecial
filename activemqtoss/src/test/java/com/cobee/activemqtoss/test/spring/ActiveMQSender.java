package com.cobee.activemqtoss.test.spring;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jms.core.JmsTemplate;

import com.cobee.activemqtoss.service.JmsSenderService;

public class ActiveMQSender {

	public static void main(String[] args) {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:applicationContext-sender-test.xml");
//		JmsTemplate jmsTemplate = ctx.getBean(JmsTemplate.class);
		JmsSenderService jmsSenderService = ctx.getBean(JmsSenderService.class);
		jmsSenderService.sendMessage("Hello");
		jmsSenderService.sendMessage("ni hao");
	}

}
