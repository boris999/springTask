package com.epam.spring.hometask.dao;

import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiFunction;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.epam.spring.hometask.domain.User;
import com.epam.spring.hometask.exeptions.NotFoundException;

@Component
public class UserDAO implements IUserDAO {

	private static Map<User, User> db = new ConcurrentHashMap<>();
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public User getById(long id) throws NotFoundException {
		Optional<User> entry = db.keySet().stream().filter(u -> u.getId().equals(id)).findFirst();
		if (entry.isPresent()) {
			return entry.get();
		} else {
			throw new NotFoundException("No entries found for id " + id);
		}
	}
	@Transactional
	@Override
	public void saveUser(User user) {
		Session session = null;
		try {
			session = sessionFactory.openSession();
			db(user, session, saveFunction);
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}

	BiFunction<User, Session, User> saveFunction = (u, s) -> {
		s.save(u);
		return u;
	};



	private void db(User user, Session session, BiFunction<User, Session, User> function) {
		Transaction transaction = null;
		try {
			session = sessionFactory.openSession();
			transaction = session.getTransaction();
			transaction.begin();
			function.apply(user, session);
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		}
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
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}


	//	@Override
	//	public void afterPropertiesSet() throws Exception {
	//		jdbcTemplate.execute("CREATE USERS");
	//
	//	}


}
