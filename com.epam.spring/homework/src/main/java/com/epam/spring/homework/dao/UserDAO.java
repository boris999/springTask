package com.epam.spring.homework.dao;

import java.util.Optional;
import java.util.Set;

import com.epam.spring.homework.domain.User;
import com.epam.spring.homework.exceptions.AlreadyExistsException;
import com.epam.spring.homework.exceptions.NotFoundException;

public class UserDAO extends DomainObjectDAO {

	public void saveUser(User user) throws Exception {
		if (db.keySet().stream().filter(u -> u.getEmail().equals(user.getEmail())).findFirst().isPresent()) {
			throw new AlreadyExistsException("There is already an user with the same e-mail");
		}
		db.put(user, user);
	}

	public void removeUser(User user) {
		db.remove(user);
	}

	public User getUserByEmail(String email) throws NotFoundException {
		final Optional<User> user = db.keySet().stream().filter(o -> o.getClass().equals(User.class))
				.map(dom -> (User) dom)
				.filter(u -> u.getEmail().equals(email)).findFirst();
		if (user.isPresent()) {
			return user.get();
		} else {
			throw new NotFoundException("User with email " + email + " was not found!");
		}
	}

	public Set<User> getAllUsers() {
		return db.keySet();
	}

}
