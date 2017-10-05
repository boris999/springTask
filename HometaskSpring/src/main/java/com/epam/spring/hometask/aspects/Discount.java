package com.epam.spring.hometask.aspects;

import java.util.HashMap;
import java.util.Map;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import com.epam.spring.hometask.domain.User;
import com.epam.spring.hometask.service.DiscountStrategy;

@Aspect
@Component
@PropertySource("resources/other.properties")
public class Discount {

	private Map<DiscountStrategy, Map<User, Long>> discountStats = new HashMap<>();

	// count how many times each discount(birthday+volume) was given total and
	// for specific user

	@After("execution(* * com.epam.spring.hometask.service.DiscountStrategy.calculateDiscount())")
	public void countgetDiscount(JoinPoint joinPoint) {
		DiscountStrategy ds = (DiscountStrategy) joinPoint.getTarget();
		Map<User, Long> allDiscounts = discountStats.get(ds);
		if(allDiscounts== null){
			HashMap<User, Long> discountTypes = new HashMap<User, Long>();
			discountTypes.put(key, value);
			discountStats.put(ds, discountTypes);
		}
		
	}

}
