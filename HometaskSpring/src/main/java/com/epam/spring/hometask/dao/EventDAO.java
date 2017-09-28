package com.epam.spring.hometask.dao;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import com.epam.spring.hometask.domain.Event;
import com.epam.spring.hometask.exeptions.NotFoundException;

public class EventDAO extends DomainObjectDAO {
	// TODO getById to return casted from here
	public void saveEvent(Event event) {
		this.saveDomainObject(event);
	}

	public void removeEvent(Event event) {
		this.removeDomainObject(event);
	}

	public Event getUserByEmail(String email) throws NotFoundException {
		final Optional<Event> event = this.getAllEvents().stream()
				.filter(u -> u.getName().equals(email))
				.findFirst();
		if (event.isPresent()) {
			return event.get();
		} else {
			throw new NotFoundException("User with email " + email + " was not found!");
		}
	}

	public Set<Event> getAllEvents() {
		return this.getAllOfSameType(Event.class).stream()
				.map(dom -> (Event) dom).collect(Collectors.toSet());
	}

	public Set<Event> getEventsForDateRange(LocalDateTime from, LocalDateTime to) {
		Set<Event> after = new LinkedHashSet<>();
		Set<Event> before = this.getNextEvents(to);
		this.getAllEvents().stream().filter(e -> (!e.getAirDates().tailSet(from, true).isEmpty())).forEach(ev -> after.add(ev));
		after.retainAll(before);
		return after;
	}

	public Set<Event> getNextEvents(LocalDateTime to) {
		Set<Event> before = new LinkedHashSet<>();
		this.getAllEvents().stream().filter(e -> (!e.getAirDates().headSet(to, true).isEmpty())).forEach(ev -> before.add(ev));
		return before;
	}

}
