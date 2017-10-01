package com.epam.spring.hometask.dao;

import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import com.epam.spring.hometask.domain.Event;
import com.epam.spring.hometask.exeptions.NotFoundException;

public class EventDAO extends DomainObjectDAO {

	public void saveEvent(Event event) {
		this.saveDomainObject(event);
	}

	public void removeEvent(Event event) {
		this.removeDomainObject(event);
	}

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

	public Set<Event> getAllEvents() {
		return this.getAllOfSameType(Event.class).stream()
				.map(dom -> (Event) dom).collect(Collectors.toSet());
	}

	public Event getById(long id) throws NotFoundException{
		return (Event)super.getById(id);
	}
}
