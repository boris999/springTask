package com.epam.spring.hometask.domain;

import java.time.LocalDate;
import java.util.Collection;
import java.util.NavigableSet;
import java.util.Objects;
import java.util.TreeSet;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Table(name="CINEMA_USER")
@Entity
public class User extends DomainObject {
	@Column(name = "FIRST_NAME")
	private String firstName;
	@Column(name = "LAST_NAME")
	private String lastName;
	@Column(name = "EMAIL")
	private String email;
	@Column(name = "BIRTHDAY")
	private LocalDate birthday;
	@OneToMany
	@JoinTable(name="USER_TICKETS", joinColumns=@JoinColumn(name="USER_ID"), inverseJoinColumns=@JoinColumn(name="TICKET_ID"))
	private Collection<Ticket> tickets = new TreeSet<>();


	public boolean isLucky() {
		if (this.firstName.equalsIgnoreCase("boris")) {
			return true;
		} else {
			return false;
		}
	}

	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public LocalDate getBirthday() {
		return this.birthday;
	}

	public void setBirthday(LocalDate birthday) {
		this.birthday = birthday;
	}

	public Collection<Ticket> getTickets() {
		return this.tickets;
	}

	public void setTickets(NavigableSet<Ticket> tickets) {
		this.tickets = tickets;
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.firstName, this.lastName, this.email);
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
		User other = (User) obj;
		if (this.email == null) {
			if (other.email != null) {
				return false;
			}
		} else if (!this.email.equals(other.email)) {
			return false;
		}
		if (this.firstName == null) {
			if (other.firstName != null) {
				return false;
			}
		} else if (!this.firstName.equals(other.firstName)) {
			return false;
		}
		if (this.lastName == null) {
			if (other.lastName != null) {
				return false;
			}
		} else if (!this.lastName.equals(other.lastName)) {
			return false;
		}
		return true;
	}

}
