package com.epam.spring.hometask.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Optional;

import com.epam.spring.hometask.console.UserCreator;
import com.epam.spring.hometask.dao.IUserDAO;
import com.epam.spring.hometask.dao.UserDAO;
import com.epam.spring.hometask.domain.User;
import com.epam.spring.hometask.exeptions.NotFoundException;

public class UserService implements IUserService {

	private IUserDAO dao;
	private UserCreator creator;

	private UserService(UserDAO dao, UserCreator creator) {
		this.dao = dao;
		this.creator = creator;
	}

	@Override
	public void createUser(BufferedReader br) throws IOException {
		User user = this.creator.createUser(br);
		synchronized (user) {
			Optional<Long> lastUserID = this.dao.getAllUsers().stream().map(u -> u.getId()).max((l1, l2) -> (Long.compare(l1, l2)));
			if (lastUserID.isPresent()) {
				user.setId(lastUserID.get() + 1);
			} else {
				user.setId(1L);
			}
		}
		this.dao.saveUser(user);
	}

	@Override
	public User selectUser(BufferedReader br) throws IOException, NotFoundException {
		return this.creator.selectUser(this, br);
	}

	@Override
	public void removeUser(User user) {
		this.dao.removeUser(user);
	}

	@Override
	public User getUserById(long id) throws NotFoundException {
		return this.dao.getById(id);
	}

	@Override
	public User getUserByEmail(String email) throws NotFoundException {
		return this.dao.getUserByEmail(email);
	}

}
