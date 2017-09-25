package com.epam.spring.com.epam.spring.core;

public class Client {

	private String id;
	private String fullName;

	public Client(String id, String name) {
		this.id = id;
		this.fullName = name;
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return this.fullName;
	}

	public void setName(String name) {
		this.fullName = name;
	}

}
