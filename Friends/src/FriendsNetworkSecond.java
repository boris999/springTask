import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class FriendsNetworkSecond {
private HashSet<Person> personSet = new HashSet<>();
	
	public HashSet<Person> readAllLines(String fileLocation) {
		List<String> friendsData = Reader.readFile(fileLocation);
		friendsData.parallelStream().forEach(s->createAndAddTwo(s));
		return personSet;
	}

	private void createAndAddTwo(String singleLine) {
		String[] line = singleLine.split("-");
		Person p1 = new Person(line[0], new Person(line[1]));
		Person p2 = new Person(line[1], new Person(line[0]));
		addPersonToTheSet(p1);
		addPersonToTheSet(p2);
	}

	private synchronized void addPersonToTheSet(Person p1) {
		if(!personSet.contains(p1)){
			personSet.add(p1);
		}
		
		Person personFromTheList = getEqualObjectFromSet(personSet, p1);
//		for(Person p : personsWithLists){
//			if(p.equals(p1)){
//				p.setFriends(combineSets(p.getFriends(), p1.getFriends()));
//			}
//		}
		personFromTheList.setFriends(combineSets(personFromTheList.getFriends(), p1.getFriends()));
		
	
	}
	
	private <T> Set<T> combineSets(Set<T> first, Set<T> second){
		first.addAll(second);
		return first;
	}	
	
	public static <T> T getEqualObjectFromSet(Set<T> set, T object){
		if(!(set.contains(object))){
			return null;
		}
		return set.parallelStream().filter(element -> element.equals(object)).collect(Collectors.toList()).get(0);
	}
	
}
