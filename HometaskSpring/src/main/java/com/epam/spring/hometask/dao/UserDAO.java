package com.epam.spring.hometask.dao;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import com.epam.spring.hometask.domain.User;
import com.epam.spring.hometask.exeptions.NotFoundException;

public class UserDAO extends DomainObjectDAO {
	// TODO getById to return casted from here
	public void saveUser(User user) throws Exception {
		this.saveDomainObject(user);
	}

	public void removeUser(User user) {
		this.removeDomainObject(user);
	}

	public User getUserByEmail(String email) throws NotFoundException {
		final Optional<User> user = this.getAllUsers().stream()
				.filter(u -> u.getEmail().equals(email))
				.findFirst();
		if (user.isPresent()) {
			return user.get();
		} else {
			throw new NotFoundException("User with email " + email + " was not found!");
		}
	}

	public Set<User> getAllUsers() {
		return this.getAllOfSameType(User.class).stream()
				.map(dom -> (User) dom).collect(Collectors.toSet());
	}

}
