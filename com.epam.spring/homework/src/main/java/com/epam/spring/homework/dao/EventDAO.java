package com.epam.spring.homework.dao;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.epam.spring.homework.domain.Event;

public class EventDAO {
	private static Map<Event, Event> events = new ConcurrentHashMap<>();

}
