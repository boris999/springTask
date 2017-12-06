package com.epam.spring.hometask.dao;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;

import com.epam.spring.hometask.domain.User;

@Component
public class UserDAO extends DomainObjectDAO implements IUserDAO {

	private static Map<User, User> db = new ConcurrentHashMap<>();

	@Override
	public User getUserByEmail(String email) {
		final Optional<User> user = this.getAllUsers().stream().filter(u -> u.getEmail().equals(email)).findFirst();
		if (user.isPresent()) {
			return user.get();
		}
		return null;
	}


	@Override
	@SuppressWarnings("unchecked")
	public List<User> getAllUsers(){
		return (List<User>) super.getAll("CINEMA_USER", User.class);
	};

	@Override
	public User getUserById(long id){
		return (User) super.getById(id);
	}

}
