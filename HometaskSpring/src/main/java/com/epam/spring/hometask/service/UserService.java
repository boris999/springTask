package com.epam.spring.hometask.service;

import java.io.BufferedReader;
import java.io.IOException;

import com.epam.spring.hometask.console.UserCreator;
import com.epam.spring.hometask.dao.IUserDAO;
import com.epam.spring.hometask.dao.UserDAO;
import com.epam.spring.hometask.domain.User;

public class UserService implements IUserService {

	private IUserDAO dao;
	private UserCreator creator;

	public UserService(UserDAO dao, UserCreator creator) {
		this.dao = dao;
		this.creator = creator;
	}

	@Override
	public void createUser(BufferedReader br) throws IOException {
		User user = this.creator.createUser(br);
		this.dao.save(user);
	}

	@Override
	public User selectUser(BufferedReader br) throws IOException {
		return this.creator.selectUser(this, br);
	}

	@Override
	public void removeUser(User user) {
		this.dao.remove(user);
	}

	@Override
	public User getUserById(long id) {
		return this.dao.getUserById(id);
	}

	@Override
	public User getUserByEmail(String email)  {
		return this.dao.getUserByEmail(email);
	}

}
