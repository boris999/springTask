package com.epam.spring.hometask.service;

import java.time.LocalDateTime;

import com.epam.spring.hometask.domain.Event;
import com.epam.spring.hometask.domain.User;

public class BookingService {

	public double getTicketsPrice(Event event, LocalDateTime dateTime, User user, int... seatNumbers) {
		int[] seatList = seatNumbers;
		// TODO to check is seat is already booked!

		return 0.0;
	}
}

// getTicketsPrice(event, dateTime, user, seats) - returns total price for buying all tickets for specified event on specific date and time for
// specified seats.
// User is needed to calculate discount (see below)
// Event is needed to get base price, rating
// Vip seats should cost more than regular seats (For example, 2xBasePrice)
// All prices for high rated movies should be higher (For example, 1.2xBasePrice)
// bookTicket(tickets) - Ticket should contain information about event, air dateTime, seat, and user. The user could be registered or not.
// If user is registered, then booking information is stored for that user (in the tickets collection). Purchased tickets for particular event should
// be stored.
// getPurchasedTicketsForEvent(event, dateTime) - get all purchased tickets for event for specific date and Time
// }
