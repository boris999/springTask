package com.epam.spring.hometask.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NavigableSet;

import com.epam.spring.hometask.dao.IDiscountDAO;
import com.epam.spring.hometask.domain.DomainObject;
import com.epam.spring.hometask.domain.Ticket;
import com.epam.spring.hometask.domain.User;

public class DiscountService implements IDiscountService {

	private List<DiscountStrategy> discountStrategies;
	private IDiscountDAO discountHistory;

	@Override
	public int getDiscount(NavigableSet<Ticket> tickets) {
		int numberOfTickets = tickets.size();
		Ticket firstTicket = tickets.first();
		DomainObject user = firstTicket.getUser();
		LocalDateTime dateTime = firstTicket.getDateTime();
		return getDiscount(user, dateTime, numberOfTickets);
	}

	@Override
	public void setDiscountStrategies(List<DiscountStrategy> discountStrategies) {
		this.discountStrategies = discountStrategies;
	}

	private boolean eligibleForBirthdayDiscount(DomainObject user, LocalDateTime airDate) {
		if(user == null){
			return false;
		}
		final LocalDate birthday = ((User) user).getBirthday();
		final LocalDate eventDate = airDate.toLocalDate();
		return eventDate.minusDays(5).isBefore(birthday) && birthday.plusDays(5).isAfter(eventDate);
	}

	public List<DiscountStrategy> getDiscountStrategies() {
		return discountStrategies;
	}


	private int getDiscount(DomainObject user, LocalDateTime dateTime, int numberOfTickets) {
		int maxDiscount = 0;
		for (DiscountStrategy ds : this.discountStrategies) {
			if (ds.isBirthday() && !this.eligibleForBirthdayDiscount(user, dateTime)) {
				continue;
			}
			int calculatetedDiscount = ds.calculateDiscount(numberOfTickets);
			if (calculatetedDiscount > maxDiscount) {
				maxDiscount = calculatetedDiscount;
			}
		}
		return maxDiscount;
	}

	public void setDiscountHistory(IDiscountDAO discountHistory) {
		this.discountHistory = discountHistory;
	}

	@Override
	public void saveDiscount(User user, DiscountStrategy selectedStrategy){
		discountHistory.saveDiscount(user, selectedStrategy);
	}


}
