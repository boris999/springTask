package com.epam.spring.hometask.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.NavigableSet;
import java.util.Set;

import com.epam.spring.hometask.domain.Event;
import com.epam.spring.hometask.domain.Ticket;
import com.epam.spring.hometask.domain.User;
import com.epam.spring.hometask.exeptions.NotFoundException;

public interface IBookingService {

	public NavigableSet<Ticket> getTicketsRegularPrice(IEventService eventService, Event choosenEvent, User user, long... seatNumbers)
			throws IOException, NotFoundException;

	public Set<Ticket> getPurchasedTicketsForEvent(Event event, LocalDateTime dateTime);

	public Event selectEvent(IEventService eventService, BufferedReader br) throws IOException;

	public long[] selectSeats(BufferedReader br) throws IOException;
	
	public int getDiscount(NavigableSet<Ticket> tickets);
}
