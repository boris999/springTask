package com.epam.spring.hometask.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.NavigableSet;
import java.util.Set;
import java.util.TreeSet;

import com.epam.spring.hometask.console.TicketDesk;
import com.epam.spring.hometask.domain.Auditorium;
import com.epam.spring.hometask.domain.Event;
import com.epam.spring.hometask.domain.EventRating;
import com.epam.spring.hometask.domain.Ticket;
import com.epam.spring.hometask.domain.User;
import com.epam.spring.hometask.exeptions.NotFoundException;

public class BookingService {

	private DiscountService discountService;
	private TicketDesk ticketDesk;
	private EventService eventService;
	private double pricePremiumForHighRating;

	public Map<NavigableSet<Ticket>, Double> getTicketsRegularPrice(Event choosenEvent, User user, long... seatNumbers)
			throws IOException, NotFoundException {
		// TODO to check is seat is already booked!
		double priceBeforeDiscount = this.getTicketsPriceWithoutDiscount(choosenEvent, this.eventService, this.pricePremiumForHighRating,
				seatNumbers);
		NavigableSet<Ticket> requestedTickets = new TreeSet<>();
		for (long seatNumber : seatNumbers) {
			requestedTickets.add(new Ticket(user, this.eventService.getById(choosenEvent.getId()), choosenEvent.getAirDates().first(), seatNumber));
		}
		Map<NavigableSet<Ticket>, Double> mapToReturn = new HashMap<>();
		mapToReturn.put(requestedTickets, priceBeforeDiscount);
		return mapToReturn;
	}

	public void bookTicket(NavigableSet<Ticket> tickets) {
		Ticket firstTicket = tickets.first();
		User user = firstTicket.getUser();
		Event event = firstTicket.getEvent();
		for (Ticket t : tickets) {
			event.buyTicket(t);
		}
		if (user != null) {
			user.getTickets().addAll(tickets);
		}
	}

	public Set<Ticket> getPurchasedTicketsForEvent(Event event, LocalDateTime dateTime) {
		return event.getAllTickets(dateTime);
	}

	public Event selectEvent(BufferedReader br) throws IOException {
		return this.ticketDesk.chooseEvent(this.eventService, br);
	}

	private double getTicketsPriceWithoutDiscount(Event event, EventService eService, double pricePremiumForHighRating, long... seats)
			throws IOException, NotFoundException {
		LocalDateTime choosenDateTime = event.getAirDates().first();
		Event eventInDB = eService.getById(event.getId());
		Auditorium auditorium = eventInDB.getAuditoriums().get(choosenDateTime);
		Set<Long> vipSeat = auditorium.getVipSeats();
		double totalPrice = 0.0;
		boolean isHighRated = eventInDB.getRating().equals(EventRating.HIGH);
		double basePrice = isHighRated ? eventInDB.getBasePrice() * pricePremiumForHighRating
				: eventInDB.getBasePrice();
		for (long seatNumber : seats) {
			if (vipSeat.contains(seatNumber)) {
				totalPrice += basePrice;
			} else {
				totalPrice += basePrice;
			}
		}
		return totalPrice;
	}

	public long[] selectSeats(BufferedReader br) throws IOException {
		return this.ticketDesk.selectSeats(br);
	}

	public int getDiscount(User user, LocalDateTime dateTime, int numberOfSeats) {
		return this.discountService.getDiscount(user, dateTime, numberOfSeats);
	}

	public DiscountService getDiscountService() {
		return this.discountService;
	}

	public void setDiscountService(DiscountService discountService) {
		this.discountService = discountService;
	}

	public TicketDesk getTicketDesk() {
		return this.ticketDesk;
	}

	public void setTicketDesk(TicketDesk ticketDesk) {
		this.ticketDesk = ticketDesk;
	}

	public EventService getEventService() {
		return this.eventService;
	}

	public void setEventService(EventService eventService) {
		this.eventService = eventService;
	}

	public void setPricePremiumForHighRating(double pricePremiumForHighRating) {
		this.pricePremiumForHighRating = pricePremiumForHighRating;
	}

}