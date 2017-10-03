package com.epam.spring.hometask.dao;

import java.util.Set;

import com.epam.spring.hometask.domain.User;
import com.epam.spring.hometask.exeptions.NotFoundException;

public interface IUserDAO {
	public User getById(long id) throws NotFoundException;

	public void saveUser(User user);

	public void removeUser(User user);

	public User getUserByEmail(String email) throws NotFoundException;

	public Set<User> getAllUsers();
}
