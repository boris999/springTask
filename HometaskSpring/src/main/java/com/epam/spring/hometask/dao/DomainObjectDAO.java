package com.epam.spring.hometask.dao;

import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import com.epam.spring.hometask.domain.DomainObject;
import com.epam.spring.hometask.exeptions.NotFoundException;

public class DomainObjectDAO {
	static Map<DomainObject, DomainObject> db = new ConcurrentHashMap<>();

	public DomainObject getById(long id) throws NotFoundException {
		final Optional<DomainObject> entry = db.keySet().stream().filter(u -> u.getId().equals(id)).findFirst();
		if (entry.isPresent()) {
			return entry.get();
		} else {
			throw new NotFoundException("No entries found for id " + id);
		}
	}

	void saveDomainObject(DomainObject domObj) {
		db.put(domObj, domObj);
	}

	void removeDomainObject(DomainObject domObj) {
		db.remove(domObj);
	}

	<T> Set<DomainObject> getAllOfSameType(Class<T> type) {
		return db.keySet().stream()
				.filter(o -> o.getClass().equals(type)).collect(Collectors.toSet());

	}
}
