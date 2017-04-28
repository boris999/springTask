import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class CommonFriendsFinder {
	private static Set<Pair<Person>> personSet = new HashSet<Pair<Person>>();

	
	public static void main(String[] args) {

		printCommonFriends(new FriendsNetwork().readAllLines(args[0]));
	}

	private synchronized static void concurrentAddToPersonSet(Pair<Person> pair) {
		personSet.add(pair);
	}


	private static void printCommonFriends(Map<Person, Set<Person>> friendsMap) {
		friendsMap.entrySet().parallelStream().forEach(e1 -> {
			friendsMap.entrySet().parallelStream().forEach(e2 -> {
				concurrentAddToPersonSet((new Pair<Person>(e1.getKey(), e2.getKey())));
			});
		});

		personSet.stream().forEach(personPair -> UtilityClass.printCommonFriendsFromList(personPair, friendsMap));
	}

}
