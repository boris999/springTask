package com.epam.spring.hometask.domain;

import java.util.HashSet;
import java.util.Set;

public class BookedAuditorium {
	private Auditorium auditoruim;
	private Set<Integer> bookedSeats = new HashSet<>();

	public Auditorium getAuditoruim() {
		return this.auditoruim;
	}

	public void setAuditoruim(Auditorium auditoruim) {
		this.auditoruim = auditoruim;
	}

	public Set<Integer> getBookedSeats() {
		return this.bookedSeats;
	}

	public void setBookedSeats(Set<Integer> bookedSeats) {
		this.bookedSeats = bookedSeats;
	}

}
