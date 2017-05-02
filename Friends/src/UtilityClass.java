import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

public class UtilityClass {

	public static <T> HashMap<T, Set<T>> copyMapAndRemoveFriend(Map<T, Set<T>> friendsMap, T friend, T p1) {
		HashMap<T, Set<T>> copyOfFriendsMap = new HashMap<T, Set<T>>();
		for (Entry<T, Set<T>> entry : friendsMap.entrySet()) {
			if (!(entry.getKey().equals(friend))) {
				Set<T> friends = copyAndRemoveFromSet(entry.getValue(), p1);
				friends = copyAndRemoveFromSet(friends, friend);
				copyOfFriendsMap.put(entry.getKey(), friends);
			}
		}
		return copyOfFriendsMap;
	}

	public static <T> HashMap<T, Set<T>> copyMapAndRemoveFriends(Map<T, Set<T>> friendsMap, T friend, T p1) {

		HashMap<T, Set<T>> copyOfFriendsMap = new HashMap<T, Set<T>>();
		Set<T> oneFriendSet = new HashSet<T>();
		oneFriendSet.add(friend);
		Set<T> p1Friends = null;
		if (friendsMap.get(p1) != null) {
			p1Friends = new HashSet<T>(friendsMap.get(p1));
		} else {
			p1Friends = new HashSet<T>();
		}
		p1Friends.removeAll(oneFriendSet);
		p1Friends.add(p1);
		for (Entry<T, Set<T>> entry : friendsMap.entrySet()) {
			if (!(p1Friends.contains(entry.getKey()))) {
				copyOfFriendsMap.put(entry.getKey(), copyAndRemoveFromSetAnotherSet(entry.getValue(), p1Friends));
			}
		}
		return copyOfFriendsMap;
	}

	public static <T> Set<T> copyAndRemoveFromSetAnotherSet(Set<T> set, Set<T> elements) {
		Set<T> copy = new HashSet<T>(set);
		copy.removeAll(elements);
		return copy;
	}

	public static <T> Set<T> copyAndRemoveFromSet(Set<T> set, T element) {
		Set<T> copy = new HashSet<T>(set);
		return removeFromSet(copy, element);
	}

	private static <T> Set<T> removeFromSet(Set<T> set, T element) {
		set.remove(element);
		return set;
	}

	public static <T> void printCommonFriendsFromList(Pair<T> personPair, Map<T, Set<T>> personsMap) {
		printCommonFriends(personPair.getFirst(), personPair.getSecond(), personsMap);
	}

	private static <T> void printCommonFriends(T p1, T p2, Map<T, Set<T>> personsMap) {
		if (p1.equals(p2)) {
			return;
		}
		Set<T> p1Friends = new HashSet<T>(personsMap.get(p1));
		p1Friends.retainAll(personsMap.get(p2));
		System.out.println(p1 + " and " + p2 + " common frieds are -> " + p1Friends);
	}
}
