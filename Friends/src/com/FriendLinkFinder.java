package com;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.Stack;

public class FriendLinkFinder {

	public static void main(String[] args) {
		List<String> lines = TxtFileReader.readFile(args[0]);
		FriendsNetwork friendsNetwork = new FriendsNetworkBuilder().build(lines);

		List<List<Person>> friendLink = findFriendsChain(new Person(args[1]), new Person(args[2]), friendsNetwork);
		friendLink.stream().forEach(System.out::println);
	}

	public static List<List<Person>> findFriendsChain(Person p1, Person p2, FriendsNetwork friendsNetwork) {
		List<List<Person>> friendLink = new LinkedList<>();
		// searchFriends(p1, p2, friendsNetwork, new ArrayList<Person>(), new HashSet<Person>(), friendLink, 2);//solved with recursion

		// solved with stack
		FriendLinkObject flo = new FriendLinkObject(p1, p2, friendsNetwork, new ArrayList<Person>(), new HashSet<Person>(), friendLink, 2);
		Stack<FriendLinkObject> stack = new Stack<>();
		stack.push(flo);
		searchFriendsWithstack(stack);

		return friendLink;
	}

	private static FriendLinkObject searchFriendsWithstack(Stack<FriendLinkObject> stack) {
		FriendLinkObject flo = null;
		while (!stack.isEmpty()) {
			flo = stack.pop();
			int depth = getMinsize(flo.getFriendLink());
			if (!(flo.getCurrentDepth() >= depth) && (depth != 0)) {
				Set<Person> p1Friends = flo.getFriendsNetwork().getFriendsOf(flo.getCurrentPerson());
				flo.getAlreadyPassed().add(flo.getCurrentPerson());
				flo.getAlreadyPassed().add(flo.getDestinationPerson());
				flo.getFriendsChain().add(flo.getCurrentPerson());
				if (p1Friends.contains(flo.getDestinationPerson())) {
					flo.getFriendsChain().add(flo.getDestinationPerson());
					if (flo.getFriendLink().size() != 0) {
						flo.getFriendLink().remove(0);
					}
					flo.getFriendLink().add(flo.getFriendsChain());
				} else {
					flo.setCurrentDepth(flo.getCurrentDepth() + 1);
					for (Person p : p1Friends) {
						if (!flo.getAlreadyPassed().contains(p)) {
							stack.push(new FriendLinkObject(p, flo.getDestinationPerson(), flo.getFriendsNetwork(),
									new ArrayList<>(flo.getFriendsChain()), new HashSet<>(flo.getAlreadyPassed()), flo.getFriendLink(),
									flo.getCurrentDepth()));
						}
					}
				}
			}
		}
		return flo;
	}

	@SuppressWarnings("unused")
	private static void searchFriends(Person p1, Person p2, FriendsNetwork friendsNetwork, List<Person> friendsChain,
			Set<Person> alreadyPassed, List<List<Person>> friendLink, int currentDepth) {
		int depth = getMinsize(friendLink);
		if ((currentDepth >= depth) && (depth != 0)) {
			return;
		}
		Set<Person> p1Friends = friendsNetwork.getFriendsOf(p1);
		alreadyPassed.add(p1);
		alreadyPassed.add(p2);
		friendsChain.add(p1);
		if (p1Friends.contains(p2)) {
			friendsChain.add(p2);
			if (friendLink.size() != 0) {
				friendLink.remove(0);
			}
			friendLink.add(friendsChain);
			return;// other friends are not checked as one of them is destination friend
		}
		currentDepth++;
		for (Person p : p1Friends) {
			if (!alreadyPassed.contains(p)) {
				searchFriends(p, p2, friendsNetwork, new ArrayList<>(friendsChain), new HashSet<>(alreadyPassed), friendLink, currentDepth);
			}
		}
	}

	private static <T> int getMinsize(List<List<T>> list) {
		int minSize = Integer.MAX_VALUE;
		for (List<T> l : list) {
			if (l.size() < minSize) {
				minSize = l.size();
			}
		}
		return minSize;
	}
}
