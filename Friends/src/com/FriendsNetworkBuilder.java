package com;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FriendsNetworkBuilder {

	public Map<Person, Person> readAllLines(String fileLocation) {
		Map<Person, Person> personMap = new HashMap<>();
		List<String> friendsData = TxtFileReader.readFile(fileLocation);
		friendsData.stream().forEach(s -> this.createAndAddTwo(s, personMap));
		return Collections.unmodifiableMap(personMap);
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
