package com.epam.spring.hometask.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.epam.spring.hometask.domain.Event;
import com.epam.spring.hometask.domain.User;

public class DiscountService {

	private List<DiscountStrategy> discountStrategies;

	public int getDiscount(User user, Event event, LocalDateTime dateTime, int numberOfTickets) {
		int maxDiscount = 0;
		for (DiscountStrategy ds : this.discountStrategies) {
			if ((user == null) || (ds.isBirthday() && !this.eligibleForBirthdayDiscount(user, dateTime))) {
				continue;
			}
			int calculatetedDiscount = ds.calculateDiscount(numberOfTickets);
			if (calculatetedDiscount > maxDiscount) {
				maxDiscount = calculatetedDiscount;
			}
		}
		return maxDiscount;
	}

	private boolean eligibleForBirthdayDiscount(User user, LocalDateTime airDate) {
		final LocalDate birthday = user.getBirthday();
		final LocalDate eventDate = airDate.toLocalDate();
		return eventDate.minusDays(5).isBefore(birthday) && birthday.plusDays(5).isAfter(eventDate);
	}
}
