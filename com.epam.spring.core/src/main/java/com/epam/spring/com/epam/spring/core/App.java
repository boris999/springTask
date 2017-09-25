package com.epam.spring.com.epam.spring.core;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {
	private Client client;
	private EventLogger eventLogger;
	private App app;

	public App(Client client, EventLogger eventLogger) {
		super();
		this.client = client;
		this.eventLogger = eventLogger;
	}

	public static void main(String[] args) {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("spring.xml");
		App app = (App) ctx.getBean("app");
		app.logEvent("Some event for 1");
		app.logEvent("Some event for 2");

	}

	private void logEvent(String msg) {
		String message = msg.replaceAll(this.client.getId(), this.client.getName());
		this.eventLogger.logEvent(message);
	}
}
