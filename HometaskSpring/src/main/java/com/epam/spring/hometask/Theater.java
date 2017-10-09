package com.epam.spring.hometask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.NavigableSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.env.Environment;

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

@PropertySource("resources/other.properties")
public class Theater {

	public static void main(String[] args) throws IOException, NotFoundException {
		ApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfig.class, DiscountConfig.class);
		// new ClassPathXmlApplicationContext("resources/spring.xml");
		IBookingService bService = ctx.getBean("bookingService", BookingService.class);
		IEventService eService = ctx.getBean("eventService", EventService.class);
		IUserService uService = ctx.getBean("userService", UserService.class);
		Environment env = ctx.getBean(Environment.class);
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		runTheater(bService, eService, uService, br, env);
		((ConfigurableApplicationContext) ctx).close();
	}

	private static void runTheater(IBookingService bService, IEventService eService, IUserService uService,
			BufferedReader br, Environment env) throws IOException, NotFoundException {
		while (true) {
			System.out.println(env.getProperty("mainMessage"));
			String choise = br.readLine();
			if (choise.equalsIgnoreCase(env.getProperty("adminChoosen"))) {
				System.out.println(env.getProperty("adminSubMenu"));
				choise = br.readLine();
				if (choise.equalsIgnoreCase(env.getProperty("enterEventChoosen"))) {
					eService.createEvent(br);
					System.out.println(env.getProperty("eventSaved"));
				}
				if (choise.equalsIgnoreCase(env.getProperty("viewTickets"))) {
					Event event = bService.selectEvent(eService, br);
					if (event != null) {
						Set<Ticket> tickets = bService.getPurchasedTicketsForEvent(event, event.getAirDates().first());
						if (tickets.isEmpty()) {
							System.out.println(env.getProperty("noTicketsSold"));
						}
						for (Ticket t : tickets) {
							System.out.println(t);
						}
					}
					continue;
				}
			} else {
				System.out.println(env.getProperty("userSubmenu"));
				choise = br.readLine();
				if (choise.equalsIgnoreCase(env.getProperty("registerUser"))) {
					uService.createUser(br);
					System.out.println(env.getProperty("userSaved"));
				}
				if (choise.equalsIgnoreCase(env.getProperty("viewEvents"))) {
					Event event = bService.selectEvent(eService, br);
					if (event == null) {
						continue;
					}
					System.out.println(env.getProperty("buyTicketOrCancel"));
					choise = br.readLine();
					if (choise.equalsIgnoreCase(env.getProperty("yes"))) {
						long[] seatArray = bService.selectSeats(br);
						User user = uService.selectUser(br);
						NavigableSet<Ticket> tickets = bService.getTicketsRegularPrice(eService, event, user,
								seatArray);
						double price = tickets.stream().mapToDouble(t -> t.getPrice()).sum();
						int discountPersantage = bService.getDiscount(tickets);
						System.out.println(env.getProperty("priceBeforeDiscount") + String.valueOf(price));
						System.out.println(env.getProperty("discountIs") + String.valueOf(discountPersantage));
						double finalPrice = (price * (100 - discountPersantage)) / 100;
						System.out.println(env.getProperty("finalPrice") + String.valueOf(finalPrice));
					} else {
						continue;
					}
				}
			}

		}
	}
}
