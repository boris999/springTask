import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.atomic.AtomicBoolean;


public class CommonFriendsFinder {
	private static Set<List<Person>> personSet = new TreeSet<List<Person>>(
			(l1, l2) -> (l1.get(0).getName().compareTo(l2.get(0).getName()) == 0
					? l1.get(1).getName().compareTo(l2.get(1).getName())
					: l1.get(0).getName().compareTo(l2.get(0).getName())));

	private static Set<List<Person>> friendLink = new TreeSet<List<Person>>((l1, l2) -> l1.size() - l2.size());

	AtomicBoolean toStop = new AtomicBoolean(false);

	public static void main(String[] args) {
		// D:\FriendsList.txt
		String fileLocation = args[0];

		List<String> friendsData = Reader.readFile(fileLocation);
		PersonBuilder pb = new PersonBuilder();
		Map<Person, Set<Person>> friendsMap = pb.readAllLines(friendsData);

		friendsChain(new Person("Vivian"), new Person("Ehtel"), friendsMap);
		System.out.println(friendLink);

		// friendsMap.entrySet().stream().forEach(e1 -> {
		// friendsMap.entrySet().stream().forEach(e2 -> {
		// // printCommonFriends(e1.getKey(), e2.getKey(), friendsMap);
		// concurrentAddToPersonSet((buildSortedListOfTwo(e1, e2)));
		// });
		// });
		//
		// personSet.stream().forEach(element ->
		// printCommonFriendsFromList(element, friendsMap));

	}

	private static Set<List<Person>> friendsChain(Person p1, Person p2, Map<Person, Set<Person>> friendsMap) {
		if (p1.equals(p2) || friendsMap.get(p1).isEmpty() || friendsMap.get(p2).isEmpty()) {
			return null;
		}
		List<Person> friendsChain = new ArrayList<Person>();
		friendsChain.add(p1);
		searchFreiends(p1, p2, copyMap(friendsMap), friendsChain);
		return friendLink;
	}

	private static <T> HashMap<T, Set<T>> copyMap(Map<T, Set<T>> friendsMap) {
		HashMap<T, Set<T>> copyOfFriendsMap = new HashMap<T, Set<T>>();
		for (Entry<T, Set<T>> entry : friendsMap.entrySet()) {
			copyOfFriendsMap.put(entry.getKey(), entry.getValue());
		}
		return copyOfFriendsMap;
	}

	private static <T> void printCommonFriendsFromList(List<T> personList, Map<T, Set<T>> personsMap) {
		printCommonFriends(personList.get(0), personList.get(1), personsMap);
	}

	private static <T> void printCommonFriends(T p1, T p2, Map<T, Set<T>> TsMap) {
		if (p1.equals(p2)) {
			return;
		}
		Set<T> p1Friends = new HashSet<T>(TsMap.get(p1));
		p1Friends.retainAll(TsMap.get(p2));
		System.out.println(p1 + " and " + p2 + " common frieds are -> " + p1Friends);
	}

	private synchronized static void concurrentAddToPersonSet(List<Person> list) {
		personSet.add(list);
	}

	private synchronized static void concurrentAddToFriendLink(List<Person> list) {
		friendLink.add(list);
	}

	private static List<Person> buildSortedListOfTwo(Entry<Person, Set<Person>> e1, Entry<Person, Set<Person>> e2) {
		List<Person> tempList = new ArrayList<Person>();
		if (e1.getKey().getName().compareTo(e2.getKey().getName()) <= 0) {
			tempList.add(e1.getKey());
			tempList.add(e2.getKey());
		} else {
			tempList.add(e2.getKey());
			tempList.add(e1.getKey());
		}
		return tempList;
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
					concurrentAddToFriendLink(new ArrayList<Person>(friendsChain));
					return;
				} else {
					for (Person p : friendsMap.get(p1)) {
						//не може да се итерира и да се променя
						friendsChain.add(p);
						searchFreiends(p, p2, removeValueAndReturnMap(friendsMap, p1, p),
								new ArrayList<Person>(friendsChain));
					}

				}
			}
		}
		return;
	}

	private static synchronized <T> HashMap<T, Set<T>> removeValueAndReturnMap(HashMap<T, Set<T>> map, T first, T second) {
		Iterator<Set<T>> it = map.values().iterator();
	    while (it.hasNext()) {
	    	Object value = it.next();
	        if(value.equals(map.get(first))){
	        	map.get(first).remove(second);
	        }
	        if(map.get(second) != null && value.equals(map.get(second))){
				map.get(second).remove(first);
			}

	    }
		
//		map.get(first).remove(second);
//		if(map.get(second) != null){
//			map.get(second).remove(first);
//		}
		//map.remove(key, map.get(key));
		return map;

	}

	
}
