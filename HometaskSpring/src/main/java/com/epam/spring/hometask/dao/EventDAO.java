package com.epam.spring.hometask.dao;

import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.epam.spring.hometask.domain.DomainObject;
import com.epam.spring.hometask.domain.Event;
import com.epam.spring.hometask.exeptions.NotFoundException;

@Component
public class EventDAO implements IEventDAO {
	
	private static Map<Event, Event> db = new ConcurrentHashMap<>();
	
	public Event getById(long id) throws NotFoundException {
		Optional<Event> entry = db.keySet().stream().filter(e -> e.getId().equals(id)).findFirst();
		if (entry.isPresent()) {
			return entry.get();
		} else {
			throw new NotFoundException("No entries found for id " + id);
		}
	}

	@Override
	public void saveEvent(Event event) {
		db.put(event, event);
	}

	@Override
	public void removeEvent(Event event) {
		db.remove(event);
	}

	@Override
	public Event getEventByName(String name) throws NotFoundException {
		final Optional<Event> event = this.getAllEvents().stream()
				.filter(e -> e.getName().equals(name))
				.findFirst();
		if (event.isPresent()) {
			return event.get();
		} else {
			throw new NotFoundException("Event with name " + name + " was not found!");
		}
	}

	@Override
	public Set<Event> getAllEvents() {
		return db.keySet();
	}
}
