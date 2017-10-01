package com.epam.spring.hometask.domain;

import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

/**
 * @author Yuriy_Tkach
 */
public class Auditorium {

	private String name;

	private long numberOfSeats;
	
	private double extraPayForVipSeat;

	private Set<Long> vipSeats = Collections.emptySet();

	public Auditorium() {
	}

	/**
	 * Counts how many vip seats are there in supplied <code>seats</code>
	 *
	 * @param seats
	 *            Seats to process
	 * @return number of vip seats in request
	 */
	public long countVipSeats(Collection<Long> seats) {
		return seats.stream().filter(seat -> this.vipSeats.contains(seat)).count();
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getNumberOfSeats() {
		return this.numberOfSeats;
	}

	public void setNumberOfSeats(long numberOfSeats) {
		this.numberOfSeats = numberOfSeats;
	}

	public Set<Long> getAllSeats() {
		return LongStream.range(1, this.numberOfSeats + 1).boxed().collect(Collectors.toSet());
	}

	public Set<Long> getVipSeats() {
		return this.vipSeats;
	}

	public void setVipSeats(Set<Long> vipSeats) {
		this.vipSeats = vipSeats;
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.name);
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
		Auditorium other = (Auditorium) obj;
		if (this.name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!this.name.equals(other.name)) {
			return false;
		}
		return true;
	}

}
