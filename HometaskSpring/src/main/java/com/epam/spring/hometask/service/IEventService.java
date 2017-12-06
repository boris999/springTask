package com.epam.spring.hometask.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Set;

import org.aspectj.lang.JoinPoint;

import com.epam.spring.hometask.domain.Event;

public interface IEventService {

	public void createEvent(BufferedReader br) throws IOException;

	public Set<Event> getEventsForDateRange(LocalDateTime from, LocalDateTime to);

	public Set<Event> getNextEvents(LocalDateTime to);

	public void saveEvent(Event event);

	public void removeEvent(Event event);

	public Event getById(long id);

	public Event getByName(String name);

	public Set<Event> getAll();

	public void incrementCount(String key, JoinPoint joinPoint);

}
