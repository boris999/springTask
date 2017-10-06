package com.epam.spring.hometask.aspects;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import com.epam.spring.hometask.dao.IDiscountDAO;
import com.epam.spring.hometask.domain.User;
import com.epam.spring.hometask.service.DiscountService;
import com.epam.spring.hometask.service.DiscountStrategy;

@Aspect
@Component
@PropertySource("resources/other.properties")
public class Discount {

	private IDiscountDAO dao;

	@AfterReturning(pointcut = "discountMethod()", returning = "retVal")
	public void countGetDiscount(JoinPoint joinPoint, Object retVal) {
		Object[] args = joinPoint.getArgs();
		User user = (User) args[0];
		Integer numberOfTickets = (Integer) args[2];
		int valueReturned = (Integer) retVal;
		DiscountService discountService = (DiscountService) joinPoint.getTarget();
		List<DiscountStrategy> strategies = discountService.getDiscountStrategies();
		DiscountStrategy selectedStrategy = null;
		for (DiscountStrategy strategy : strategies) {
			if (strategy.calculateDiscount(numberOfTickets) == valueReturned) {
				selectedStrategy = strategy;
				break;
			}
		}
		if (valueReturned != 0) {
			dao.saveDiscount(user, selectedStrategy);
		}
	}

	@Pointcut("execution(* com.epam.spring.hometask.service.DiscountService.getDiscount())")
	private void discountMethod() {
	}

}
