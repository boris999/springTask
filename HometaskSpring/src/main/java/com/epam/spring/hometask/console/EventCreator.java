package com.epam.spring.hometask.console;

import java.io.BufferedReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.epam.spring.hometask.domain.Auditorium;
import com.epam.spring.hometask.domain.Event;
import com.epam.spring.hometask.domain.EventRating;
import com.epam.spring.hometask.domain.Ticket;

@Component
public class EventCreator {

	private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

	public Event createEvent(ApplicationContext context, BufferedReader br) throws IOException {
		System.out.println("Enter event name:");
		String name = br.readLine();
		double basePrice = 0.0;
		while (basePrice == 0.0) {
			System.out.println("Enter event base price:");
			String price = br.readLine();
			try {
				basePrice = Double.parseDouble(price);
			} catch (NumberFormatException e) {
				System.out.print("Invalid entry try again. ");
			}
		}

		EventRating eRating = null;
		while (eRating == null) {
			System.out.println("Choose event rating  - low, mid or high");
			String rating = br.readLine();
			try {
				eRating = EventRating.valueOf(rating.toUpperCase());
			} catch (IllegalArgumentException e) {
				System.out.print("Invalid entry try again. ");
			}
		}
		Event event = new Event();
		Auditorium selectedAuditorium = null;
		LocalDateTime airDateTime = null;
		boolean addedAUditoriumAndAirDate = false;
		do {
			while (airDateTime == null) {
				System.out.println("Enter date and time for event in yyyy-MM-dd HH:mm format:");
				String dateTime = br.readLine();

				try {
					airDateTime = LocalDateTime.parse(dateTime, DATE_TIME_FORMATTER);
				} catch (DateTimeParseException e) {
					System.out.print("Invalid entry try again. ");
				}
			}

			Optional<Auditorium> audi = Optional.ofNullable(null);
			while (!audi.isPresent()) {
				System.out.println("Choose auditorium for the event. Here are the available auditoriums:");
				Map<String, Auditorium> map = context.getBeansOfType(Auditorium.class);
				for (Auditorium a : map.values()) {
					System.out.println(a.getName());
				}
				System.out.println("Enter auditorium name:");
				String auditorium = br.readLine();
				audi = map.values().stream().filter(a -> a.getName().equalsIgnoreCase(auditorium)).findFirst();
			}
			// TODO to check if not booked!!
			selectedAuditorium = audi.get();
			addedAUditoriumAndAirDate = event.addAirDateTime(airDateTime, selectedAuditorium);
			if (!addedAUditoriumAndAirDate) {
				System.out.println("Cannot create event for entered dateTime and auditorium. Choose other dateTime and/or Auditorium");
			}
		} while (!addedAUditoriumAndAirDate);
		//TODO to check if time is available
		event.addNewEventToTicketRegister(airDateTime);
		event.setName(name);
		event.setBasePrice(basePrice);
		event.setRating(eRating);
		return event;
	}

}
