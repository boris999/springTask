package com.epam.spring.hometask.service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.NavigableSet;
import java.util.Set;
import java.util.TreeSet;

import com.epam.spring.hometask.console.ConsolePrinter;
import com.epam.spring.hometask.console.TicketDesk;
import com.epam.spring.hometask.domain.Auditorium;
import com.epam.spring.hometask.domain.Event;
import com.epam.spring.hometask.domain.EventRating;
import com.epam.spring.hometask.domain.Ticket;
import com.epam.spring.hometask.domain.User;
import com.epam.spring.hometask.exeptions.NotFoundException;
import com.epam.spring.hometask.service.DiscountService;

public class BookingService {
	
	private DiscountService discountService;
	private TicketDesk ticketDesk;
	private EventService eventService;
	private int pricePremiumForHighRating;
	//private ConsolePrinter printer;
//Event event, LocalDateTime dateTime, are not needed they are in choosenEvent. to make method called with 
	//Event event, LocalDateTime dateTime, User user, long... seatNumbers from somewhere else with passes args
	
	public Map<Set<Ticket>, Double> getTicketsRegularPrice(Event choosenEvent, User user, long... seatNumbers) throws IOException, NotFoundException {
		// TODO to check is seat is already booked!
		double priceBeforeDiscount = getTicketsPriceWithoutDiscount(choosenEvent, eventService, pricePremiumForHighRating, seatNumbers);
//		int discount = discountService.getDiscount(user, dateTime, seatNumbers.length);
//		printer.print("Price before discount =>"+String.valueOf(priceBeforeDiscount));
//		printer.print("Discount is " + String.valueOf(discount) +"%");
//		double finalPrice = priceBeforeDiscount*(100-discount)/100;
//		printer.print("Final price is " + String.valueOf(finalPrice));
		Set<Ticket> requestedTickets = new HashSet<Ticket>();
		for(long seatNumber :seatNumbers){
			requestedTickets.add(new Ticket(user, eventService.getById(choosenEvent.getId()), choosenEvent.getAirDates().first(), seatNumber));
		}
		Map<Set<Ticket>, Double> mapToReturn = new HashMap<>();
		mapToReturn.put(requestedTickets, priceBeforeDiscount);
		return mapToReturn;
	}

	
	public void bookTicket(NavigableSet<Ticket> tickets){
		Ticket firstTicket = tickets.first();
		User user = firstTicket.getUser();
		Event event = firstTicket.getEvent();
		for(Ticket t : tickets){
			event.buyTicket(t);
		}
		if(user != null){
			user.getTickets().addAll(tickets);
		}
	} 
	
	
	public Set<Ticket> getPurchasedTicketsForEvent(Event event, LocalDateTime dateTime){
		return event.getAllTickets(dateTime);
	}
	
	public Event selectEvent(EventService eventService) throws IOException{
		return ticketDesk.chooseEvent(eventService);
	}
	
	private double getTicketsPriceWithoutDiscount(Event event, EventService eService, double pricePremiumForHighRating, long... seats)
			throws IOException, NotFoundException {
		LocalDateTime choosenDateTime = event.getAirDates().first();
		Event eventInDB = (Event) eService.getById(event.getId());
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
	
	public DiscountService getDiscountService() {
		return discountService;
	}

	public void setDiscountService(DiscountService discountService) {
		this.discountService = discountService;
	}

	public TicketDesk getTicketDesk() {
		return ticketDesk;
	}

	public void setTicketDesk(TicketDesk ticketDesk) {
		this.ticketDesk = ticketDesk;
	}

	public EventService getEventService() {
		return eventService;
	}

	public void setEventService(EventService eventService) {
		this.eventService = eventService;
	}
	
}