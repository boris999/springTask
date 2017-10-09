package com.epam.spring.hometask.dao;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;

import com.epam.spring.hometask.domain.User;
import com.epam.spring.hometask.service.DiscountStrategy;

@Component
public class DiscountDAO implements IDiscountDAO{

	private Map<DiscountStrategy, Map<User, Long>> discountStats = new ConcurrentHashMap<>();
	
	public void saveDiscount(User user, DiscountStrategy selectedStrategy) {
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
