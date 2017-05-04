package com;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FriendLinkFinder {

	public static void main(String[] args) {
		List<String> lines = TxtFileReader.readFile(args[0]);
		FriendsNetwork friendsNetwork = new FriendsNetworkBuilder().build(lines);
		List<List<Person>> friendLink = findFriendsChain(new Person(args[1]), new Person(args[2]),
				friendsNetwork);
		friendLink.stream().sorted(Comparator.comparing(l1 -> l1.size())).forEach(System.out::println);
	}

	public static List<List<Person>> findFriendsChain(Person p1, Person p2, FriendsNetwork friendsNetwork) {
		List<List<Person>> friendLink = new ArrayList<>();
		if (p1.equals(p2) || friendsNetwork.getFriendsOf(p1).isEmpty() ||
				friendsNetwork.getFriendsOf(p2).isEmpty()) {
			return friendLink;
		}
		searchFreiends(p1, p2, friendsNetwork, new ArrayList<Person>(), new HashSet<Person>(), friendLink);
		return friendLink;
	}

	private static void searchFreiends(Person p1, Person p2, FriendsNetwork friendsNetwork, List<Person> friendsChain,
			Set<Person> alreadyPassed, List<List<Person>> friendLink) {
		Set<Person> p1Friends = friendsNetwork.getFriendsOf(p1);
		alreadyPassed.add(p1);
		alreadyPassed.add(p2);
		friendsChain.add(p1);
		if (p1Friends.contains(p2)) {
			friendsChain.add(p2);
			friendLink.add(new ArrayList<>(friendsChain));
			return;
		} else {
			for (Person p : p1Friends) {
				if (alreadyPassed.contains(p)) {
					return;
				}
				searchFreiends(p, p2, friendsNetwork, new ArrayList<>(friendsChain), new HashSet<>(alreadyPassed), friendLink);
			}
		}
	}
}
