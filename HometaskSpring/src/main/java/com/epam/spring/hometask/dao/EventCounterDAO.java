package com.epam.spring.hometask.dao;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.aspectj.lang.JoinPoint;
import org.springframework.stereotype.Component;

import com.epam.spring.hometask.domain.Event;

@Component
public class EventCounterDAO implements IEventCounterDAO {

	private Map<Event, Map<String, Long>> counterStats = new ConcurrentHashMap<>();
	
	public synchronized void incrementCount(String key, JoinPoint joinPoint) {
		Event event = (Event) joinPoint.getTarget();
		Map<String, Long> methodsStats = counterStats.get(event);
		if (methodsStats == null) {
			HashMap<String, Long> calledCount = new HashMap<String, Long>();
			calledCount.put(key, 1L);
			counterStats.put(event, calledCount);
		} else {
			Long currentNameCount = methodsStats.get(key);
			methodsStats.put(key, currentNameCount + 1);
		}
	}
}
