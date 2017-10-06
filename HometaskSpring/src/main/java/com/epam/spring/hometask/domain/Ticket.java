package com.epam.spring.hometask.domain;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * @author Yuriy_Tkach
 */
public class Ticket extends DomainObject implements Comparable<Ticket> {

	private User user;

	private Event event;

	private LocalDateTime dateTime;

	private long seat;
	
	private double price;

	public Ticket(User user, Event event, LocalDateTime dateTime, long seat, double price) {
		this.user = user;
		this.event = event;
		this.dateTime = dateTime;
		this.seat = seat;
		this.price = price;
	}

	public User getUser() {
		return this.user;
	}

	public Event getEvent() {
		return this.event;
	}

	public LocalDateTime getDateTime() {
		return this.dateTime;
	}

	public long getSeat() {
		return this.seat;
	}

	public double getPrice() {
		return this.price;
	}
	
	public void setPrice(double price){
	this.price = price;
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.dateTime, this.event, this.seat);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (this.getClass() != obj.getClass()) {
			return false;
		}
		Ticket other = (Ticket) obj;
		if (this.dateTime == null) {
			if (other.dateTime != null) {
				return false;
			}
		} else if (!this.dateTime.equals(other.dateTime)) {
			return false;
		}
		if (this.event == null) {
			if (other.event != null) {
				return false;
			}
		} else if (!this.event.equals(other.event)) {
			return false;
		}
		if (this.seat != other.seat) {
			return false;
		}
		return true;
	}

	@Override
	public int compareTo(Ticket other) {
		if (other == null) {
			return 1;
		}
		int result = this.dateTime.compareTo(other.getDateTime());

		if (result == 0) {
			result = this.event.getName().compareTo(other.getEvent().getName());
		}
		if (result == 0) {
			result = Long.compare(this.seat, other.getSeat());
		}
		return result;
	}

	@Override
	public String toString() {
		return "Ticket [event=" + event + ", dateTime=" + dateTime + ", seat=" + seat + "]";
	}

	
}
