package com.epam.spring.hometask;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import com.epam.spring.hometask.dao.IDiscountDAO;
import com.epam.spring.hometask.service.DiscountService;
import com.epam.spring.hometask.service.DiscountStrategy;

@Configuration
@PropertySource("resources/other.properties")
public class DiscountConfig {
	
	@Autowired
	Environment env;
	
	@Autowired
	@Qualifier("discountStrategyBirthDay")
	DiscountStrategy dsb;
	
	@Autowired
	@Qualifier("discountStrategyVolume")
	DiscountStrategy dsv;
	
	@Autowired
	IDiscountDAO discountHistory;
	

	private DiscountStrategy getDiscountStrategy(){
		DiscountStrategy  ds = new DiscountStrategy();
		ds.setBirthdayDiscount(Integer.parseInt(env.getProperty("birthdayDiscount")));
		ds.setNumberOfTicketsTogetVolumeDiscount(Integer.parseInt(env.getProperty("numberOfTicketsTogetVolumeDiscount")));
		ds.setVolumeDiscount(Integer.parseInt(env.getProperty("volumeDiscount")));
		return ds;
	}
	
	@Bean(name = "discountStrategyBirthDay")
	public DiscountStrategy getBirthDayDiscount() {
		DiscountStrategy ds = getDiscountStrategy();
		ds.setBirthday(true);
		return ds;
	}
	
	@Bean(name = "discountStrategyVolume")
	public DiscountStrategy getVolumeDiscount() {
		DiscountStrategy ds = getDiscountStrategy();
		ds.setBirthday(false);
		return ds;
	}
	

	@Bean(name = "discountService")
	public DiscountService getDiscountService(){
		DiscountService ds = new DiscountService();
		List<DiscountStrategy> strategies = new ArrayList<>();
		strategies.add(dsb);
		strategies.add(dsv);
		ds.setDiscountStrategies(strategies);
		ds.setDiscountHistory(discountHistory);
		return ds;
	}
	

}
