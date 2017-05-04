package com;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public final class UtilityClass {

	// constants - static final (public -> private)
	// static
	// object fields - final -> non final
	// constructor
	// public methods (static -> non static) getters/setters
	// private methods (static -> non static)

	private UtilityClass() {

	}

	public static <T> HashMap<T, Set<T>> copyMapAndRemoveFriend(Map<T, Set<T>> friendsMap, T friend, T p1) {
		HashMap<T, Set<T>> copyOfFriendsMap = new HashMap<>();
		for (Entry<T, Set<T>> entry : friendsMap.entrySet()) {
			if (!(entry.getKey().equals(friend))) {
				Set<T> friends = copyAndRemoveFromSet(entry.getValue(), p1);
				friends = copyAndRemoveFromSet(friends, friend);
				copyOfFriendsMap.put(entry.getKey(), friends);
			}
		}
		return copyOfFriendsMap;
	}

	public static <T> Map<T, T> copyMapAndRemoveFriends(Map<T, T> friendsMap, Set<T> toBeRemoved) {
		Map<T, T> copyOffriendsMap = new HashMap<>();
		for (Entry<T, T> entry : friendsMap.entrySet()) {
			for (T t : toBeRemoved) {
				if (!entry.getKey().equals(t)) {
					copyOffriendsMap.put(entry.getKey(), entry.getValue());
				}
			}
		}
		return copyOffriendsMap;
	}

	public static <T> Set<T> copyAndRemoveFromSetAnotherSet(Set<T> set, Set<T> elements) {
		Set<T> copy = new HashSet<>(set);
		copy.removeAll(elements);
		return copy;
	}

	public static <T> Set<T> copyAndRemoveFromSet(Set<T> set, T element) {
		Set<T> copy = new HashSet<>(set);
		return removeFromSet(copy, element);
	}

	private static <T> Set<T> removeFromSet(Set<T> set, T element) {
		set.remove(element);
		return set;
	}

}
