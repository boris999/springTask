package com;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class CommonFriendsFinder {

	public static void main(String[] args) {
		List<String> lines = TxtFileReader.readFile(args[0]);
		getCommonFriends(new FriendsNetworkBuilder().build(lines)).stream().forEach(System.out::println);

	}

	public static List<String> getCommonFriends(FriendsNetwork friendsNetwork) {
		Set<Person> members = friendsNetwork.getAllNetworkMembers();
		return members.stream().flatMap(m1 -> members.stream().map(m2 -> new Pair<>(m1, m2)))
				.filter(pair -> !pair.getFirst().equals(pair.getSecond()))
				.map(e -> friendsNetwork.getCommonFriendsFromPair(e))
				.collect(Collectors.toList());
	}
}
