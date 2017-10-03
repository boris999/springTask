package com.epam.spring.hometask.service;

import java.io.BufferedReader;
import java.io.IOException;

import com.epam.spring.hometask.domain.User;
import com.epam.spring.hometask.exeptions.NotFoundException;

public interface IUserService {
	public void createUser(BufferedReader br) throws IOException;

	public User selectUser(BufferedReader br) throws IOException, NotFoundException;

	public void removeUser(User user);

	public User getUserById(long id) throws NotFoundException;

	public User getUserByEmail(String email) throws NotFoundException;
}
