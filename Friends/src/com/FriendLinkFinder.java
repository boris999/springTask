package com;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class FriendLinkFinder {

	private static List<List<Person>> friendLink = new ArrayList<>();

	public static void main(String[] args) {
		Map<Person, Person> friends = new FriendsNetwork().readAllLines(args[0]);
		List<List<Person>> friendLink = findFriendsChain(new Person(args[1]), new Person(args[2]), friends);
		Collections.sort(friendLink, (l1, l2) -> l1.size() - l2.size());
		for (List<Person> pList : friendLink) {
			System.out.println(pList);
		}

	}

	private static List<List<Person>> findFriendsChain(Person p1, Person p2, Map<Person, Person> friends) {
		if (p1.equals(p2) || friends.get(p1).getFriends().isEmpty() || friends.get(p2).getFriends().isEmpty()) {
			return null;
		}
		searchFreiends(p1, p2, friends, new ArrayList<Person>(), new HashSet<Person>());
		return friendLink;
	}

	private static void searchFreiends(Person p1, Person p2, Map<Person, Person> friends, List<Person> friendsChain,
			Set<Person> alreadyPassed) {
		Person p1FromSet = friends.get(p1);
		if (p1FromSet == null) {
			return;
		}
		Set<Person> p1Friends = p1FromSet.getFriends();
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
						new ArrayList<>(friendsChain), new HashSet<>(alreadyPassed));
			}
		}

	}

}
