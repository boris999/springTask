package com.epam.spring.hometask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.NavigableSet;
import java.util.Set;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.epam.spring.hometask.domain.Event;
import com.epam.spring.hometask.domain.Ticket;
import com.epam.spring.hometask.domain.User;
import com.epam.spring.hometask.exeptions.NotFoundException;
import com.epam.spring.hometask.service.BookingService;
import com.epam.spring.hometask.service.EventService;
import com.epam.spring.hometask.service.IBookingService;
import com.epam.spring.hometask.service.IEventService;
import com.epam.spring.hometask.service.IUserService;
import com.epam.spring.hometask.service.UserService;

public class Theater {

	public static void main(String[] args) throws IOException, NotFoundException {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("resources/spring.xml");
		IBookingService bService = ctx.getBean("bookingService", BookingService.class);
		IEventService eService = ctx.getBean("eventService", EventService.class);
		IUserService uService = ctx.getBean("userService", UserService.class);
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		runTheater(bService, eService, uService, br);
		((ConfigurableApplicationContext) ctx).close();
	}

	private static void runTheater(IBookingService bService, IEventService eService, IUserService uService, BufferedReader br)
			throws IOException, NotFoundException {
		while (true) {
			System.out.println("Enter 'A' for admin menu or anything for user menu");
			String choise = br.readLine();
			if (choise.equalsIgnoreCase("A")) {
				System.out.println("Enter 'E' to add new event or 'T' to view purchased tickets");
				choise = br.readLine();
				if (choise.equalsIgnoreCase("E")) {
					eService.createEvent(br);
					System.out.println("Event saved");
				}
				if (choise.equalsIgnoreCase("T")) {
					Event event = bService.selectEvent(eService, br);
					if (event != null) {
						Set<Ticket> tickets = bService.getPurchasedTicketsForEvent(event, event.getAirDates().first());
						for (Ticket t : tickets) {
							System.out.println(t);
						}
					}
					continue;
				}
			} else {
				System.out.println("Enter 'R' to register new user or  'V' to view available events");
				choise = br.readLine();
				if (choise.equalsIgnoreCase("R")) {
					uService.createUser(br);
					System.out.println("User saved");
				}
				if (choise.equalsIgnoreCase("V")) {
					Event event = bService.selectEvent(eService, br);
					if (event == null) {
						continue;
					}
					System.out.println("Do you want to get the price for tickets for this event? Enter Y to confirm or any key to cancel:");
					choise = br.readLine();
					if (choise.equalsIgnoreCase("Y")) {
						long[] seatArray = bService.selectSeats(br);
						User user = uService.selectUser(br);
						Map<NavigableSet<Ticket>, Double> ticketsWithRegularPrce = bService.getTicketsRegularPrice(eService, event, user, seatArray);
						NavigableSet<Ticket> tickets = ticketsWithRegularPrce.keySet().stream().findFirst().get();
						double price = ticketsWithRegularPrce.get(tickets);
						int discountPersantage = bService.getDiscount(user, event.getAirDates().first(), seatArray.length);
						System.out.println("Price before discount =>" + String.valueOf(price));
						System.out.println("Discount is " + String.valueOf(discountPersantage));
						double finalPrice = (price * (100 - discountPersantage)) / 100;
						System.out.println("Final price is " + String.valueOf(finalPrice));
						System.out.println("Do you want to by the tickets, enter 'Y' to confirm or anythig else to cancel");
						choise = br.readLine();
						if (choise.equalsIgnoreCase("Y")) {
							bService.bookTicket(tickets);
						} else {
							continue;
						}
					}
				}

			}
		}
	}

}
