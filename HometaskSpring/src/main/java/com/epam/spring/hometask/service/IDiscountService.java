package com.epam.spring.hometask.service;

import java.util.List;
import java.util.NavigableSet;

import com.epam.spring.hometask.domain.Ticket;
import com.epam.spring.hometask.domain.User;

public interface IDiscountService {
	
	public void setDiscountStrategies(List<DiscountStrategy> discountStrategies);
	
	public int getDiscount(NavigableSet<Ticket> tickets);
}
