package com.epam.spring.hometask.dao;

import java.util.Set;

import com.epam.spring.hometask.domain.Event;

public interface IEventDAO {
	public void saveEvent(Event event);

	public void removeEvent(Event event);

	public Event getEventByName(String name);

	public Set<Event> getAllEvents();

	public Event getById(long id);
}
