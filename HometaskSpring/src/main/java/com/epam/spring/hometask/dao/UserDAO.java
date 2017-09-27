package com.epam.spring.hometask.dao;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import com.epam.spring.hometask.domain.User;
import com.epam.spring.hometask.exeptions.AlreadyExistsException;
import com.epam.spring.hometask.exeptions.NotFoundException;

@Local
@Stateless
public class UserDAO extends DomainObjectDAO {

	public void saveUser(User user) throws Exception {
		if (db.keySet().stream()
				.filter(o -> o.getClass().equals(User.class))
				.map(dom -> (User) dom)
				.filter(u -> u.getEmail().equals(user.getEmail()))
				.findFirst().isPresent()) {
			throw new AlreadyExistsException("There is already an user with the same e-mail");
		}
		db.put(user, user);
	}

	public void removeUser(User user) {
		db.remove(user);
	}

	public User getUserByEmail(String email) throws NotFoundException {
		final Optional<User> user = db.keySet().stream()
				.filter(o -> o.getClass().equals(User.class))
				.map(dom -> (User) dom)
				.filter(u -> u.getEmail().equals(email))
				.findFirst();
		if (user.isPresent()) {
			return user.get();
		} else {
			throw new NotFoundException("User with email " + email + " was not found!");
		}
	}

	public Set<User> getAllUsers() {
		return db.keySet().stream()
				.filter(o -> o.getClass().equals(User.class))
				.map(dom -> (User) dom).collect(Collectors.toSet());
	}

}
