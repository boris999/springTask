package com.epam.spring.hometask.service;

import java.io.BufferedReader;
import java.io.IOException;

import com.epam.spring.hometask.domain.User;

public interface IUserService {
	public void createUser(BufferedReader br) throws IOException;

	public User selectUser(BufferedReader br) throws IOException;

	public void removeUser(User user);

	public User getUserById(long id);

	public User getUserByEmail(String email);
}
