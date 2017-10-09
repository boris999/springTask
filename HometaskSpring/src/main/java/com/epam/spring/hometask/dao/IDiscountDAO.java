package com.epam.spring.hometask.dao;

import com.epam.spring.hometask.domain.User;
import com.epam.spring.hometask.service.DiscountStrategy;

public interface IDiscountDAO {
	
	public void saveDiscount(User user, DiscountStrategy selectedStrategy);
}
