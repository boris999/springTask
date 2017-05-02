
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class FriendsNetwork {

	private Map<Person, Set<Person>> personsWithLists = new HashMap<>();
	
	public Map<Person, Set<Person>> readAllLines(String fileLocation) {
		List<String> friendsData = Reader.readFile(fileLocation);
		friendsData.parallelStream().forEach(s->createAndAddTwo(s));
		return personsWithLists;
	}

	private void createAndAddTwo(String singleLine) {
		String[] line = singleLine.split("-");
		Person p1 = new Person(line[0], new Person(line[1]));
		Person p2 = new Person(line[1], new Person(line[0]));
		addPersonToTheMap(p1);
		addPersonToTheMap(p2);
	}

	private synchronized void addPersonToTheMap(Person p1) {
		personsWithLists.compute(p1, (k, v)->(v == null) ? k.getFriends() : combineSets(v, k.getFriends()));
		
	}
	
	private <T> Set<T> combineSets(Set<T> first, Set<T> second){
		first.addAll(second);
		return first;
	}
	
}
