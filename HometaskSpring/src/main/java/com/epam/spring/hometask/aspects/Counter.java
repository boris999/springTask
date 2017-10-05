package com.epam.spring.hometask.aspects;

import java.util.HashMap;
import java.util.Map;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.epam.spring.hometask.domain.Event;

@Aspect
@Component
@PropertySource("resources/other.properties")
public class Counter {
	
	private Map<Event, Map<String, Long>> counterStats = new HashMap<>();

	@Autowired
	Environment env;
	
	@After("allEventMethods() && allGetNameMethods()")
	public void countgetName(JoinPoint joinPoint){
		incrementCount(env.getProperty("eventGetName"), joinPoint);
	}
	
	@After("eventPrice()")
	public void countgetPrice(JoinPoint joinPoint){
		incrementCount(env.getProperty("eventGetPrice"), joinPoint);
	}
	
	@After("buyTicket()")
	public void countBuyTicket(JoinPoint joinPoint){
		incrementCount(env.getProperty("eventBuyTicket"), joinPoint);
	}

	@Pointcut("execution(* com.epam.spring.hometask.domain.Event.*())")
	private void allEventMethods() {}
	
	@Pointcut("execution(String getName())")
	private void allGetNameMethods() {}
	
	@Pointcut("execution(public double com.epam.spring.hometask.domain.Event.getBasePrice())")
	private void eventPrice() {}
	
	@Pointcut("execution(public double com.epam.spring.hometask.domain.Event.buyTicket(*))")
	private void buyTicket() {}
	
	private synchronized void incrementCount(String key, JoinPoint joinPoint) {
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
