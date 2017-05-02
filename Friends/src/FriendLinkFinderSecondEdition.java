import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FriendLinkFinderSecondEdition {

	private static List<List<Person>> friendLink = new ArrayList<List<Person>>();
	// private static Set<Person> alreadyPassed = new HashSet<Person>();

	public static void main(String[] args) {
		Set<Person> friends = new FriendsNetworkSecond().readAllLines(args[0]);
		findFriendsChain(new Person(args[1]), new Person(args[2]), friends);
		Collections.sort(friendLink, (l1, l2)->l1.size()-l2.size());
		for(List<Person> pList : friendLink){
			System.out.println(pList);
		}

	}

	private static List<List<Person>> findFriendsChain(Person p1, Person p2, Set<Person> friends) {
		if (p1.equals(p2) || FriendsNetworkSecond.getEqualObjectFromSet(friends, p1).getFriends().isEmpty()
				|| FriendsNetworkSecond.getEqualObjectFromSet(friends, p2).getFriends().isEmpty()) {
			return null;
		}
		searchFreiends(p1, p2, friends, new ArrayList<Person>(), new HashSet<Person>());
		return friendLink;
	}

	private static void searchFreiends(Person p1, Person p2, Set<Person> friends, List<Person> friendsChain,
			Set<Person> alreadyPassed) {
		Person p1FromSet = FriendsNetworkSecond.getEqualObjectFromSet(friends, p1);
		if (p1FromSet == null) {
			return;
		}
		Set<Person> p1Friends = p1FromSet.getFriends();
		alreadyPassed.add(p1);
		alreadyPassed.add(p2);
		friendsChain.add(p1);
		if (p1Friends.contains(p2)) {
			friendsChain.add(p2);
			friendLink.add(new ArrayList<Person>(friendsChain));
			return;
		} else {
			for (Person p : p1Friends) {
				if (alreadyPassed.contains(p)) {
					return;
				}
				searchFreiends(p, p2, UtilityClass.copyAndRemoveFromSetAnotherSet(friends, alreadyPassed),
						new ArrayList<Person>(friendsChain), new HashSet<Person>(alreadyPassed));
			}
		}

	}

}
