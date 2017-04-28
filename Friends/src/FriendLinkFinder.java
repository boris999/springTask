import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class FriendLinkFinder {

	private static List<List<Person>> friendLink = new ArrayList<List<Person>>();
	
	
	public static void main(String[] args) {

		friendsChain(new Person(args[1]), new Person(args[2]), new FriendsNetwork().readAllLines(args[0]));
		System.out.println(friendLink);
	}
	
	private static List<List<Person>> friendsChain(Person p1, Person p2, Map<Person, Set<Person>> friendsMap) {
		if (p1.equals(p2) || friendsMap.get(p1).isEmpty() || friendsMap.get(p2).isEmpty()) {
			return null;
		}
		List<Person> friendsChain = new ArrayList<Person>();
		friendsChain.add(p1);

		for (Person friend : friendsMap.get(p1)) {
			searchFreiends(p1, p2, UtilityClass.copyMapAndRemoveFriend(friendsMap, friend, p1), friendsChain);
		}


		return friendLink;
	}
	
	private static void searchFreiends(Person p1, Person p2, HashMap<Person, Set<Person>> friendsMap,
			List<Person> friendsChain) {
		if (friendsMap.get(p1) != null) {
			if (friendsMap.get(p1).isEmpty()) { 
				friendsMap.remove(p1);
				return;
			} else {
				if (friendsMap.get(p1).contains(p2)) {
					friendsChain.add(p2);
					friendLink.add(new ArrayList<Person>(friendsChain));
					return;
				} else {
					Set<Person> p1Friends = friendsMap.get(p1);
					friendsMap.remove(p1);
					for (Person p : p1Friends) {
						if (friendsMap.get(p) != null) {
							friendsMap.get(p).remove(p1);
						}
						friendsChain.add(p);
						searchFreiends(p, p2, friendsMap, new ArrayList<Person>(friendsChain));
					}

				}
			}
		}
		return;
	}

}
