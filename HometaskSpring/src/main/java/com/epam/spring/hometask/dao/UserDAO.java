package com.epam.spring.hometask.dao;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.epam.spring.hometask.domain.User;
import com.epam.spring.hometask.exeptions.NotFoundException;

@Component
public class UserDAO extends DomainObjectDAO implements IUserDAO {

	@Override
	public User getById(long id) throws NotFoundException {
		return (User) super.getById(id);
	}

	@Override
	public void saveUser(User user) {
		this.saveDomainObject(user);
	}

	@Override
	public void removeUser(User user) {
		this.removeDomainObject(user);
	}

	@Override
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

	@Override
	public Set<User> getAllUsers() {
		return this.getAllOfSameType(User.class).stream()
				.map(dom -> (User) dom).collect(Collectors.toSet());
	}

}
