package com.epam.spring.hometask.dao;

import java.util.List;

import com.epam.spring.hometask.domain.User;

public interface IUserDAO extends IDomainObjectDAO {

	public User getUserByEmail(String email);

	public List<User> getAllUsers();

	public User getUserById(long id);
}
