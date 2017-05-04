package com;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class FriendsNetworkBuilder {

	public FriendsNetworkBuilder() {
	}

	public FriendsNetwork build(List<String> lines) {
		Map<Person, Person> personMap = new HashMap<>();
		personMap = this.readAllLines(lines, personMap);
		return new FriendsNetwork(personMap);

	}

	private Map<Person, Person> readAllLines(List<String> lines, Map<Person, Person> personMap) {
		lines.stream().forEach(s -> this.createAndAddTwo(s, personMap));
		return personMap;
	}

	private void createAndAddTwo(String singleLine, Map<Person, Person> personMap) {
		String[] line = singleLine.split("-");
		Person p1 = new Person(line[0], new Person(line[1]));
		Person p2 = new Person(line[1], new Person(line[0]));
		this.addPersonToTheMap(p1, personMap);
		this.addPersonToTheMap(p2, personMap);
	}

	private void addPersonToTheMap(Person p1, Map<Person, Person> personMap) {
		if (personMap.get(p1) == null) {
			personMap.put(p1, p1);
		}
		personMap.get(p1).getFriends().addAll(p1.getFriends());
	}

}
