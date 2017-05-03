package com;

import java.util.HashMap;
import java.util.List;

public class FriendsNetwork {

	public HashMap<Person, Person> readAllLines(String fileLocation) {
		HashMap<Person, Person> personMap = new HashMap<>();
		List<String> friendsData = Reader.readFile(fileLocation);
		friendsData.parallelStream().forEach(s -> this.createAndAddTwo(s, personMap));
		return personMap;
	}

	public void createAndAddTwo(String singleLine, HashMap<Person, Person> personMap) {
		String[] line = singleLine.split("-");
		Person p1 = new Person(line[0], new Person(line[1]));
		Person p2 = new Person(line[1], new Person(line[0]));
		this.addPersonToTheMap(p1, personMap);
		this.addPersonToTheMap(p2, personMap);
	}

	public synchronized void addPersonToTheMap(Person p1, HashMap<Person, Person> personMap) {
		if (personMap.get(p1) == null) {
			personMap.put(p1, p1);
		}
		personMap.get(p1).getFriends().addAll(p1.getFriends());
	}

}
