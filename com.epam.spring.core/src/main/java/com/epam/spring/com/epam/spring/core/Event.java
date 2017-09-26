package com.epam.spring.com.epam.spring.core;

import java.util.Date;
import java.util.Random;

public class Event {
	private int id = new Random().nextInt(100);
	private String msg;
	private Date date;

	public Event(Date date) {
		this.date = date;
	}

	public String getMsg() {
		return this.msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}
