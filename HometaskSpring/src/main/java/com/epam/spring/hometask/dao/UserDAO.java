package com.epam.spring.hometask.dao;

import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.epam.spring.hometask.domain.User;
import com.epam.spring.hometask.exeptions.NotFoundException;

@Component
public class UserDAO implements IUserDAO, InitializingBean {

private static Map<User, User> db = new ConcurrentHashMap<>();
@Autowired
private JdbcTemplate jdbcTemplate;
	
	public User getById(long id) throws NotFoundException {
		Optional<User> entry = db.keySet().stream().filter(u -> u.getId().equals(id)).findFirst();
		if (entry.isPresent()) {
			return entry.get();
		} else {
			throw new NotFoundException("No entries found for id " + id);
		}
	}

	@Override
	public void saveUser(User user) {
		//db.put(user, user);
		String statement = "INSERT INTO USERS (ID, FIRST_NAME, LAST_NAME, EMAIL, BIRTHDAY) VALUES(?,?,?,?,?)";
		jdbcTemplate.update(statement, user.getId(), user.getFirstName(), user.getLastName(), user.getEmail(), user.getBirthday());
	}
	
	
	
	@Override
	public void removeUser(User user) {
		db.remove(user);
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
		return db.keySet();
	}
	

	@Override
	public void afterPropertiesSet() throws Exception {
		jdbcTemplate.execute("CREATE USERS");
		
	}
	

}
