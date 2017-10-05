package com.epam.spring.hometask.aspects;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import com.epam.spring.hometask.domain.User;
import com.epam.spring.hometask.service.DiscountService;
import com.epam.spring.hometask.service.DiscountStrategy;

@Aspect
@Component
@PropertySource("resources/other.properties")
public class Discount {

	private Map<DiscountStrategy, Map<User, Long>> discountStats = new HashMap<>();
	// count how many times each discount(birthday+volume) was given total and
	// for specific user


@After("args(user, dateTime, numberOfTickets)")
	public void countGetDiscount(User user, LocalDateTime dateTime, int numberOfTickets, JoinPoint joinPoint) {
		DiscountService ds = (DiscountService) joinPoint.getTarget();
		List<DiscountStrategy> strategies = ds.getDiscountStrategies();
		DiscountStrategy selectedStrategy = null;
		for (DiscountStrategy strategy : strategies) {
			if (strategy.calculateDiscount(numberOfTickets) == ds.getDiscount(user, dateTime, numberOfTickets)) {
				selectedStrategy = strategy;
				break;
			}
		}
		Map<User, Long> discountsOfSameType = discountStats.get(selectedStrategy);
		if (discountsOfSameType == null) {
			discountsOfSameType = new HashMap<User, Long>();
			discountsOfSameType.put(user, 1L);
			discountStats.put(selectedStrategy, discountsOfSameType);
		} else {
			Long numberOfSpecifiecDiscountForUser = discountsOfSameType.get(user);
			if (numberOfSpecifiecDiscountForUser == null) {
				discountsOfSameType.put(user, 1L);
			} else {
				discountsOfSameType.put(user, numberOfSpecifiecDiscountForUser + 1);
			}
		}
	}

//	@Pointcut("execution(* com.epam.spring.hometask.service.DiscountService.*())")
//	private void discountMethods() {}
//	
//	@Pointcut("args(user, dateTime, numberOfTickets)")
//	private void specificArgs() {}
	
}
