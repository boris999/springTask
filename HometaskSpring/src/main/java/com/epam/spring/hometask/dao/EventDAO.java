package com.epam.spring.hometask.dao;

import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;

import com.epam.spring.hometask.domain.Event;

@Component
public class EventDAO implements IEventDAO {

	private static Map<Event, Event> db = new ConcurrentHashMap<>();

	@Override
	public Event getById(long id) {
		Optional<Event> entry = db.keySet().stream().filter(e -> e.getId().equals(id)).findFirst();
		if (entry.isPresent()) {
			return entry.get();
		}
		return null;
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
	public Event getEventByName(String name) {
		final Optional<Event> event = this.getAllEvents().stream()
				.filter(e -> e.getName().equals(name))
				.findFirst();
		if (event.isPresent()) {
			return event.get();
		} return null;
	}

	@Override
	public Set<Event> getAllEvents() {
		return db.keySet();
	}
}
