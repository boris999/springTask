package com.epam.spring.hometask.service;

import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.epam.spring.hometask.console.EventCreator;
import com.epam.spring.hometask.dao.EventDAO;
import com.epam.spring.hometask.domain.Event;

public class EventService implements ApplicationContextAware {

	private EventDAO dao;
	private EventCreator creator;
	private ApplicationContext context;

	public EventService(EventDAO dao, EventCreator creator) {
		this.dao = dao;
		this.creator = creator;
	}

	public void createEvent() throws IOException {
		Event event = this.creator.createEvent(this.context);
		synchronized (event) {
			Optional<Long> lastEventID = this.dao.getAllEvents().stream().map(e -> e.getId()).max((l1, l2) -> (Long.compare(l1, l2)));
			if (lastEventID.isPresent()) {
				event.setId(lastEventID.get() + 1);
			} else {
				event.setId(1L);
			}
		}
		this.dao.saveEvent(event);
		System.out.println("Event saved");
	}
	// createEvent
	// save, remove, getById, getByName, getAll
	// getForDateRange(from, to) - returns events for specified date range (OPTIONAL)
	// getNextEvents(to) - returns events from now till the ‘to’ date (OPTIONAL)
	// private String name;
	//
	// private NavigableSet<LocalDateTime> airDates = new TreeSet<>();
	//
	// private double basePrice;
	//
	// private EventRating rating;
	//
	// private NavigableMap<LocalDateTime, Auditorium> auditoriums = new TreeMap<>();

	@Override
	public void setApplicationContext(ApplicationContext context) throws BeansException {
		this.context = context;
	}
}
