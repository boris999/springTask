package com;

import java.util.HashSet;
import java.util.Set;

public class Person {
	private final String name;
	private Set<Person> friends = new HashSet<>();

	public Person(String name, Person friend) {
		this(name);
		this.friends.add(friend);
	}

	public Person(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	public Set<Person> getFriends() {
		return this.friends;
	}

	public void setFriends(Set<Person> friends) {
		this.friends = friends;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = (prime * result) + ((this.name == null) ? 0 : this.name.hashCode());
		return result;
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
		Person other = (Person) obj;
		if (this.name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!this.name.equals(other.name)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return this.name;
	}

}
