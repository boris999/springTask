package com;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class FriendLinkFinder {

	public static void main(String[] args) {
		Map<Person, Person> friends = new FriendsNetworkBuilder().readAllLines(args[0]);
		List<List<Person>> friendLink = findFriendsChain(new Person(args[1]), new Person(args[2]), friends,
				new ArrayList<List<Person>>());
		if (friendLink != null) {
			Collections.sort(friendLink, (l1, l2) -> l1.size() - l2.size());
			for (List<Person> pList : friendLink) {
				System.out.println(pList);
			}
		}

	}

	public static List<List<Person>> findFriendsChain(Person p1, Person p2, Map<Person, Person> friends,
			List<List<Person>> friendLink) {
		if (p1.equals(p2) || friends.get(p1).getFriends().isEmpty() ||
				friends.get(p2).getFriends().isEmpty()) {
			return friendLink;
		}
		searchFreiends(p1, p2, friends, new ArrayList<Person>(), new HashSet<Person>(), friendLink);
		return friendLink;
	}

	private static void searchFreiends(Person p1, Person p2, Map<Person, Person> friends, List<Person> friendsChain,
			Set<Person> alreadyPassed, List<List<Person>> friendLink) {
		Person p1FromMap = friends.get(p1);
		if (p1FromMap == null) {
			return;
		}
		Set<Person> p1Friends = p1FromMap.getFriends();
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
				searchFreiends(p, p2, UtilityClass.copyMapAndRemoveFriends(friends, alreadyPassed),
						new ArrayList<>(friendsChain), new HashSet<>(alreadyPassed), friendLink);
			}
		}

	}

}
