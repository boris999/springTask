package com;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class CommonFriendsFinder {
	private static Set<Pair<Person>> personSet = new HashSet<>();

	public static void main(String[] args) {

		printCommonFriends(new FriendsNetwork().readAllLines(args[0]));
	}

	private synchronized static void concurrentAddToPersonSet(Pair<Person> pair) {
		personSet.add(pair);
	}

	private static void printCommonFriends(Map<Person, Person> friendsMap) {
		friendsMap.entrySet().parallelStream().forEach(e1 -> {
			friendsMap.entrySet().parallelStream().forEach(e2 -> {
				concurrentAddToPersonSet((new Pair<>(e1.getKey(), e2.getKey())));
			});
		});

		personSet.stream().forEach(personPair -> printCommonFriendsFromList(personPair, friendsMap));
	}

	public static void printCommonFriendsFromList(Pair<Person> personPair, Map<Person, Person> personsMap) {
		printCommonFriends(personPair.getFirst(), personPair.getSecond(), personsMap);
	}

	private static void printCommonFriends(Person p1, Person p2, Map<Person, Person> personsMap) {
		if (p1.equals(p2)) {
			return;
		}
		Set<Person> p1Friends = new HashSet<>(personsMap.get(p1).getFriends());
		p1Friends.retainAll(personsMap.get(p2).getFriends());
		System.out.println(p1 + " and " + p2 + " common frieds are -> " + p1Friends);
	}

}
