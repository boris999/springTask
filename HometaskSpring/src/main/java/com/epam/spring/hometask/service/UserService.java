package com.epam.spring.hometask.service;

import com.epam.spring.hometask.dao.UserDAO;

public class UserService {

	private UserDAO dao;

	private UserService(UserDAO dao) {
		this.dao = dao;
	}

	// save, remove, getById, getUserByEmail, getAll

}
