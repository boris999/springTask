package com;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class FriendLinkFinder {
	private static AtomicInteger depth = new AtomicInteger(0);

	public static void main(String[] args) {
		List<String> lines = TxtFileReader.readFile(args[0]);
		FriendsNetwork friendsNetwork = new FriendsNetworkBuilder().build(lines);
		List<List<Person>> friendLink = findFriendsChain(new Person(args[1]), new Person(args[2]), friendsNetwork);
		friendLink.stream()
				// .filter(l1 -> l1.size() < maxlinkLength)
				.sorted(Comparator.comparing(l1 -> l1.size()))
				.forEach(System.out::println);
	}

	public static List<List<Person>> findFriendsChain(Person p1, Person p2, FriendsNetwork friendsNetwork) {
		List<List<Person>> friendLink = new ArrayList<>();
		friendLink = quickLinkFinder(p1, p2, friendsNetwork, friendLink);
		if (friendLink != null) {
			return friendLink;
		} else {
			friendLink = new ArrayList<>();
		}
		searchFriends(p1, p2, friendsNetwork, new ArrayList<Person>(), new HashSet<Person>(), friendLink, 0);
		int shotestLink = friendLink.stream().map(e1 -> e1.size()).distinct().min(Comparator.comparing(Integer::valueOf)).get();
		friendLink = friendLink.stream().filter(e -> e.size() == shotestLink).collect(Collectors.toList());
		return friendLink;
	}

	private static List<List<Person>> quickLinkFinder(Person p1, Person p2, FriendsNetwork friendsNetwork, List<List<Person>> friendLink) {
		if (p1.equals(p2) || friendsNetwork.getFriendsOf(p1).isEmpty() || friendsNetwork.getFriendsOf(p2).isEmpty()) {
			return friendLink;
		}
		if (friendsNetwork.getFriendsOf(p1).contains(p2)) {
			List<Person> directLink = new ArrayList<>();
			directLink.add(p1);
			directLink.add(p2);
			friendLink.add(directLink);
			return friendLink;
		}
		Set<Person> friends = friendsNetwork.getFriendsOf(p1);
		friends.retainAll(friendsNetwork.getFriendsOf(p2));
		if (!friends.isEmpty()) {
			for (Person p : friends) {
				List<Person> shortLink = new ArrayList<>();
				shortLink.add(p1);
				shortLink.add(p);
				shortLink.add(p2);
				friendLink.add(shortLink);
			}
			return friendLink;
		}
		return null;
	}

	private static void searchFriends(Person p1, Person p2, FriendsNetwork friendsNetwork, List<Person> friendsChain,
			Set<Person> alreadyPassed, List<List<Person>> friendLink, int currentDepth) {
		if ((currentDepth > depth.get()) && (depth.get() != 0)) {
			return;
		}
		Set<Person> p1Friends = friendsNetwork.getFriendsOf(p1);
		alreadyPassed.add(p1);
		alreadyPassed.add(p2);
		friendsChain.add(p1);
		if (p1Friends.contains(p2)) {
			friendsChain.add(p2);
			friendLink.add(new ArrayList<>(friendsChain));
			if (depth.get() == 0) {
				depth.set(currentDepth);
			} else {
				if (depth.get() > currentDepth) {
					depth.set(currentDepth);
				}
			}
			return;// other friends are not checked as one of them is destination friend
		}
		currentDepth++;
		for (Person p : p1Friends) {
			if (!alreadyPassed.contains(p)) {
				searchFriends(p, p2, friendsNetwork, new ArrayList<>(friendsChain), new HashSet<>(alreadyPassed), friendLink, currentDepth);
			}
		}
	}
}
