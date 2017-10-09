package com.epam.spring.hometask.aspects;

import java.util.NavigableSet;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import com.epam.spring.hometask.domain.Event;
import com.epam.spring.hometask.domain.Ticket;
import com.epam.spring.hometask.domain.User;

@Aspect
@Component
@PropertySource("resources/other.properties")
public class LuckyWinner {
	
	//every time the bookTicket method is executed perform the checkLucky method for the user that based on some randomness 
	//will return true or false. If user is lucky, the ticketPrice changes to zero and ticket is booked, 
	//thus user pays nothing. Store the information about this lucky event into the user object (like some system messages or so) - OPTIONAL

	@Pointcut("execution(public void com.epam.spring.hometask.service.BookingService.bookTicket())")
	private void buyingTicket() {}
	
	@Around("buyingTicket()")
	public void applyLuck(ProceedingJoinPoint jp, JoinPoint joinPoint) throws Throwable{
		Event choosenEvent = (Event) joinPoint.getArgs()[1];
		User user = (User) joinPoint.getArgs()[2];
		@SuppressWarnings("unchecked")
		NavigableSet<Ticket> requestedTickets = (NavigableSet<Ticket>) joinPoint.getArgs()[3];
		long seatNumber = (Long) joinPoint.getArgs()[4];
		if(!user.isLucky()){
			jp.proceed();
		}else{
			Ticket luckyTicket = new Ticket(user, choosenEvent, choosenEvent.getAirDates().first(), seatNumber, 0);
			requestedTickets.add(luckyTicket);
			choosenEvent.buyTicket(luckyTicket);
		}
		
	}
	
	
}
