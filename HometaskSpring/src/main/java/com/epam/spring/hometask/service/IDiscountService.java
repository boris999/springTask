package com.epam.spring.hometask.service;

import java.time.LocalDateTime;
import java.util.List;

import com.epam.spring.hometask.domain.User;

public interface IDiscountService {
	public int getDiscount(User user, LocalDateTime dateTime, int numberOfTickets);

	public void setDiscountStrategies(List<DiscountStrategy> discountStrategies);
}
