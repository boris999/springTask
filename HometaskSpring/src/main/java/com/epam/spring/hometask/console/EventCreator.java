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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.epam.spring.hometask.domain.Auditorium;
import com.epam.spring.hometask.domain.Event;
import com.epam.spring.hometask.domain.EventRating;
import com.epam.spring.hometask.domain.Ticket;

@PropertySource("resources/other.properties")
@Component
public class EventCreator {

	private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
	
	@Autowired
	Environment env;

	public Event createEvent(ApplicationContext context, BufferedReader br) throws IOException {
		System.out.println(env.getProperty("event.name.enter"));
		String name = br.readLine();
		double basePrice = 0.0;
		while (basePrice == 0.0) {
			System.out.println(env.getProperty("event.price.enter"));
			String price = br.readLine();
			try {
				basePrice = Double.parseDouble(price);
			} catch (NumberFormatException e) {
				System.out.print(env.getProperty("invalidEntryTryAgain"));
			}
		}

		EventRating eRating = null;
		while (eRating == null) {
			System.out.println(env.getProperty("event.choose.rating"));
			String rating = br.readLine();
			try {
				eRating = EventRating.valueOf(rating.toUpperCase());
			} catch (IllegalArgumentException e) {
				System.out.print(env.getProperty("invalidEntryTryAgain"));
			}
		}
		Event event = new Event();
		Auditorium selectedAuditorium = null;
		LocalDateTime airDateTime = null;
		boolean addedAUditoriumAndAirDate = false;
		do {
			while (airDateTime == null) {
				System.out.println(env.getProperty("event.date.enter"));
				String dateTime = br.readLine();

				try {
					airDateTime = LocalDateTime.parse(dateTime, DATE_TIME_FORMATTER);
				} catch (DateTimeParseException e) {
					System.out.print(env.getProperty("invalidEntryTryAgain"));
				}
			}

			Optional<Auditorium> audi = Optional.ofNullable(null);
			while (!audi.isPresent()) {
				System.out.println(env.getProperty("event.auditorium.choose"));
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
				System.out.println(env.getProperty("event.create.cannot"));
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
