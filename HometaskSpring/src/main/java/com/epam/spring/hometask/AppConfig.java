package com.epam.spring.hometask;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.core.env.Environment;

import com.epam.spring.hometask.console.EventCreator;
import com.epam.spring.hometask.console.TicketDesk;
import com.epam.spring.hometask.console.UserCreator;
import com.epam.spring.hometask.dao.EventDAO;
import com.epam.spring.hometask.dao.UserDAO;
import com.epam.spring.hometask.domain.Auditorium;
import com.epam.spring.hometask.service.BookingService;
import com.epam.spring.hometask.service.DiscountService;
import com.epam.spring.hometask.service.EventService;
import com.epam.spring.hometask.service.UserService;

@Configuration
@PropertySources({
	@PropertySource("resources/auditorium.properties"), @PropertySource("resources/other.properties")
})
public class AppConfig {

	@Autowired
	private Environment env;
	
	@Autowired
	private UserDAO userDAO;
	
	@Autowired
	private UserCreator userCreator;
	
	@Autowired
	private EventDAO eventDAO;
	
	@Autowired
	private EventCreator eventCreator;
	
	@Autowired
	private DiscountService discountService;
	
	@Autowired
	private TicketDesk ticketDesk;

	@Bean(name = "auditorium1")
	public Auditorium getAuditoriumOne(){
		Auditorium audi1 = new Auditorium();
		audi1.setExtraPayForVipSeat(Double.parseDouble(env.getProperty("auditorium1.extraPayForVipSeat")));
		audi1.setName(env.getProperty("auditorium1.name"));
		audi1.setNumberOfSeats(Long.parseLong(env.getProperty("auditorium1.numberOfSeats")));
		Set<Long> vipSeats = new HashSet<>();
		vipSeats.add(Long.parseLong(env.getProperty("auditorium1.vipseat1")));
		vipSeats.add(Long.parseLong(env.getProperty("auditorium1.vipseat2")));
		vipSeats.add(Long.parseLong(env.getProperty("auditorium1.vipseat3")));
		vipSeats.add(Long.parseLong(env.getProperty("auditorium1.vipseat4")));
		audi1.setVipSeats(vipSeats);
		return audi1;
	}
	
	@Bean(name = "auditorium2")
	public Auditorium getAuditoriumTwo(){
		Auditorium audi1 = new Auditorium();
		audi1.setExtraPayForVipSeat(Double.parseDouble(env.getProperty("auditorium2.extraPayForVipSeat")));
		audi1.setName(env.getProperty("auditorium2.name"));
		audi1.setNumberOfSeats(Long.parseLong(env.getProperty("auditorium2.numberOfSeats")));
		Set<Long> vipSeats = new HashSet<>();
		vipSeats.add(Long.parseLong(env.getProperty("auditorium2.vipseat1")));
		vipSeats.add(Long.parseLong(env.getProperty("auditorium2.vipseat2")));
		vipSeats.add(Long.parseLong(env.getProperty("auditorium2.vipseat3")));
		audi1.setVipSeats(vipSeats);
		return audi1;
	}
	
	
	@Bean(name = "userService")
	public UserService getUserService(){
		return new UserService(userDAO, userCreator);
	}
	
	@Bean(name = "eventService")
	public EventService getEventService(){
		return new EventService(eventDAO, eventCreator);
	}

	@Bean(name = "bookingService")
	public BookingService getBookingService(){
		BookingService bService = new BookingService();
		bService.setDiscountService(discountService);
		bService.setPricePremiumForHighRating(Double.parseDouble(env.getProperty("pricePremiumForHighRating")));
		bService.setTicketDesk(ticketDesk);
		return bService;
	}

	@Bean
	public UserDAO getUserDAO(){
		return new UserDAO();
	};
	
	@Bean
	public UserCreator getUserCreator(){
		return new UserCreator();
	};
	
	@Bean
	public EventDAO getEventDAO(){
		return new EventDAO();
	};
	
	@Bean
	public EventCreator getEventCreator(){
		return new EventCreator();
	};
	
	@Bean
	public DiscountService getDiscountService(){
		return new DiscountService();
	};
	
	@Bean
	public TicketDesk getTicketDesk(){
		return new TicketDesk();
	};

}
