package com.epam.spring.hometask.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.epam.spring.hometask.dao.IEventCounterDAO;

@Aspect
@Component
@PropertySource("resources/other.properties")
public class Counter {
	
	private IEventCounterDAO dao;
	

	@Autowired
	Environment env;
	
	@After("allEventMethods() && allGetNameMethods()")
	public void countgetName(JoinPoint joinPoint){
		dao.incrementCount(env.getProperty("eventGetName"), joinPoint);
	}
	
	@After("eventPrice()")
	public void countgetPrice(JoinPoint joinPoint){
		dao.incrementCount(env.getProperty("eventGetPrice"), joinPoint);
	}
	
	@After("buyTicket()")
	public void countBuyTicket(JoinPoint joinPoint){
		dao.incrementCount(env.getProperty("eventBuyTicket"), joinPoint);
	}

	@Pointcut("execution(* com.epam.spring.hometask.domain.Event.*())")
	private void allEventMethods() {}
	
	@Pointcut("execution(String getName())")
	private void allGetNameMethods() {}
	
	@Pointcut("execution(public double com.epam.spring.hometask.domain.Event.getBasePrice())")
	private void eventPrice() {}
	
	@Pointcut("execution(public double com.epam.spring.hometask.domain.Event.buyTicket(*))")
	private void buyTicket() {}
	
	
	
}
