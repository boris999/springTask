package com.epam.spring.hometask.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.NavigableMap;
import java.util.NavigableSet;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * @author Yuriy_Tkach
 */
public class Event extends DomainObject {

	private String name;

	private NavigableSet<LocalDateTime> airDates = new TreeSet<>();

	private double basePrice;

	private EventRating rating;

	private NavigableMap<LocalDateTime, Auditorium> auditoriums = new TreeMap<>();
	
	private Map<LocalDateTime, Set<Ticket>> eventTickets = new HashMap<>();

	
	public void buyTicket(Ticket ticket){
		eventTickets.get(ticket.getDateTime()).add(ticket);
	}
	
	public Set<Ticket> getAllTickets(LocalDateTime dateTime){
		Set<Ticket> tickets = eventTickets.get(dateTime);
		if(tickets == null){
			tickets=Collections.emptySet();
		}
		return Collections.unmodifiableSet(tickets);
	}
	
	public void addNewEventToTicketRegister(LocalDateTime dateTime){
		eventTickets.put(dateTime, new HashSet<Ticket>());
	}
	
	/**
	 * Checks if event is aired on particular <code>dateTime</code> and assigns auditorium to it.
	 *
	 * @param dateTime
	 *            Date and time of aired event for which to assign
	 * @param auditorium
	 *            Auditorium that should be assigned
	 * @return <code>true</code> if successful, <code>false</code> if event is not aired on that date
	 */
	public boolean assignAuditorium(LocalDateTime dateTime, Auditorium auditorium) {
		if (this.airDates.contains(dateTime)) {
			this.auditoriums.put(dateTime, auditorium);
			eventTickets.put(dateTime, new HashSet<Ticket>());
			return true;
		} else {
			return false;
		}
	}

//	/**
//	 * Removes auditorium assignment from event
//	 *
//	 * @param dateTime
//	 *            Date and time to remove auditorium for
//	 * @return <code>true</code> if successful, <code>false</code> if not removed
//	 */
//	public boolean removeAuditoriumAssignment(LocalDateTime dateTime) {
//		Auditorium auditorium = this.auditoriums.get(dateTime);
//		eventTickets.remove(new Pair(dateTime, auditorium));
//		return this.auditoriums.remove(dateTime) != null;
//	}
//
//	/**
//	 * Add date and time of event air
//	 *
//	 * @param dateTime
//	 *            Date and time to add
//	 * @return <code>true</code> if successful, <code>false</code> if already there
//	 */
//	public boolean addAirDateTime(LocalDateTime dateTime) {
//		return this.airDates.add(dateTime);
//	}

	/**
	 * Adding date and time of event air and assigning auditorium to that
	 *
	 * @param dateTime
	 *            Date and time to add
	 * @param auditorium
	 *            Auditorium to add if success in date time add
	 * @return <code>true</code> if successful, <code>false</code> if already there
	 */
	public boolean addAirDateTime(LocalDateTime dateTime, Auditorium auditorium) {
		boolean result = this.airDates.add(dateTime);
		if (result) {
			this.auditoriums.put(dateTime, auditorium);
			eventTickets.put(dateTime, new HashSet<Ticket>());
		}
		return result;
	}

	/**
	 * Removes the date and time of event air. If auditorium was assigned to that date and time - the assignment is also removed
	 *
	 * @param dateTime
	 *            Date and time to remove
	 * @return <code>true</code> if successful, <code>false</code> if not there
	 */
	public boolean removeAirDateTime(LocalDateTime dateTime) {
		boolean result = this.airDates.remove(dateTime);
		if (result) {
			eventTickets.remove(dateTime);
			this.auditoriums.remove(dateTime);
			
		}
		return result;
	}

	/**
	 * Checks if event airs on particular date and time
	 *
	 * @param dateTime
	 *            Date and time to check
	 * @return <code>true</code> event airs on that date and time
	 */
	public boolean airsOnDateTime(LocalDateTime dateTime) {
		return this.airDates.stream().anyMatch(dt -> dt.equals(dateTime));
	}

	/**
	 * Checks if event airs on particular date
	 *
	 * @param date
	 *            Date to ckeck
	 * @return <code>true</code> event airs on that date
	 */
	public boolean airsOnDate(LocalDate date) {
		return this.airDates.stream().anyMatch(dt -> dt.toLocalDate().equals(date));
	}

	/**
	 * Checking if event airs on dates between <code>from</code> and <code>to</code> inclusive
	 *
	 * @param from
	 *            Start date to check
	 * @param to
	 *            End date to check
	 * @return <code>true</code> event airs on dates
	 */
	public boolean airsOnDates(LocalDate from, LocalDate to) {
		return this.airDates.stream()
				.anyMatch(dt -> (dt.toLocalDate().compareTo(from) >= 0) && (dt.toLocalDate().compareTo(to) <= 0));
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public NavigableSet<LocalDateTime> getAirDates() {
		return this.airDates;
	}

	public void setAirDates(NavigableSet<LocalDateTime> airDates) {
		this.airDates = airDates;
	}

	public double getBasePrice() {
		return this.basePrice;
	}

	public void setBasePrice(double basePrice) {
		this.basePrice = basePrice;
	}

	public EventRating getRating() {
		return this.rating;
	}

	public void setRating(EventRating rating) {
		this.rating = rating;
	}

	public NavigableMap<LocalDateTime, Auditorium> getAuditoriums() {
		return this.auditoriums;
	}

	public void setAuditoriums(NavigableMap<LocalDateTime, Auditorium> auditoriums) {
		this.auditoriums = auditoriums;
	}

	
	@Override
	public Event clone() {
		Event copy = new Event();
		copy.setId(this.getId());
		copy.setName(this.name);
		copy.setRating(this.rating);
		copy.setAirDates(this.airDates);
		copy.setAuditoriums(this.auditoriums);
		copy.setBasePrice(this.basePrice);
		return copy;

	}

	@Override
	public String toString() {
		return " " + name + " ";
	}

	
}
