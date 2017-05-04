package com;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public final class FriendsNetwork {
	Map<Person, Person> personMap;

	public FriendsNetwork(Map<Person, Person> personMap) {
		this.personMap = personMap;
	}

	public Set<Person> getAllNetworkMembers() {
		return this.personMap.keySet();
	}

	public String getCommonFriendsFromPair(Pair<Person> personPair) {
		return this.getCommonFriends(personPair.getFirst(), personPair.getSecond());
	}

	public Set<Person> getFriendsOf(Person person) {
		if (this.personMap.get(person) == null) {
			return new HashSet<>();
		}
		return new HashSet<>(this.personMap.get(person).getFriends());
	}

	private String getCommonFriends(Person p1, Person p2) {
		Set<Person> p1Friends = new HashSet<>(this.personMap.get(p1).getFriends());
		p1Friends.retainAll(this.personMap.get(p2).getFriends());
		return p1 + " and " + p2 + " common friends are -> " + p1Friends;
	}

}
