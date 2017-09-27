package com.epam.spring.hometask.dao;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import com.epam.spring.homework.domain.DomainObject;
import com.epam.spring.homework.exceptions.NotFoundException;

public class DomainObjectDAO {
	static Map<DomainObject, DomainObject> db = new ConcurrentHashMap<>();

	public DomainObject getUserById(long id) throws NotFoundException {
		final Optional<DomainObject> user = db.keySet().stream().filter(u -> u.getId().equals(id)).findFirst();
		if (user.isPresent()) {
			return user.get();
		} else {
			throw new NotFoundException("No entries found for id " + id);
		}
	}
}
