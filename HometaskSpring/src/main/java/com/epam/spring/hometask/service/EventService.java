package com.epam.spring.hometask.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.epam.spring.hometask.console.EventCreator;
import com.epam.spring.hometask.dao.EventDAO;
import com.epam.spring.hometask.domain.Event;
import com.epam.spring.hometask.exeptions.NotFoundException;

public class EventService implements ApplicationContextAware {

	private EventDAO dao;
	private EventCreator creator;
	private ApplicationContext context;

	public EventService(EventDAO dao, EventCreator creator) {
		this.dao = dao;
		this.creator = creator;
	}

	public void createEvent(BufferedReader br) throws IOException {
		Event event = this.creator.createEvent(this.context, br);
		synchronized (event) {
			Optional<Long> lastEventID = this.dao.getAllEvents().stream().map(e -> e.getId()).max((l1, l2) -> (Long.compare(l1, l2)));
			if (lastEventID.isPresent()) {
				event.setId(lastEventID.get() + 1);
			} else {
				event.setId(1L);
			}
		}
		this.dao.saveEvent(event);
	}

	public Set<Event> getEventsForDateRange(LocalDateTime from, LocalDateTime to) {
		if ((from == null) && (to == null)) {
			return this.dao.getAllEvents();
		}
		Set<Event> after = this.dao.getAllEvents().stream().filter(e -> !e.getAirDates().headSet(to, true).isEmpty()).collect(Collectors.toSet());
		Set<Event> before = this.dao.getAllEvents().stream().filter(e -> !e.getAirDates().tailSet(from, true).isEmpty()).collect(Collectors.toSet());
		if (from == null) {
			return after;
		}
		if (to == null) {
			return before;
		}
		after.retainAll(before);
		return after;
	}

	public Set<Event> getNextEvents(LocalDateTime to) {
		return this.getEventsForDateRange(LocalDateTime.now(), to);
	}

	public void saveEvent(Event event) {
		this.dao.saveEvent(event);
	}

	public void removeEvent(Event event) {
		this.dao.removeEvent(event);
	}

	public Event getById(long id) throws NotFoundException {
		return this.dao.getById(id);
	}

	public Event getByName(String name) throws NotFoundException {
		return this.dao.getEventByName(name);
	}

	public Set<Event> getAll() {
		return this.dao.getAllEvents();
	}

	@Override
	public void setApplicationContext(ApplicationContext context) throws BeansException {
		this.context = context;
	}

}
