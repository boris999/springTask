package com.epam.spring.hometask.service;

import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.epam.spring.hometask.console.UserCreator;
import com.epam.spring.hometask.dao.UserDAO;
import com.epam.spring.hometask.domain.User;

public class UserService implements ApplicationContextAware {

	private UserDAO dao;
	private UserCreator creator;
	private ApplicationContext context;

	private UserService(UserDAO dao) {
		this.dao = dao;
	}

	public void createUser() throws IOException {
		User user = this.creator.createUser();
		synchronized (user) {
			Optional<Long> lastEventID = this.dao.getAllUsers().stream().map(e -> e.getId()).max((l1, l2) -> (Long.compare(l1, l2)));
			if (lastEventID.isPresent()) {
				user.setId(lastEventID.get() + 1);
			} else {
				user.setId(1L);
			}
		}
		this.dao.saveUser(user);
		System.out.println("Event saved");
	}
	// save, remove, getById, getUserByEmail, getAll

	@Override
	public void setApplicationContext(ApplicationContext context) throws BeansException {
		this.context = context;
	}
}
