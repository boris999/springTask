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


	@After("discountMethod()")
	public void countGetDiscount(JoinPoint joinPoint) {
		Object[] args = joinPoint.getArgs();
		User user = (User) args[0];
		LocalDateTime dateTime = (LocalDateTime) args[1];
		Integer numberOfTickets = (Integer) args[2];
		DiscountService ds = (DiscountService) joinPoint.getTarget();
		List<DiscountStrategy> strategies = ds.getDiscountStrategies();
		DiscountStrategy selectedStrategy = null;
		int discount = 0;
		for (DiscountStrategy strategy : strategies) {
			int currentDiscount = ds.getDiscount(user, dateTime, numberOfTickets);
			if (strategy.calculateDiscount(numberOfTickets) == discount) {
				selectedStrategy = strategy;
				discount = currentDiscount;
				break;
			}
		}
		if (discount != 0) {
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

	}

	@Pointcut("execution(* com.epam.spring.hometask.service.DiscountService.getDiscount())")
	private void discountMethod() {}
	
	
}
