package com.epam.spring.hometask.dao;

import org.aspectj.lang.JoinPoint;

public interface IEventCounterDAO {

	public void incrementCount(String key, JoinPoint joinPoint);
}
