package com.epam.spring.hometask.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.time.LocalDateTime;
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

public class BookingService implements IBookingService {

	private IDiscountService discountService;
	private TicketDesk ticketDesk;
	private double pricePremiumForHighRating;

	@Override
	public NavigableSet<Ticket> getTicketsRegularPrice(IEventService eventService, Event choosenEvent, User user, long... seatNumbers)
			throws IOException, NotFoundException {
		// TODO to check is seat is already booked! From all tickets stream+filter
		NavigableSet<Ticket> requestedTickets = new TreeSet<>();
		for (long seatNumber : seatNumbers) {
			bookTicket(eventService, choosenEvent, user, requestedTickets, seatNumber);
		}
		if (user != null) {
			user.getTickets().addAll(requestedTickets);
		}
		return requestedTickets;
	}

	public void bookTicket(IEventService eventService, Event choosenEvent, User user,
			NavigableSet<Ticket> requestedTickets, long seatNumber) throws IOException, NotFoundException {
		double currentTicketPrice = calculateTicketRegularPrice(choosenEvent, eventService, pricePremiumForHighRating, seatNumber);
		Ticket currentTicket = new Ticket(user, choosenEvent, choosenEvent.getAirDates().first(), seatNumber, currentTicketPrice);
		requestedTickets.add(currentTicket);
		choosenEvent.buyTicket(currentTicket);
	}

	@Override
	public Set<Ticket> getPurchasedTicketsForEvent(Event event, LocalDateTime dateTime) {
		return event.getAllTickets(dateTime);
	}

	@Override
	public Event selectEvent(IEventService eventService, BufferedReader br) throws IOException {
		return this.ticketDesk.chooseEvent(eventService, br);
	}

	@Override
	public long[] selectSeats(BufferedReader br) throws IOException {
		return this.ticketDesk.selectSeats(br);
	}

	@Override
	public int getDiscount(NavigableSet<Ticket> tickets) {
		return this.discountService.getDiscount(tickets);
	}
	
	public double calculateTicketRegularPrice(Event event, IEventService eventService, double pricePremiumForHighRating, long seatNumber)
			throws IOException, NotFoundException {
		LocalDateTime choosenDateTime = event.getAirDates().first();
		Event eventInDB = eventService.getById(event.getId());
		Auditorium auditorium = eventInDB.getAuditoriums().get(choosenDateTime);
		Set<Long> vipSeat = auditorium.getVipSeats();
		double price = 0.0;
		boolean isHighRated = eventInDB.getRating().equals(EventRating.HIGH);
		double priceForRating = isHighRated ? eventInDB.getBasePrice() * pricePremiumForHighRating
				: eventInDB.getBasePrice();
			if (vipSeat.contains(seatNumber)) {
				price += priceForRating*auditorium.getExtraPayForVipSeat();
			} else {
				price += priceForRating;
			}
		return price;
	}

	public void setDiscountService(DiscountService discountService) {
		this.discountService = discountService;
	}

	public void setTicketDesk(TicketDesk ticketDesk) {
		this.ticketDesk = ticketDesk;
	}

	public void setPricePremiumForHighRating(double pricePremiumForHighRating) {
		this.pricePremiumForHighRating = pricePremiumForHighRating;
	}

}