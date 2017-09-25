package com.epam.spring.com.epam.spring.core;

public class ConsoleEventLogger implements EventLogger {

	public void logEvent(String message) {
		System.out.println(message);
	}
}
